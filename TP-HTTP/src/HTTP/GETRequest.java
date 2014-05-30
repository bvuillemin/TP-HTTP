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
        System.out.println(params[0]);
        System.out.println(params[2]);
        
        return params[0].equals("GET") && params[2].equals(VERSION);
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
