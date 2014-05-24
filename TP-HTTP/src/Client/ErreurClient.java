package Client;

import Commun.Erreur;

public class ErreurClient extends Erreur{
    public ErreurClient(){
        super();
    }
    
    public ErreurClient(String s){
        super(s);
    }
}
