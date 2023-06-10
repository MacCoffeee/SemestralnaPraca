import java.io.IOException;
import java.awt.event.MouseEvent;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import javax.swing.WindowConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Táto trieda slúži najmä ako launcher hry. Obsahuje množstvo obrázkov, zvuky a podobne.
 * V tejto triede som použil ActionListener pre časovač a MouseAdapter pre vstup z myši.
 * Obsahuje, čo sa má vykonať pri ukázaní myšou na rôzne tlačítka alebo pri kliknutí na tlačítka.
 * 
 * Použil som v nej hudbu: Skladba: Tribal / Tribalium
 *                         Interpret: R3bellion44
 *                         Album: Corruptions
 *                         Licencia: [Merlin] Symphonic Distribution (v mene spoločnosti Raxtion Records)
 *                         Prevzatá zo zdroja: https://www.youtube.com/watch?v=0jXTBAGv9ZQ
 * Všetky použité zvuky som prevzial zo zdroja: https://freesound.org/
 * Zvuky sú voľne dostupné a voľne použiteľné.
 * Všetky obrázky som vytváral vlastnoručne.
 * 
 * @Patrik Macura 
 * @04/01/2023
 */
public class Menu extends MouseAdapter {
    
    private Platno menu;
    private JFrame frame;
    private boolean jeViditelny;
    private int lavyHornyX;
    private int lavyHornyY;
    private int stredX;
    private int stredY;
    private int poziciaMysiX;
    private int poziciaMysiY;
    private int x;
    private int y;
    private long oldTick;
    private static final long TICK_LENGTH = 25000000;
    private Hra hra;
    private Obrazok startObrazok;
    private Obrazok soundButton;
    private Obrazok title;
    private Obrazok quit;
    private Zvuk mainTheme;
    private Zvuk button;
    private boolean hudbaStlmena;
    
    private Obrazok namePicker;
    private int statusPozadia;
    private Obrazok continueObrazok;
    private String menoCervenehoHraca;
    private String menoModrehoHraca;
    private Obrazok klavesnica;
    private Obrazok tankModry;
    private Obrazok tankCerveny;
    private Obrazok boom;
    private Obrazok dialogoveOkno;
    private Obrazok yes;
    private Obrazok no;
    private Obrazok projektil1;
    private Obrazok projektil2;
    private Zvuk boom1;
    
    
    /**
     * V konštruktore triedy "Menu" sme implementovali plátno, zvuky a obrázky.
     * Do plátna sme pridali MouseMotionListener, MouseListener, TimerListener.
     * Obrázkom sme nastavili počiatočné pozície.
     */
    public Menu() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.menu = Platno.dajPlatno2();
        this.menu.setVisible(true);
        this.mainTheme = new Zvuk("sounds/HaloTheme.wav");
        this.mainTheme.vyberSubor(0);
        this.mainTheme.loop();
        this.statusPozadia = 0;
        this.frame = this.menu.getFrame();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.boom1 = new Zvuk("sounds/bubny.wav");
        
        
        this.title = new Obrazok(2, "pictures/Menu/title.png");
        this.title.zmenPolohu(450, 250);
        this.title.zobraz();
        
        this.startObrazok = new Obrazok(2, "pictures/Menu/Start.png");
        this.startObrazok.zmenPolohu(450, 500);
        this.startObrazok.zobraz();
        
        this.soundButton = new Obrazok(2, "pictures/Menu/listen1.png");
        this.soundButton.zmenPolohu(850, 50);
        this.soundButton.zobraz();
        
        this.quit = new Obrazok(2, "pictures/Menu/Quit.png");
        this.quit.zmenPolohu(450, 600);
        this.quit.zobraz();
        
        this.tankModry = new Obrazok(2, "pictures/Menu/modryTank.png");
        this.tankCerveny = new Obrazok(2, "pictures/Menu/cervenyTank.png");
        this.boom = new Obrazok(2, "pictures/Menu/boom.png");
        
        this.yes = new Obrazok(2, "pictures/Menu/yes.png");
        this.yes.zmenPolohu(176, 700);
        this.no = new Obrazok(2, "pictures/Menu/no.png");
        this.no.zmenPolohu(724, 700);
        this.projektil1 = new Obrazok(2, "pictures/Menu/projektil1.png");
        this.projektil2 = new Obrazok(2, "pictures/Menu/projektil2.png");
        
