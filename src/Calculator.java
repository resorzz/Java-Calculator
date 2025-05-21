import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends JFrame implements ActionListener {
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] operatorButtons = new JButton[11];
    JButton addButton, subButton, multButton, divButton, negButton;
    JButton decButton, equButton, delButton, clrButton;
    JButton leftParButton, rightParButton; // botones parentesis
    JPanel panel;
    Font myFont = new Font("Segoe UI", Font.BOLD, 30);
    
    double num1 = 0, num2 = 0, result = 0;
    char selectedOperation; // holds mult div sub add
    
    private boolean isEqualsPressed = false;
    private double lastNumber = 0;
    private StringBuilder expression = new StringBuilder(); // Para la expresion con parentesis
    private boolean isNewExpression = true; // Para controlar si estamos empezando una nueva expresion

    public Calculator() {
        setTitle("Calculadora en Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        addPanel();
        addTextFields();
        addButtons();
        addLabels();
        textField.setText("");
        ui.applyPurpleTheme(this);
    }

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        // panel.setBackground(Color.GRAY);
        this.add(panel);
    }

    private void addTextFields() {
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        this.add(textField); // directly added into JFrame
    }

    private void addButtons() {
        addButton = new JButton("+");
        subButton = new JButton("-");
        multButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Del");
        clrButton = new JButton("C");
        negButton = new JButton("(-)");
        leftParButton = new JButton("(");
        rightParButton = new JButton(")");

        operatorButtons[0] = addButton;
        operatorButtons[1] = subButton;
        operatorButtons[2] = multButton;
        operatorButtons[3] = divButton;
        operatorButtons[4] = decButton;
        operatorButtons[5] = equButton;
        operatorButtons[6] = delButton;
        operatorButtons[7] = clrButton;
        operatorButtons[8] = negButton;
        operatorButtons[9] = leftParButton;
        operatorButtons[10] = rightParButton;

        for (int i = 0; i < operatorButtons.length; i++) {
            operatorButtons[i].addActionListener(this);
            operatorButtons[i].setFont(myFont);
            operatorButtons[i].setFocusable(false);
        }

        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].addActionListener(this);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);
        leftParButton.setBounds(50, 490, 100, 50);  // Nuevos botones de paréntesis
        rightParButton.setBounds(150, 490, 100, 50);

        this.add(delButton);
        this.add(clrButton);
        this.add(negButton);
        this.add(leftParButton);  // Añadir botones de paréntesis
        this.add(rightParButton);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(multButton);
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(equButton);
        panel.add(divButton);
    }

    private void addLabels() {
        // You can add labels here if needed
    }

    private void displayResult(double value) {
        if (value == (long) value) {
            textField.setText(String.format("%d", (long) value));
        } else {
            textField.setText(String.format("%s", value));
        }
    }
    
    // eval expresiones con parentesis
    private double evaluateExpression(String expr) {
        List<String> tokens = tokenize(expr);
        List<String> postfix = infixToPostfix(tokens);
        return evaluatePostfix(postfix);
    }

    private List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder numBuilder = new StringBuilder();
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                numBuilder.append(c);
            } else if (c == '-' && (i == 0 || expression.charAt(i-1) == '(' || 
                      !Character.isDigit(expression.charAt(i-1)))) {
                numBuilder.append(c);
            } else {
                if (numBuilder.length() > 0) {
                    tokens.add(numBuilder.toString());
                    numBuilder = new StringBuilder();
                }
                
                if (c != ' ') {
                    tokens.add(String.valueOf(c));
                }
            }
        }
        
        if (numBuilder.length() > 0) {
            tokens.add(numBuilder.toString());
        }
        
        return tokens;
    }

    private List<String> infixToPostfix(List<String> infix) {
        List<String> postfix = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        
        for (String token : infix) {
            if (isNumber(token)) {
                postfix.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek().equals("(")) {
                    stack.pop(); // Eliminar "("
                }
            } else { // Operador
                while (!stack.isEmpty() && !stack.peek().equals("(") && 
                       precedence(token) <= precedence(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }
        
        return postfix;
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }

    private double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();
        
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("División por cero");
                        }
                        stack.push(a / b);
                        break;
                }
            }
        }
        
        return stack.pop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                    expression.append(i);
                    isEqualsPressed = false;
                    isNewExpression = false;
                }
            }
            
            if (e.getSource() == decButton) {
                if (!textField.getText().contains(".")) {
                    textField.setText(textField.getText().concat("."));
                    expression.append(".");
                }
                isEqualsPressed = false;
                isNewExpression = false;
            }
            
            if (e.getSource() == addButton) {
                // Para operaciones, verificamos si es el inicio o no
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    // No hacemos nada si está vacío o solo tiene un signo negativo
                    return;
                } else {
                    num1 = Double.parseDouble(textField.getText());
                    selectedOperation = '+';
                    textField.setText("");
                    expression.append("+");
                    isEqualsPressed = false;
                }
            }
            
            if (e.getSource() == subButton) {
                // Si está vacío o estamos empezando una nueva expresión, simplemente agregamos el "-"
                if (textField.getText().isEmpty() || isNewExpression) {
                    textField.setText("-");
                    expression.append("-");
                    isEqualsPressed = false;
                    isNewExpression = false;
                } else {
                    num1 = Double.parseDouble(textField.getText());
                    selectedOperation = '-';
                    textField.setText("");
                    expression.append("-");
                    isEqualsPressed = false;
                }
            }
            
            if (e.getSource() == multButton) {
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    return;
                } else {
                    num1 = Double.parseDouble(textField.getText());
                    selectedOperation = '*';
                    textField.setText("");
                    expression.append("*");
                    isEqualsPressed = false;
                }
            }
            
            if (e.getSource() == divButton) {
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    return;
                } else {
                    num1 = Double.parseDouble(textField.getText());
                    selectedOperation = '/';
                    textField.setText("");
                    expression.append("/");
                    isEqualsPressed = false;
                }
            }
            
            if (e.getSource() == leftParButton) {
                textField.setText(textField.getText() + "(");
                expression.append("(");
                isEqualsPressed = false;
                isNewExpression = false;
            }
            
            if (e.getSource() == rightParButton) {
                textField.setText(textField.getText() + ")");
                expression.append(")");
                isEqualsPressed = false;
                isNewExpression = false;
            }
            
            if (e.getSource() == equButton) {
                if (expression.toString().contains("(") || expression.toString().contains(")")) {
                    try {
                        result = evaluateExpression(expression.toString());
                        displayResult(result);
                        expression = new StringBuilder(String.valueOf(result));
                        isEqualsPressed = true;
                        isNewExpression = true;
                    } catch (Exception ex) {
                        textField.setText("Error: Expresión inválida");
                        expression = new StringBuilder();
                        isNewExpression = true;
                    }
                } else if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                    if (!isEqualsPressed) {
                        num2 = Double.parseDouble(textField.getText());
                        
                        if (selectedOperation == '/' && num2 == 0) {
                            textField.setText("Error: Division by 0");
                            return;
                        }
                        
                        switch (selectedOperation) {
                            case '+':
                                result = num1 + num2;
                                break;
                            case '-':
                                result = num1 - num2;
                                break;
                            case '*':
                                result = num1 * num2;
                                break;
                            case '/':
                                result = num1 / num2;
                                break;
                        }
                        
                        lastNumber = num2;
                        displayResult(result);
                        num1 = result;
                        isEqualsPressed = true;
                        expression = new StringBuilder(String.valueOf(result));
                        isNewExpression = true;
                    } else {
                        num1 = Double.parseDouble(textField.getText());
                        
                        if (selectedOperation == '/' && lastNumber == 0) {
                            textField.setText("Error: Division by 0");
                            return;
                        }
                        
                        switch (selectedOperation) {
                            case '+':
                                result = num1 + lastNumber;
                                break;
                            case '-':
                                result = num1 - lastNumber;
                                break;
                            case '*':
                                result = num1 * lastNumber;
                                break;
                            case '/':
                                result = num1 / lastNumber;
                                break;
                        }
                        
                        displayResult(result);
                        num1 = result;
                        expression = new StringBuilder(String.valueOf(result));
                        isNewExpression = true;
                    }
                }
            }
            
            if (e.getSource() == clrButton) {
                textField.setText("");
                expression = new StringBuilder();
                isEqualsPressed = false;
                isNewExpression = true;
            }
            
            if (e.getSource() == delButton) {
                String string = textField.getText();
                if (!string.isEmpty()) {
                    textField.setText(string.substring(0, string.length() - 1));
                    if (expression.length() > 0) {
                        expression.deleteCharAt(expression.length() - 1);
                    }
                }
                isEqualsPressed = false;
            }
            
            if (e.getSource() == negButton) {
                if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                    double temp = Double.parseDouble(textField.getText());
                    temp *= -1;
                    displayResult(temp);
                    isEqualsPressed = false;
                    isNewExpression = false;
                    if (isNumber(expression.toString())) {
                        expression = new StringBuilder(String.valueOf(temp));
                    }
                }
            }
            
        } catch (Exception ex) {
            textField.setText("Error");
            expression = new StringBuilder();
            isNewExpression = true;
        }
    }
}
