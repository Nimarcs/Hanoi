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

        final int yBaseTour = 7*h/8, yHautTour = h/8, hauteurDisque = (yBaseTour - yHautTour) / modele.getNbDisque();


        for (int i = 0; i < 3; i++ ){
            g.drawLine((i+1) * w/16 + i*w/4, yBaseTour, (i+1) * w/16 + (i+1)*w/4, yBaseTour);
            g.drawLine((i+1)* (3*w/16) + i * (w/8), yBaseTour,(i+1)* (3*w/16) + i * (w/8), yHautTour );
        }


        for (int i = 0; i < 3 ; i++){
            Integer[] disques = new Integer[0];

            try {
                disques = modele.getDisques(i);
            } catch (TourInexistanteException e) {
                e.printStackTrace();//erreur theoriquement impossible
            }

            for (int j = 0; j < disques.length; j++ ){
                //largeur maximum d'une marche
                final int maxLargeur = ((i + 1) * w / 4);
                //largeur disponible pour chaque marche
                final double largeurMarche = ((maxLargeur - (w/10.0)) /  (double) (modele.getNbDisque())) ;
                //largeur retirer des marches pour faire les paliers
                final int largeurRetire = (int) (1.5* (largeurMarche) * (double) (modele.getNbDisque()+1 - disques[j])) ;
                //valeur de x sur le cote gauche de la ième tour
                final int xCoteGaucheTour = (i + 1) * w / 16 + i * w / 4;

                g.setColor(Color.gray);
                g.fillRect(xCoteGaucheTour + largeurRetire/2,yBaseTour - ((j+1)*hauteurDisque) , maxLargeur - largeurRetire , hauteurDisque );

                g.setColor(Color.black);
                g.drawRect(xCoteGaucheTour + largeurRetire/2,yBaseTour - ((j+1)*hauteurDisque) , maxLargeur - largeurRetire , hauteurDisque );
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
        repaint();
    }
}
