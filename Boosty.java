/**
 * Táto trieda slúži na to, aby sa na mape objavovali boosty na upgrade veže tanku.
 * Ak po nich tank prejde, zmení sa jeho veža, a objaví sa ďalší boost na inom mieste.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public class Boosty {
    
    private Obrazok obrazok;
    private Obrazok gulometUpgrade1;
    private Obrazok gulometUpgrade2;
    private Obrazok gulometUpgrade3;
    private Obrazok gulometUpgrade4;
    private boolean boostAktivny1;
    private boolean boostAktivny2;
    private boolean boostAktivny3;
    private boolean boostAktivny4;
    private int stavBoostu;     //stav = 0 (Boost nenainstalovany), stav = 1 (Boost nainstalovany), stav = 2 (Boost pouzity)
    private Poloha polohaTankModry;
    private Poloha polohaTankCerveny;
    private Manazer manazer;
    private Tank tankModry;
    private Tank tankCerveny;
    private int stavBoostuVystrielane;
    private boolean nainstalovanyModry;
    private boolean nainstalovanyCerveny;
    private boolean vystrielanyModry;
    private boolean vystrielanyCerveny;
    private boolean resetModry;
    private boolean resetCerveny;
    private int vyhodnotenie;
    
    /**
     * V konštruktore triedy "Boosty" sme si inicializovali obrázky a premenné aktívnych boostov.
     * Pridali sme manažéra na správu objektov.
     */
    public Boosty(Tank modry, Tank cerveny) {
        this.tankModry = modry;
        this.tankCerveny = cerveny;
        this.gulometUpgrade1 = new Obrazok("pictures/Boosty/gulomet.png");
        this.gulometUpgrade1.zmenPolohu(270, 200);
        
        this.gulometUpgrade2 = new Obrazok("pictures/Boosty/gulomet.png");
        this.gulometUpgrade2.zmenPolohu(710, 470);
        
        this.gulometUpgrade3 = new Obrazok("pictures/Boosty/gulomet.png");
        this.gulometUpgrade3.zmenPolohu(500, 750);
        
        this.gulometUpgrade4 = new Obrazok("pictures/Boosty/gulomet.png");
        this.gulometUpgrade4.zmenPolohu(150, 650);
        
        this.boostAktivny1 = true;
        this.boostAktivny2 = false;
        this.boostAktivny3 = false;
        this.boostAktivny4 = false;
        this.stavBoostu = 0;
        this.stavBoostuVystrielane = 0;
        this.nainstalovanyModry = false;
        this.nainstalovanyCerveny = false;
        this.vystrielanyModry = false;
        this.vystrielanyCerveny = false;
        
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
        this.vyhodnotenie = 0;
    }
    
    /**
     * Setter atribútu "vyhodnotenie".
     */
    public void setVyhodnotenie(int x) {
        this.vyhodnotenie = x;
    }
    
    /**
     * Túto metódu posiela pravidelne každých 0,25sekundy manažér. Slúži na to, aby volala metódy ktoré aktualizujú
     * privátne atribúty triedy.
     */
    public void tik() {
        if (this.vyhodnotenie == 0) {
            this.aktualizujStavBoostu();
            this.zoberBoost();
            this.aktualizujZobrazenieBoostu();
        }
    }
    
    /**
     * Metóda, ktorá aktualizuje polohy tankov.
     */
    public void aktualizujPolohy(Poloha modryTank, Poloha cervenyTank) {
        this.polohaTankModry = modryTank;
        this.polohaTankCerveny = cervenyTank;
    }
    
    /**
     * Metóda, ktorá aktualizuje boosty na aktívne/neaktívne.
     */
    public void aktualizujZobrazenieBoostu() {
        if (this.boostAktivny1) {
            this.gulometUpgrade1.zobraz();
        }
        if (this.boostAktivny2) {
            this.gulometUpgrade2.zobraz();
        }
        if (this.boostAktivny3) {
            this.gulometUpgrade3.zobraz();
        }
        if (this.boostAktivny4) {
            this.gulometUpgrade4.zobraz();
        }
        
        if (!this.boostAktivny1) {
            this.gulometUpgrade1.skry();
        }
        if (!this.boostAktivny2) {
            this.gulometUpgrade2.skry();
        }
        if (!this.boostAktivny3) {
            this.gulometUpgrade3.skry();
        }
        if (!this.boostAktivny4) {
            this.gulometUpgrade4.skry();
        }
    }
    
    /**
     * Setter na boolean hodnotu ktorá určuje, či je tank vystrieľany alebo nie.
     */
    public void setVystrielanyModry(boolean bool) {
        this.vystrielanyModry = bool;
    }
    
    /**
     * Setter na boolean hodnotu ktorá určuje, či je tank vystrieľany alebo nie.
     */
    public void setVystrielanyCerveny(boolean bool) {
        this.vystrielanyCerveny = bool;
    }
    
    /**
     * Getter na boolean hodnotu ktorá určuje, či je tank vystrieľany alebo nie.
     */
    public boolean getVystrielanyModry() {
        return this.vystrielanyModry;
    }
    
    /**
     * Getter na boolean hodnotu ktorá určuje, či je tank vystrieľany alebo nie.
     */
    public boolean getVystrielanyCerveny() {
        return this.vystrielanyCerveny;
    }
    
    /**
     * Getter na boolean hodnotu ktorá určuje, či modrý tank má nainštalovanú špeciálnu vežu.
     */
    public boolean getNainstalovanyModry() {
        return this.nainstalovanyModry;
    }
    
    /**
     * Getter na boolean hodnotu ktorá určuje, či červený tank má nainštalovanú špeciálnu vežu.
     */
    public boolean getNainstalovanyCerveny() {
        return this.nainstalovanyCerveny;
    }
    
    /**
     * Setter ktorý nastaví boolean hodnotu atributu "resetModry", na základe ktorej sa obnovia defaultné hodnoty.
     */
    public void setResetModry(boolean bool) {
        this.resetModry = bool;
    }
    
    /**
     * Setter ktorý nastaví boolean hodnotu atributu "resetCerveny", na základe ktorej sa obnovia defaultné hodnoty.
     */
    public void setResetCerveny(boolean bool) {
        this.resetCerveny = bool;
    }
    
    /**
     * Metóda, ktorá aktualizuje stavy boostov.
     */
    public void aktualizujStavBoostu() {
        if (this.vystrielanyModry && this.nainstalovanyModry) {
            this.nainstalovanyModry = false;
            this.tankModry.zmenDelo("pictures/Tank/delo/delo2.png");
        }
        if (this.resetModry) {
            this.resetModry = false;
            this.vystrielanyModry = false;
            this.nainstalovanyModry = false;
        }
        if (this.vystrielanyCerveny && this.nainstalovanyCerveny) {
            this.nainstalovanyCerveny = false;
            this.tankCerveny.zmenDelo("pictures/Tank/delo/delo2.png");
        }
        if (this.resetCerveny) {
            this.resetCerveny = false;
            this.vystrielanyCerveny = false;
            this.nainstalovanyCerveny = false;
        }
    }
    
    /**
     * Metóda, ktorá zistí či tank prešiel bo booste a následne mu nastaví špeciálnu vežu. Boost sa deaktivuje a zobrazí sa ďalší.
     */
    public void zoberBoost() {
        if (this.polohaTankModry.getX() >= 240 && this.polohaTankModry.getX() <= 300        //x -30 +30 y -14 + 14
            && this.polohaTankModry.getY() >= 186 && this.polohaTankModry.getY() <= 214
            && !this.nainstalovanyModry) {
            if (this.boostAktivny1) {
                this.boostAktivny1 = false;
                this.boostAktivny2 = true;
                Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
                upgrade.vyberSubor(0);
                upgrade.play();
                this.tankModry.zmenDelo("pictures/Tank/delo/deloGulomet.png");
                this.nainstalovanyModry = true;
            }
        }
        if (this.polohaTankCerveny.getX() >= 240 && this.polohaTankCerveny.getX() <= 300        //x -30 +30 y -14 + 14
            && this.polohaTankCerveny.getY() >= 186 && this.polohaTankCerveny.getY() <= 214
            && !this.nainstalovanyCerveny) {
            if (this.boostAktivny1) {
                this.boostAktivny1 = false;
                this.boostAktivny2 = true;
                Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
                upgrade.vyberSubor(0);
                upgrade.play();
                this.tankCerveny.zmenDelo("pictures/Tank/delo/deloGulomet.png");
                this.nainstalovanyCerveny = true;
            }
        }
        
        if (this.polohaTankModry.getX() >= 680 && this.polohaTankModry.getX() <= 740
            && this.polohaTankModry.getY() >= 456 && this.polohaTankModry.getY() <= 484
            && !this.nainstalovanyModry) {
            if (this.boostAktivny2) {
                this.boostAktivny2 = false;
                this.boostAktivny3 = true;
                Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
                upgrade.vyberSubor(0);
                upgrade.play();
                this.tankModry.zmenDelo("pictures/Tank/delo/deloGulomet.png");
                this.nainstalovanyModry = true;
            }
        }
        if (this.polohaTankCerveny.getX() >= 680 && this.polohaTankCerveny.getX() <= 740
            && this.polohaTankCerveny.getY() >= 456 && this.polohaTankCerveny.getY() <= 484
            && !this.nainstalovanyCerveny) {
            if (this.boostAktivny2) {
                this.boostAktivny2 = false;
                this.boostAktivny3 = true;
                Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
                upgrade.vyberSubor(0);
                upgrade.play();
                this.tankCerveny.zmenDelo("pictures/Tank/delo/deloGulomet.png");
                this.nainstalovanyCerveny = true;
            }
        }
        
        if (this.polohaTankModry.getX() >= 470 && this.polohaTankModry.getX() <= 530
            && this.polohaTankModry.getY() >= 736 && this.polohaTankModry.getY() <= 764
            && !this.nainstalovanyModry) {
            this.boostAktivny3 = false;
            this.boostAktivny4 = true;
            Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
            upgrade.vyberSubor(0);
            upgrade.play();
            this.tankModry.zmenDelo("pictures/Tank/delo/deloGulomet.png");
            this.nainstalovanyModry = true;
        }
        if (this.polohaTankModry.getX() >= 470 && this.polohaTankModry.getX() <= 530
            && this.polohaTankModry.getY() >= 736 && this.polohaTankModry.getY() <= 764
            && !this.nainstalovanyModry) {
            this.boostAktivny3 = false;
            this.boostAktivny4 = true;
            Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
            upgrade.vyberSubor(0);
            upgrade.play();
            this.tankModry.zmenDelo("pictures/Tank/delo/deloGulomet.png");
            this.nainstalovanyModry = true;
        }
        
        if (this.polohaTankModry.getX() >= 120 && this.polohaTankModry.getX() <= 180
            && this.polohaTankModry.getY() >= 636 && this.polohaTankModry.getY() <= 664
            && !this.nainstalovanyModry) {
            this.boostAktivny4 = false;
            this.boostAktivny1 = true;
            Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
            upgrade.vyberSubor(0);
            upgrade.play();
            this.tankModry.zmenDelo("pictures/Tank/delo/deloGulomet.png");
            this.nainstalovanyModry = true;
        }
        if (this.polohaTankCerveny.getX() >= 120 && this.polohaTankCerveny.getX() <= 180
            && this.polohaTankCerveny.getY() >= 636 && this.polohaTankCerveny.getY() <= 664
            && !this.nainstalovanyCerveny) {
            this.boostAktivny4 = false;
            this.boostAktivny1 = true;
            Zvuk upgrade = new Zvuk("sounds/upgrade.wav");
            upgrade.vyberSubor(0);
            upgrade.play();
            this.tankCerveny.zmenDelo("pictures/Tank/delo/deloGulomet.png");
            this.nainstalovanyCerveny = true;
        }
    }
}
