package exceptions;

public class DisqueTropGrandException extends Exception{
    public DisqueTropGrandException(int tailleDisqueEnMouvement, int tailleDisquePose){
        super("Le disque de taille " + tailleDisqueEnMouvement + " est trop grand pour etre pose sur le disque de taille "+ tailleDisquePose);
    }
}
