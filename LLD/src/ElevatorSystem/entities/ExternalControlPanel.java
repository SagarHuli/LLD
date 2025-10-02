package ElevatorSystem.entities;

import ElevatorSystem.ElevatorSystem;
import ElevatorSystem.enums.Trajectory;

public class ExternalControlPanel {
    ElevatorSystem elevatorSystem;
    int currentFloor;

    public void pressButton(Trajectory trajectory) {
        this.elevatorSystem.handleExternalRequest(trajectory, currentFloor);
    }
}
