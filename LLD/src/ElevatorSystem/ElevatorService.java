package ElevatorSystem;

import ElevatorSystem.entities.Elevator;
import ElevatorSystem.enums.ElevatorState;
import ElevatorSystem.enums.Trajectory;

public class ElevatorService {

    public void makeDecision(Elevator elevator, Trajectory direction, int floorOfPerson) throws InterruptedException {
        System.out.println("Boarding the people");
        elevator.openDoor(elevator.getCurrentFloor());
        Thread.sleep(1000);
        elevator.getInternalControlPanel().pressButton(floorOfPerson);
    }

    public boolean canHandleRequest(Elevator elevator, Trajectory direction, int floorOfPerson) {
        if(elevator.getCurrentState() == ElevatorState.IDLE) {
            return true;
        } else if(elevator.getCurrentState() == ElevatorState.MOVING_UP &&
                direction == Trajectory.UP && elevator.getCurrentFloor() <= floorOfPerson) {
            return true;
        } else return elevator.getCurrentState() == ElevatorState.MOVING_DOWN
                && direction == Trajectory.DOWN && elevator.getCurrentFloor() >= floorOfPerson;
    }
}
