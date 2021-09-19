package modeles;

import exceptions.DisqueTropGrandException;

import java.util.LinkedList;
import java.util.List;
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
     * liste de mouvement permettant de resoudre la solution sur la tour de droite
     */
    private List<Mouvement> solutionD;

    /**
     * liste de mouvement permettant de resoudre la solution sur la tour du milieu
     */
    private List<Mouvement> solutionM;

    /**
     * contructeur de Jeu
     * @param pTours tour de hanoi
     */
    public Jeu(Tours pTours, Selection selection){
        tours = pTours;
        nbMouvement = 0;
        this.selection = selection;
        demarrerPartie(4, true);
    }

    /**
     * methode appele lorque l'on cherche a demarrer une nouvelle partie
     * maximum de 25 disques (car deja realistiquement infaisable par un humain (33 million de mouvement necessaire)
     * @param nbDisque nombre de disque dans la prochaine partie
     */
    public void demarrerPartie(int nbDisque, boolean genereSolution){
        if (nbDisque > 25)
            nbDisque = 25;
        tours.initialiserTours(nbDisque);
        nbMouvement = 0;
        selection.setDerniereSelection(Selection.pasSelection);
        solutionD = new LinkedList<>();
        solutionM = new LinkedList<>();
        if (genereSolution)
            resolutionAuto(false);//genere la liste de solution
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

            //si on a generer une solution
            if (solutionGenerer()) {

                //si on a fait le bon mouvement pour la tour de droite
                if (tourDep == solutionD.get(0).getDep() && tourArr == solutionD.get(0).getArr()) {
                    solutionD.remove(0);//on retire le mouvement de la liste des mouvement a faire
                } else {
                    solutionD.add(0, new Mouvement(tourDep, tourArr).inverse());//sinon on ajoute le mouvement contraire
                }

                //si on a fait le bon mouvement pour la tour du milieu
                if (tourDep == solutionM.get(0).getDep() && tourArr == solutionM.get(0).getArr()) {
                    solutionM.remove(0);//on retire le mouvement de la liste des mouvement a faire
                } else {
                    solutionM.add(0, new Mouvement(tourDep, tourArr).inverse());//sinon on ajoute le mouvement contraire
                }
            }

            setChanged();
            notifyObservers();
        }
    }

    /**
     * methode qui permet de savoir si la solution a ete genere
     * si une solution est atteinte elle compte comme non generer
     * @return vrai si la solution a ete genere, faux sinon
     */
    private boolean solutionGenerer(){
        return (!(solutionM.size() == 0 || solutionD.size() == 0));
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
     * methode qui permet de faire le prochain mouvement conseille
     */
    public void faireProchainMouvement(){

        if (solutionGenerer()) {//si il n'y pas de solution on ne peut pas trouver le prochain mouvement
            List<Mouvement> solution; //on resout avec la solution la plus rapide
            if (solutionD.size() > solutionM.size()) {
                solution = solutionM;
            } else {
                solution = solutionD;
            }

            try {
                bougerDisque(solution.get(0).getDep(), solution.get(0).getArr());
            } catch (DisqueTropGrandException e) {
                e.printStackTrace();
                //pas cense arriver
            }
        }
    }

    /**
     * methode permettant de resoudre automatiquement des tours du meme nombre de disque
     * n'utilise pas l'avancement du joueur
     * @param estInstantane si vrai alors la resolution sera instantane sinon elle generera juste la liste solution
     */
    public void resolutionAuto(boolean estInstantane){
        if (estInstantane) {
            demarrerPartie(tours.getNbDisque(), false);
            resolutionRecursive(tours.getNbDisque(), Tours.tourGauche, Tours.tourDroite, Tours.tourMilieu, (!estInstantane),null );
        } else {
            resolutionRecursive(tours.getNbDisque(), Tours.tourGauche, Tours.tourDroite, Tours.tourMilieu, (!estInstantane), solutionD );
            resolutionRecursive(tours.getNbDisque(), Tours.tourGauche, Tours.tourMilieu, Tours.tourDroite, (!estInstantane), solutionM );
        }
    }

    /**
     * methode qui resout recursivement le probleme de 0
     * @param n nombre de disque
     * @param dep numero de la tour de depart
     * @param arr numero de la tour d'arrive
     * @param inter numero de la tour intermediaire
     * @param doitGenererListe booleen vrai dans le cas ou l'on a besoin de generer la liste des mouvements pour resoudre, faux dans le cas ou l'on veut juste faire les mouvements
     */
    public void resolutionRecursive(int n, int dep, int arr, int inter, boolean doitGenererListe, List<Mouvement> solution){
        if (n == 1) {
            if (doitGenererListe){
                solution.add(new Mouvement(dep, arr));
            }else { //si on veut juste faire les mouvements
                try {//on essaie de bouger le disque
                    bougerDisque(dep, arr); //faire une liste ?
                } catch (DisqueTropGrandException e) { //n'est pas cense arrive
                    e.printStackTrace();
                }
            }
        }else {
            resolutionRecursive(n-1, dep, inter, arr, doitGenererListe, solution);
            //on deplace tous les disque sauf le plus grand de la tour de depart a la tour intermediaire
            resolutionRecursive(1, dep, arr, inter, doitGenererListe, solution);
            //puis on deplace le plus grand disque a l'arrive
            resolutionRecursive(n-1, inter, arr, dep, doitGenererListe, solution);
            //puis on repose tous les autres disque par dessus
        }
    }

    public int getNbMouvement() {
        return nbMouvement;
    }



}
