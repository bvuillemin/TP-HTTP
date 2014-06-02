package Serveur.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.GETRequest;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
public class CommunicationServeur extends Communication implements Runnable {

    private Socket s;
    private final String PATH = "";

    public CommunicationServeur(Socket s1) throws Erreur {
        super();
        this.s = s1;
    }
    
    public void comServeur() throws ErreurServeur {
        GETRequest request=new GETRequest();
        byte[] b = new byte[2048];
        
        try {
            this.in = this.s.getInputStream();
            BufferedInputStream bi = new BufferedInputStream(this.in);
            
            StringBuilder page = new StringBuilder("");
            
            while (bi.available() == 0) {
            }
            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (bi.available() != 0) {
                page.append((char)bi.read());
            }
            
            System.out.println("Serveur: message reçu");
            String res = page.toString();
            System.out.println(res);
            if (request.getGETRequest(res)){
                System.out.println("Paquet GET!!!");
                String nomFichier = request.getFileName();
                envoyerFichier(nomFichier);
            }
            else {
                throw new ErreurServeur("Le packet reçu n'est pas correct ou non traité");
            }
        } catch (IOException ex) {
            throw new ErreurServeur("Erreur dans la lecture du flux " + ex.getMessage());
        }
    }

    private void envoyerFichier(String nomFichier) throws ErreurServeur {
        String nomFichierFinal="";

        /*On récupère le répertoire courant*/
        String repertoire = System.getProperty("user.dir");
        //String repertoire = this.PATH;
        /*On vérifie si l'on doit sélectionner l'index ou non*/
        if (nomFichier.equals(new String("\\"))) {
            nomFichierFinal = repertoire + "\\index.html";
        } else {
            nomFichierFinal = repertoire + nomFichier;
        }
        
        File f = new File(nomFichierFinal);
        if(!f.exists()){
            nomFichierFinal = repertoire + "\\404.html";
        }
        
        //nomFichierFinal = "C:\\Users\\Pierre\\index.html";

        try {
            StringBuilder page = new StringBuilder("");
            this.out = this.s.getOutputStream();
            FileInputStream fe = new FileInputStream(nomFichierFinal);
            BufferedInputStream br = new BufferedInputStream(fe);
            
            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (br.available() != 0) {
                page.append((char)br.read());
            }
            
            Reponse rep = new Reponse(200, "OK", page.toString());
            
            System.out.println("Serveur: " + rep.getRequest());
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
