package HTTP;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


public class Reponse extends Requete{
    private int code;
    private String version;
    private String message;
    private final Date date;
    private String content;
    private int contentLength;
    private final String contentType;
    
    
    public Reponse(){
        
        date = new Date();
        version = VERSION;
        contentType = TYPE;        
        content = "";
    }
    
    public static Reponse Erreur (int type, String message){
        FileInputStream fe = null;
        try {
            String repertoire = System.getProperty("user.dir");
            StringBuilder page = new StringBuilder("");
            fe = new FileInputStream(repertoire+ "\\Server\\"+type+".html");
            BufferedInputStream br = new BufferedInputStream(fe);
            /*On va transférer chaque information du fichier vers un string que l'on pourra envoyer*/
            while (br.available() != 0) {
                page.append((char)br.read());
            }
            fe.close();
            return new Reponse (type, message, page.toString());
        } catch (FileNotFoundException ex) {
            return new Reponse (type, message, "");
        } catch (IOException ex) {
            return new Reponse (type, message, "");
        } 
    }
    
    public Reponse (int _code, String _message, String _content){
        code = _code;
        message = _message;
        content = _content;
        contentLength=content.length();  
        contentType = TYPE; 
        date = new Date();
        version = VERSION;
        
        request = version + GAP + code + GAP + message + NL +
                DateToString(date) + NL +
                "Content-Type : " + contentType + NL +
                "Content-Length : " + contentLength + NL + NL +//Deux NL pour séparer l'entête du corps
                content;
    }
    
    public static boolean isReponse(String _reponse) {
        String[] params;
        String version;
        int code;

        //Séparation entête-corps
        params = _reponse.split(NL + NL);//Recherche des deux lignes qui séparent le corps de l'entête

        //Récupération de la première ligne
        params = params[0].split(NL);
        params = params[0].split(GAP);
        if (params.length == 3) {//La première ligne doit contenir une version un code et un message
            version = params[0];
            code = Integer.parseInt(params[1]);
            if (code > 0 && version.equals(new String(VERSION))) {
                return true;
            }
        }
        return false;
    }
    
    public boolean getReponse(String _reponse) {
        String[] params, word;
        request = _reponse;

        if (isReponse(_reponse)) {
            //Séparation entête-corps
            params = request.split(NL + NL);//Recherche des deux lignes qui séparent le corps de l'entête
            if (params.length >= 2) {
                content = params[1];
                contentLength = content.length();
            }
            header = params[0];

            //Récupération de la première ligne
            params = params[0].split(NL);
            word = params[0].split(GAP);
            version = word[0];
            code = Integer.parseInt(word[1]);
            message = word[2];
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Reponse : " + NL +
                "Version : " + version + GAP + "Code : " + code + GAP + "Message : " + message + NL + NL +
                "Content : " + content;
    }

    public int getCode() {
        return code;
    }

    public String getVersion() {
        return version;
    }

    public String getMessage() {
        return message;
    }

    public String getContent() {
        return content;
    }
}
