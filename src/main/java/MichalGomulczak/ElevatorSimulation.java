package MichalGomulczak;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ElevatorSimulation extends JFrame {
    private final JTextField floorsField;
    private final JTextField elevatorsField;
    private final JTextField requestsField;
    private final JTextField manualRequestsField;
    private final JPanel gridPanel;
    private ElevatorController elevatorController;
    private Timer timer;
    private final List<ElevatorPanel> elevatorPanels;
    private List<String> csvEntries;
    private int simulationSpeed;

    public ElevatorSimulation(int simulationSpeed) {
        this.elevatorPanels = new ArrayList<>();
        this.simulationSpeed = simulationSpeed;
        setTitle("Elevator Simulation");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        inputPanel.add(new JLabel("Number Of Floors:"));
        floorsField = new JTextField();
        inputPanel.add(floorsField);

        inputPanel.add(new JLabel("Number Of Elevators:"));
        elevatorsField = new JTextField();
        inputPanel.add(elevatorsField);

        inputPanel.add(new JLabel("Automatic Requests Per Iteration:"));
        requestsField = new JTextField();
        inputPanel.add(requestsField);

        JButton startButton = new JButton("Start With Automatic Inputs");
        startButton.addActionListener(e -> startSimulation(false));
        inputPanel.add(startButton);

        JButton startManualButton = new JButton("Start With Manual Inputs");
        startManualButton.addActionListener(e -> startSimulation(true));
        inputPanel.add(startManualButton);

        manualRequestsField = new MyTextField();
        manualRequestsField.addActionListener(e -> handleManualInput(null));
        inputPanel.add(manualRequestsField);


        JButton manualInputButton = new JButton("Open Inputs From CSV");
        CSVFileChooser csvFileChooser = new CSVFileChooser(manualInputButton, this);
        inputPanel.add(manualInputButton);

        add(inputPanel, BorderLayout.SOUTH);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1, 0));
        add(gridPanel, BorderLayout.CENTER);
    }

    public void handleManualInput(String input) {
        if (input == null) {
            input = manualRequestsField.getText();
        }
        StringTokenizer st = new StringTokenizer(input, " ");

        if (st.countTokens() < 2) {
            return;
        }

        String reqType = st.nextToken();
        String floor = st.nextToken();

        if (!reqType.matches("[0-9]+|[UDud]") || !floor.matches("[0-9]+")) {
            return;
        }

        try {
            if (reqType.equalsIgnoreCase("U")) {
                elevatorController.handleOnFloorInputs(List.of(new Request(Integer.parseInt(floor), Direction.UP)));
            } else if (reqType.equalsIgnoreCase("D")) {
                elevatorController.handleOnFloorInputs(List.of(new Request(Integer.parseInt(floor), Direction.DOWN)));
            } else {
                int elevatorId = Integer.parseInt(reqType);
                int flr = Integer.parseInt(floor);
                if (elevatorId >= 0 && elevatorId < elevatorController.getElevators().size() && flr >= 0 && flr < elevatorController.getMaxFloor()) {
                    elevatorController.handleInElevatorInputs(List.of(new InteriorRequest(flr, elevatorId)));
                }
                else {
                    return;
                }
            }
            manualRequestsField.setText("");
        } catch (NumberFormatException ignored) {
            System.out.println("NumberFormatException");
        }
    }

    public void setCSVInput(List<String> inputs) {
        this.csvEntries = inputs;
    }

    public List<String> getCsvEntries() {
        return csvEntries;
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void startSimulation(boolean isManual) {
        stopTimer();
        elevatorPanels.clear();
        int floors, elevators, requests = 0;
        try {
            floors = Integer.parseInt(floorsField.getText());
            elevators = Integer.parseInt(elevatorsField.getText());
            if (!isManual) {
                requests = Integer.parseInt(requestsField.getText());
            }
        }
        catch (NumberFormatException ignored) {
            System.out.println("NumberFormatException");
            return;
        }
        elevatorController = new ElevatorController(elevators, floors);


        gridPanel.removeAll();
        for (int i = 0; i < elevators; i++) {
            ElevatorPanel panel = new ElevatorPanel(floors, elevatorController.getElevators().get(i));
            elevatorPanels.add(panel);
            gridPanel.add(panel);
        }
        gridPanel.revalidate();
        gridPanel.repaint();

        int finalRequests = requests;
        if (isManual) {
            timer = new Timer(simulationSpeed, e -> updateManualSimulation());
            timer.start();
        }
        else {
            timer = new Timer(simulationSpeed, e -> updateSimulation(finalRequests));
            timer.start();
        }
    }

    private void updateSimulation(int requests) {
        Random rand = new Random();
        int numOfInputs = (int)(requests * 0.7);
        List<Request> inputs = new ArrayList<>(numOfInputs);
        for (int i = 0; i < numOfInputs; i++) {
            inputs.add(new Request(rand.nextInt(elevatorController.getMaxFloor()+1), Direction.values()[rand.nextInt(Direction.values().length)]));
        }
        int numOfInsideRequests = requests - numOfInputs;
        List<InteriorRequest> reqs = new ArrayList<>(numOfInsideRequests);
        for (int i = 0; i < numOfInsideRequests; i++) {
            reqs.add(new InteriorRequest(rand.nextInt(elevatorController.getMaxFloor()+1), rand.nextInt(elevatorController.getElevators().size())));
        }

        elevatorController.handleInElevatorInputs(reqs);
        elevatorController.handleOnFloorInputs(inputs);
        elevatorController.step();

        for (int i = 0; i < elevatorController.getElevators().size(); i++) {
            Elevator elevator = elevatorController.getElevators().get(i);
            ElevatorPanel panel = elevatorPanels.get(i);
            panel.setCurrentFloor(elevator.getCurrentFloor());
        }
    }

    private void updateManualSimulation() {
        Thread csvReading = new Thread(new CSVHandler(this));
        csvReading.start();

        elevatorController.step();
        for (int i = 0; i < elevatorController.getElevators().size(); i++) {
            Elevator elevator = elevatorController.getElevators().get(i);
            ElevatorPanel panel = elevatorPanels.get(i);
            panel.setCurrentFloor(elevator.getCurrentFloor());
        }
    }
}