package jeu2;

import javax.swing.JLabel;
import javax.swing.JPanel;

import menu.DownAction;

import com.sun.glass.events.KeyEvent;

import jeu2.Action;
import jeu2.ConstantesJeu;
import devint.Fenetre;

public class Jeu extends Fenetre implements ConstantesJeu {
	private static final long serialVersionUID = 1L;

	private Chronometer ch;
	
	private JPanel world;
    private JLabel info;
    private int count;
    private boolean pressed;
    
    private int record;
	
    public Jeu() {
        init();
        this.setVisible(true);
        this.setFocusable(true);
	}
    
    public void init() {
    	world = new JPanel();
    	world.setBackground(getForeground());
    	world.setLayout(null); 
    	
    	addControlDown(KeyEvent.VK_SPACE,new Action(this,true));
    	addControlUp(KeyEvent.VK_SPACE,new Action(this,false));
    	addControlDown(KeyEvent.VK_ENTER,new Restart(this));
    	
   		info = new JLabel(CONSIGNE,JLabel.CENTER);
   		info.setFont(getFont());
		info.setVisible(true);
   		world.add(info);
    	
    	this.add(world);
    }
    
	public void loop() {
		   long lastLoopTime = System.nanoTime();
		   final int TARGET_FPS = 60;
		   final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
		   
		   reset();
	       while (this.isDisplayable()) {
	            long now = System.nanoTime();
	            lastLoopTime = now;
	            
	            if(count>0) {
	            	update();
	            }
	        	render();
	        	
	        	try {
					Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch(IllegalArgumentException e) {
					
				}
	        }
	    }

	private void reset() {
		count=0;
    	ch = new Chronometer();
    	this.pressed = true;
    	info.setText(CONSIGNE);
	}

	private void render() {
		info.setBounds(0, 0, this.getWidth(), this.getHeight());
    	world.setBackground(getBackground());
	}

	private void update() {
		ch.stop();
		int seconds = (int)ch.getSeconds();
		if(seconds<10) {
			info.setText("<html><center>"+ch.getChrono()+"<br /><br />"+String.valueOf(count)+"</center></html>");
			record = count;
		} else {
			info.setText("<html><center>BRAVO<br />Vous avez appuye "+record+" fois sur espace<br /><br /><br />Pour recommencer, appuyez sur 'Entree'</center></html>");
		}
	}

	public void action(boolean value) {
		if(pressed) {
			if(count==0) ch.start();
			count++;
			pressed = false;
		}
		if(!value) pressed = true;
	}

	public void restart() {
		reset();
	}
}
