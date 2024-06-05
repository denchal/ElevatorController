package MichalGomulczak;

import javax.swing.*;
import java.awt.*;

class ElevatorPanel extends JPanel {
    private int currentFloor;
    private int maxFloor;
    private Elevator elevator;

    public ElevatorPanel(int maxFloor, Elevator elevator) {
        this.maxFloor = maxFloor;
        this.currentFloor = 0;
        this.elevator = elevator;
        setBackground(Color.BLACK);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int height = getHeight();
        int stepHeight = height / maxFloor;
        int y = height - (currentFloor + 1) * stepHeight;
        Font font = new Font("Arial", Font.BOLD, Math.min((int)(getWidth()*0.66), 80));
        setFont(font);
        switch(elevator.getCurrentDirection()) {
            case UP: g.setColor(Color.GREEN); break;
            case DOWN: g.setColor(Color.RED); break;
            case EITHER: g.setColor(Color.BLUE); break;
        }
        if (elevator.isBeingBoarded()) {
            g.setColor(Color.YELLOW);
        }
        g.fillRect(10, y, getWidth() - 20, stepHeight);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(currentFloor), (int)(getWidth()*0.15) , y+stepHeight);
    }
}
