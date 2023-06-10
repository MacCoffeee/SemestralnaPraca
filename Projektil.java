import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Táto trieda slúži na vytvorenie projektilu, a aby projektil letel na základe smeru natočenia veže tanku.
 * Použil som v nej množstvo obrázkov z toho dôvodu, aby som vytvoril GIF. 
 * 
 * @Patrik Macura 
 * @04/01/2023
 */
public class Projektil {
    
    private int poziciaX;
    private int poziciaY;
    private int uhol;
    private Hra hra;
    private BufferedImage mapa;
    private double rychlostX;
    private double rychlostY;
    private int stav; // 0 - nevystreleny 1 - vystreleny 2 - vybuchnuty 3 - po vybuchu
    private Obrazok projektil1;
    private Obrazok projektil2;
    private Obrazok projektil3;
    private Obrazok projektil4;
    private Obrazok explosionWall;
    private final int k = 10;  //Premenna, ktorá uchováva hodnotu skoku projektilu.
    private double v1;
    private double v2;
    private int pocObr;
    private boolean boostNainstalovany;
    private Poloha enemyTank;
    private String tank;
    private Platno mapaPlatno;
    
    private long oldTick;
    private static final long TICK_LENGTH = 25000000;
    /**
     * Konštruktor triedy "Projektil", v ktorom sme uložili vstupné premenné do privátnych atribútov tejto triedy.
     * Následne sme vypočítali vektory ktoré sme nastavili obrázkom.
     * Riešil som aj aké projektily bude tank strieľať keď zo zeme vezme boost.
     * Konštruktor tejto triedy slúži najmä pre modrý tank, pretože výpočet vektorov sa u neho riešil na základe kliku myši a uhla natočenia.
     */
    public Projektil(Platno mapa, Poloha enemyTank, String tank, int uhol, int poziciaX, int poziciaY, int poziciaKurzoruX, int poziciaKurzoruY, boolean boostNainstalovany) {
        this.poziciaX = poziciaX;
        this.poziciaY = poziciaY;
        this.uhol = uhol;
        this.pocObr = 0;
        this.stav = 1;
        this.boostNainstalovany = boostNainstalovany;
        this.tank = tank;
        this.enemyTank = enemyTank;
        this.mapaPlatno = mapa;
        this.mapaPlatno.addTimerListener(new Casovac());
        
        //Výpočet vektorov a rychlosti.
        int u1 =  poziciaKurzoruX - poziciaX;
        int u2 =  poziciaKurzoruY - poziciaY;
        double sizeU = Math.sqrt(u1 * u1 + u2 * u2);
        this.v1 = (double)u1 / (sizeU / this.k);
        this.v2 = (double)u2 / (sizeU / this.k);
        
        //Inicializácia obrázkov ak tank má aktívny boost.
        if (!this.boostNainstalovany) {
            this.projektil1 = new Obrazok("pictures/Projektil/Vrstva 1.png");
            this.explosionWall = new Obrazok("pictures/Projektil/explosionWall.png");
        
            //Zobrazenie projektilu.
            this.projektil1.zobraz(); 
            this.projektil1.zmenPolohu(poziciaX, poziciaY);
            
            //Aplikácia uhla.
            this.projektil1.zmenUhol(uhol);
            //Inicializácia obrázkov ak tank nemá aktívny boost.
        } else {
            this.projektil1 = new Obrazok("pictures/Projektil/naboj.png");
            this.explosionWall = new Obrazok("pictures/Projektil/explosionWall.png");
            
            //Zobrazenie projektilu.
            this.projektil1.zobraz(); 
            this.projektil1.zmenPolohu(poziciaX, poziciaY);
            //Aplikácia uhla.
            this.projektil1.zmenUhol(uhol);
        }
    }
    
