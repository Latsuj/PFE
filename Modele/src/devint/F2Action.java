package devint;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class F2Action extends AbstractAction implements ConstantesDevint {
	private static final long serialVersionUID = 1L;
	private Fenetre fenetre;
	
	public F2Action(Fenetre fenetre) {
		this.fenetre = fenetre;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		fenetre.getSIVOX().playWav(F2_SON);
	}

}