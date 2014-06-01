package Client.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class CommunicationClient extends Communication{
    
    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }
    
    public File attente_fichier(String nom_fichier) throws ErreurClient{
        try {
            byte[] b = new byte[2048];
            Reponse rep=new Reponse();
            BufferedInputStream bi = new BufferedInputStream(this.in);
            
            int read = bi.read(b);
            if (read > 0) {
                System.out.println("Client: message reçu");
                String res = new String(b, "UTF-8");
                if(rep.getReponse(res)){
                    String header = rep.get_header();
                    System.out.println(header);
                    
                    /*On enregistre la data dans un fichier*/
                    return new File(rep.getContent());
                    
                }
            }
            if (read == - 1) {
                throw new ErreurClient("Erreur dans la lecture du flux");
            }
        } catch (IOException ex) {
            throw new ErreurClient("Erreur dans la lecture du flux " + ex.getMessage());
        }
        return null;
    }
}
