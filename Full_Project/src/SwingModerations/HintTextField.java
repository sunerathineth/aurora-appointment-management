
package SwingModerations;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;



public class HintTextField extends JPanel {

    private final JTextField textField;
    private final JLabel hintLabel;

    public HintTextField(String hint) {
        setLayout(new OverlayLayout(this));

        textField = new JTextField();
        textField.setForeground(Color.BLACK);
        textField.setCaretColor(Color.BLACK);

        hintLabel = new JLabel(hint);
        hintLabel.setForeground(Color.LIGHT_GRAY);
        hintLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        hintLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        add(textField);
        add(hintLabel);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                hintLabel.setVisible(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                hintLabel.setVisible(textField.getText().isEmpty());
            }
        });

        hintLabel.setVisible(textField.getText().isEmpty());
    }

    public JTextField getTextField() {
        return textField;
    }
}
