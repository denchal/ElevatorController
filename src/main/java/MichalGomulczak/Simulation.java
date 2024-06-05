package MichalGomulczak;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private ElevatorController elevatorController;
    private int steps;
    private int maxFloor;
    private int requestsPerIteration;


    public Simulation(ElevatorController elevatorController, int steps, int maxFloor, int requestsPerIteration) {
        this.elevatorController = elevatorController;
        this.steps = steps;
        this.maxFloor = maxFloor;
        this.requestsPerIteration = requestsPerIteration;
    }

    public void simulate()  {
        Random rand = new Random();
        int maxIter = steps;
        int j = 0;
        while (j < maxIter) {
            int numOfInputs = (int)(requestsPerIteration*0.7);
            List<Request> inputs = new ArrayList<>(numOfInputs);
            for (int i = 0; i < numOfInputs; i++) {
                inputs.add(new Request(rand.nextInt(maxFloor), Direction.values()[rand.nextInt(Direction.values().length)]));
            }
            int numOfInsideRequests = requestsPerIteration - numOfInputs;
            List<InteriorRequest> reqs = new ArrayList<>(numOfInsideRequests);
            for (int i = 0; i < numOfInsideRequests; i++) {
                reqs.add(new InteriorRequest(rand.nextInt(maxFloor), rand.nextInt(elevatorController.getElevators().size())));
            }


            elevatorController.handleInElevatorInputs(reqs);
            elevatorController.handleOnFloorInputs(inputs);
            elevatorController.step();


            for (Elevator elevator : elevatorController.getElevators()) {
                System.out.println(elevator.toString());
            }
            j++;
        }
    }
}