        this.menu.addMouseMotionListener(new PohybMysi());
        this.menu.addMouseListener(new KlikMysi());
        this.menu.addTimerListener(new Casovac());
    }
        
    /**
     * Túto triedu som si vypožičial z triedy "Manazer".
     */
    public class PohybMysi extends MouseAdapter {
        public void mouseMoved(MouseEvent event) {
            Menu.this.mouseMoved(event.getX(), event.getY());
        }
    }
    
    /**
     * Túto triedu som si vypožičial z triedy "Manazer".
     */
    private class KlikMysi extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                Menu.this.mouseClicked(event.getX(), event.getY());
            }
        }
        
    }
    
    /**
     * Túto triedu som si vypožičial z triedy "Manazer" a následne ju upravil.
     * Slúži najmä na to, aby sa tanky a projektily pri načítavaní hry pohybovali.
     * Pokiaľ sa projektily stretnú, následne vybuchnú a spustí sa hra.
     */
    private class Casovac implements ActionListener {
        private int x1 = 100;
        private int x2 = 800;
        private boolean vypni = false;
        private int x;
        private int x3 = 250;
        private int x4 = 600;
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - Menu.this.oldTick >= Menu.TICK_LENGTH || newTick < Menu.TICK_LENGTH) {
                Menu.this.oldTick = (newTick / Menu.TICK_LENGTH) * Menu.TICK_LENGTH;
                if (Menu.this.statusPozadia == 2) {
                    if (this.x1 == 250 || this.x2 == 600) {
                        this.x1 = this.x1;
                        this.x2 = this.x2;
                    } else {
                        this.x1++;
                        this.x2--;
                        Menu.this.tankModry.zmenPolohu(this.x1, 800);
                        Menu.this.tankCerveny.zmenPolohu(this.x2, 800);
                    }
                    
                    if (Menu.this.tankModry.getX() == 250 || Menu.this.tankCerveny.getX() == 600) {
                        if (Menu.this.projektil1.getX() == 430 || Menu.this.projektil2.getX() == 470) {
                            Menu.this.tankCerveny.skry();
                            Menu.this.tankModry.skry();
                            Menu.this.projektil1.skry();
                            Menu.this.projektil2.skry();
                            Menu.this.boom.zmenPolohu(450, 800);
                            Menu.this.boom.zobraz();
                            this.vypni = true;
                        } else {
                            this.x3++;
                            this.x4--;
                            Menu.this.projektil1.zobraz();
                            Menu.this.projektil2.zobraz();
                            Menu.this.projektil1.zmenPolohu(this.x3, 800);
                            Menu.this.projektil2.zmenPolohu(this.x4, 800);
                        }
                    }
                }
                if (this.vypni) {
                    this.x++;
                }
                if (this.x == 10) {
                    Menu.this.ukonciMenu(true);
                }
            }
        }
    }
    
    /**
     * Táto metóda sa zavolá po stretnutí načitávacích projektilov, pri načítavaní hry. Slúži na to, aby zatvorila okno a zapla hru.
     * Následne prehrá zvuk.
     */
    public void ukonciMenu(boolean bool) {
        if (bool) {
            this.boom1.vyberSubor(0);
            this.boom1.play();
            this.menu.zatvorOkno();
            this.hra = new Hra(this.menoModrehoHraca, this.menoCervenehoHraca);
            this.mainTheme.stop();
        }
    }
    
    /**
     * Getter na status pozadia.
     */
    public int getStatusPozadia() {
        return this.statusPozadia;
    }
    
    /**
     * Táto metóda sa zavolá po kliknutí na tlačítko štart. Obsahuje textfieldy a obrázky. Po zavolaní tejto metódy sa zbytočné obrázky skryjú.
     */
    public void vyberSiMeno() {
        if (this.statusPozadia == 1) {
            this.title.skry();
            this.startObrazok.skry();
            this.quit.skry();
            this.namePicker = new Obrazok(2, "pictures/Menu/NamePicker.png");
            this.namePicker.zmenPolohu(450, 450);
            this.namePicker.zobraz();
            this.menoModrehoHraca = "Blue";
            this.menoCervenehoHraca = "Red";
            
            this.continueObrazok = new Obrazok(2, "pictures/Menu/continue.png");
            this.continueObrazok.zmenPolohu(450, 650);
            this.continueObrazok.zobraz();
        }
    }
    
    /**
     * Táto metóda sa zavolá po kliknutí na tlačítko continue. Obsahuje obrázok schémy ovládania (klávesnica) a načitácacie tanky.
     */
    public void pokracuj() {
        this.title.skry();
        this.startObrazok.skry();
        this.quit.skry();
        this.namePicker.skry();
        this.continueObrazok.skry();
        this.statusPozadia = 2;
        this.klavesnica = new Obrazok(2, "pictures/Menu/klavesnica.png");
        this.klavesnica.zmenPolohu(450, 450);
        this.klavesnica.zobraz();
        
        this.tankModry.zmenPolohu(100, 800);
        this.tankCerveny.zmenPolohu(800, 800);
        this.tankModry.zobraz();
        this.tankCerveny.zobraz();
    }
    
    /**
     * Tútu metódu pravidelne volá trieda "KlikMyši" a zapisuje do nej polohu myši.
     * Táto metóda obsahuje kedy a pri akom statuse pozadia sa môžu metódy pre kliknutie tlačidiel používať.
     */
    public void mouseClicked(int xMysi, int yMysi) {
        this.klikniNaObrazokSound(xMysi, yMysi);
        if (this.statusPozadia == 0) {
            this.klikniNaObrazokQuit(xMysi, yMysi);
            this.klikniNaObrazokStart(xMysi, yMysi);
        }
        if (this.statusPozadia == 1) {
            this.klikniNaObrazokContinue(xMysi, yMysi);
        }
        if (this.statusPozadia == 3) {
            this.klikniNaObrazokYes(xMysi, yMysi);
            this.klikniNaObrazokNo(xMysi, yMysi);
        }
    }
    
    /**
     * Tútu metódu pravidelne volá trieda "PohybMysi" a zapisuje do nej polohu myši.
     * Táto metóda obsahuje kedy a pri akom statuse pozadia sa môžu metódy pre ukázanie na tlačidlá používať.
     */
    public void mouseMoved(int x, int y) {
        this.ukazNaObrazokSound(x, y);
        if (this.statusPozadia == 0) {
            this.ukazNaObrazokStart(x, y);
            this.ukazNaObrazokQuit(x, y);
        }
        if (this.statusPozadia == 1) {
            this.ukazNaObrazokContinue(x, y);
        }
        if (this.statusPozadia == 3) {
            this.ukazNaObrazokYes(x, y);
            this.ukazNaObrazokNo(x, y);
        }
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "start". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaStart(int xMysi, int yMysi) {
        if (xMysi >= 315 && xMysi <= 585) {
            if (yMysi >= 450 && yMysi <= 550) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "continue". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaContinue(int xMysi, int yMysi) {
        if (xMysi >= 358 && xMysi <= 542) {
            if (yMysi >= 629 && yMysi <= 671) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokContinue(int x, int y) {
        if (this.hranicaTlacitkaContinue(x, y)) {
            this.continueObrazok.zmenObrazok("pictures/Menu/continueH.png");
            this.continueObrazok.zmenPolohu(450, 650);
            this.button = new Zvuk("sounds/Button.wav");
            this.button.vyberSubor(0);
            this.button.play();
        } else {
            this.continueObrazok.zmenObrazok("pictures/Menu/continue.png");
            this.continueObrazok.zmenPolohu(450, 650);
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokContinue(int x, int y) {
        if (this.hranicaTlacitkaContinue(x, y)) {
            this.pokracuj();
        }
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "Yes". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaYes(int xMysi, int yMysi) {
        if (xMysi >= 136 && xMysi <= 216) {
            if (yMysi >= 620 && yMysi <= 780) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "No". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaNo(int xMysi, int yMysi) {
        if (xMysi >= 698 && xMysi <= 750) {
            if (yMysi >= 688 && yMysi <= 712) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokYes(int x, int y) {
        if (this.hranicaTlacitkaYes(x, y)) {
            this.yes.zmenObrazok("pictures/Menu/yesH.png");
            this.yes.zmenPolohu(176, 700);
            this.button = new Zvuk("sounds/Button.wav");
            this.button.vyberSubor(0);
            this.button.play();
        } else {
            this.yes.zmenObrazok("pictures/Menu/yes.png");
            this.yes.zmenPolohu(176, 700);
        }
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokNo(int x, int y) {
        if (this.hranicaTlacitkaNo(x, y)) {
            this.no.zmenObrazok("pictures/Menu/noH.png");
            this.no.zmenPolohu(724, 700);
            this.button = new Zvuk("sounds/Button.wav");
            this.button.vyberSubor(0);
            this.button.play();
        } else {
            this.no.zmenObrazok("pictures/Menu/no.png");
            this.no.zmenPolohu(724, 700);
        }
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "Sound". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaSound(int xMysi, int yMysi) {
        if (xMysi >= 825 && xMysi <= 875) {
            if (yMysi >= 25 && yMysi <= 75) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokSound(int x, int y) {
        if (!this.hudbaStlmena) {
            if (this.hranicaTlacitkaSound(x, y)) {
                this.soundButton.zmenObrazok("pictures/Menu/listenH.png");
                this.soundButton.zmenPolohu(850, 50);
                this.button = new Zvuk("sounds/Button.wav");
                this.button.vyberSubor(0);
                this.button.play();
            } else {
                this.soundButton.zmenObrazok("pictures/Menu/listen1.png");
                this.soundButton.zmenPolohu(850, 50);
            }
        } else {
            if (this.hranicaTlacitkaSound(x, y)) {
                this.soundButton.zmenObrazok("pictures/Menu/listenOffH.png");
                this.soundButton.zmenPolohu(850, 50);
                this.button = new Zvuk("sounds/Button.wav");
                this.button.vyberSubor(0);
                this.button.play();
            } else {
                this.soundButton.zmenObrazok("pictures/Menu/listenOff.png");
                this.soundButton.zmenPolohu(850, 50);
            }
        }
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokStart(int x, int y) {
        if (this.hranicaTlacitkaStart(x, y)) {
            this.startObrazok.zmenObrazok("pictures/Menu/StartH.png");
            this.startObrazok.zmenPolohu(445, 500);
            this.button = new Zvuk("sounds/Button.wav");
            this.button.vyberSubor(0);
            this.button.play();
        } else {
            this.startObrazok.zmenObrazok("pictures/Menu/Start.png");
            this.startObrazok.zmenPolohu(450, 500);
        }
    }
    
    /**
     * Táto metóda sa zavolá po kliknutí na tlačidlo "Quit". Zobrazí obrázok s 2 možnosťami či si prajeme naozaj ukončiť hru.
     */
    public void oknoUkonciHru() {
        this.title.skry();
        this.startObrazok.skry();
        this.quit.skry();
        
        this.dialogoveOkno = new Obrazok(2, "pictures/Menu/prajesSiUkoncitHru.png");
        this.dialogoveOkno.zmenPolohu(450, 450);
        this.dialogoveOkno.zobraz();
        
        this.yes.zobraz();
        this.no.zobraz();
    }
    
    /**
     * Metóda, ktorá definuje hranice tlačidla "Yes". Ak sa poloha myši [x, y] dostane do hranice tlačidla, vráti hodnotu "true".
     */
    public boolean hranicaTlacitkaQuit(int xMysi, int yMysi) {
        if (xMysi >= 405 && xMysi <= 495) {
            if (yMysi >= 575 && yMysi <= 625) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ak sa myš dostane na polohu hranice, obrázok sa zväčší a prehrá sa zvuk. Ak sa poloha myši dostane mimo hranice,
     * obrázok sa vráti do počiatočnej veľkosti.
     */
    public void ukazNaObrazokQuit(int x, int y) {
        if (this.hranicaTlacitkaQuit(x, y)) {
            this.quit.zmenObrazok("pictures/Menu/QuitH.png");
            this.quit.zmenPolohu(450, 600);
            this.button = new Zvuk("sounds/Button.wav");
            this.button.vyberSubor(0);
            this.button.play();
        } else {
            this.quit.zmenObrazok("pictures/Menu/Quit.png");
            this.quit.zmenPolohu(450, 600);
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokStart(int x, int y) {
        if (this.hranicaTlacitkaStart(x, y)) {
            this.statusPozadia = 1;
            this.vyberSiMeno();
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokQuit(int x, int y) {
        if (this.hranicaTlacitkaQuit(x, y)) {
            this.statusPozadia = 3;
            this.oknoUkonciHru();
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokYes(int x, int y) {
        if (this.hranicaTlacitkaYes(x, y)) {
            System.exit(1);
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokNo(int x, int y) {
        if (this.hranicaTlacitkaNo(x, y)) {
            this.statusPozadia = 0;
            this.yes.skry();
            this.no.skry();
            this.dialogoveOkno.skry();
            
            this.title.zobraz();
            this.startObrazok.zobraz();
            this.quit.zobraz();
        }
    }
    
    /**
     * Metóda, sa zavolá po kliknutí myši na hranice tlačidla. Následne zavolá svoju metódu.
     */
    public void klikniNaObrazokSound(int x, int y) {
        if (this.hranicaTlacitkaSound(x, y)) {
            if (!this.hudbaStlmena) {
                try {
                    this.mainTheme.volume(0);
                    this.hudbaStlmena = true;
                } catch (Exception e) {
                }
            } else {
                try {
                    this.mainTheme.volume(1.0);
                    this.mainTheme.loop();
                    this.hudbaStlmena = false;
                } catch (Exception e) {
                }
            }
        }
    }
}

