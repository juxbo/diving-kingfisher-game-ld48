package de.ld48.kingfisher.component;

import com.badlogic.ashley.core.Component;

public class BirdStateComponent implements Component {
    public enum BirdState {
        INITIAL,
        JUMPING,
        FALLING,
        DIVING,
        ASCENDING,
        DONE;
    }

    public BirdStateComponent(BirdState state) {
        this.state = state;
    }

    public BirdState state;
    public float depth;

    public BirdState nextState() {
        // XXX: easier way pls
        switch (state) {
            case INITIAL:
                state = BirdState.JUMPING;
                break;
            case JUMPING:
                state = BirdState.FALLING;
                break;
            case FALLING:
                state = BirdState.DIVING;
                break;
            case DIVING:
                state = BirdState.ASCENDING;
                break;
            case ASCENDING:
                state = BirdState.DONE;
                break;
        }
        return state;
    }
}
