package Serveur;

import Commun.Communication;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationServeur extends Communication implements Runnable{
    
    public CommunicationServeur(InetAddress _ip, int _port) throws ErreurServeur{
        super(_ip, _port);        
    }
    
    public void comServeur(Socket connexion) throws IOException{
        this.in = connexion.getInputStream();
    }
    
    
    
    @Override
    public void run() {
        
    }
}
