package Serveur.Core;

import HTTP.Erreur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serveur {
    private int nombreConnexion;
    private int portEcoute;
    private boolean fonctionnementServeur;
    
    private ServerSocket socket;
    
    public Serveur() throws ErreurServeur{
        this.fonctionnementServeur = true;
        this.portEcoute = 1086;
        this.nombreConnexion = 4;
        try {
            this.socket = new ServerSocket(1086, 5);
        } catch (IOException ex) {
            throw new ErreurServeur("Erreur dans la création du socket serveur");
        }
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public ServerSocket getSocket() {
        return socket;
    }
    
    public void ecoute() throws ErreurServeur{
        Socket connexion = null;
        try {
            while(this.fonctionnementServeur){
                connexion = socket.accept();
                CommunicationServeur com = new CommunicationServeur(connexion);
                com.run();
            }
        } 
        catch (IOException ex) {
            throw new ErreurServeur("Erreur pour se connecter");
        } catch (Erreur ex) {
            throw new ErreurServeur(ex.getMessage());
        }
        finally{
            try{
                if(connexion != null)
                    connexion.close();
            }
            catch(IOException ex){
                throw new ErreurServeur("Erreur fermeture connexion");
            }
        }
    }
}
