package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
