/**
 * Túto triedu som si vypožičial z cvičení. Prišla mi ako vhodná inšpirácia pre môj projekt.
 * 
 * @Patrik Macura
 * @04/01/2023
 */
public enum Smer {
    HORE (90, 0, -1),
    DOLE (270, 0, 1),
    VPRAVO (180, 1, 0),
    VLAVO (0, -1, 0);
    
    
    private int uhol;
    private int vektorX;
    private int vektorY;
    
    /**
     * Konšruktor triedy "Smer", v ktorom sa uložia vstupné atribúty do privátnych atribútov triedy "Smer".
     */
    Smer(int uhol, int vektorX, int vektorY) {
        this.uhol = uhol;
        this.vektorX = vektorX;
        this.vektorY = vektorY;
    }
    
    /**
     * Getter na vektorX.
     */
    public int getVektorX() {
        return this.vektorX;
    }
    
    /**
     * Getter na vektorY.
     */
    public int getVektorY() {
        return this.vektorY;
    }
}
