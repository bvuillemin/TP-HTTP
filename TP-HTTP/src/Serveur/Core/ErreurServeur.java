package Serveur.Core;

import HTTP.Erreur;

public class ErreurServeur extends Erreur{
    public ErreurServeur(){
        super();
    }
    
    public ErreurServeur(int code, String s){
        super(code, s);       
    }
}
