package controleurs;

import exceptions.DisqueTropGrandException;
import modeles.Jeu;
import modeles.Selection;
import modeles.Tours;
import vues.Affichage;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class ControleurAffichage extends MouseInputAdapter {

    //attributs

    /**
     * Tours de hanoi
     */
    private Jeu modele;

    /**
     * classe d'affichage des 3 tours
     */
    private Affichage affichage;

    /**
     * garde en memoire la derniere tour selectionne
     * valeur entre 1 et 3
     * (-1 pour pas de selection)
     */
    private Selection derniereSelection;

    //constructeurs

    /**
     * Contructeur de ControleurAffichage
     * @param pModele modele des tours
     * @param pAffichage affichage des tours
     */
    public ControleurAffichage(Jeu pModele, Affichage pAffichage, Selection selection){
        modele=pModele;
        affichage=pAffichage;
        derniereSelection = selection; //pas de selection au depart
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
                    derniereSelection.setDerniereSelection(Selection.pasSelection); //si on fait un click droit on deselectionne
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
        if (x != derniereSelection.getDerniereSelection() && derniereSelection.getDerniereSelection() !=-1){
            try {
                modele.bougerDisque(derniereSelection.getDerniereSelection(), x);
            } catch (DisqueTropGrandException e){
                System.out.println("disque trop grand");
            }
            x= Selection.pasSelection;
        }
        derniereSelection.setDerniereSelection(x);

    }
}
