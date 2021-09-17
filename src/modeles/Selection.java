package modeles;

import java.util.Observable;

/**
 * Classe representant une selection entre 1 et 3
 * -1 est pas de selection
 */
public class Selection extends Observable {

    /**
     * valeur entre entre 1 et 3
     * -1 est pas de selection
     */
    private int derniereSelection;

    /**
     * contructeur de selection
     * contruit avec -1 comme valeur (pas de selection)
     */
    public Selection(){
        derniereSelection = -1;
    }

    public int getDerniereSelection() {
        return derniereSelection;
    }

    public void setDerniereSelection(int derniereSelection) {
        if (derniereSelection <= 3 && derniereSelection >= -1 && derniereSelection != 0)
            this.derniereSelection = derniereSelection;
        setChanged();
        notifyObservers();
    }
}
