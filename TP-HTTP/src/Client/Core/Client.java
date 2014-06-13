package Client.Core;

import HTTP.Erreur;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
public class Client {

    public CommunicationClient com;
    public URL url;
    public String lastDomain="";
    public File file;
    
    /**
     * Va permettre de construire la requête puis de l'envoyer
     * @param url
     * @throws ErreurClient 
     */
    public void lancerRequete(URL _url) throws ErreurClient {
        url = _url;
        try {
            if (!lastDomain.equals(url.getHost())){
                InetAddress address= getIPDNS(url);
                this.com = new CommunicationClient(address, 1086);
                lastDomain = url.getHost();
            }
            
        } catch (Erreur ex) {
            throw (ErreurClient) ex;
        }
        
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
            throw new ErreurClient(500,"Erreur dans l'envoi");
        }
    }
    
    public InetAddress getIPDNS (URL url) throws ErreurClient{
        String domain=url.getHost();
        try {
        if (domain.equals("www.tp-ARAR.net")||domain.equals("localhost")){
                return InetAddress.getLocalHost();
        }
        else {
            domain=domain.replaceAll("www.","");
            return InetAddress.getByName(domain);
        }
        } catch (UnknownHostException ex) {
            throw new ErreurClient (500,"Domaine non reconnu");
        }
    }
}
