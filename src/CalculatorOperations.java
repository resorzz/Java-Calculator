public class CalculatorOperations {
    
    public static double performOperation(double num1, double num2, char operation) { // operaciones basicas
        switch (operation) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2;
            default:
                return 0;
        }
    }
    
    // Método para validar división por cero
    public static boolean isDivisionByZero(char operation, double num2) {
        return operation == '/' && Double.compare(num2, 0.0) == 0;
    }
    
    // Método para cambiar signo de un número
    public static double toggleSign(double number) {
        return number * -1;
    }
}
