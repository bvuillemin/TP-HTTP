package Client.Core;

import HTTP.Communication;
import HTTP.Erreur;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationClient extends Communication{
    
    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }
    
    public void attente_fichier(String nom_fichier) throws ErreurClient{
        try {
            byte[] b = null;
            BufferedInputStream bi = new BufferedInputStream(this.in);
            int read = bi.read(b);
            if (read > 0) {
                System.out.println("Client: message reçu");
                String res = new String(b, "UTF-8");
                if(CommunicationClient.reponse_valide(res)){
                    System.out.println(res);
                }
            }
            if (read == - 1) {
                throw new ErreurClient("Erreur dans la lecture du flux");
            }
        } catch (IOException ex) {
            throw new ErreurClient("Erreur dans la lecture du flux " + ex.getMessage());
        }
    }
    
    public static boolean reponse_valide(String _reponse){
        String [] params;    
        
        params = _reponse.split("\n");
        //Récupération de la première ligne
        return params[0] == "HTTP/1.1 200 OK";
    }
}
