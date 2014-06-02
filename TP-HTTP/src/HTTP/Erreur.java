package HTTP;

public class Erreur extends Exception{
    private int codeErreur;
    
    public Erreur(){
        super();
        this.codeErreur = 0;
    }
    
    public Erreur(int code,String s){
        super(s);
        this.codeErreur = code;
    }
}
