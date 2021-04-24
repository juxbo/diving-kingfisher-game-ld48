package de.ld48.kingfisher.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.ld48.kingfisher.component.PositionComponent;
import de.ld48.kingfisher.component.VelocityComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent.class, VelocityComponent.class).get()
        );
    }

    @Override
    public void update(float deltaTime) {
        for (Entity e : entities) {
            PositionComponent positionComponent = pm.get(e);
            VelocityComponent velocityComponent = vm.get(e);

            positionComponent.x += velocityComponent.x * deltaTime;
            positionComponent.y += velocityComponent.y * deltaTime;
        }
    }
}
