package Serveur.Core;

import HTTP.Erreur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    private int portEcoute;
    private boolean fonctionnementServeur;
    
    private ServerSocket socket;
    
    public Serveur() throws ErreurServeur{
        this.fonctionnementServeur = true;
        this.portEcoute = 1086;
        try {
            this.socket = new ServerSocket(portEcoute, 5);
        } catch (IOException ex) {
            throw new ErreurServeur(500,"Erreur dans la création du socket serveur");
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
                connexion.setSoTimeout(3000);
                CommunicationServeur com = new CommunicationServeur(connexion);
                com.run();
            }
        } 
        catch (IOException ex) {
            throw new ErreurServeur(500, "Erreur ouverture connexion");
        } catch (Erreur ex) {
            throw (ErreurServeur) ex;
        }
        finally{
            try{
                if(connexion != null)
                    connexion.close();
            }
            catch(IOException ex){
                throw new ErreurServeur(500, "Erreur fermeture connexion");
            }
        }
    }
}
