package Client;

import Commun.Erreur;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private CommunicationClient com;

    public Client(InetAddress _ip, int _port) throws ErreurClient {
        try {
            this.com = new CommunicationClient(_ip, _port);
        } catch (Erreur ex) {
            throw new ErreurClient(ex.getMessage());
        }
    }
    
    /**
     * Va permettre de construire la requête puis de l'envoyer
     * @param url
     * @throws ErreurClient 
     */
    public void lancerRequete(URL url) throws ErreurClient {
        String requete = com.requeteGET(url.getFile(), "HTTP/1.1");
        if ("".equals(requete)) {
            requete = "/";
        }
        System.out.println("Client: " + requete);
        
        try {
            com.getOut().write(requete.getBytes());
            com.getOut().flush();
        } catch (IOException ex) {
            System.out.println(ex.toString());
            throw new ErreurClient("Erreur dans l'envoi");
        }
    }
}
