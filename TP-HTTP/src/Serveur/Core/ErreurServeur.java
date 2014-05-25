package Serveur.Core;

import HTTP.Erreur;

public class ErreurServeur extends Erreur{
    public ErreurServeur(){
        super();
    }
    
    public ErreurServeur(String s){
        super(s);
    }
}
