import javax.sound.sampled.Clip;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
/**
 * Túto triedu som skopíroval z internetu a upravil na základe ďalších zdrojov
 * z internetu.
 * Zdroj: http://www.java2s.com/Code/Java/Development-Class/SettingtheVolumeofaSampledAudioPlayer.htm
 * Zdroj videa, z ktorého som kód použil: https://www.youtube.com/watch?v=nUHh_J2Acy8
 * 
 * @autor videa: RyiSnow 
 * @dátum videa: 18. 4. 2022
 */
public class Zvuk {
    
    private Clip zvuk;
    private URL[] cesta;
    private FloatControl volume;
    /**
     * Načíta zvuk a uloží ho do poľa.
     */
    public Zvuk(String cestaKSuboru) {
        this.cesta = new URL[1];
        this.cesta[0] = getClass().getResource(cestaKSuboru);
    }
    
    /**
     * Vyberie súbor.
     */
    public void vyberSubor(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(this.cesta[i]);
            this.zvuk = AudioSystem.getClip();
            this.zvuk.open(ais);
        } catch (Exception e) {
        }
    }
    
    /**
     * Prehrá zvuk.
     */
    public void play() {
        this.zvuk.start();
    }
    
    /**
     * Prehrá zvuk opakovane. (LOOP)
     */
    public void loop() {
        this.zvuk.loop(this.zvuk.LOOP_CONTINUOUSLY);
    }
    
    /**
     * Pozastaví zvuk.
     */
    public void stop() {
        this.zvuk.stop();
    }
    
    /**
     * Túto metódu som použil a následne upravil zo zdroja: http://www.java2s.com/Code/Java/Development-Class/SettingtheVolumeofaSampledAudioPlayer.htm
     */
    public void volume(double hladinaZvuku) throws Exception {
        FloatControl gainControl = (FloatControl)this.zvuk.getControl(FloatControl.Type.MASTER_GAIN);
        double gain = hladinaZvuku; // číslo 0 = najnižšia hladina, 1 = najvyššia hladina zvuku
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}