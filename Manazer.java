import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

/**
 * 
 * Trieda je vypožičaná z cvičení, nie je súčasťou mojej časti programu ktorú mám pridelenú.
 * Je upravená tak, aby spĺňala požiadavky pre môj projekt.
 * 
 * 
 * Automaticky posiela spravy danym objektom:<br />
 * posunDole() - pri stlaceni klavesy DOWN<br />
 * posunHore() - pri stlaceni klavesy UP<br />
 * posunVlavo() - pri stlacen klavesy LEFT<br />
 * posunVpravo() - pri stlaceni klavesy RIGHT<br />
 * vystrel() - pri stlaceni klavesy ENTER alebo SPACE<br />
 * zrus() - pri stlaceni klavesy ESC<br />
 * tik() - kazdych 0,25 sekundy<br />
 * vyberSuradnice(x, y) - pri kliknuti mysou
 */
public class Manazer {
    private ArrayList<Object> spravovaneObjekty;
    private ArrayList<Integer> vymazaneObjekty;
    
    private long oldTick;
    private static final long TICK_LENGTH = 25000000;
    
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;
    private boolean controlPressed = false;
    private boolean shiftPressed = false;
    private boolean spacePressed = false;
    
    private class ManazerKlaves extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                //Manazer.this.posliSpravu("posunDole");
                Manazer.this.downPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                //Manazer.this.posliSpravu("posunHore");
                Manazer.this.upPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                //Manazer.this.posliSpravu("posunVlavo");
                Manazer.this.leftPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                //Manazer.this.posliSpravu("posunVpravo");
                Manazer.this.rightPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_W) {
                //Manazer.this.posliSpravu("cervenyHore");
                Manazer.this.wPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_A) {
                //Manazer.this.posliSpravu("cervenyVlavo");
                Manazer.this.aPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_D) {
                //Manazer.this.posliSpravu("cervenyVpravo");
                Manazer.this.dPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_S) {
                //Manazer.this.posliSpravu("cervenyDole");
                Manazer.this.sPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                //Manazer.this.posliSpravu("cervenyVystrel");
                Manazer.this.spacePressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Manazer.this.posliSpravu("zrus");
            }
            if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
                //Manazer.this.posliSpravu("cervenyOtocVezuProtiSmeru");
                Manazer.this.shiftPressed = true;
            }
            if (event.getKeyCode() == KeyEvent.VK_CONTROL) {
                //Manazer.this.posliSpravu("cervenyOtocVezuVSmere");
                Manazer.this.controlPressed = true;
            }
        }
        
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                Manazer.this.upPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                Manazer.this.downPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                Manazer.this.leftPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                Manazer.this.rightPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_W) {
                Manazer.this.wPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_A) {
                Manazer.this.aPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_S) {
                Manazer.this.sPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_D) {
                Manazer.this.dPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
                Manazer.this.shiftPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_CONTROL) {
                Manazer.this.controlPressed = false;
            }
            if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                Manazer.this.spacePressed = false;
                Manazer.this.posliSpravu("cervenyVystrel");
            }
            if (Manazer.this.shiftPressed) {
                Manazer.this.posliSpravu("cervenyOtocVezuProtiSmeru");
            }
            if (Manazer.this.controlPressed) {
                Manazer.this.posliSpravu("cervenyOtocVezuVSmere");
            }
        }
    }
    
    private class ManazerCasovaca implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - Manazer.this.oldTick >= Manazer.TICK_LENGTH || newTick < Manazer.TICK_LENGTH) {
                Manazer.this.oldTick = (newTick / Manazer.TICK_LENGTH) * Manazer.TICK_LENGTH;
                Manazer.this.posliSpravu("tik");
                if (Manazer.this.downPressed) {
                    Manazer.this.posliSpravu("posunDole");
                }
                if (Manazer.this.upPressed) {
                    Manazer.this.posliSpravu("posunHore");
                }
                if (Manazer.this.rightPressed) {
                    Manazer.this.posliSpravu("posunVpravo");
                }
                if (Manazer.this.leftPressed) {
                    Manazer.this.posliSpravu("posunVlavo");
                }
                if (Manazer.this.wPressed) {
                    Manazer.this.posliSpravu("cervenyHore");
                }
                if (Manazer.this.aPressed) {
                    Manazer.this.posliSpravu("cervenyVlavo");
                }
                if (Manazer.this.sPressed) {
                    Manazer.this.posliSpravu("cervenyDole");
                }
                if (Manazer.this.dPressed) {
                    Manazer.this.posliSpravu("cervenyVpravo");
                }
                if (Manazer.this.shiftPressed) {
                    Manazer.this.posliSpravu("cervenyOtocVezuProtiSmeru");
                }
                if (Manazer.this.controlPressed) {
                    Manazer.this.posliSpravu("cervenyOtocVezuVSmere");
                }
            }
        }
        
    }
    
    private class ManazerMysi extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                Manazer.this.posliSpravu("vyberSuradnice", event.getX(), event.getY());
                Manazer.this.posliSpravu("vystrel");
            }
        }
    }
    
    /**
     * Pridal som aj "MouseMotionListener", kedže táto trieda ho ešte nemala.
     */
    public class ManazerPohybuMysi extends MouseAdapter {
        public void mouseMoved(MouseEvent event) {
            Manazer.this.posliSpravu("vyberSuradnicePohybuMysi", event.getX(), event.getY());
        }
    }
    
    private void posliSpravu(String selektor) {
        for (Object adresat : this.spravovaneObjekty) {
            try {
                if (adresat != null) {                    
                    Method sprava = adresat.getClass().getMethod(selektor);
                    sprava.invoke(adresat);
                }
            } catch (SecurityException e) {
                this.doNothing();
            } catch (NoSuchMethodException e) {
                this.doNothing();
            } catch (IllegalArgumentException e) {
                this.doNothing();
            } catch (IllegalAccessException e) {
                this.doNothing();
            } catch (InvocationTargetException e) {
                this.doNothing();
            }
        }
        this.removeDeletedObjects();
    }
    
    private void posliSpravu(String selektor, int prvyParameter, int druhyParameter) {
        for (Object adresat : this.spravovaneObjekty) {
            try {
                if (adresat != null) {
                    Method sprava = adresat.getClass().getMethod(selektor, Integer.TYPE, Integer.TYPE);
                    sprava.invoke(adresat, prvyParameter, druhyParameter);
                }
            } catch (SecurityException e) {
                this.doNothing();
            } catch (NoSuchMethodException e) {
                this.doNothing();
            } catch (IllegalArgumentException e) {
                this.doNothing();
            } catch (IllegalAccessException e) {
                this.doNothing();
            } catch (InvocationTargetException e) {
                this.doNothing();
            }
        }
        this.removeDeletedObjects();
    }
    
    private void doNothing() {
        
    }
    
    private void removeDeletedObjects() {
        if (this.vymazaneObjekty.size() > 0) {
            Collections.sort(this.vymazaneObjekty, Collections.reverseOrder());
            for (int i = this.vymazaneObjekty.size() - 1; i >= 0; i--) {
                this.spravovaneObjekty.remove(this.vymazaneObjekty.get(i));
            }
            this.vymazaneObjekty.clear();
        }        
    }
    
    /**
     * Vytvori novy manazer, ktory nespravuje zatial ziadne objekty.
     * Pridal som aj "MouseMotionListener", kedže táto trieda ho ešte nemala.
     */
    public Manazer() {
        this.spravovaneObjekty = new ArrayList<Object>();
        this.vymazaneObjekty = new ArrayList<Integer>();
        Platno.dajPlatno().addKeyListener(new ManazerKlaves());
        Platno.dajPlatno().addTimerListener(new ManazerCasovaca());
        Platno.dajPlatno().addMouseListener(new ManazerMysi());
        Platno.dajPlatno().addMouseMotionListener(new ManazerPohybuMysi());
    }
    
    /**
     * Manazer bude spravovat dany objekt.
     */
    public void spravujObjekt(Object objekt) {
        this.spravovaneObjekty.add(objekt);
    }
    
    /**
     * Manazer prestane spravovat dany objekt.
     */
    public void prestanSpravovatObjekt(Object objekt) {
        int index = this.spravovaneObjekty.indexOf(objekt);
        if (index >= 0) {
            this.spravovaneObjekty.set(index, null);
            this.vymazaneObjekty.add(index);
        }
    }
}