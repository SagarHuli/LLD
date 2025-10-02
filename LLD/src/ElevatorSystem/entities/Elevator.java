package ElevatorSystem.entities;

import ElevatorSystem.enums.Door;
import ElevatorSystem.enums.ElevatorState;
import ElevatorSystem.observer.ElevatorSubject;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Elevator extends ElevatorSubject {
    String name;
    ElevatorState currentState;
    AtomicInteger currentFloor;
    Queue<Integer> floorsToVisit;
    Door doorState;
    InternalControlPanel internalControlPanel;


    public Elevator(String name) {
        this.name = name;
        this.currentState = ElevatorState.IDLE;
        this.currentFloor = new AtomicInteger(0);
        this.floorsToVisit = new PriorityQueue<>(Comparator.comparingInt(a -> a));
        this.doorState = Door.CLOSED;
        this.internalControlPanel = new InternalControlPanel(this);
        this.addObserver(this.internalControlPanel);
    }

    public synchronized void moveUp(int nextFloor) {
        this.floorsToVisit.add(nextFloor);
        this.currentState = ElevatorState.MOVING_UP;
        while(!floorsToVisit.isEmpty()) {
            try {
                if(this.currentFloor.get() > 10) {
                    System.out.println("Elevator "+this.name+" is at top floor");
                    break;
                }
                this.currentFloor.incrementAndGet();
                notifyObservers();
                if(this.currentFloor.get() == floorsToVisit.peek()) {
                    floorsToVisit.poll();
                    System.out.println("Elevator " + this.name + " has reached floor " + this.currentFloor);
                    System.out.println("Opening the doors!! for elevator " + this.name);
                    this.openDoor(this.currentFloor.get());
                    Thread.sleep(200);
                    this.closeDoor();
                    System.out.println("Doors closing for " + this.name);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Error while moving to next floor");
            }
        }
    }

    public synchronized void moveDown(int nextFloor) {
        this.floorsToVisit.add(nextFloor);
        this.currentState = ElevatorState.MOVING_DOWN;
        while(!floorsToVisit.isEmpty()) {
            try {
                this.currentFloor.decrementAndGet();
                notifyObservers();
                if(this.currentFloor.get() == floorsToVisit.peek()) {
                    floorsToVisit.poll();
                    System.out.println("Elevator "+this.name+ " has reached floor "+this.currentFloor);
                    System.out.println("Opening the doors!! for elevator "+this.name);
                    this.openDoor(this.currentFloor.get());
                    Thread.sleep(200);
                    this.closeDoor();
                    System.out.println("Doors closing for "+this.name);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void simulateInternalAction() {

    }

    public void openDoor(int floor) {
        if(currentFloor.get() == floor && Door.CLOSED.equals(doorState)) {
            System.out.println("Door opened on floor " + floor);
            this.doorState = Door.OPEN;
            return;
        }
        throw new RuntimeException("Door can't be opened on this floor");
    }

    public void closeDoor() {
        if(Door.OPEN.equals(doorState)) {
            System.out.println("Door closed");
            this.doorState = Door.CLOSED;
            return;
        }
        throw new RuntimeException("Door can't be closed on this floor");
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }

    public ElevatorState getCurrentState() {
        return currentState;
    }

    public String getName() {
        return name;
    }

    public InternalControlPanel getInternalControlPanel() {
        return this.internalControlPanel;
    }

    public void setCurrentState(ElevatorState state) {
        this.currentState = state;
    }

}
