import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class ui {
    // Colores del tema morado
    public static final Color BACKGROUND_COLOR = new Color(20, 20, 20);
    public static final Color DISPLAY_BACKGROUND = new Color(30, 30, 30);
    public static final Color BUTTON_COLOR = new Color(45, 45, 45);
    public static final Color OPERATOR_BUTTON_COLOR = new Color(75, 60, 110);
    public static final Color EQUALS_BUTTON_COLOR = new Color(75, 60, 110);
    public static final Color TEXT_COLOR = new Color(230, 230, 230);
    public static final Color PURPLE_TEXT = new Color(200, 160, 255);
    
    // Método para aplicar el tema a la calculadora
    public static void applyPurpleTheme(Calculator calculator) {
        // Cambiar el fondo de la calculadora
        calculator.getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Estilizar el campo de texto
        calculator.textField.setBackground(DISPLAY_BACKGROUND);
        calculator.textField.setForeground(PURPLE_TEXT);
        calculator.textField.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Estilizar botones numéricos
        for (int i = 0; i < 10; i++) {
            styleButton(calculator.numberButtons[i], BUTTON_COLOR);
        }
        
        // Estilizar botones de operadores
        for (JButton button : calculator.operatorButtons) {
            styleButton(button, OPERATOR_BUTTON_COLOR);
        }
        
        // Ocultar el panel base para que se vea el fondo
        calculator.panel.setOpaque(false);
        calculator.panel.setBackground(new Color(0, 0, 0, 0));
    }
    
    // Método para estilizar un botón individual
    private static void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(PURPLE_TEXT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        // Redondear los botones
        button.setUI(new RoundedButtonUI());
    }
    
    // Clase interna para crear botones redondeados
    static class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            ((AbstractButton) c).setContentAreaFilled(false);
        }
        
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AbstractButton b = (AbstractButton) c;
            ButtonModel model = b.getModel();
            
            int width = c.getWidth();
            int height = c.getHeight();
            
            // Dibujar fondo redondeado
            if (model.isPressed()) {
                g2.setColor(c.getBackground().darker());
            } else if (model.isRollover()) {
                g2.setColor(c.getBackground().brighter());
            } else {
                g2.setColor(c.getBackground());
            }
            
            g2.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 20, 20));
            
            // Efecto acrílico (brillo sutil)
            g2.setColor(new Color(255, 255, 255, 30));
            g2.fill(new RoundRectangle2D.Double(0, 0, width - 1, height / 2, 20, 20));
            
            g2.dispose();
            
            super.paint(g, c);
        }
    }
}
