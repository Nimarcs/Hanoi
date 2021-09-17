package vues;

import exceptions.TourInexistanteException;
import modeles.Selection;
import modeles.Tours;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Affichage extends JPanel implements Observer {

    //constantes



    //attributs

    /**
     * modele du systeme (les tour de hanoi)
     */
    private Tours modele;

    private Selection selection;

    //constructeur

    /**
     * contructeur de l'affichage
     * @param pModele modele du systeme (les tour de hanoi)
     */
    public Affichage(Tours pModele, Selection selection){
        this.selection = selection;

        if (pModele == null)
            throw new NullPointerException();
        modele = pModele;

    }

    //methodes

    /**
     * methode appele a chaque fois que la fenetre est redimentione ou que repaint est appele
     * ne pas appeler manuellement
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int h = getHeight();
        int w = getWidth();

        //si on a gagner on affiche gg
        if (modele.avoirGagner()) {
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, w ,h);
            g.setFont(g.getFont().deriveFont((float) 50));
            g.setColor(Color.red);
            g.drawString("GG",w/3, h/2);

        } else {//sinon les tours

            final int yBaseTour = 7 * h / 8, yHautTour = h / 8, hauteurDisque = (yBaseTour - yHautTour) / modele.getNbDisque();

            //affichage des "tours"
            for (int i = 0; i < 3; i++) {
                g.drawLine((i + 1) * w / 16 + i * w / 4, yBaseTour, (i + 1) * w / 16 + (i + 1) * w / 4, yBaseTour);
                g.drawLine((i + 1) * (3 * w / 16) + i * (w / 8), yBaseTour, (i + 1) * (3 * w / 16) + i * (w / 8), yHautTour);
            }

            //affichage de la selection
            if (selection.getDerniereSelection() != -1){
                int i = selection.getDerniereSelection();
                g.setColor(Color.red);
                g.fillOval( i * w / 16 + (i - 1) * w / 4, yBaseTour + w/20, w/20, w/20);
            }

            //affichage des disques
            for (int i = 1; i <= 3; i++) {
                Integer[] disques = new Integer[0];

                try {
                    disques = modele.getDisques(i);
                } catch (TourInexistanteException e) {
                    e.printStackTrace();//erreur theoriquement impossible
                }

                for (int j = 0; j < disques.length; j++) {
                    //largeur maximum d'une marche
                    final int maxLargeur = (w / 4);
                    //largeur disponible pour chaque marche
                    final double largeurMarche = ((maxLargeur - (w / 10.0)) / (double) (modele.getNbDisque()));
                    //largeur retirer des marches pour faire les paliers
                    final int largeurRetire = (int) (1.5 * (largeurMarche) * (double) (modele.getNbDisque() + 1 - disques[j]));
                    //valeur de x sur le cote gauche de la ième tour
                    final int xCoteGaucheTour = i * w / 16 + (i - 1) * w / 4;

                    g.setColor(Color.gray);
                    g.fillRect(xCoteGaucheTour + largeurRetire / 2, yBaseTour - ((j + 1) * hauteurDisque), maxLargeur - largeurRetire, hauteurDisque);

                    g.setColor(Color.black);
                    g.drawRect(xCoteGaucheTour + largeurRetire / 2, yBaseTour - ((j + 1) * hauteurDisque), maxLargeur - largeurRetire, hauteurDisque);
                }

            }
        }
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
        repaint();
    }
}
