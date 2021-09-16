package modeles;


import exceptions.DisqueTropGrandException;
import exceptions.TourInexistanteException;

import java.util.Observable;
import java.util.Stack;

public class Tours extends Observable {

    //constantes

    /**
     * code de chaque tours
     */
    static final public int tourGauche= 1, tourMilieu = 2, tourDroite = 3;

    //variables

    /**
     * trois tours de Hanoi
     */
    private Stack<Integer> tourD, tourM, tourG;

    private int nbDisque;

   //constructeurs

    /**
     * constructeur de Tours
     * cree les trois tours et pose les disques sur la tour de gauche
     * @param n nombre de disque a mettre sur la tour de gauche si negatif prendra la valeur 0
     */
    public Tours(int n){
        tourG = new Stack<>();
        tourM = new Stack<>();
        tourD = new Stack<>();

        nbDisque = n;
        if (nbDisque < 0)
            nbDisque = 0;

        for (int i = nbDisque; i > 0; i--){
            tourG.push(i);
        }
    }

    //methodes

    /**
     * methode permettant de bouger des disques sur des tours
     * @param tourDep code de la tour de depart
     * @param tourArr code de la tour d'arrive
     * @throws DisqueTropGrandException disque non deplaçable a cet endroit
     */
    public void bougerDisque(int tourDep, int tourArr) throws DisqueTropGrandException {
        Stack<Integer> tourDepart ,tourArrive;
        boolean toursExistent = true;

        try {
             tourDepart = getTour(tourDep);
             tourArrive = getTour(tourArr);
        } catch (Exception e){
            toursExistent = false;
            tourArrive = null;
            tourDepart = null;
        }

        if (toursExistent && !tourDepart.isEmpty()) {
            Integer disque = tourDepart.peek();


            Integer valTourArrive;
            if (tourArrive.isEmpty()){
                valTourArrive = Integer.MAX_VALUE;//pas de disque donc on peut toujours poser
            } else  {
                 valTourArrive = tourArrive.peek();
            }

            if (disque > valTourArrive)
                throw new DisqueTropGrandException(disque, valTourArrive);

            tourDepart.pop();
            tourArrive.push(disque);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * methode qui permet de recuperer la tour correspondant a un numero
     * @param index numero correspondant a la tour
     * @return tour correspondante
     * @throws TourInexistanteException renvoye si la tour n'existe pas
     */
    private Stack<Integer> getTour(int index) throws TourInexistanteException {
        return switch (index) {
            case tourGauche -> tourG;
            case tourMilieu -> tourM;
            case tourDroite -> tourD;
            default -> throw new TourInexistanteException(index);
        };
    }

    /**
     * methode permettant de verifier si on a gagner le jeu
     * condition pour gagner
     * tous les disques doivent etre sur une tour autre que cette de depart
     * @return
     */
    public boolean avoirGagner(){
        return tourG.empty() && (tourM.isEmpty() || tourD.isEmpty());
    }

    /**
     * methode qui retourne un array avec la valeur des disques
     * @param index index de la tour de laquelle on prend les disques
     * @return tableau d'entier correspondant a la taille des disques
     * @throws TourInexistanteException renvoye si l'index ne correspond a aucune tour
     */
    public Integer[] getDisques(int index) throws TourInexistanteException {
        Integer[] res = getTour(index).toArray(new Integer[0]);
        return res;
    }

    /**
     * getter de nbDisque
     * @return nombre de disque sur les tours
     */
    public int getNbDisque() {
        return nbDisque;
    }
}
