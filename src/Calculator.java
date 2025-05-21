import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] operatorButtons = new JButton[9];
    JButton addButton, subButton, multButton, divButton, negButton;
    JButton decButton, equButton, delButton, clrButton;
    JPanel panel;

    Font myFont = new Font("Segoe UI",Font.BOLD, 30);

    double num1=0, num2=0, result=0;
    char selectedOperation; //holds mult div sub add

    public Calculator() { 
        setTitle("Calculadora en Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 550);
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
    }

    private void addPanel() {
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300); 
        panel.setLayout(new GridLayout(4, 4,10,10));
        //panel.setBackground(Color.GRAY);
        this.add(panel);
    }

    private void addTextFields() {
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(myFont);
        textField.setEditable(false);
        this.add(textField); // directly added into JFrame
    }

    private void addButtons() {
        addButton = new JButton("+");
        subButton = new JButton("-");
        multButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Delete");
        clrButton = new JButton("Clear");
        negButton = new JButton("(-)");

        operatorButtons[0] = addButton;
        operatorButtons[1] = subButton;
        operatorButtons[2] = multButton;
        operatorButtons[3] = divButton;
        operatorButtons[4] = decButton;
        operatorButtons[5] = equButton;
        operatorButtons[6] = delButton;
        operatorButtons[7] = clrButton;
        operatorButtons[8] = negButton;

        for (int i=0; i<operatorButtons.length; i++) {
            operatorButtons[i].addActionListener(this);
            operatorButtons[i].setFont(myFont);
            operatorButtons[i].setFocusable(false);
        }

        for (int i=0; i<numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].addActionListener(this);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);
        this.add(delButton);
        this.add(clrButton);
        this.add(negButton);

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=0; i<numberButtons.length; i++) {
            if(e.getSource() == numberButtons[i]) {
                textField.setText (textField.getText().concat(String.valueOf(i)));
            }
        }  
        
        if(e.getSource() == addButton) {
            num1 = Double.parseDouble(textField.getText());
            selectedOperation ='+';
            textField.setText("");
        }

        if(e.getSource() == subButton) {
            num1 = Double.parseDouble(textField.getText());
            selectedOperation ='-';
            textField.setText("");
        }

        if(e.getSource() == multButton) {
            num1 = Double.parseDouble(textField.getText());
            selectedOperation ='*';
            textField.setText("");
        }

        if(e.getSource() == divButton) {
            num1 = Double.parseDouble(textField.getText());
            selectedOperation ='/';
            textField.setText("");
        }

        if(e.getSource()==equButton) {
            num2=Double.parseDouble(textField.getText());

            switch (selectedOperation) {
            case'+':
                result=num1+num2;
                break;
            case'-':
                result=num1-num2;
                break;
            case'*':
                result=num1*num2;
                break;
            case'/':
                result=num1/num2;
                break;
            }
            textField.setText(String.valueOf(result));
            num1=result;
        }
        if(e.getSource() == clrButton) {
            textField.setText("");
        }
        if(e.getSource() == delButton) {
            String string = textField.getText();
            textField.setText("");
            for(int i=0; i<string.length()-1;i++) {
                textField.setText(textField.getText()+string.charAt(i));
            }
        }
        if(e.getSource() == negButton) {
            double temp = Double.parseDouble(textField.getText());
            temp*=-1;
            textField.setText(String.valueOf(temp));
        }

        if (e.getSource() == decButton) {
            String temp = textField.getText();
            if (!temp.contains(".")) {
                //preventing adding more than one decimal
                textField.setText(temp + ".");
            }
        }
    }
}