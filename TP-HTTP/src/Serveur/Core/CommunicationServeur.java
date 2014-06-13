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
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class CommunicationServeur extends Communication implements Runnable {

    private boolean fonctionnement = true;

    public CommunicationServeur(Socket s1) throws Erreur {
        super();
        this.socket = s1;
        try {
            socket.setSoTimeout(3000);
        } catch (SocketException ex) {
            throw new Erreur(500, "Impossible de modifier le timeout");
        }
    }

    protected void sendReponse(Reponse rep) throws ErreurServeur {
        try {
            this.out.write(rep.getRequest().getBytes());
            this.out.flush();
        } catch (IOException ex) {
            throw new ErreurServeur(500, "Impossible d'envoyer la réponse");
        }
    }

    public void traiter(String res) throws ErreurServeur {
        GETRequest request = new GETRequest();
        if (request.getGETRequest(res)) {
            System.out.println("Packet GET reçu");
            System.out.println(request.toString());
            String nomFichier = request.getFileName();
            envoyerFichier(nomFichier);
        } else {
            sendReponse(Reponse.Erreur(400, "UNTREATED",false));
            System.out.println(new ErreurServeur(400, "Le packet reçu n'est pas correct ou non traité").toString());
        }
    }

    public void comServeur() throws ErreurServeur {
        byte[] b = new byte[2048];
        try {
            this.in = this.socket.getInputStream();
        } catch (IOException ex) {
            throw new ErreurServeur(500,"Erreur récupération stream");
        }
        while (fonctionnement) {
            try {
                BufferedInputStream bi = new BufferedInputStream(this.in);
                StringBuilder page = new StringBuilder("");
                if (bi.available() != 0) {
                    while (bi.available() != 0) {
                        page.append((char) bi.read());
                    }
                    String res = page.toString();
                    System.out.println(res);
                    traiter(res);
                }
                if (socket.isClosed()){
                    fonctionnement=false;
                }
            } catch (ErreurServeur er) {
                throw er;
            }catch (SocketTimeoutException ste) {
                throw new ErreurServeur(118,"Connection timed out");
            } 
            catch (IOException ex) {
                if (!socket.isClosed())sendReponse(Reponse.Erreur(500, "SERVER_INTERNAL_ERROR",true));
                throw new ErreurServeur(500, "Erreur dans la lecture du flux");
            }
        }

    }

    private void envoyerFichier(String nomFichier) throws ErreurServeur {
        String nomFichierFinal = "";

        /*On récupère le répertoire courant*/
        String repertoire = System.getProperty("user.dir");
        /*On vérifie si l'on doit sélectionner l'index ou non*/
        if (nomFichier.equals(new String("\\"))) {
            nomFichierFinal = repertoire + "\\Server\\index.html";
        } else {
            nomFichierFinal = repertoire + "\\Server" + nomFichier;
        }

        //nomFichierFinal = "C:\\Users\\Pierre\\index.html";
        StringBuilder page = new StringBuilder("");
        try {
            this.out = this.socket.getOutputStream();
            FileInputStream fe = new FileInputStream(nomFichierFinal);
            BufferedInputStream br = new BufferedInputStream(fe);

            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (br.available() != 0) {
                page.append((char) br.read());
            }
            sendReponse(new Reponse(200, "OK", page.toString(),false));
        } catch (ErreurServeur er) {
            throw er;
        } catch (FileNotFoundException ex) {
            sendReponse(Reponse.Erreur(404, "FILE_NOT_FOUND",false));
            System.out.println(new ErreurServeur(404, "File requested not found").toString());
        } catch (SecurityException ex) {
            sendReponse(Reponse.Erreur(403, "FORBIDDEN",false));
            System.out.println(new ErreurServeur(403, "File requested forbidden to read").toString());
        } catch (IOException ex) {
            System.out.println(ex.toString());
            if (!socket.isClosed())sendReponse(Reponse.Erreur(500, "SERVER_INTERNAL_ERROR",true));
            throw new ErreurServeur(500, "Erreur dans l'envoi");
        }
    }

    @Override
    public void run() {
        try {
            this.fonctionnement = true;
            this.comServeur();
        } catch (ErreurServeur ex) {
            System.out.println("Erreur serveur: "+ ex.getMessage());
            
        }
    }
}
