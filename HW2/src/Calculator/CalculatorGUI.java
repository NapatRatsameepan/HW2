package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {
    private JTextField display;
    private StringBuilder expression;

    public CalculatorGUI() {
        expression = new StringBuilder();
        setTitle("Modern Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Field
        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font for display
        display.setPreferredSize(new Dimension(400, 60));  // Larger height for the text box
        add(display, BorderLayout.NORTH);

        // Panel for Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // Adjusted to 5 rows and 4 columns
        panel.setBackground(Color.BLACK); // Dark background for button panel

        // Define buttons in the desired layout
        String[] buttons = {
            "(", ")", "", "←",
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font for buttons
            button.setFocusPainted(false); // Remove focus border around text
            button.setBackground(Color.DARK_GRAY); // Dark gray background for buttons
            button.setForeground(Color.WHITE); // White text color for better contrast

            // Highlight the "=" button
            if (text.equals("=")) {
                button.setBackground(new Color(58, 175, 222)); // Color for #3AAFDE
                button.setForeground(Color.WHITE);
            }
            
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = ((JButton) e.getSource()).getText();

            switch (command) {
                case "=":
                    String result = Calculator.getInstance().calculate(expression.toString());
                    display.setText(result);
                    expression.setLength(0); // Clear expression
                    expression.append(result); // Use result for further calculation
                    break;
                case "←":
                    if (expression.length() > 0) {
                        expression.setLength(expression.length() - 1);
                        display.setText(expression.toString());
                    }
                    break;
                default:
                    expression.append(command);
                    display.setText(expression.toString());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalculatorGUI calculatorGUI = new CalculatorGUI();
                calculatorGUI.setVisible(true);
            }
        });
    }
}
