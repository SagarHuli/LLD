package ElevatorSystem.entities;

import ElevatorSystem.observer.ElevatorObserver;

import java.util.concurrent.*;

public class InternalControlPanel implements ElevatorObserver {
    Elevator elevator;
    ScheduledExecutorService executor;

    public InternalControlPanel(Elevator elevator) {
        this.elevator = elevator;
        this.executor = Executors.newScheduledThreadPool(2);
        simulateAction();
    }

    public void pressButton(int floor) {
        if(elevator.getCurrentFloor() < floor) {
            elevator.moveUp(floor);
        } else if(elevator.getCurrentFloor() > floor) {
            elevator.moveDown(floor);
        }
    }

    public void simulateAction() {
        System.out.println("simulate action called");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ScheduledFuture<?> future =  executor.scheduleWithFixedDelay(() -> {
            int floorToGoTo = (int) (Math.random()*10) % 10;
            System.out.println("Internal passenger pressed "+floorToGoTo+" on elevator "+elevator.getName());
            this.pressButton(floorToGoTo);
        }, 3, 1, TimeUnit.SECONDS);

        //Stop simulation after 5s
        executor.schedule(() -> {
            future.cancel(true); // true attempts to interrupt if running
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void update(Elevator elevator) {
        System.out.println("Elevator "+elevator.getName()+ " is at floor "+elevator.getCurrentFloor() +
                " and is " + elevator.getCurrentState());
    }
}
