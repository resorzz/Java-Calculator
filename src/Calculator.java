import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    JTextField textField;
    JTextField expressionField;
    JButton[] numberButtons = new JButton[10];
    JButton[] operatorButtons = new JButton[9];
    JButton addButton, subButton, multButton, divButton, negButton;
    JButton decButton, equButton, delButton, clrButton;
    JPanel panel;
    Font myFont = new Font("Segoe UI", Font.BOLD, 30);
    private History history;
    public JList<String> historyList;
    private DefaultListModel<String> historyModel;
    public JButton clearHistoryButton;
    public JPanel historyPanel;
    private CalculatorFlow flow = new CalculatorFlow();

    public Calculator() {
        setTitle("Calculadora en Java");
        // Cargar y redimensionar el icono de la ventana
        ImageIcon originalIcon = new ImageIcon("icon\\icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        setIconImage(resizedIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        // Inicializar historial antes de crear componentes porque algunos lo necesitan
        history = new History();
        historyModel = new DefaultListModel<>(); // Modelo para la JList del historial
        initComponents();
    }

    private void initComponents() { // Orden importante: primero crear paneles, luego limpiar campos, finalmente aplicar tema
        createCalculatorPanel();
        createHistoryPanel();
        textField.setText("");
        expressionField.setText(""); // Asegurar que los campos empiecen vacíos
        ui.applyPurpleTheme(this); // Aplicar tema visual morado
    }

    private void createCalculatorPanel() {
        // Panel principal de la calculadora
        JPanel calculatorPanel = new JPanel();
        calculatorPanel.setLayout(null); // Layout absoluto porque necesitamos posiciones exactas para el diseño de calculadora
        calculatorPanel.setPreferredSize(new Dimension(420, 650));
        calculatorPanel.setOpaque(false);
        addPanel();
        addTextFields();
        addButtons();
        addLabels();
        // Agregar todos los componentes al panel de la calculadora
        calculatorPanel.add(expressionField);
        calculatorPanel.add(textField);
        calculatorPanel.add(panel);
        calculatorPanel.add(delButton);
        calculatorPanel.add(clrButton);
        calculatorPanel.add(negButton);
        this.add(calculatorPanel, BorderLayout.CENTER); // Ocupar el centro de la ventana principal
    }

    private void createHistoryPanel() {
        historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setPreferredSize(new Dimension(280, 650));
        // Lista del historial + Título del historial
        JLabel historyTitle = new JLabel("Historial", JLabel.CENTER);
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        historyPanel.add(historyTitle, BorderLayout.NORTH);
        // Listener para cuando seleccionas una operación del historial
        historyList = new JList<>(historyModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && historyList.getSelectedValue() != null) {
                String selectedOperation = historyList.getSelectedValue();
                String result = history.extractResult(selectedOperation);
                // Poner el resultado en la calculadora y resetear para nueva operación
                textField.setText(result);
                flow.reset();
                flow.setNewExpression(true);
                expressionField.setText("");
                // Deseleccionar después de usar
                historyList.clearSelection();
            }
        });
        JScrollPane historyScrollPane = new JScrollPane(historyList);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        // Botón para limpiar historial
        clearHistoryButton = new JButton("Limpiar Historial");
        clearHistoryButton.addActionListener(e -> {
            SoundPlayer.playSound("equ_button_soundeffect.wav");
            history.clearHistory();
            updateHistoryDisplay();
        });
        historyPanel.add(clearHistoryButton, BorderLayout.SOUTH);
        this.add(historyPanel, BorderLayout.EAST);
    }

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(50, 150, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
    }

    private void addTextFields() {
        // Campo para la expresión en curso (arriba del resultado)
        expressionField = new JTextField();
        expressionField.setBounds(50, 25, 300, 35);
        expressionField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        expressionField.setEditable(false);
        expressionField.setHorizontalAlignment(JTextField.RIGHT);
        textField = new JTextField(); // Campo principal donde se muestra el número/resultado
        textField.setBounds(50, 70, 300, 60);
        textField.setFont(myFont);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
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
        negButton = new JButton("±");
        // Array para manejar todos los botones de operadores juntos
        operatorButtons[0] = addButton;
        operatorButtons[1] = subButton;
        operatorButtons[2] = multButton;
        operatorButtons[3] = divButton;
        operatorButtons[4] = decButton;
        operatorButtons[5] = equButton;
        operatorButtons[6] = delButton;
        operatorButtons[7] = clrButton;
        operatorButtons[8] = negButton;
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
        // Posiciones absolutas para botones especiales
        negButton.setBounds(50, 480, 100, 50);
        delButton.setBounds(150, 480, 100, 50);
        clrButton.setBounds(250, 480, 100, 50);
        // Agregar botones al grid en orden específico (como calculadora real)
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(multButton);
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(equButton);
        panel.add(divButton);
    }

    private void addLabels() {
    }

    private void displayResult(double value) {
        if (value == (long) value) {
            textField.setText(String.format("%d", (long) value)); // Si es entero no muestra el .0
        } else {
            textField.setText(String.format("%s", value)); // Para formato decimal
        }
    }

    // Actualiza el campo de expresión que muestra la operación en curso
    private void updateExpressionDisplay() {
        if (flow.getSelectedOperation() != '\0' && flow.getNum1() != 0) {
            String operator = "";
            switch (flow.getSelectedOperation()) {
                case '+': operator = " + "; break;
                case '-': operator = " - "; break;
                case '*': operator = " × "; break;
                case '/': operator = " ÷ "; break;
            }
            if (flow.getNum1() == (long) flow.getNum1()) {
                expressionField.setText(String.format("%d%s", (long) flow.getNum1(), operator));
            } else {
                expressionField.setText(String.format("%s%s", flow.getNum1(), operator));
            }
        } else {
            expressionField.setText("");
        }
    }

    // Actualiza la lista visual del historial
    private void updateHistoryDisplay() {
        historyModel.clear();
        String[] operations = history.getOperations();
        for (String operation : operations) {
            historyModel.addElement(operation);
        }
    }

    // Crea el string formateado para mostrar en historial (ej: "5 + 3 = 8")
    private String createOperationString(double num1, double num2, char operation, double result) {
        String operatorSymbol = "";
        switch (operation) {
            case '+': operatorSymbol = " + "; break;
            case '-': operatorSymbol = " - "; break;
            case '*': operatorSymbol = " × "; break;
            case '/': operatorSymbol = " ÷ "; break;
        }
        String num1Str = (num1 == (long) num1) ? String.format("%d", (long) num1) : String.valueOf(num1);
        String num2Str = (num2 == (long) num2) ? String.format("%d", (long) num2) : String.valueOf(num2);
        String resultStr = (result == (long) result) ? String.format("%d", (long) result) : String.valueOf(result);
        return num1Str + operatorSymbol + num2Str + " = " + resultStr;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Si hay error, solo permite limpiar (C)
            if (flow.hasError() && e.getSource() != clrButton) {
                return;
            }

            // Manejo de botones numéricos
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    SoundPlayer.playSound("button_soundeffect.wav"); //sonidos
                    textField.setText(textField.getText().concat(String.valueOf(i)));
                    flow.getExpression().append(i);
                    flow.setEqualsPressed(false);
                    flow.setNewExpression(false);
                }
            }

            if (e.getSource() == decButton) {
                SoundPlayer.playSound("button_soundeffect.wav"); //sonidos
                if (!textField.getText().contains(".")) { // Solo un punto decimal por número
                    textField.setText(textField.getText().concat("."));
                    flow.getExpression().append(".");
                }
                flow.setEqualsPressed(false);
                flow.setNewExpression(false);
            }

            if (e.getSource() == addButton) {
                SoundPlayer.playSound("button_soundeffect.wav"); //sonidos
                // Validar que no esté vacío o solo tenga signo negativo
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    // No hacemos nada si está vacío o solo tiene un signo negativo
                    return;
                } else {
                    flow.setNum1(Double.parseDouble(textField.getText()));
                    flow.setSelectedOperation('+');
                    textField.setText("");
                    flow.getExpression().append("+");
                    flow.setEqualsPressed(false);
                    updateExpressionDisplay();
                }
            }

            if (e.getSource() == subButton) {
                SoundPlayer.playSound("button_soundeffect.wav");
                // ESPECIAL: Solo permitir número negativo si es verdaderamente una nueva expresión (sin operación seleccionada)
                if (textField.getText().isEmpty() || (flow.isNewExpression() && flow.getSelectedOperation() == '\0')) {
                    textField.setText("-");
                    flow.getExpression().append("-");
                    flow.setEqualsPressed(false);
                    flow.setNewExpression(false);
                } else {
                    // Validar que no esté vacío o solo tenga signo negativo
                    if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                        return;
                    } else {
                        flow.setNum1(Double.parseDouble(textField.getText()));
                        flow.setSelectedOperation('-');
                        textField.setText("");
                        flow.getExpression().append("-");
                        flow.setEqualsPressed(false);
                        updateExpressionDisplay();
                    }
                }
            }

            if (e.getSource() == multButton) {
                SoundPlayer.playSound("button_soundeffect.wav"); //sonidos
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    return;
                } else {
                    flow.setNum1(Double.parseDouble(textField.getText()));
                    flow.setSelectedOperation('*');
                    textField.setText("");
                    flow.getExpression().append("*");
                    flow.setEqualsPressed(false);
                    updateExpressionDisplay();
                }
            }

            if (e.getSource() == divButton) {
                SoundPlayer.playSound("button_soundeffect.wav");
                if (textField.getText().isEmpty() || textField.getText().equals("-")) {
                    return;
                } else {
                    flow.setNum1(Double.parseDouble(textField.getText()));
                    flow.setSelectedOperation('/');
                    textField.setText("");
                    flow.getExpression().append("/");
                    flow.setEqualsPressed(false);
                    updateExpressionDisplay();
                }
            }

            if (e.getSource() == equButton) {
                // NO reproducir sonido aquí - se decidirá después según si hay error o no
                if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                    if (!flow.isEqualsPressed()) {
                        flow.setNum2(Double.parseDouble(textField.getText()));
                        if (CalculatorOperations.isDivisionByZero(flow.getSelectedOperation(), flow.getNum2())) { 
                            // ERROR: Solo reproducir sonido de error
                            SoundPlayer.playSound("error_effect.wav");
                            textField.setText("Error: Division by 0");
                            flow.setHasError(true);
                            return;
                        }

                        // ÉXITO: Reproducir sonido de equals ya que no hay error
                        SoundPlayer.playSound("equ_button_soundeffect.wav");
                        double result = CalculatorOperations.performOperation(flow.getNum1(), flow.getNum2(), flow.getSelectedOperation());
                        
                        // Agregar al historial
                        String operationString = createOperationString(flow.getNum1(), flow.getNum2(), flow.getSelectedOperation(), result);
                        history.addOperation(operationString);
                        updateHistoryDisplay();
                        flow.setLastNumber(flow.getNum2()); // Guardar para repetir operación
                        displayResult(result);
                        flow.setNum1(result);
                        flow.setResult(result);
                        flow.setEqualsPressed(true);
                        flow.setExpression(new StringBuilder(String.valueOf(result)));
                        flow.setNewExpression(true);
                        expressionField.setText("");
                    } else {
                        // Presionar equals múltiples veces repite la última operación
                        flow.setNum1(Double.parseDouble(textField.getText()));
                        if (CalculatorOperations.isDivisionByZero(flow.getSelectedOperation(), flow.getLastNumber())) {
                            // ERROR: Solo reproducir sonido de error
                            SoundPlayer.playSound("error_effect.wav");
                            textField.setText("Error: Division by 0");
                            flow.setHasError(true);
                            return;
                        }

                        // ÉXITO: Reproducir sonido de equals ya que no hay error
                        SoundPlayer.playSound("equ_button_soundeffect.wav");
                        double result = CalculatorOperations.performOperation(flow.getNum1(), flow.getLastNumber(), flow.getSelectedOperation());
                        // Agregar al historial
                        String operationString = createOperationString(flow.getNum1(), flow.getLastNumber(), flow.getSelectedOperation(), result);
                        history.addOperation(operationString);
                        updateHistoryDisplay();
                        displayResult(result);
                        flow.setNum1(result);
                        flow.setResult(result);
                        flow.setExpression(new StringBuilder(String.valueOf(result)));
                        flow.setNewExpression(true);
                        expressionField.setText("");
                    }
                }
            }

            if (e.getSource() == clrButton) {
                SoundPlayer.playSound("equ_button_soundeffect.wav");
                textField.setText("");
                expressionField.setText("");
                flow.reset(); // Resetear todo el estado
            }

            if (e.getSource() == delButton) {
                SoundPlayer.playSound("button_soundeffect.wav");
                String string = textField.getText();
                if (!string.isEmpty()) {
                    textField.setText(string.substring(0, string.length() - 1)); // Borrar último carácter
                    if (flow.getExpression().length() > 0) {
                        flow.getExpression().deleteCharAt(flow.getExpression().length() - 1);
                    }
                }
                flow.setEqualsPressed(false);
            }

            if (e.getSource() == negButton) { // Botón +/- para cambiar signo
                SoundPlayer.playSound("button_soundeffect.wav");
                if (!textField.getText().isEmpty() && !textField.getText().equals("-")) {
                    double temp = Double.parseDouble(textField.getText());
                    temp = CalculatorOperations.toggleSign(temp);
                    displayResult(temp);
                    flow.setEqualsPressed(false);
                    flow.setNewExpression(false);
                    flow.setExpression(new StringBuilder(String.valueOf(temp)));
                }
            }

        } catch (Exception ex) {
            // Manejo de errores generales (formato de número inválido, etc.)
            textField.setText("Error");
            expressionField.setText("");
            flow.setExpression(new StringBuilder());
            flow.setNewExpression(true);
            flow.setHasError(true);
            SoundPlayer.playSound("error_effect.wav");
        }
    }
}