    /**
     * Konštruktor triedy "Projektil", v ktorom sme uložili vstupné premenné do privátnych atribútov tejto triedy.
     * Následne sme vypočítali vektory ktoré sme nastavili obrázkom.
     * Riešil som aj aké projektily bude tank strieľať keď zo zeme vezme boost.
     * Konštruktor tejto triedy slúži najmä pre červený tank, pretože výpočet vektorov sa u neho riešil na základe uhla natočenia.
     */
    public Projektil(Platno mapa, Poloha enemyTank, String tank, int uhol, int poziciaX, int poziciaY, boolean boostNainstalovany) {
        this.poziciaX = poziciaX;
        this.poziciaY = poziciaY;
        this.uhol = uhol;
        this.pocObr = 0;
        this.stav = 1;
        this.boostNainstalovany = boostNainstalovany;
        this.tank = tank;
        this.enemyTank = enemyTank;
        this.mapaPlatno = mapa;
        this.mapaPlatno.addTimerListener(new Casovac());
        
        //Výpočet vektorov a rychlosti.
        if (uhol > 180 && uhol <= 360) {
            this.v1 = -this.k * Math.cos(Math.toRadians(uhol - 180));
            this.v2 = -Math.sqrt(this.k * this.k - this.v1 * this.v1);
        } else {
            this.v1 = this.k * Math.cos(Math.toRadians(uhol));
            this.v2 = Math.sqrt(this.k * this.k - this.v1 * this.v1);
        }
        
        //Inicializácia obrázkov ak tank má aktívny boost.
        if (!this.boostNainstalovany) {
            this.projektil1 = new Obrazok("pictures/Projektil/Vrstva 1.png");
            this.explosionWall = new Obrazok("pictures/Projektil/explosionWall.png");
            //Zobrazenie projektilu.
            this.projektil1.zobraz(); 
            this.projektil1.zmenPolohu(poziciaX, poziciaY);
            //Aplikácia uhla.
            this.projektil1.zmenUhol(uhol);
            //Inicializácia obrázkov ak tank nemá aktívny boost.
        } else {
            this.projektil1 = new Obrazok("pictures/Projektil/naboj.png");
            this.explosionWall = new Obrazok("pictures/Projektil/explosionWall.png");
            //Zobrazenie projektilu.
            this.projektil1.zobraz(); 
            this.projektil1.zmenPolohu(poziciaX, poziciaY);
            //Aplikácia uhla.
            this.projektil1.zmenUhol(uhol);
        }
        
    }
    
