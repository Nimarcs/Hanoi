package controleurs;

import exceptions.DisqueTropGrandException;
import modeles.Tours;
import vues.Affichage;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class ControleurAffichage extends MouseInputAdapter {

    //attributs

    /**
     * Tours de hanoi
     */
    private Tours modele;

    /**
     * classe d'affichage des 3 tours
     */
    private Affichage affichage;

    /**
     * garde en memoire la derniere tour selectionne
     * valeur entre 1 et 3
     * (-1 pour pas de selection)
     */
    private int derniereSelection;

    //constructeurs

    /**
     * Contructeur de ControleurAffichage
     * @param pModele modele des tours
     * @param pAffichage affichage des tours
     */
    public ControleurAffichage(Tours pModele, Affichage pAffichage){
        modele=pModele;
        affichage=pAffichage;
        derniereSelection = -1; //pas de selection au depart
    }


    //methode

    /**
     * methode appele a chaque fois que le bouton de la souris est presse
     * @param e Mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        for (int i = 1 ; i <=3; i++ ){
            int xMin  =  (i-1) *  (affichage.getWidth()/3);
            int xMax = i * (affichage.getWidth()/3);
            //(w/3) = largeur d'une colonne

            if (!modele.avoirGagner()) {//bloque les mouvements si on a gagner
                if (e.getButton() == 3) {
                    derniereSelection = -1; //si on fait un click droit on deselectionne
                } else {
                    if (xMin <= e.getX() && e.getX() < xMax) {
                        selectionner(i);
                    }
                }
            }
        }
    }

    /**
     * methode qui permet de selectionner une tour
     * @param x numero de la tour selectionne (-1 : pas de tour)
     */
    private void selectionner(int x){
        if (x != derniereSelection && derniereSelection !=-1){
            try {
                modele.bougerDisque(derniereSelection, x);
                x= -1;
            } catch (DisqueTropGrandException e){
                System.out.println("disque trop grand");
            }
        }
        derniereSelection = x;

    }
}
