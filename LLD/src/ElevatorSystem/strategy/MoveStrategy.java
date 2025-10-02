package ElevatorSystem.strategy;

import ElevatorSystem.enums.Trajectory;

public interface MoveStrategy {
    Trajectory decideTrajectory(int floor);
}
