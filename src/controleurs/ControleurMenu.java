package controleurs;

import modeles.Jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Objects;
import javax.swing.*;


public class ControleurMenu implements ActionListener, FocusListener {

    private Jeu modele;

    /**
     * dernier nombr de disque enregistre
     */
    private int nbDisque;

    /**
     * contructeur de Controleur menu
     * @param modele jeu que le menu controlle
     */
    public ControleurMenu(Jeu modele){
        this.modele = modele;
        this.nbDisque = 0;
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (JButton.class.equals( e.getSource().getClass())) {
            JButton button = (JButton) e.getSource();
            if (Objects.equals( button.getText(), "resoudre")){
                modele.resolutionAuto(true);//on veut resoudre intantanement le probleme
            } else if (Objects.equals( button.getText(), "prochain mouvement (triche)")) {
                modele.faireProchainMouvement();
            }else{
                // sinon c'est le bouton pour demarrer une autre partie
                modele.demarrerPartie(this.nbDisque, true);
            }
        }
    }

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusGained(FocusEvent e) {
        //nothing
    }

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e the event to be processed
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (JTextField.class.equals( e.getSource().getClass())) {

            JTextField jtf = (JTextField) e.getSource();
            String numStr = jtf.getText();

            try { //on essai d'avoir un nombre
                nbDisque = Integer.parseInt(numStr);
            } catch (NumberFormatException exception) {
                //sinon rien
            }
        }
    }
}
