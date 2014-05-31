/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HTTP;

import java.net.MalformedURLException;
import java.net.URL;


public class GETRequest extends Requete{
    private URL url;
    
    public void buildRequest (URL _url){
        url = _url;
        request="GET" + GAP + url.toString() + GAP + VERSION + NL + "Accept : " + TYPE;
    }
    
    public static boolean isGETRequest(String _request) {
        String[] params;

        //Récupération de la première ligne
        params = _request.split(NL);
        params = params[0].split(GAP);
        String version = params[2];
        //String[] res = params[2].split(GAP);

        if(params[0].equals(new String ("GET"))){
            for(int i = 0; i < 8; i++){ /*On compara caractère pas caractère*/
                if(version.charAt(i) != new String(VERSION).charAt(i))
                    return false;
            }
            return true;
        }
        return false;
        
    }
    
    public static String getNomFichierRequest(String _request){
        String[] params;

        params = _request.split(NL);
        params = params[0].split(GAP);
        
        return params[1];
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
