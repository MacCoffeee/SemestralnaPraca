import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
/**
 * Táto trieda slúži ako hlavný spúšťač hry.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public class Main {
    /**
     * Vytvorí Menu.
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Menu();
    }
}
