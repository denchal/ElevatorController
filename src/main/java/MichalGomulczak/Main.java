package MichalGomulczak;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        int simulationSpeed = 100;
        SwingUtilities.invokeLater(() -> new ElevatorSimulation(simulationSpeed).setVisible(true));
    }
}