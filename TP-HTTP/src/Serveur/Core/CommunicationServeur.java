package Serveur.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.GETRequest;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;


public class CommunicationServeur extends Communication implements Runnable {

    private Socket s;
    private final String PATH = "";
    private boolean fonctionnement=true;

    public CommunicationServeur(Socket s1) throws Erreur {
        super();
        this.s = s1;
    }
    
    
    protected void sendReponse (Reponse rep) throws ErreurServeur{
        try {
            this.out.write(rep.getRequest().getBytes());
            this.out.flush();
        } catch (IOException ex) {
            throw new ErreurServeur (500, "Impossible d'envoyer la réponse");
        }
    }
    
    public void traiter (String res) throws ErreurServeur{
        GETRequest request=new GETRequest();
        if (request.getGETRequest(res)){
            System.out.println("Paquet GET reçu");
            String nomFichier = request.getFileName();
            envoyerFichier(nomFichier);
        }
        else {
            sendReponse(new Reponse (400, "Le packet reçu n'est pas correct ou non traité", null));
            throw new ErreurServeur(400,"Le packet reçu n'est pas correct ou non traité");
        }
    }
    
    public void comServeur() throws ErreurServeur {
        byte[] b = new byte[2048];
        while (fonctionnement){
            try {
                this.in = this.s.getInputStream();
                BufferedInputStream bi = new BufferedInputStream(this.in);
                int read = bi.read(b);
                if (read >= 0) {
                    System.out.println("Serveur: message reçu");
                    String res = new String(b, "UTF-8");
                    System.out.println(res);
                    traiter (res);
                }
                if (read == - 1) {
                    throw new ErreurServeur(500, "Erreur dans la lecture du flux 1");
                }
            }catch(ErreurServeur er){
                throw er;
            } 
            catch (IOException ex) {
                throw new ErreurServeur(500, "Erreur dans la lecture du flux 2");
            }
                
        }
    }

    private void envoyerFichier(String nomFichier) throws ErreurServeur {
        String nomFichierFinal="";

        /*On récupère le répertoire courant*/
        String repertoire = System.getProperty("user.dir");
        /*On vérifie si l'on doit sélectionner l'index ou non*/
        if (nomFichier.equals(new String("\\"))) {
            nomFichierFinal = repertoire + "Server\\index.html";
        } else {
            nomFichierFinal = repertoire + "\\" + nomFichier;
        }
        
        //nomFichierFinal = "C:\\Users\\Pierre\\index.html";

        StringBuilder page = new StringBuilder("");
        try {
            this.out = this.s.getOutputStream();
            FileInputStream fe = new FileInputStream(nomFichierFinal);
            BufferedInputStream br = new BufferedInputStream(fe);
             
            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (br.available() != 0) {
                page.append((char)br.read());
            }
            sendReponse(new Reponse(200, "OK", page.toString())); 
        }
        catch(ErreurServeur er){
            throw er;
        }catch(FileNotFoundException ex){
            sendReponse(new Reponse (404, "FILE NOT FOUND", null));
            throw new ErreurServeur(404,"File requested not found : " + nomFichierFinal);
        }
        catch(SecurityException ex){
            sendReponse(new Reponse (403, "FORBIDDEN", null));
            throw new ErreurServeur(404,"File requested forbidden to read : " + nomFichierFinal);
        }
        catch (IOException ex) { 
            System.out.println(ex.toString());
            throw new ErreurServeur(500,"Erreur dans l'envoi");
        } 
    }
    
    
    @Override
    public void run() {
        try {
            this.fonctionnement=true;
            this.comServeur();
        } catch (ErreurServeur ex) {
            System.out.println(ex.getMessage());
        }
    }
}
