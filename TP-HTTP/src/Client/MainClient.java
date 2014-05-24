package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

public class MainClient{
    public static void main(String[] args) {
        Navigateur fenetre = new Navigateur();
        try {
            System.out.println("Démarrage client");
            lancerNavigateur (InetAddress.getLocalHost(),new URL("http://wwww.google.fr/"));
        } catch (Exception ex) {
            System.out.println("Erreur client: " + ex.getMessage());
        }       
    }
    
    public static void lancerNavigateur(InetAddress ip, URL url) throws ErreurClient{
        CommunicationClient com = new CommunicationClient(ip,80);
        String requete = com.requeteGET(url.getFile(), "HTTP/1.1");
        if ("".equals(requete)){
            requete="/";
        }
        System.out.println("Client: " + requete);
        try {
            com.getOut().write(requete.getBytes());
            com.getOut().flush();
        } catch (IOException ex) {
            throw new ErreurClient("Erreur dans l'envoi");
        }
    }
    
    public void fonctionnement_client(){
        
    }
    
}
