package Client;

import Commun.Communication;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CommunicationClient extends Communication{
    
    private Client client;

    public Client getClient() {
        return client;
    }
    
    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient {
        super(_ip, _port);
        client=new Client();
    }
    
    public void comClient(Socket connexion) throws IOException{
        this.in = connexion.getInputStream();   
    }
}
