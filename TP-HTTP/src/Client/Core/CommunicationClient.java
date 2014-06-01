package Client.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

public class CommunicationClient extends Communication {

    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }

    public void attente_fichier(String nom_fichier) throws ErreurClient {
        try {
            byte[] b = new byte[2048];
            Reponse rep = new Reponse();
            BufferedInputStream bi = new BufferedInputStream(this.in);

            int read = bi.read(b);
            if (read > 0) {
                System.out.println("Client: message reçu");
                String res = new String(b, "UTF-8");
                if (rep.getReponse(res)) {
                    String header = rep.get_header();
                    System.out.println(header);

                    /*On récupère le répertoire courant*/
                    String repertoire = System.getProperty("user.dir");
                    String nomFichierFinal;
                    //String repertoire = this.PATH;
                    /*On vérifie si l'on doit sélectionner l'index ou non*/
                    if (nom_fichier.equals(new String("\\"))) {
                        nomFichierFinal = repertoire + "\\Client\\index.html";
                    } else {
                        nomFichierFinal = repertoire + "\\Client\\" + nom_fichier;
                    }

                    /*On enregistre la data dans un fichier*/
                    FileWriter writer = new FileWriter(nomFichierFinal, false);
                    writer.write(rep.getMessage());

                    if (writer != null) {
                        writer.close();
                    }

                }
            }
            if (read == - 1) {
                throw new ErreurClient("Erreur dans la lecture du flux");
            }
        } catch (IOException ex) {
            throw new ErreurClient("Erreur dans la lecture du flux " + ex.getMessage());
        }
    }
}
