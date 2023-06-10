import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import javax.swing.WindowConstants;
/**
 * V tejto triede "Hra" som nastavil pohyby tanku a podmienku v nich, v ktorej sa zisťujú farby ďalších súradníc,
 * kde by sa tank pri ďalšom kroku posunul. Ak je ďalšia súradnica v danom smere (RGB == white), tank sa na danú suradnicu môže posunúť.
 * Tak isto obsahuje metódu na výpočet uhla na základe pozície tanku a pozície kurzoru myši. Vypočítaný uhol sa
 * dosadí do metódy, ktorá otočí obrázok na základe stupňa.
 * Inšpiroval som sa triedou, ktorú sme robili na cvičeniach. (Trieda "Hra" Vlaciky)
 * 
 * @Patrik Macura 
 * @04/01/2023
 */
public class Hra {
    
    private String frakcia;
    private Mapa mapa;
    private Manazer manazer;
    private Smer smer;
    private Tank modryTank;
    private Tank cervenyTank;
    private final int velkostSkoku;
    private int smerKurzoruX;
    private int smerKurzoruY;
    private List<Projektil> projektilListModry;  
    private List<Projektil> projektilListCerveny;  
    private int vyslednyUholModry;
    private int vyslednyUholCerveny;
    private Zvuk zvukVystrelu;
    private Boosty boosty;
    private int trojityVystrel;
    private List<Projektil> trojityVystrelModry;
    private List<Projektil> trojityVystrelCerveny;
    private Projektil projektilModry;
    private Projektil projektilCerveny;
    private Platno mapaPlatno;
    private String menoModreho;
    private String menoCerveneho;
    private int stavHry;
    private Graphics2D graphic;
    private JFrame frame;
    private int pozastavenieHry;
    private Obrazok vyhodnotenieModry;
    private Obrazok vyhodnotenieCerveny;
    
    /**
     * V konštruktore triedy "Hra" sme si inicializovali veľkosť skoku, teda aby sme sa s tankom pohybovali po 3 pixeloch.
     * Inicializovali sme mapu, manažéra a tank. Tanku sme nastavili polohu (100, 100, 0).
     * Následne sme zavolali metódu "spravujObjekt(this)".
     */
    public Hra(String menoModreho, String menoCerveneho) {
        this.stavHry = 0;
        this.menoModreho = menoModreho;
        this.menoCerveneho = menoCerveneho;
        this.mapa = new Mapa(menoModreho, menoCerveneho);
        this.mapaPlatno = this.mapa.getCanvas();
        this.graphic = this.mapaPlatno.getGraphics();
        this.frame = this.mapaPlatno.getFrame();
        this.velkostSkoku = 3;
        this.projektilListModry = new ArrayList<Projektil>();
        this.projektilListCerveny = new ArrayList<Projektil>();
        this.trojityVystrelModry = new ArrayList<Projektil>();
        this.trojityVystrelCerveny = new ArrayList<Projektil>();
        this.manazer = new Manazer();
        this.modryTank = new Tank(new Poloha(100, 100, 0), "blue");
        this.cervenyTank = new Tank(new Poloha(800, 100, 0), "red");
        this.manazer.spravujObjekt(this);
        this.vyslednyUholCerveny = 0;
        this.boosty = new Boosty(this.modryTank, this.cervenyTank);
        this.mapaPlatno.resetujStavyTeamov();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pozastavenieHry = 0;
        this.vyhodnotenieModry = new Obrazok("pictures/Menu/modryVyhrali.png");
        this.vyhodnotenieModry.zmenPolohu(450, 450);
        this.vyhodnotenieCerveny = new Obrazok("pictures/Menu/cervenyVyhrali.png");
        this.vyhodnotenieCerveny.zmenPolohu(450, 450);
    }
    
    /**
     * Getter na polohu modrého tanku.
     */
    public Poloha getPolohaModryTank() {
        return this.modryTank.getPoloha();
    }
    
    /**
     * Getter na polohu červeného tanku.
     */
    public Poloha getPolohaCervenyTank() {
        return this.cervenyTank.getPoloha();
    }
    
