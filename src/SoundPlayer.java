import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.BufferedInputStream;

public class SoundPlayer {

    public static void playSound(String soundFile) {
        try {
            // Intentar cargar el archivo desde el JAR
            InputStream audioStream = SoundPlayer.class.getResourceAsStream("/sounds/" + soundFile);
            
            if (audioStream == null) {
                audioStream = SoundPlayer.class.getResourceAsStream("sounds/" + soundFile);
            }
            
            if (audioStream == null) {
                System.out.println("Archivo no encontrado: " + soundFile);
                return;
            }

            // Usar try-with-resources para manejar los streams correctamente
            try (InputStream finalAudioStream = audioStream;
                 BufferedInputStream bufferedStream = new BufferedInputStream(finalAudioStream);
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream)) {
                
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Agregar listener para cerrar el clip cuando termine
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
            }

        } catch (Exception e) {
            System.out.println("Error al reproducir el sonido: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
