package ElevatorSystem;

import ElevatorSystem.entities.Elevator;
import ElevatorSystem.enums.ElevatorState;
import ElevatorSystem.enums.Trajectory;

import java.util.HashMap;
import java.util.Map;

public class ElevatorSystem {
    int noOfFloors;
    Map<String, Elevator> elevatorMap;
    ElevatorService elevatorService;

    public ElevatorSystem(int noOfFloors) {
        this.noOfFloors = noOfFloors;
        this.elevatorService = new ElevatorService();
        this.elevatorMap = new HashMap<>();
    }

    public void addElevator(Elevator elevator) {
        elevatorMap.put(elevator.getName(), elevator);
    }

    public void removeElevator(Elevator elevator) {
        if(elevator.getCurrentState() == ElevatorState.IDLE) {
            elevatorMap.remove(elevator.getName());
        }
        throw new RuntimeException("Elevator is moving.... Please wait");
    }


    public void handleExternalRequest(Trajectory direction, int currentFloor) {
        for(Elevator elevator : elevatorMap.values()) {
            if(elevatorService.canHandleRequest(elevator, direction, currentFloor)) {
                System.out.println("Elevator "+elevator.getName()+ " is available to board");
                try {
                    elevatorService.makeDecision(elevator, direction, currentFloor);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return;
            } else {
                System.out.println("Elevator "+elevator.getName()+ " is not available to board.. Checking if any other " +
                        "elevator is available");
            }
        }
        throw new RuntimeException("Please wait for sometime. No elevator can handle this request");
    }
}
