package exceptions;

public class TourInexistanteException extends Exception{
    public TourInexistanteException(int i){
        super("La tour numero " + i + " n'existe pas");
    }
}
