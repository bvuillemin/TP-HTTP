/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HTTP;

import java.io.File;


public class GETRequest extends Requete{
    private File file;
    
    public GETRequest(){
        request="GET" + GAP + "/" + GAP + VERSION + NL + "Accept : " + TYPE + NL + NL;
    }
    
    public GETRequest (File _file){
        file = _file;
        request="GET" + GAP + _file.toString() + GAP + VERSION + NL + "Accept : " + TYPE + NL + NL;
    }
    
    public static boolean isGETRequest(String _request) {
        String[] params;

        //Récupération de la première ligne
        params = get_header(_request).split(GAP);
        String version = params[2];

        if(params[0].equals(new String ("GET"))){
            for(int i = 0; i < 8; i++){ /*On comparera caractère pas caractère*/
                if(version.charAt(i) != new String(VERSION).charAt(i))
                    return false;
            }
            return true;
        }
        return false;
        
    }
    
    public boolean getGETRequest (String _request){
        String [] params, word;
        request = _request;

        //Récupération de la première ligne
        if (isGETRequest(_request)){
            params = request.split(NL);
            word = params[0].split(GAP);
            file = new File (word[1]);
            return true;
        }
        return false;        
    }

    public File getFile() {
        return file;
    }
    
    public String getFileName(){
        return file.toString();
    }
}
