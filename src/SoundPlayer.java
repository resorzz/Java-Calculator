import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
    public static void playSound(String soundFile) {
        try {
            // Verificar que el archivo existe
            File file = new File("sounds/" + soundFile);
            if (!file.exists()) {
                System.out.println("Archivo no encontrado: " + file.getAbsolutePath());
                return;
            }
            
            // Cargar el audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            
            // Agregar listener para cerrar correctamente cuando termine
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            
            clip.start();
            
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Formato de archivo no soportado: " + soundFile);
        } catch (LineUnavailableException e) {
            System.out.println("Línea de audio no disponible");
        } catch (Exception e) {
            System.out.println("Error al reproducir el sonido: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
