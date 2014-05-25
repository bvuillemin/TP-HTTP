/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HTTP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Dimitri
 */
public class Reponse extends Requete{
    private int code;
    private String version;
    private String message;
    private final Date date;
    private String content;
    private int contentLength;
    private final String contentType;
    
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    
    public Reponse (int _code, String _message){
        code = _code;
        message = _message;
        date = new Date();
        version = VERSION;
        contentType = TYPE;        
        content = "";
        request = version + GAP + code + GAP + message;
    }
    
    public void buildReponse (int _code, String _message, String _content){
        code = _code;
        message = _message;
        content = _content;
        contentLength=content.length();
        
        request = version + GAP + code + GAP + message + NL +
                DateToString() + NL +
                "Content-Type : " + contentType + NL +
                "Content-Length : " + contentLength + NL + NL +//Deux NL pour séparer l'entête du corps
                content;
    }
    
    public static boolean isReponse (String _reponse){
        String [] params;
        String version;
        int code;
        
        //Séparation entête-corps
        params=_reponse.split("\n\n");//Recherche des deux lignes qui séparent le corps de l'entête
        
        //Récupération de la première ligne
        params = params[0].split("\n");
        params = params[0].split(" ");
        if (params.length!=3){//La première ligne doit contenir une version un code et un message
            version = params[0];
            code = Integer.parseInt(params[1]);
            if (code>0 && version=="HTTP/1.1"){
                return true;
            }
        }
        return false;
    }
    
    public void getReponse (String _reponse){
        String [] params, word;
        request = _reponse;
        
        //Séparation entête-corps
        params=request.split(NL+NL);//Recherche des deux lignes qui séparent le corps de l'entête
        if (params.length>=2){
            content = params[1];
            contentLength=content.length();
        }
        
        //Récupération de la première ligne
        params = params[0].split(NL);
        word = params[0].split(GAP);
        version = word[0];
        code = Integer.parseInt(word[1]);
        message = word[2];
    }
    
    public Date StringToDate (String _date){
        String temp = _date.replaceAll("Date : ", "");//On retire le texte du départ
        try {
            return DATE_FORMAT.parse(temp);
        } catch (ParseException ex) {
            //Date inconvertible
            return null;
        }
    }
    
    public String DateToString() {
        return "Date : " + DATE_FORMAT.format(date);
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