    /**
     * Túto triedu som si vypožičial z triedy "Manazer" následne som ju upravil a vložil do nej premennu "pocetObr".
     * Premenna "pocetObr" slúži na to, aby po výbuchu začala narástať, a ak dosiahne hodnotu 12, resetuje sa na hodnotu 0.
     * Následne ju posielame triede "Projektil" aby v nej obnovil vlastnú premennú.
     */
    private class Casovac implements ActionListener {
        private int pocetObr = 0;
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - Projektil.this.oldTick >= Projektil.TICK_LENGTH || newTick < Projektil.TICK_LENGTH) {
                Projektil.this.oldTick = (newTick / Projektil.TICK_LENGTH) * Projektil.TICK_LENGTH;
                if (Projektil.this.getStav() == 2) {
                    this.pocetObr++;
                    Projektil.this.setPocetObr(this.pocetObr);
                    if (this.pocetObr >= 12) {
                        this.pocetObr = 0;
                    }
                }
            }
        }
    }
    
    /**
     * Setter na premennú "pocObr". Odosiela ju trieda "Casovac".
     */
    public void setPocetObr(int pocetObr) {
        this.pocObr = pocetObr;
    }
    
    /**
     * Getter na stav projektilu.
     */
    public int getStav() {
        return this.stav;
    }
    
    /**
     * Metóda, ktorá slúži na identifikáciu RGB daného pixelu na základe vstupných parametrov súradníc.
     * Biely pixel je tzv. hracia plocha. Iné ako biele pixely sú hranice alebo bariéry.
     * Inšpiroval som sa následujúcim príkladom zo zdroja: https://stackoverflow.com/questions/10088465/need-faster-way-to-get-rgb-value-for-each-pixel-of-a-buffered-image
     */
    public boolean isBorder(int x1, int x2, int y1, int y2) {
        BufferedImage mapa = null;
        
        try {
            mapa = ImageIO.read(new File("pictures/Mapy/Mapa1.png"));
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Subor " + "pictures/Mapy/Mapa1.png" + " sa nenasiel.");
        } 
        this.mapa = mapa;
        
        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                int c = this.mapa.getRGB(x, y);
                Color color = new Color(c);

                if (color.getRed() != 255 || color.getGreen() != 255 || color.getBlue() != 255) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Metóda, ktorú volá trieda "Hra" po stlačení tlačítka pre výstrel.
     * Slúži na pohyb projektilu a zároveň upravuje stav na základe výbuchu projektilu.
     */
    public void zmenPolohu() {
        //Výpočet súradníc na základe vektoru.
        int x = this.poziciaX + (int)this.v1;
        int y = this.poziciaY + (int)this.v2;
        if (this.tank.equals("red")) {
            if (this.stav == 1) {
                this.projektil1.zmenPolohu(x, y);
                
                //Ak následujúca poloha nie je biely pixel, skryje všetky rakety a zobrazí výbuch.
                if (!(this.isBorder(this.projektil1.getX(), this.projektil1.getX(), this.projektil1.getY() - 5, this.projektil1.getY() + 5))) {
                    this.stav = 2;
                    this.projektil1.skry();
                    Zvuk boom = new Zvuk("sounds/boom.wav");
                    boom.vyberSubor(0);
                    boom.play();
                    this.explosionWall.zobraz();
                    this.explosionWall.zmenPolohu(x, y);
                    //Ak následujúca poloha je poloha nepriateľského tanku, skryje všetky rakety a zobrazí výbuch.
                } else if (x >= (this.enemyTank.getX() - 15) && x <= (this.enemyTank.getX() + 15)
                    && y >= (this.enemyTank.getY() - 15) && y <= (this.enemyTank.getY() + 15)) {
                    this.stav = 2;
                    this.projektil1.skry();
                    Zvuk boom = new Zvuk("sounds/boom.wav");
                    boom.vyberSubor(0);
                    boom.play();
                    this.explosionWall.zobraz();
                    this.explosionWall.zmenPolohu(x, y);
                    this.mapaPlatno.nastavStavTeamuCerveny();
                }
            }
        
            //Podmienka ktorá skryje výbuch po 2,25sec a nastaví stav na 3 == po výbuchu.
            if (this.pocObr == 10) {
                if (this.stav == 2) {
                    this.explosionWall.zmenPolohu(x, y);
                    this.explosionWall.skry();
                    this.stav = 3;
                }
            }
            
            //Ošetrenie proti preletu cez okraje mapy.
            if (this.projektil1.getX() >= 899 || this.projektil1.getY() >= 899) {
                this.stav = 2;
                this.projektil1.skry();
                Zvuk boom = new Zvuk("sounds/boom.wav");
                boom.vyberSubor(0);
                boom.play();
                this.explosionWall.zobraz();
                this.explosionWall.zmenPolohu(x, y);
            }
        }
        
        if (this.tank.equals("blue")) {
            if (this.stav == 1) {
                this.projektil1.zmenPolohu(x, y);
                //Ak následujúca poloha nie je biely pixel, skryje všetky rakety a zobrazí výbuch.
                if (!(this.isBorder(this.projektil1.getX(), this.projektil1.getX(), this.projektil1.getY() - 5, this.projektil1.getY() + 5))) {
                    this.stav = 2;
                    this.projektil1.skry();
                    Zvuk boom = new Zvuk("sounds/boom.wav");
                    boom.vyberSubor(0);
                    boom.play();
                    this.explosionWall.zobraz();
                    this.explosionWall.zmenPolohu(x, y);
                    //Ak následujúca poloha je poloha nepriateľského tanku, skryje všetky rakety a zobrazí výbuch.
                } else if (x >= (this.enemyTank.getX() - 15) && x <= (this.enemyTank.getX() + 15)
                    && y >= (this.enemyTank.getY() - 15) && y <= (this.enemyTank.getY() + 15)) {
                    this.stav = 2;
                    this.projektil1.skry();
                    Zvuk boom = new Zvuk("sounds/boom.wav");
                    boom.vyberSubor(0);
                    boom.play();
                    this.explosionWall.zobraz();
                    this.explosionWall.zmenPolohu(x, y);
                    this.mapaPlatno.nastavStavTeamuModry();
                }
            }
        
            //Podmienka ktorá skryje výbuch po 2,25sec a nastaví stav na 3 == po výbuchu.
            if (this.pocObr == 10) {
                if (this.stav == 2) {
                    this.explosionWall.zmenPolohu(x, y);
                    this.explosionWall.skry();
                    this.stav = 3;
                }
            }
            
            //Ošetrenie proti preletu cez okraje mapy.
            if (this.projektil1.getX() == 899 || this.projektil1.getY() == 899) {
                this.stav = 2;
                this.projektil1.skry();
                Zvuk boom = new Zvuk("sounds/boom.wav");
                boom.vyberSubor(0);
                boom.play();
                this.explosionWall.zobraz();
                this.explosionWall.zmenPolohu(x, y);
            }
        }
    
        //Uloženie pozície po vykonaní metódy do premennej, keby sa metóda znovu opakovala, aby nezačínala od začiatku, ale aby pokračovala na poslednej pozícii.
        this.poziciaX = x;
        this.poziciaY = y;
        
    }
}
    
