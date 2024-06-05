package MichalGomulczak;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileChooser extends JFrame {
    private List<String> csvEntries;
    private ElevatorSimulation simulation;

    public CSVFileChooser(JButton button, ElevatorSimulation simulation) {
        button.addActionListener(e -> handleCSVInput());
        this.simulation =  simulation;
    }

    private void handleCSVInput(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            csvEntries = readCSV(filePath);

            simulation.setCSVInput(csvEntries);
        }
    }

    private List<String> readCSV(String filePath) {
        List<String> entries = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    entries.add(part.trim());
                }
            }
        } catch (IOException ignored) {

        }

        return entries;
    }
}

