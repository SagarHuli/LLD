package ElevatorSystem;

import ElevatorSystem.entities.Elevator;
import ElevatorSystem.enums.ElevatorState;
import ElevatorSystem.enums.Trajectory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorDemo {

    public static void main(String[] ar) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(10);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Elevator e1 = new Elevator("E1");
        Elevator e2 = new Elevator("E2");
        Elevator e3 = new Elevator("E3");

        e1.setCurrentState(ElevatorState.MOVING_UP);

        elevatorSystem.addElevator(e1);
        elevatorSystem.addElevator(e2);
        elevatorSystem.addElevator(e3);

        executor.submit(() -> elevatorSystem.handleExternalRequest(Trajectory.UP, 5));
        executor.submit(() -> elevatorSystem.handleExternalRequest(Trajectory.DOWN, 2));

        executor.shutdown();
    }
}
