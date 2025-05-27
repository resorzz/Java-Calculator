public class CalculatorFlow {

    private double num1 = 0;
    private double num2 = 0;
    private double result = 0;
    private char selectedOperation;
    private boolean isEqualsPressed = false;
    private double lastNumber = 0;
    private StringBuilder expression = new StringBuilder();
    private boolean isNewExpression = true;
    private boolean hasError = false;

    // Getters y setters para todos los campos
    public double getNum1() { return num1; }
    public void setNum1(double num1) { this.num1 = num1; }

    public double getNum2() { return num2; }
    public void setNum2(double num2) { this.num2 = num2; }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public char getSelectedOperation() { return selectedOperation; }
    public void setSelectedOperation(char selectedOperation) { this.selectedOperation = selectedOperation; }

    public boolean isEqualsPressed() { return isEqualsPressed; }
    public void setEqualsPressed(boolean equalsPressed) { isEqualsPressed = equalsPressed; }

    public double getLastNumber() { return lastNumber; }
    public void setLastNumber(double lastNumber) { this.lastNumber = lastNumber; }

    public StringBuilder getExpression() { return expression; }
    public void setExpression(StringBuilder expression) { this.expression = expression; }

    public boolean isNewExpression() { return isNewExpression; }
    public void setNewExpression(boolean newExpression) { isNewExpression = newExpression; }

    public boolean hasError() { return hasError; }
    public void setHasError(boolean hasError) { this.hasError = hasError; }

    // Método para resetear el estado completo
    public void reset() {
        num1 = 0;
        num2 = 0;
        result = 0;
        selectedOperation = '\0';
        isEqualsPressed = false;
        lastNumber = 0;
        expression = new StringBuilder();
        isNewExpression = true;
        hasError = false;
    }
}
