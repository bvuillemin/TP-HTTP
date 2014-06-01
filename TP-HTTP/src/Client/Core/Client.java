package Client.Core;

import HTTP.Erreur;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

public class Client {

    public CommunicationClient com;
    public URL url;
    public File file;

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
    public void lancerRequete(URL _url) throws ErreurClient {
        url = _url;
        String fileName=url.getFile();
        if ("".equals(fileName)) {
            fileName = "/";
        }
        String requete = com.requeteGET(fileName, "HTTP/1.1");
        
        System.out.println("Client: " + requete);
        
        try {
            com.getOut().write(requete.getBytes());
            com.getOut().flush();
            file = this.com.attente_fichier(fileName);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            throw new ErreurClient("Erreur dans l'envoi");
        }
    }
}
