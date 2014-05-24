package Serveur;

import Commun.Communication;
import Commun.Erreur;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationServeur extends Communication implements Runnable {

    private Socket s;

    public CommunicationServeur(Socket s1) throws ErreurServeur, Erreur {
        super();
        this.s = s1;
    }

    public void comServeur() throws ErreurServeur {
        try {
            byte[] b = new byte[2048];
            this.in = this.s.getInputStream();
            BufferedInputStream bi = new BufferedInputStream(this.in);
            int read = bi.read(b);
            if (read > 0) {
                System.out.println("Serveur: message reçu");
                String res = new String(b, "UTF-8");
                System.out.println(res);
            }
            if (read == - 1) {
                throw new ErreurServeur("Erreur dans la lecture du flux");
            }
        } catch (IOException ex) {
            throw new ErreurServeur("Erreur dans la lecture du flux " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            this.comServeur();
        } catch (ErreurServeur ex) {

        }
    }
}
