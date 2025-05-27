import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {
    
    public static void playSound(String soundFile) {
        try {
            File file = new File("sounds/" + soundFile);  
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error al reproducir el sonido");
        }
    }
}
