package Serveur;

import Commun.Communication;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationServeur extends Communication{
    
    private Serveur serveur;
    
    public CommunicationServeur(InetAddress _ip, int _port) throws ErreurServeur {
        super(_ip, _port);
        serveur=new Serveur();
    }
    
    public void comServeur(Socket connexion) throws IOException{
        this.in = connexion.getInputStream();
        
    }
}
