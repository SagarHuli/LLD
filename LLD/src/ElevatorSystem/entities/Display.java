package ElevatorSystem.entities;

import ElevatorSystem.enums.ElevatorState;

public abstract class Display {
    public int getFloor() {
        return 0;
    }
    ElevatorState getCurrentState() {
        return ElevatorState.MOVING_UP;
    }
}
