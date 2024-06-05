package MichalGomulczak;

import java.util.List;

public class CSVHandler implements Runnable{
    private ElevatorSimulation elevatorSimulation;

    public CSVHandler(ElevatorSimulation elevatorSimulation) {
        this.elevatorSimulation = elevatorSimulation;
    }

    @Override
    public void run() {
        List<String> inputs = elevatorSimulation.getCsvEntries();
        elevatorSimulation.setCSVInput(null);
        long expectedTime = System.currentTimeMillis();
        if (inputs != null) {
            for (String input : inputs) {
                elevatorSimulation.handleManualInput(input);
                while(System.currentTimeMillis() < expectedTime){
                    //Waiting 333 ms
                }
                expectedTime += 333;
            }
        }
    }
}
