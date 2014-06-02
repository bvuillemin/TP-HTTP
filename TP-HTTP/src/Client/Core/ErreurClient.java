package Client.Core;

import HTTP.Erreur;

public class ErreurClient extends Erreur{
    public ErreurClient(){
        super();
    }
    
    public ErreurClient(int code,String s){
        super(code,s);
    }
}
