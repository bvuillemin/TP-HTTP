package Serveur;

import Commun.Communication;
import Commun.Erreur;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationServeur extends Communication implements Runnable{
    
    private Socket s;
    
    public CommunicationServeur(Socket s1) throws ErreurServeur, Erreur{
        super(s1.getInetAddress(), s1.getPort());        
        this.s = s1;
    }
    
    public void comServeur() throws IOException{
        byte[] b = new byte[2048];
        this.in = this.s.getInputStream();
        this.in.read(b);
        System.out.println("Serveur: message reçu");
        System.out.println(Arrays.toString(b));
    }
 
    @Override
    public void run() {
        try {
            this.comServeur();
        } catch (IOException ex) {
            System.out.println("Erreur communication");
        }
    }
}
