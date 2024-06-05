package MichalGomulczak;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new ElevatorSimulation().setVisible(true));
    }
}