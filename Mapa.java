import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFrame;
/**
 * Táto trieda slúži na vykreslenie mapy na plátno.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public class Mapa {
    
    private int rozmeryXY;
    private int nahodnySpawnXY;
    private int nahodneBonusy;
    private double zakazaneHranice;
    private Obrazok mapa;
    private Platno platno;
    private JFrame frame;
    
    private JLabel casovac;
    private int sekundy;
    private int minuty;
    
    /**
     * V konštruktore som vytvoril a uložil obrázok do súkromnej premennej.
     * Mape som nastavil polohu zobrazenia na základe súradníc ľavéhoHorného bodu X a Y.
     * Následne som mapu zobrazil.
     */
    public Mapa(String menoModry, String menoCerveny) {
        this.mapa = new Obrazok("pictures/Mapy/Mapa1.png");
        this.mapa.zmenPolohu(450, 450);
        this.mapa.zobraz();
        this.platno = this.mapa.getCanvas();
        this.frame = this.platno.getFrame();
        this.platno.aktualizujMenaHracov(menoModry, menoCerveny);
    }
    
    /**
     * Getter, ktorý vracia plátno mapy.
     */
    public Platno getCanvas() {
        return this.mapa.getCanvas();
    }
    
    /**
     * Getter, ktorý vracia obrázok mapy.
     */
    public Obrazok mapa() {
        return this.mapa;
    }
    
    /**
     * Metóda, ktorá slúži na identifikáciu RGB daného pixelu na základe vstupných parametrov súradníc.
     * Inšpiroval som sa následujúcim príkladom zo zdroja: https://stackoverflow.com/questions/10088465/need-faster-way-to-get-rgb-value-for-each-pixel-of-a-buffered-image
     */
    public boolean isBorder(int x1, int x2, int y1, int y2) {
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                int c = this.mapa.obrazok.getRGB(x, y);
                Color color = new Color(c);

                if (color.getRed() != 255 || color.getGreen() != 255 || color.getBlue() != 255) {
                    return false;
                }
            }
        }
        return true;
    }
}
