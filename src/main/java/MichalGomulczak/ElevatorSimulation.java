package MichalGomulczak;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class ElevatorSimulation extends JFrame {
    private JTextField floorsField;
    private JTextField elevatorsField;
    private JTextField requestsField;
    private JTextField manualRequestsField;
    private JPanel gridPanel;
    private ElevatorController elevatorController;
    private Timer timer;
    private List<ElevatorPanel> elevatorPanels;

    public ElevatorSimulation() {
        this.elevatorPanels = new ArrayList<>();
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
        startButton.addActionListener(e -> startSimulation(Integer.parseInt(requestsField.getText())));
        inputPanel.add(startButton);

        JButton startManualButton = new JButton("Start With Manual Inputs");
        startManualButton.addActionListener(e -> startSimulation(0));
        inputPanel.add(startManualButton);

        manualRequestsField = new MyTextField();
        manualRequestsField.addActionListener(e -> handleManualInput());
        inputPanel.add(manualRequestsField);


        JButton manualInputButton = new JButton("Send Input");
        manualInputButton.addActionListener(e -> handleManualInput());
        inputPanel.add(manualInputButton);

        add(inputPanel, BorderLayout.SOUTH);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1, 0));
        add(gridPanel, BorderLayout.CENTER);
    }

    private void handleManualInput() {
        String input = manualRequestsField.getText();
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

        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void startSimulation(int requests) {
        stopTimer();
        elevatorPanels.clear();
        int floors = Integer.parseInt(floorsField.getText());
        int elevators = Integer.parseInt(elevatorsField.getText());
        elevatorController = new ElevatorController(elevators, floors);


        gridPanel.removeAll();
        for (int i = 0; i < elevators; i++) {
            ElevatorPanel panel = new ElevatorPanel(floors, elevatorController.getElevators().get(i));
            elevatorPanels.add(panel);
            gridPanel.add(panel);
        }
        gridPanel.revalidate();
        gridPanel.repaint();

        timer = new Timer(100, e -> updateSimulation(requests));
        timer.start();
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
}