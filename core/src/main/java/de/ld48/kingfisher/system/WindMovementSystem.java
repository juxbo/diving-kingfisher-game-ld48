package de.ld48.kingfisher.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.ld48.kingfisher.component.PositionComponent;
import de.ld48.kingfisher.component.StaticPositionComponent;

import java.util.concurrent.ThreadLocalRandom;

public class WindMovementSystem extends EntitySystem {
    private final ComponentMapper<StaticPositionComponent> spm = ComponentMapper.getFor(StaticPositionComponent.class);
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final int maxMovement = 20;
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(StaticPositionComponent.class, PositionComponent.class).get()
        );
    }

    @Override
    public void update(float deltaTime) {
        float movementY = maxMovement * deltaTime;
        boolean up = ThreadLocalRandom.current().nextInt(3) == 0;

        for (Entity e : entities) {
            PositionComponent positionComponent = pm.get(e);
            StaticPositionComponent staticPositionComponent = spm.get(e);

            if (up) {
                positionComponent.y += movementY;
            } else {
                positionComponent.y -= movementY;
            }
            if (positionComponent.y < staticPositionComponent.y - maxMovement) {
                positionComponent.y = staticPositionComponent.y - maxMovement;
            }
            if (positionComponent.y > staticPositionComponent.y + maxMovement) {
                positionComponent.y = staticPositionComponent.y + maxMovement;
            }
        }
    }
}
