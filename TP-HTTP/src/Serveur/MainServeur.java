package Serveur;

import Serveur.Core.ErreurServeur;
import Serveur.Core.Serveur;

public class MainServeur {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage serveur");
            Serveur serveur = new Serveur();
            serveur.ecoute();
            
        } catch (ErreurServeur ex) {
            System.out.println("Erreur serveur: " + ex.getMessage());
        }
    }
}
