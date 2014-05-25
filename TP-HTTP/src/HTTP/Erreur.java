package HTTP;

public class Erreur extends Exception{
    private int codeErreur;
    
    public Erreur(){
        super();
        this.codeErreur = 0;
    }
    
    public Erreur(String s){
        super(s);
    }
}
