
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class calculator extends JFrame implements ActionListener {
    JTextField display;
    String operator = "";
    double num1, num2, result;
    boolean isOperatorClicked = false;

    public calculator() {
        setTitle("Scientific Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        display = new JTextField();
        display.setFont(new Font("Segoe UI", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(7, 4, 5, 5));
        String[] buttons = {
                "C", "←", "±", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", "π",
                "sin", "cos", "tan", "√",
                "x^2", "x^x", "1/x", "!",
                "log", "ln", "e", "%"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        DecimalFormat df = new DecimalFormat("0.######");

        try {
            switch (cmd) {
                case "C" -> display.setText("");
                case "←" -> {
                    String current = display.getText();
                    if (!current.isEmpty())
                        display.setText(current.substring(0, current.length() - 1));
                }
                case "±" -> {
                    double value = Double.parseDouble(display.getText());
                    display.setText(df.format(-value));
                }
                case "+" -> processOperator("+");
                case "-" -> processOperator("-");
                case "*" -> processOperator("*");
                case "/" -> processOperator("/");
                case "=" -> {
                    num2 = Double.parseDouble(display.getText());
                    switch (operator) {
                        case "+" -> result = num1 + num2;
                        case "-" -> result = num1 - num2;
                        case "*" -> result = num1 * num2;
                        case "/" -> result = num2 != 0 ? num1 / num2 : Double.NaN;
                        case "x^x" -> result = Math.pow(num1, num2);
                    }
                    display.setText(df.format(result));
                    isOperatorClicked = false;
                }
                case "." -> display.setText(display.getText() + ".");
                case "π" -> display.setText(String.valueOf(Math.PI));
                case "e" -> display.setText(String.valueOf(Math.E));
                case "%" -> {
                    double val = Double.parseDouble(display.getText());
                    display.setText(df.format(val / 100));
                }
                case "sin" -> display.setText(df.format(Math.sin(Math.toRadians(Double.parseDouble(display.getText())))));
                case "cos" -> display.setText(df.format(Math.cos(Math.toRadians(Double.parseDouble(display.getText())))));
                case "tan" -> display.setText(df.format(Math.tan(Math.toRadians(Double.parseDouble(display.getText())))));
                case "√" -> display.setText(df.format(Math.sqrt(Double.parseDouble(display.getText()))));
                case "x^2" -> {
                    double val = Double.parseDouble(display.getText());
                    display.setText(df.format(val * val));
                }
                case "x^x" -> processOperator("x^x");
                case "1/x" -> {
                    double val = Double.parseDouble(display.getText());


                    display.setText(val != 0 ? df.format(1 / val) : "Error");
                }
                case "!" -> {
                    int n = Integer.parseInt(display.getText());
                    display.setText(n >= 0 ? String.valueOf(factorial(n)) : "Error");
                }
                case "log" -> display.setText(df.format(Math.log10(Double.parseDouble(display.getText()))));
                case "ln" -> display.setText(df.format(Math.log(Double.parseDouble(display.getText()))));
                default -> display.setText(display.getText() + cmd); // Numbers
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private void processOperator(String op) {
        num1 = Double.parseDouble(display.getText());
        operator = op;
        isOperatorClicked = true;
        display.setText("");
    }

    private long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) fact *= i;
        return fact;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(calculator::new);
    }
}