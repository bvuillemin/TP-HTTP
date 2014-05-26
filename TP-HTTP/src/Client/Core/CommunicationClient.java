package Client.Core;

import HTTP.Communication;
import HTTP.Erreur;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

public class CommunicationClient extends Communication{
    
    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }
    
    public void attente_fichier(String nom_fichier) throws ErreurClient{
        try {
            byte[] b = new byte[2048];
            BufferedInputStream bi = new BufferedInputStream(this.in);
            int read = bi.read(b);
            if (read > 0) {
                System.out.println("Client: message reçu");
                String res = new String(b, "UTF-8");
                if(CommunicationClient.reponse_valide(res)){
                    String header = get_header(res);
                    System.out.println(header);
                    
                    /*On enregistre la data dans un fichier*/
                    File fichier = new File(get_data(res));
                    
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
    
    public String get_header(String _reponse){
        String [] params;    
        String header = null;
        
        params = _reponse.split("\n");
        int i = 0;
        
        while(params[i] != ""){
            header += params[i];
            i++;
        }
        return header;
    }
    
    public String get_data(String _reponse){
        String [] params;    
        
        params = _reponse.split("\n");
        int i = 0;
        
        while(params[i] != ""){
            i++;
        }
        return params[i];
    }
}
