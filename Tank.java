/**
 * V tejto triede vytvoríme tank. Táto trieda slúži na správu tanku.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public class Tank {
    
    private String team;
    private Obrazok tank;
    private Obrazok delo;
    private Smer smer;
    private Poloha poloha;
    private int poziciaX;
    private int poziciaY;
    private Mapa mapa;
    private Projektil projektil;
    private int velkostSkoku;
    private Manazer manazer;
    
    
    /**
     * Vytovrí tank na základe polohy a teamu.
     */
    public Tank(Poloha poloha, String vyberTeam) {
        this.team = vyberTeam;
        this.poloha = poloha;
        if (this.team.equals("blue")) {
            this.modryTank();
        } else {
            this.cervenyTank();
        }
        this.setPoloha(this.poloha);
        this.velkostSkoku = 3;
        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);
    }
    
    /**
     * Vracia hodnotu z privátneho atribútu "team".
     */
    public String getTeam() {
        return this.team;
    }
    
    /**
     * Vracia hodnotu z privátneho atribútu "poloha".
     */
    public Poloha getPoloha() {
        return this.poloha;
    }
    
    /**
     * Setter hodnoty polohy tanku.
     */
    public void setPoloha(Poloha poloha) {
        this.tank.zmenPolohu(poloha.getX(), poloha.getY());
        this.delo.zmenPolohu(poloha.getX(), poloha.getY());
        this.poloha = poloha;
    }
    
    /**
     * Setter uhla dela tanku.
     */
    public void zmenUholDela(int uhol) {
        this.delo.zmenUhol(uhol);
    }
    
    /**
     * Zmení delo tanku na konkrétny obrázok.
     */
    public void zmenDelo(String cestaKSuboru) {
        this.delo.zmenObrazok(cestaKSuboru);
    }
    
    /**
     * Getter uhla dela tanku.
     */
    public int getUholDela() {
        return this.delo.getUholNatoceniaObrazku();
    }
    
    /**
     * Metóda, v ktorej sa inicializujú obrázky tanku.
     */
    public void modryTank() {
        this.tank = new Obrazok("pictures/Tank/blue/PodvozokModreho.png");
        this.tank.zobraz();
        this.delo = new Obrazok("pictures/Tank/delo/delo2.png");
        this.delo.zobraz();
        //this.poloha = new Poloha(50, 50, 0);
    }
    
    /**
     * Metóda, v ktorej sa inicializujú obrázky tanku.
     */
    public void cervenyTank() {
        this.tank = new Obrazok("pictures/Tank/red/PodvozokCerveneho.png");
        this.tank.zobraz();
        this.delo = new Obrazok("pictures/Tank/delo/delo2.png");
        this.delo.zobraz();
    }
    
    /**
     * Metóda, ktorá zmení pozíciu tanku na konkrétnu polohu.
     */
    public void zmenPoziciu(Poloha poloha) {
        if (this.team.equals("blue")) {
            this.poloha = poloha;
            this.tank.skry();
            this.delo.skry();
            this.tank.zmenPolohu(this.poloha.getX(), this.poloha.getY());
            this.delo.zmenPolohu(this.poloha.getX(), this.poloha.getY());
            //this.tankModry.zmenUhol(this.poloha.getUhol());
            this.delo.zobraz();
        }
    }
    
    /**
     * Metóda, ktorá nastaví ďalšiu polohu tanku.
     */
    public void pohniNaNovuPoziciu(Poloha novaPoloha) {
        Poloha p = this.getPoloha();
        this.setPoloha(novaPoloha);
    }
    
    /**
     * Getter na ďalšiu polohu.
     */
    public Poloha getNextPoloha(Smer smer) {
        return this.getPoloha().getPosunutuPolohu(this.velkostSkoku, smer);
    }
}
