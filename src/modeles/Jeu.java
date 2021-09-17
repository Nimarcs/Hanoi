package modeles;

import exceptions.DisqueTropGrandException;

import java.util.Observable;

public class Jeu extends Observable {

    /**
     * nombre de mouvement effectue
     */
    private int nbMouvement;

    /**
     * tours de hanoi
     */
    private Tours tours;

    /**
     * element selectionne actuellement
     */
    private Selection selection;

    /**
     * contructeur de Jeu
     * @param pTours tour de hanoi
     */
    public Jeu(Tours pTours, Selection selection){
        tours = pTours;
        nbMouvement = 0;
        this.selection = selection;
    }

    /**
     * methode appele lorque l'on cherche a demarrer une nouvelle partie
     * @param nbDisque nombre de disque dans la prochaine partie
     */
    public void demarrerPartie(int nbDisque){
        tours.initialiserTours(nbDisque);
        nbMouvement = 0;
        selection.setDerniereSelection(Selection.pasSelection);
    }


    /**
     * methode permettant de bouger des disques sur des tours
     * @param tourDep code de la tour de depart
     * @param tourArr code de la tour d'arrive
     * @throws DisqueTropGrandException disque non deplaçable a cet endroit
     */
    public void bougerDisque(int tourDep, int tourArr) throws DisqueTropGrandException {
        boolean aBouge = tours.bougerDisque(tourDep, tourArr);
        if (aBouge) {
            nbMouvement++;
        }
    }

    /**
     * methode permettant de verifier si on a gagner le jeu
     * condition pour gagner
     * tous les disques doivent etre sur une tour autre que cette de depart
     * @return vrai si je la victoire est atteinte, faux sinon
     */
    public boolean avoirGagner(){
        return tours.avoirGagner();
    }

    public int getNbMouvement() {
        return nbMouvement;
    }



}
