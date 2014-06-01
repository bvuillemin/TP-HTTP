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

    private void envoyerFichier(String nomFichier) throws ErreurServeur {
        String nomFichierFinal;

        /*On récupère le répertoire courant*/
        String repertoire = System.getProperty("user.dir");

        /*On vérifie si l'on doit sélectionner l'index ou non*/
        if (nomFichier.equals(new String("/"))) {
            nomFichierFinal = repertoire + "\\index.html";
        } else {
            nomFichierFinal = repertoire + nomFichier;
        }
        
        //nomFichierFinal = "C:\\Users\\Pierre\\index.html";


        try {
            StringBuilder page = new StringBuilder("");
            this.out = this.s.getOutputStream();
            FileInputStream fe = new FileInputStream(nomFichier);
            BufferedInputStream br = new BufferedInputStream(fe);
             
            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (br.available() != 0) {
                page.append((char)br.read());
            }
            
            Reponse rep = new Reponse();
            rep.buildReponse(200, "OK", page.toString());
            
            this.out.write(rep.getRequest().getBytes());
            this.out.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.toString());
            throw new ErreurServeur("Erreur dans l'envoi");
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
