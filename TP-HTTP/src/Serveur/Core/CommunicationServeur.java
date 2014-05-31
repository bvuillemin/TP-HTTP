package Serveur.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.GETRequest;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationServeur extends Communication implements Runnable {

    private Socket s;
    private final String PATH = "";

    public CommunicationServeur(Socket s1) throws Erreur {
        super();
        this.s = s1;
    }
    
    public void requeteURL (URL url)throws ErreurServeur{
        File file = new File(PATH + url.getFile());
    }
    
    public void comServeur() throws ErreurServeur {
        GETRequest request = new GETRequest();
        byte[] b = new byte[2048];
        
        try {
            this.in = this.s.getInputStream();
            BufferedInputStream bi = new BufferedInputStream(this.in);
            int read = bi.read(b);
            if (read >= 0) {
                System.out.println("Serveur: message reçu");
                String res = new String(b, "UTF-8");
                System.out.println(res);
                if (GETRequest.isGETRequest(res)){
                    System.out.println("Paquet GET!!!");
                    String nomFichier = GETRequest.getNomFichierRequest(res);
                    envoyerFichier(nomFichier);
                }
                else {
                    throw new ErreurServeur("Le packet reçu n'est pas correct ou non traité");
                }
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

    private void envoyerFichier(String nomFichier) {
        try {
            this.out = this.s.getOutputStream();
            BufferedOutputStream bufout = new BufferedOutputStream(this.out);
            
            FileInputStream fe= new FileInputStream(nomFichier);
            
            Reponse rep = new Reponse(200, "OK");
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(CommunicationServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
