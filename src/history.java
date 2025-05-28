import java.util.ArrayList;

public class History {
    
    private ArrayList<String> operations; // CORREGIDO: <String> agregado
    private static final int MAX_OPERATIONS = 300;
    
    public History() {
        operations = new ArrayList<>();
    }
    
    // Agregar una nueva operación al historial
    public void addOperation(String operation) {
        operations.add(0, operation); // Agregar al inicio para que aparezca arriba
        
        // Mantener solo las últimas 300 operaciones
        if (operations.size() > MAX_OPERATIONS) {
            operations.remove(operations.size() - 1);
        }
    }
    
    // Obtener todas las operaciones como array para el JList
    public String[] getOperations() {
        return operations.toArray(new String[0]);
    }
    
    // Limpiar todo el historial
    public void clearHistory() {
        operations.clear();
    }
    
    // Verificar si el historial está vacío
    public boolean isEmpty() {
        return operations.isEmpty();
    }
    
    // Obtener el resultado de una operación (lo que está después del =)
    public String extractResult(String operation) {
        if (operation.contains(" = ")) {
            return operation.split(" = ")[1];
        }
        return "0";
    }
}
