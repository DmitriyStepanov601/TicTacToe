package modes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * Abstract class describing the game mode
 *
 * @author Dmitriy Stepanov
 */
public abstract class XO extends JButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}