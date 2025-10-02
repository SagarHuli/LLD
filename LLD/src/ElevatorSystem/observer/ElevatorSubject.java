package ElevatorSystem.observer;

import ElevatorSystem.entities.Elevator;

import java.util.ArrayList;
import java.util.List;

public abstract class ElevatorSubject {
    List<ElevatorObserver> observers = new ArrayList<>();

    public void addObserver(ElevatorObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(ElevatorObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for(ElevatorObserver observer : this.observers) {
            observer.update((Elevator) this);
        }
    }
}
