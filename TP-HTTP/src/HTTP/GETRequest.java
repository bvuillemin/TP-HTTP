/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HTTP;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Dimitri
 */
public class GETRequest extends Requete{
    private URL url;
    
    public void buildRequest (URL _url){
        url = _url;
        request="GET" + GAP + url.toString() + GAP + VERSION + NL + "Accept : " + TYPE;
    }
    
    public static boolean isGETRequest (String _request){
        String [] params;
                
        //Récupération de la première ligne
        params = _request.split("\n");
        params = params[0].split(" ");
        if (params.length!=3){//La première ligne doit contenir une version une url et GET
            try {
                new URL (params[1]);
            } catch (MalformedURLException ex) {
                return false;
            }
            if (params[0]=="GET" && params[2]=="HTTP/1.1"){
                return true;
            }
        }
        return false;
    }
    
    public void getGETRequest (String _request){
        String [] params, word;
        request = _request;

        //Récupération de la première ligne
        params = request.split(NL);
        word = params[0].split(GAP);
        try {
            url = new URL (word[1]);
        } catch (MalformedURLException ex) {
            System.out.println("Ceci n'est pas une requête get valide");
        }
    }

    public URL getUrl() {
        return url;
    }
}
