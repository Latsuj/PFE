package dvt.menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import static dvt.devint.ConstantesDevint.*;
import static dvt.menu.ConstantesMenu.*;
import dvt.devint.Fenetre;

/**
 * Permet de gerer le menu et la fenetre qui contient le menu
 * @author Justal Kevin
 */
public class Menu extends Fenetre {
    private static final long serialVersionUID = 1L;

    private GridBagConstraints c = new GridBagConstraints();
    private JPanel menuPrincipal = new JPanel();
    private int countMenu, menuSelected;

    private List<JButton> listeBoutton;
    private JLabel title;
    private int gameChoice;

    public Menu() {
        listeBoutton = new ArrayList<JButton>();
        this.getSIVOX().playWav(ACCUEIL_SON);

        GridBagLayout layoutMenu = new GridBagLayout();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(MARGE_TOP_BOT, MARGE_LEFT_RIGHT, MARGE_TOP_BOT,
                MARGE_LEFT_RIGHT);
        c.fill = GridBagConstraints.BOTH;
        c.ipady = 100;
        c.gridwidth = 3;
        menuPrincipal.setLayout(layoutMenu);

        addLabel(TITLE_GAME);
        addMenu("Jeu 1", new Action(this, 1));
        addMenu("Jeu 2", new Action(this, 2));
        addMenu("Option", new Action(this, -1));
        addMenu("Quitter", new Action(this, -2));
        addControl("DOWN", new DownAction(this));
        addControl("UP", new UpAction(this));

        this.add(menuPrincipal);
        this.setVisible(true);
    }

    public void loop() {
        while (this.isDisplayable()) {
            render();
            switch (gameChoice) {
            case -2:
                this.dispose();
                break;
            case -1:
                // TODO Option
                break;
            case 1:
                this.setVisible(false);
                this.getSIVOX().stop();
                new dvt.jeumultijoueur.Jeu().loop();
                gameChoice = 0;
                this.setVisible(true);
                break;
            case 2:
                this.setVisible(false);
                this.getSIVOX().stop();
                new dvt.jeuchronometre.Jeu().loop();
                gameChoice = 0;
                this.setVisible(true);
                break;
            default:
                break;
            }
        }
    }

    private void render() {
        menuPrincipal.setBackground(getBackground());
        this.title.setFont(getFont());
        this.title.setForeground(getForeground());

        for (int i = 0; i < listeBoutton.size(); i++) {
            if (i == menuSelected % listeBoutton.size()) {
                selectedButton(listeBoutton.get(i));
            } else {
                unselectedButton(listeBoutton.get(i));
            }
        }
    }

    /**
     * Permet d'ajouter du texte dans le menu
     * @param title Le titre du jeu
     */
    private void addLabel(String title) {
        this.title = new JLabel(title.toUpperCase(), JLabel.CENTER);
        c.weightx = c.weighty = 1.0;
        c.gridy = countMenu++;
        menuPrincipal.add(this.title, c);
    }

    /**
     * Permet d'ajouter un bouton
     * @param title Le texte sur le bouton
     * @param action L'action que l'on lie au bouton
     */
    private void addMenu(String title, ActionListener action) {
        JButton button = new JButton(title.toUpperCase());
        button.addActionListener(action);
        unselectedButton(button);
        c.weightx = c.weighty = 1.0;
        c.gridy = countMenu++;
        menuPrincipal.add(button, c);
        listeBoutton.add(button);
    }

    /**
     * Permet de gere l'action lorsque 'lon appuie sur bas
     */
    public void down() {
        unselectedButton(listeBoutton.get(menuSelected++ % listeBoutton.size()));
        selectedButton(listeBoutton.get(menuSelected % listeBoutton.size()));
        this.getSIVOX().playText(listeBoutton.get(menuSelected % listeBoutton.size()).getText(),SYNTHESE_MOYENNE);
        this.getSIVOX().playText("CETTE OPTION EST "+listeBoutton.get(menuSelected % listeBoutton.size()).getText(),SYNTHESE_MINIMALE);
    }

    /**
     * Permet de gerer l'action lorsque l'on appuie sur haut
     */
    public void up() {
        unselectedButton(listeBoutton.get(menuSelected % listeBoutton.size()));
        menuSelected = menuSelected == 0 ? (listeBoutton.size() - 1)
                : menuSelected - 1;
        selectedButton(listeBoutton.get(menuSelected % listeBoutton.size()));
        this.getSIVOX().playText(listeBoutton.get(menuSelected % listeBoutton.size()).getText(),SYNTHESE_MOYENNE);
        this.getSIVOX().playText("CETTE OPTION EST "+listeBoutton.get(menuSelected % listeBoutton.size()).getText(),SYNTHESE_MINIMALE);
    }

    /**
     * Permet de gerer le style lorsqu'un bouton n'est pas selectionner
     * @param button Le bouton sur lequel on souhaite fixer le style
     */
    private void unselectedButton(JButton button) {
        button.setFont(getFont());
        button.setBorder(new LineBorder(BORDURE_SELECTED_DEFAULT,
                BORDURE_SIZE_UNSELECTED_DEFAULT));
        button.setBackground(getButtonUnselectedBackground());
        button.setForeground(getButtonUnselectedForeground());
    }

    /**
     * Permet de gerer le style lorsqu'un bouton est selectionne
     * @param button Le bouton sur lequel on souhaite fixer le style
     */
    private void selectedButton(JButton button) {
        button.setFont(getFont().deriveFont(getFont().getSize() * 1.f + 20)); // Tricky,
                                                                              // tricky
                                                                              // Hehe
                                                                              // !
        button.setBorder(new LineBorder(BORDURE_UNSELECTED_DEFAULT,
                BORDURE_SIZE_SELECTED_DEFAULT));
        button.setBackground(getButtonSelectedBackground());
        button.setForeground(getButtonSelectedForeground());
        this.getRootPane().setDefaultButton(button);
    }

    /**
     * Permet de gerer les action lie au choix dans le menu
     * @param choice Le choix que l'on a effectue dans le menu
     */
    public void chooseChoice(int choice) {
        this.gameChoice = choice;
    }

}
