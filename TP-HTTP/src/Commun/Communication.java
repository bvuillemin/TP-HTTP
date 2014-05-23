package Commun;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class Communication {
    protected InputStream in;
    protected OutputStream out;
    private InetAddress adresseIP;
    private int port;
    
    public Communication(InetAddress _ip, int _port){
        this.adresseIP = _ip;
        this.port =_port;
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
