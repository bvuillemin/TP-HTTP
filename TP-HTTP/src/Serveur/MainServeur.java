package Serveur;

public class MainServeur {
    public static void main(String[] args) {
        try {
            System.out.println("Démarrage serveur");
            Serveur serveur = new Serveur();
            serveur.accept();
            
        } catch (ErreurServeur ex) {
            System.out.println("Erreur serveur: " + ex.getMessage());
        }
    }
}
