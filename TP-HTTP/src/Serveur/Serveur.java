package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serveur {
    private int nombreConnexion;
    private int portEcoute;
    private ServerSocket socket;
    private boolean fonctionnementServeur;
    
    public Serveur() throws ErreurServeur{
        this.portEcoute = 80;
        this.nombreConnexion = 5;
        this.fonctionnementServeur = true;
        try {
            this.socket = new ServerSocket(80, 5);
        } catch (IOException ex) {
            throw new ErreurServeur();
        }
    }
    
    public void accept() throws ErreurServeur{
        Socket connexion = null;
        try {
            ServerSocket serveur = new ServerSocket(0);
            
            while(this.fonctionnementServeur){
                connexion = serveur.accept();
                
            }
        } 
        catch (IOException ex) {
            throw new ErreurServeur();
        }
        finally{
            try{
                if(connexion != null)
                    connexion.close();
            }
            catch(IOException ex){
                throw new ErreurServeur();
            }
        }
        
    }
    
    
}
