package modeles;

public class Mouvement {

    /**
     * indice des tour de depart et d'arrive
     */
    private int dep, arr;

    /**
     * constructeur basique de Mouvement
     * @param dep indice de la tour de depart
     * @param arr indice de la tour d'arr
     */
    public Mouvement(int dep,int arr){
        this.dep = dep;
        this.arr = arr;
    }

    /**
     * contructeur copie de mouvement
     * duplique un mouvement
     * @param mvt mouvement a copier
     */
    public Mouvement(Mouvement mvt){
        if (mvt == null)
            throw new NullPointerException();
        this.dep = mvt.dep;
        this.arr = mvt.arr;
    }

    /**
     * methode qui permet d'avoir le mouvement inverse de this
     * @return mouvement inverse de this
     */
    public Mouvement inverse(){
        return new Mouvement(arr, dep);
    }

    public int getArr() {
        return arr;
    }

    public int getDep() {
        return dep;
    }

    @Override
    public String toString() {
        return "Mouvement{" +
                "dep=" + dep +
                ", arr=" + arr +
                '}';
    }
}
