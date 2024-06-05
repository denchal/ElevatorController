package MichalGomulczak;

import java.util.*;

public class ElevatorController {
    private final List<Elevator> elevators;
    private final int maxFloor;

    public ElevatorController(int size, int numOfFloors) {
        this.elevators = new ArrayList<>(size);
        this.maxFloor = numOfFloors-1;

        for (int i = 0; i < size; i++) {
            elevators.add(new Elevator(i,0));
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void handleOnFloorInputs(List<Request> input) {
        int[] counts = getFloorCounts();
        for (Request req : input) {
            int minDistance = Integer.MAX_VALUE;
            Elevator closestElevator = null;
            int i = req.floor();
            try {
                if (counts[i] < 1) {
                    Direction requestedDirection = req.direction();

                    for (Elevator elevator : elevators) {
                        int distance = getDistance(elevator, requestedDirection, i);

                        if (distance < minDistance) {
                            minDistance = distance;
                            closestElevator = elevator;
                        }
                    }
                    if (closestElevator != null && minDistance != 0) {
                        closestElevator.addToRoute(i);
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
    }

    public void handleInElevatorInputs(List<InteriorRequest> input) {
        for (InteriorRequest request : input) {
            Elevator elevator = elevators.get(request.elevator());
            elevator.addToRoute(request.floor());
        }
    }

    private int[] getFloorCounts() {
        int[] counts = new int[maxFloor+1];
        for (Elevator elevator : elevators) {
            List<Integer> route = elevator.getRoute();
            for (Integer integer : route) {
                counts[integer]++;
            }
        }
        return counts;
    }

    private static int getDistance(Elevator elevator, Direction requestedDirection, int i) {
        int distance;
        Direction elevatorDirection = elevator.getCurrentDirection();
        int currentDestination = elevator.getCurrentDestination();
        int currentFloor = elevator.getCurrentFloor();
        if ((elevatorDirection == Direction.EITHER || elevatorDirection == requestedDirection) && currentDestination >= i) {
            distance = Math.abs(currentFloor - i);
        }
        else {
            distance = Math.abs(currentDestination - currentFloor) + Math.abs(currentDestination - i);
        }
        return distance;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }
}
