package de.ld48.kingfisher.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import de.ld48.kingfisher.component.BirdStateComponent;
import de.ld48.kingfisher.component.PositionComponent;
import de.ld48.kingfisher.component.VelocityComponent;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    private static float GRAVITY = 1;
    private static float WATER_LEVEL = 200;
    private static float DRAG = 1f;

    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private final ComponentMapper<BirdStateComponent> bm = ComponentMapper.getFor(BirdStateComponent.class);

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(
                Family.all(PositionComponent.class, VelocityComponent.class, BirdStateComponent.class).get()
        );
    }

    @Override
    public void update(float deltaTime) {
        for (Entity e : entities) {
            PositionComponent positionComponent = pm.get(e);
            VelocityComponent velocityComponent = vm.get(e);
            BirdStateComponent stateComponent = bm.get(e);

            System.out.println(stateComponent.state);

            // TODO: MOVE TO NEW SYSTEM TO PLAYER MOVES SEPARATELY FROM OTHER ENTITIES
            // TODO: ADD CURRENT IN X DIRECTION FOR PLAYER
            switch (stateComponent.state) {
                case INITIAL:
                    // DON'T FALL
                    break;
                case JUMPING:
                case FALLING:
                    // ACCELERATE IN NEGATIVE Y DIRECTION BY GRAVITY
                    velocityComponent.y -= GRAVITY;
                    if(positionComponent.y < WATER_LEVEL) {
                        stateComponent.nextState();
                    }
                    break;
                case DIVING:
                    // DECELERATE Y BY WATER DRAG
                    velocityComponent.y += DRAG;
                    if(velocityComponent.y > 0) {
                        // STOP MOVEMENT
                        velocityComponent.y = 0;
                        stateComponent.nextState();
                        stateComponent.depth = positionComponent.y;
                    }
                    break;
                case ASCENDING:
                    if(positionComponent.y > stateComponent.depth) {
                        velocityComponent.y -= DRAG;
                    } else {
                        velocityComponent.y += DRAG*2;
                    }
                    if(positionComponent.y >= WATER_LEVEL) {
                        stateComponent.nextState();
                    }
                    break;
                case DONE:
                    velocityComponent.x = 0;
                    velocityComponent.y = 0;
            }

            positionComponent.x += velocityComponent.x * deltaTime;
            positionComponent.y += velocityComponent.y * deltaTime;
        }
    }
}
