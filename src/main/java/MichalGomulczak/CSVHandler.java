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
        long expectedtime = System.currentTimeMillis();
        if (inputs != null) {
            for (String input : inputs) {
                elevatorSimulation.handleManualInput(input);
                while(System.currentTimeMillis() < expectedtime){
                    //Empty Loop
                }
                expectedtime += 333;
            }
        }
    }
}
