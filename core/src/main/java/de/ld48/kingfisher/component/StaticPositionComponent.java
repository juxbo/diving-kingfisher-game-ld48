package de.ld48.kingfisher.component;

import com.badlogic.ashley.core.Component;

public class StaticPositionComponent implements Component {
    public float x;
    public float y;

    public StaticPositionComponent() {
    }

    public StaticPositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
