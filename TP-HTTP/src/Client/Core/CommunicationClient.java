package Client.Core;

import HTTP.Communication;
import HTTP.Erreur;
import HTTP.Reponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;

public class CommunicationClient extends Communication {

    public CommunicationClient(InetAddress _ip, int _port) throws ErreurClient, Erreur {
        super(_ip, _port);
    }
    
    public File attente_fichier(String nom_fichier) throws ErreurClient {
        try {
            byte[] b = new byte[2048];
            Reponse rep = new Reponse();
            BufferedInputStream bi = new BufferedInputStream(this.in);

            StringBuilder page = new StringBuilder("");

            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (bi.available()==0){
                
            }
            while (bi.available() != 0) {
                page.append((char) bi.read());
            }

            String res = page.toString();
            System.out.println("Client: message reçu");
            if (rep.getReponse(res)) {
                /*On récupère le répertoire courant*/
                String repertoire = System.getProperty("user.dir");
                String nomFichierFinal;
                String header = rep.get_header();
                System.out.println(header);

                /*On vérifie si l'on doit sélectionner l'index ou non*/
                if (nom_fichier.equals(new String("/"))) {
                    nomFichierFinal = repertoire + "\\Client\\index.html";
                } else {
                    nomFichierFinal = repertoire + "\\Client\\" + rep.getCode()+".html";
                }

                /*On enregistre la data dans un fichier*/
                FileWriter writer = new FileWriter(nomFichierFinal, false);
                writer.write(rep.getContent());

                if (writer != null) {
                    writer.close();
                }
                
                //Si demande de déconnection du serveur
                if (rep.isClose()){
                    socket.close();
                }
                
                File f = new File(nomFichierFinal);
                return f;
            }
            else{
                socket.close();
                throw new ErreurClient(400, "Message reçu non traité");
            }

        } catch (IOException ex) {
            throw new ErreurClient(500, "Problème de socket " + ex.getMessage());
        }
    }
}
