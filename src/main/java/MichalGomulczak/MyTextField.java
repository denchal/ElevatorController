package MichalGomulczak;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().isEmpty()){
            int h = getHeight();
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.GRAY);
            g.drawString("(elevatorId) (destination) OR (U/D) (callingFloor) and ENTER", ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

}
