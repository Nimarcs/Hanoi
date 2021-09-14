package vues;

import exceptions.TourInexistanteException;
import modeles.Tours;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Affichage extends JPanel implements Observer {

    //constantes



    //attributs

    private Tours modele;

    //constructeur

    public Affichage(Tours pModele){
        if (pModele == null)
            throw new NullPointerException();
        modele = pModele;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int h = getHeight();
        int w = getWidth();

        for (int i = 0; i < 3; i++ ){
            g.drawLine((i+1) * w/16 + i*w/4, 7*h/8, (i+1) * w/16 + (i+1)*w/4, 7*h/8);
            g.drawLine((i+1)* (3*w/16) + i * (w/8), 7*h/8,(i+1)* (3*w/16) + i * (w/8), h/8 );
        }


        for (int i = 0; i < 3 ; i++){
            Integer[] disques = new Integer[0];

            try {
                disques = modele.getDisques(i);
            } catch (TourInexistanteException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < disques.length; j++ ){

            }

        }

    }

    //methodes

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

    }
}