    /**
     * Getter na hodnotu atributu "pozastavenieHry".
     */
    public int getPozastavenaHraInfo() {
        return this.pozastavenieHry;
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky hore(VK_W).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void posunHore() {
        Poloha newPoloha = this.getNextPoloha(Smer.HORE);
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&        //pravý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&           //ľavý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX(), newPoloha.getX(), newPoloha.getY() - 15, newPoloha.getY() - 15)) {                     //stredný dolný roh tanku
            this.modryTank.setPoloha(this.getNextPoloha(Smer.HORE));
            
            this.smer = Smer.HORE;
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky hore(VK_UP).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void cervenyHore() {
        Poloha newPoloha = this.getNextPolohaCerveny(Smer.HORE);
        
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&        //pravý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&           //ľavý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX(), newPoloha.getX(), newPoloha.getY() - 15, newPoloha.getY() - 15)) {                     //stredný dolný roh tanku
            this.cervenyTank.setPoloha(this.getNextPolohaCerveny(Smer.HORE));
            
            this.smer = Smer.HORE;
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky dole(VK_S).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void posunDole() {
        Poloha newPoloha = this.getNextPoloha(Smer.DOLE);
        
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //pravý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&           //ľavý horný roh tanku
            this.mapa.isBorder(newPoloha.getX(), newPoloha.getX(), newPoloha.getY() + 15, newPoloha.getY() + 15)) {                      //stredný horný roh tanku
            this.modryTank.setPoloha(this.getNextPoloha(Smer.DOLE));
            
            this.smer = Smer.DOLE;
                
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky dole(VK_DOWN).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void cervenyDole() {
        Poloha newPoloha = this.getNextPolohaCerveny(Smer.DOLE);
        
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //pravý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&           //ľavý horný roh tanku
            this.mapa.isBorder(newPoloha.getX(), newPoloha.getX(), newPoloha.getY() + 15, newPoloha.getY() + 15)) {                      //stredný horný roh tanku
            this.cervenyTank.setPoloha(this.getNextPolohaCerveny(Smer.DOLE));
            
            this.smer = Smer.DOLE;
                
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky vľavo(VK_A).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void posunVlavo() {
        Poloha newPoloha = this.getNextPoloha(Smer.VLAVO);
        
        if (this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //ľavý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&           //ľavý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY(), newPoloha.getY())) {                      //ľavý stredný roh tanku
            this.modryTank.setPoloha(this.getNextPoloha(Smer.VLAVO));
            
            this.smer = Smer.VLAVO;
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky vľavo(VK_LEFT).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void cervenyVlavo() {
        Poloha newPoloha = this.getNextPolohaCerveny(Smer.VLAVO);
        
        if (this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //ľavý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&           //ľavý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() - 15, newPoloha.getX() - 15, newPoloha.getY(), newPoloha.getY())) {                      //ľavý stredný roh tanku
            this.cervenyTank.setPoloha(this.getNextPolohaCerveny(Smer.VLAVO));
            
            this.smer = Smer.VLAVO;
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky vpravo(VK_D).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void posunVpravo() {
        Poloha newPoloha = this.getNextPoloha(Smer.VPRAVO);
        
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //pravý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&           //pravý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY(), newPoloha.getY())) {                      //pravý stredný roh tanku
            this.modryTank.setPoloha(this.getNextPoloha(Smer.VPRAVO));
            
            this.smer = Smer.VPRAVO;
        }
    }
    
    /**
     * Túto metódu volá trieda "Manazer" po stlačení šípky vpravo(VK_RIGHT).
     * Zistí či nová poloha na mape v danom smere obsahuje bielu farbu(RGB(255, 255, 255)) a ak ju obsahuje, metóda posunie tank na danú pozíciu.
     * Ak nová poloha v danom smere neobsahuje bielu farbu, metóda sa nevykoná.
     */
    public void cervenyVpravo() {
        Poloha newPoloha = this.getNextPolohaCerveny(Smer.VPRAVO);
        
        if (this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() + 15, newPoloha.getY() + 15) &&        //pravý horný roh tanku
            this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY() - 15, newPoloha.getY() - 15) &&           //pravý dolný roh tanku
            this.mapa.isBorder(newPoloha.getX() + 15, newPoloha.getX() + 15, newPoloha.getY(), newPoloha.getY())) {                      //pravý stredný roh tanku
            this.cervenyTank.setPoloha(this.getNextPolohaCerveny(Smer.VPRAVO));
            
            this.smer = Smer.VPRAVO;
        }
    }
    
    /**
     * Do tejto metódy zapisuje trieda "Manazer" súradnice kliku myši.
     * Tieto hodnoty (x, y) som využil na získanie uhlu pomocou metódy Math.atan2() ktorú som z radiánov premenil do stupňov.
     * Následne som nastavil hranicu od 0 do 360°.
     * Výsledok som upravil tak, aby sa mi obrázok otočil do správneho smeru, teda aby uhol do 180° bol o 90° zmenšený a uhol od 180°
     * do 360° bol o 90° zväčšený. Po úpravach uhla sa uhol obrázka dela zmení na "vyslednyUhol".
     * Delo bude otočené k smeru kliku myši.
     */
    public void vyberSuradnicePohybuMysi(int x, int y) {
        this.smerKurzoruX = x;
        this.smerKurzoruY = y;
        
        //Výpočet uhla a úprava na základe polohy kurzoru myši
        int uhol = (int)Math.toDegrees(Math.atan2(x - this.modryTank.getPoloha().getX(), y - this.modryTank.getPoloha().getY()));
        if (uhol <= 180) {
            this.vyslednyUholModry = 360 - (uhol - 90);
        } else {
            this.vyslednyUholModry = 360 - (uhol + 90);
        }
        //Aplikácia uhla pre otočenie obrázka.
        this.modryTank.zmenUholDela(this.vyslednyUholModry);
    }
    
    /**
     * Getter na súradnice kurzoru myši X-osi.
     */
    public int vybraneSuradniceKurzoruX() {
        return this.smerKurzoruX;
    }
    
    /**
     * Getter na súradnice kurzoru myši Y-osi.
     */
    public int vybraneSuradniceKurzoruY() {
        return this.smerKurzoruY;
    }
    
    /**
     * Getter na výsledný uhol natočenia veže tanku.
     */
    public int getUholNatoceniaModry() {
        return this.vyslednyUholModry;
    }
    
    /**
     * Metóda, ktorá každým volaním otočí vežu červeného tanku o 3° proti smeru hodinových ručičiek.
     */
    public void cervenyOtocVezuProtiSmeru() {
        this.vyslednyUholCerveny = this.vyslednyUholCerveny - 3;
        if (this.vyslednyUholCerveny <= 0) {
            this.vyslednyUholCerveny = this.vyslednyUholCerveny + 360;
        }
        this.cervenyTank.zmenUholDela(this.vyslednyUholCerveny);
    }
    
    /**
     * Metóda, ktorá každým volaním otočí vežu o 3° v smere hodinových ručičiek.
     */
    public void cervenyOtocVezuVSmere() {
        this.vyslednyUholCerveny = this.vyslednyUholCerveny + 3;
        if (this.vyslednyUholCerveny >= 360) {
            this.vyslednyUholCerveny = this.vyslednyUholCerveny - 360;
        }
        this.cervenyTank.zmenUholDela(this.vyslednyUholCerveny);
    }
    
    /**
     * Getter na výsledný uhol natočenia veže tanku.
     */
    public int getUholNatoceniaCerveny() {
        return this.vyslednyUholCerveny;
    }
    
    /**
     * Metóda, ktorá vytvorí projektil, zaradí ho do "ArrayListu" a prehrá zvuk výstrelu.
     */
    public void vystrel() {
        if (!this.boosty.getNainstalovanyModry()) {
            this.projektilModry = new Projektil(this.mapaPlatno, this.cervenyTank.getPoloha(), "blue", this.vyslednyUholModry, this.modryTank.getPoloha().getX(), this.modryTank.getPoloha().getY(), this.vybraneSuradniceKurzoruX(), this.vybraneSuradniceKurzoruY(), false);       
            this.projektilListModry.add(this.projektilModry);
            this.trojityVystrel = 0;
            this.zvukVystrelu = new Zvuk("sounds/vystrel.wav");
            this.zvukVystrelu.vyberSubor(0);
            this.zvukVystrelu.play();
        }
        if (this.boosty.getNainstalovanyModry()) {
            if (this.trojityVystrelModry.size() >= 3) {
                this.trojityVystrelModry.clear();
                this.boosty.setVystrielanyModry(true);
                this.boosty.setResetModry(true);
            } else {
                this.projektilModry = new Projektil(this.mapaPlatno, this.cervenyTank.getPoloha(), "blue", this.vyslednyUholModry, this.modryTank.getPoloha().getX(), this.modryTank.getPoloha().getY(), this.vybraneSuradniceKurzoruX(), this.vybraneSuradniceKurzoruY(), true);       
                this.projektilListModry.add(this.projektilModry);
                this.trojityVystrel = 1;
                this.trojityVystrelModry.add(this.projektilModry);
                this.zvukVystrelu = new Zvuk("sounds/burstFire.wav");
                this.zvukVystrelu.vyberSubor(0);
                this.zvukVystrelu.play();
            }
        }
    }
    
    /**
     * Metóda, ktorá vytvorí projektil, zaradí ho do "ArrayListu" a prehrá zvuk výstrelu.
     */
    public void cervenyVystrel() {
        if (!this.boosty.getNainstalovanyCerveny()) {
            this.projektilCerveny = new Projektil(this.mapaPlatno, this.modryTank.getPoloha(), "red", this.vyslednyUholCerveny, this.cervenyTank.getPoloha().getX(), this.cervenyTank.getPoloha().getY(), false);       
            this.projektilListCerveny.add(this.projektilCerveny);
            this.trojityVystrel = 0;
            this.zvukVystrelu = new Zvuk("sounds/vystrel.wav");
            this.zvukVystrelu.vyberSubor(0);
            this.zvukVystrelu.play();
        }
        if (this.boosty.getNainstalovanyCerveny()) {
            if (this.trojityVystrelCerveny.size() >= 3) {
                this.trojityVystrelCerveny.clear();
                this.boosty.setVystrielanyCerveny(true);
                this.boosty.setResetCerveny(true);
            } else {
                this.projektilCerveny = new Projektil(this.mapaPlatno, this.modryTank.getPoloha(), "red", this.vyslednyUholCerveny, this.cervenyTank.getPoloha().getX(), this.cervenyTank.getPoloha().getY(), true);       
                this.projektilListCerveny.add(this.projektilCerveny);
                this.trojityVystrel = 1;
                this.trojityVystrelCerveny.add(this.projektilCerveny);
                this.zvukVystrelu = new Zvuk("sounds/burstFire.wav");
                this.zvukVystrelu.vyberSubor(0);
                this.zvukVystrelu.play();
            }
        }
    }
    
    /**
     * Metóda, ktorú volá manažér každých 0,25sekundy. Ak je projektil vystrelený, nachádza sa v "ArrayListe". No ak v ňom stále existuje, posiela
     * triede "Projektil" správu aby zmenil polohu (aby letel). Ak stav projektilu je rovný "3"(vybuchnutý), automaticky projektil odstráni
     * z "ArrayListu".
     * Aktualizuje aj polohy tankov pre triedu "Boosty".
     */
    public void tik() {
        if (this.projektilListModry.size() > 0) {
            for (int i = this.projektilListModry.size() - 1; i >= 0; i--) {
                Projektil projektil = this.projektilListModry.get(i);
                projektil.zmenPolohu();
                if (projektil.getStav() == 3) {
                    this.projektilListModry.remove(projektil);
                }
            }
        }
        if (this.projektilListCerveny.size() > 0) {
            for (int i = this.projektilListCerveny.size() - 1; i >= 0; i--) {
                Projektil projektil = this.projektilListCerveny.get(i);
                projektil.zmenPolohu();
                if (projektil.getStav() == 3) {
                    this.projektilListCerveny.remove(projektil);
                }
            }
        }
        this.boosty.aktualizujPolohy(this.getPolohaModryTank(), this.getPolohaCervenyTank());
        if (this.mapaPlatno.getStavModry() == 10) {
            this.boosty.setVyhodnotenie(1);
            this.vyhodnotenieModry.zobraz();
        } else if (this.mapaPlatno.getStavCerveny() == 10) {
            this.boosty.setVyhodnotenie(1);
            this.vyhodnotenieCerveny.zobraz();
        }
    }
    
    /**
     * Getter na ďalšiu polohu pre modrý tank.
     */
    public Poloha getNextPoloha(Smer smer) {
        return this.modryTank.getPoloha().getPosunutuPolohu(this.velkostSkoku, smer);
    }
    
    /**
     * Getter na ďalšiu polohu pre červený tank.
     */
    public Poloha getNextPolohaCerveny(Smer smer) {
        return this.cervenyTank.getPoloha().getPosunutuPolohu(this.velkostSkoku, smer);
    }
}
