package Serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    private int nombreConnexion;
    private int portEcoute;
    private boolean fonctionnementServeur;
    
    private ServerSocket socket;
    
    public Serveur() throws ErreurServeur{
        this.portEcoute = 80;
        this.nombreConnexion = 4;
        try {
            this.socket = new ServerSocket(80, 5);
        } catch (IOException ex) {
            throw new ErreurServeur();
        }
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public ServerSocket getSocket() {
        return socket;
    }
    
    public void accept() throws ErreurServeur{
        Socket connexion = null;
        try {
            while(this.fonctionnementServeur){
                connexion = socket.accept();
                CommunicationServeur com = new CommunicationServeur(connexion.getInetAddress(),connexion.getPort());
                com.run();
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
