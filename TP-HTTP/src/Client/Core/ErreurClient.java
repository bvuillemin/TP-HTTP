package Client.Core;

import HTTP.Erreur;

public class ErreurClient extends Erreur{
    public ErreurClient(){
        super();
    }
    
    public ErreurClient(String s){
        super(s);
    }
}
