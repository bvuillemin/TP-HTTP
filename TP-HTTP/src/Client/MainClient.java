package Client;

import Client.Core.ErreurClient;
import Client.Core.Client;
import java.net.InetAddress;
import java.net.URL;

public class MainClient {

    /**
     * Main du client, va demander une URL et la traiter
     * @param args 
     */
    public static void main(String[] args){
        //Navigateur fenetre = new Navigateur();
        try {
            System.out.println("Démarrage client");
            Client c = new Client(InetAddress.getLocalHost(), 1086);
            c.lancerRequete(new URL("http://wwww.google.fr/"));
        } catch (ErreurClient er) {
            System.out.println("Client: " + er.getMessage());
        } catch (Exception ex) {
            System.out.println("Client : mauvaise URL");
        }
    }
}
