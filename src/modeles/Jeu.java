package modeles;

import exceptions.DisqueTropGrandException;

import java.util.Observable;

import static java.lang.Thread.sleep;

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
        setChanged();
        notifyObservers();
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
            setChanged();
            notifyObservers();
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

    /**
     * methode permettant de resoudre automatiquement des tours du meme nombre de disque
     * n'utilise pas l'avancement du joueur
     */
    public void resolutionAuto(){
        demarrerPartie(tours.getNbDisque());
        resolutionRecursive(tours.getNbDisque(), Tours.tourGauche, Tours.tourDroite, Tours.tourMilieu);
    }

    /**
     * methode qui resout recursivement le probleme de 0
     * @param n nombre de disque
     * @param dep numero de la tour de depart
     * @param arr numero de la tour d'arrive
     * @param inter numero de la tour intermediaire
     */
    public void resolutionRecursive(int n, int dep, int arr, int inter){
        if (n == 1) {
            try {//on essaie de bouger le disque
                bougerDisque(dep, arr); //faire une liste ?
            } catch (DisqueTropGrandException e){ //n'est pas cense arrive
                e.printStackTrace();
            }
        }else {
            resolutionRecursive(n-1, dep, inter, arr);
            //on deplace tous les disque sauf le plus grand de la tour de depart a la tour intermediaire
            resolutionRecursive(1, dep, arr, inter);
            //puis on deplace le plus grand disque a l'arrive
            resolutionRecursive(n-1, inter, arr, dep);
            //puis on repose tous les autres disque par dessus
        }
    }

    public int getNbMouvement() {
        return nbMouvement;
    }



}
