package MichalGomulczak;

import java.util.*;

public class ElevatorController {
    private List<Elevator> elevators;
    private int maxFloor;

    public ElevatorController(int size, int numOfFloors) {
        this.elevators = new ArrayList<Elevator>(size);
        this.maxFloor = numOfFloors-1;

        for (int i = 0; i < size; i++) {
            elevators.add(new Elevator(i,0, this));
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
            int i = req.getFloor();
            if (counts[i] < 2) {
                Direction requestedDirection = req.getDirection();

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
    }

    private int[] getFloorCounts() {
        int[] counts = new int[maxFloor+1];
        for (Elevator elevator : elevators) {
            List<Integer> route = elevator.getRoute();
            for (int i = 0; i < route.size(); i++) {
                counts[route.get(i)]++;
            }
        }
        return counts;
    }

    private static int getDistance(Elevator elevator, Direction requestedDirection, int i) {
        int distance;
        Direction elevatorDirection = elevator.getCurrentDirection();

        if ((elevatorDirection == Direction.EITHER || elevatorDirection == requestedDirection) && elevator.getCurrentDestination() >= i) {
            distance = Math.abs(elevator.getCurrentFloor() - i) ;
        }
        else {
            distance = Math.abs(elevator.getCurrentDestination() - elevator.getCurrentFloor()) + Math.abs(elevator.getCurrentDestination() - i);
        }
        return distance;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public void handleInElevatorInputs(List<InteriorRequest> input) {
        for (InteriorRequest request : input) {
            Elevator elevator = elevators.get(request.getElevator());
            elevator.addToRoute(request.getFloor());
        }
    }
}
