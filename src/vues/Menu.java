package vues;

import controleurs.ControleurMenu;
import modeles.Jeu;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class Menu extends JPanel implements Observer {

    private final Jeu modele;

    private final JLabel nombreLabel;

    public Menu(ControleurMenu controleurMenu, Jeu modele){

        this.modele = modele;

        setLayout(new GridLayout(5,1));
        setBackground(Color.LIGHT_GRAY);

        JTextField insNumbDisque = new JTextField("4");
        insNumbDisque.addFocusListener(controleurMenu);
        this.add(insNumbDisque);

        JButton btnNouvellePartie = new JButton("Nouvelle partie");
        btnNouvellePartie.addActionListener(controleurMenu);
        this.add(btnNouvellePartie);

        String explication = "<html>Vous devez deplacer les disques de la tours de depart a l'une des deux autres tours <br>" +
                "Vous devez respecter deux regles: <br><ul>" +
                "<li> on ne peut deplacer qu'un disque a la fois </li>" +
                "<li> un disque ne peut etre pose sur un disque de diametre inferieur </li></ul></html>";
        JLabel explicationLabel = new JLabel(explication);
        this.add(explicationLabel);


        nombreLabel = new JLabel("Nombre de mouvement: " + this.modele.getNbMouvement());
        this.add(nombreLabel);

        JButton btnResoudre = new JButton("resoudre");
        btnResoudre.addActionListener(controleurMenu);
        this.add(btnResoudre);




    }


    /**
     * This method is called whenever the observed object is changed. An
     * application calls an {@code Observable} object's
     * {@code notifyObservers} method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     */
    @Override
    public void update(Observable o, Object arg) {
        nombreLabel.setText("Nombre de mouvement: " + this.modele.getNbMouvement());
    }
}
