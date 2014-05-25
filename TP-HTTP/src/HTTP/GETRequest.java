/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HTTP;

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
}
