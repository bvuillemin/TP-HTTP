package Serveur;

import Commun.Communication;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CommunicationServeur extends Communication{
    
    public void comServeur(Socket connexion) throws IOException{
        this.in = connexion.getInputStream();
        
    }
}
