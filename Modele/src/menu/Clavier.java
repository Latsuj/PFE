package menu;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;

public class Clavier extends Thread implements KeyListener,ConstantesMenu {
    private Set<Integer> keyPressed;
    
    public Clavier(Set<Integer> keyPressed) {
        this.keyPressed = keyPressed;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	System.out.println("key");
    	keyPressed.add(e.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public Set<Integer> getPressed() {
    	return keyPressed;
    }
}