package Client;

import Commun.Communication;
import Commun.Erreur;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationClient extends Communication{
    
    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }

    public void comClient(Socket connexion) throws IOException {
        this.in = connexion.getInputStream();
    }
}
