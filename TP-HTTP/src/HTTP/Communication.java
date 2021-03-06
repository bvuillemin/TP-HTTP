package HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public abstract class Communication{
    protected InputStream in;
    protected OutputStream out;
    protected Socket socket;
    private InetAddress adresseIP;
    private int port;
    
    public Communication(InetAddress _ip, int _port) throws Erreur{
        try {
            this.socket =  new Socket(_ip, _port);
            this.adresseIP = _ip;
            this.port =_port;
            this.out = this.socket.getOutputStream();
            this.in = this.socket.getInputStream();
        } catch (IOException ex) {
            throw new Erreur(500,"Erreur dans la création du socket");
        }
    }
    
    public Communication() throws Erreur{
    }
    
    public String requeteGET(String url, String method){
        String res = "GET " + url + " " + method;
        return res;
    }
    
    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public InetAddress getAdresseIP() {
        return adresseIP;
    }

    public int getPort() {
        return port;
    }
}
