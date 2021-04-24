package de.ld48.kingfisher.component;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public PositionComponent() {
    }

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x;
    public float y;
}
