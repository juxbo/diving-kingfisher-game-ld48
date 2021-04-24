package de.ld48.kingfisher;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.ld48.kingfisher.component.PositionComponent;
import de.ld48.kingfisher.component.VelocityComponent;
import de.ld48.kingfisher.system.MovementSystem;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class KingfisherGame extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private Engine engine;
    private Entity playerEntity;
    private MovementSystem movementSystem;

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        playerEntity = new Entity();
        playerEntity.add(new PositionComponent());
        playerEntity.add(new VelocityComponent());

        engine = new Engine();
        engine.addEntity(playerEntity);
        movementSystem = new MovementSystem();
        engine.addSystem(movementSystem);
    }

    @Override
    public void render() {
        super.render();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(Gdx.graphics.getDeltaTime());

        PositionComponent playerPositionComponent = playerEntity.getComponent(PositionComponent.class);
        VelocityComponent playerVelocityComponent = playerEntity.getComponent(VelocityComponent.class);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            playerVelocityComponent.x = -100;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            playerVelocityComponent.x = 100;
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(playerPositionComponent.x, playerPositionComponent.y, 100, 100);
        shapeRenderer.end();
    }
}