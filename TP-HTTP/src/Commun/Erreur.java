package Commun;

public class Erreur extends Exception{
    private int codeErreur;
    
    public Erreur(){
        super();
        this.codeErreur = 0;
    }
}
