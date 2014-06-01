package Client.Core;

import HTTP.Erreur;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

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
        String file=url.getFile();
        if ("".equals(file)) {
            file = "/";
        }
        String requete = com.requeteGET(file, "HTTP/1.1");
        
        System.out.println("Client: " + requete);
        
        try {
            com.getOut().write(requete.getBytes());
            com.getOut().flush();
            this.com.attente_fichier(file);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            throw new ErreurClient("Erreur dans l'envoi");
        }
    }
}
