package MichalGomulczak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Elevator {

    private final int elevatorId;
    private int currentFloor;
    private final List<Integer> route;
    private boolean isBeingBoarded;
    private Direction currentDirection;
    private int currentDestination;
    private int timeWaited;

    public Elevator(int elevatorId, int currentFloor) {
        this.elevatorId = elevatorId;
        this.currentFloor = currentFloor;
        this.route = new ArrayList<>();
        this.isBeingBoarded = false;
        this.currentDirection = Direction.UP;
        this.currentDestination = 0;
        this.timeWaited = 0;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public List<Integer> getRoute() {
        return route;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getCurrentDestination() {
        return currentDestination;
    }

    public boolean isBeingBoarded() {
        return isBeingBoarded;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "elevatorId=" + elevatorId +
                ", currentFloor=" + currentFloor +
                ", route=" + route +
                ", isBeingBoarded=" + isBeingBoarded +
                ", currentDirection=" + currentDirection +
                ", currentDestination=" + currentDestination +
                ", timeWaited=" + timeWaited +
                '}';
    }

    public void addToRoute(int floor) {
        if (!route.contains(floor)) {
            route.add(floor);
        }
    }

    public void calcCurrentDestination() {
        if (!route.isEmpty()) {
            if (currentDirection == Direction.UP) {
                currentDestination = Collections.max(route);
            }
            else if (currentDirection == Direction.DOWN) {
                currentDestination = Collections.min(route);
            }
        }
    }

    public void step() {
        calcCurrentDestination();
        if (!isBeingBoarded) {
            if (!route.isEmpty()) {
                if (currentDirection == Direction.EITHER) {
                    if (route.getFirst() > currentFloor) {
                        currentDirection = Direction.UP;
                    }
                    else {
                        currentDirection = Direction.DOWN;
                    }
                }
                if (currentDirection == Direction.UP && Collections.max(route) > currentFloor) {
                    currentFloor++;
                }
                else if (currentDirection == Direction.DOWN && Collections.min(route) < currentFloor) {
                    currentFloor--;
                }

                if (route.contains(currentFloor)) {
                    isBeingBoarded = true;
                    route.remove((Object)currentFloor);
                }
                if (!route.isEmpty()) {
                    if (currentDirection == Direction.UP && Collections.max(route) <= currentFloor) {
                        currentDirection = Direction.DOWN;
                    }
                    else if (currentDirection == Direction.DOWN && Collections.min(route) >= currentFloor) {
                        currentDirection = Direction.UP;
                    }
                }
            }
            else {
                currentDirection = Direction.EITHER;
            }
        }
        else {
            if (timeWaited >= 3) {
                isBeingBoarded = false;
                timeWaited = 0;
            }
            else {
                timeWaited++;
            }
        }
    }
}
