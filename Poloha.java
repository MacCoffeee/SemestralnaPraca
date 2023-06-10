
/**
 * Túto triedu som si vypožičial z cvičení. Prišla mi ako vhodná inšpirácia pre môj projekt.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public class Poloha {
    
    private final int x;
    private final int y;
    private final int uhol;
    
    /**
     * Constructor for objects of class Poloha
     */
    public Poloha(int x, int y, int uhol) {
        this.uhol = uhol;
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getUhol() {
        return this.uhol;
    }
    
    public Poloha getPosunutuPolohu(int krok, Smer smer) {
        return new Poloha(smer.getVektorX() * krok + this.x, smer.getVektorY() * krok + this.y, this.uhol);
    }
}
