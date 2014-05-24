/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Commun;

import java.net.URL;

/**
 *
 * @author Dimitri
 */
public abstract class Requete {
    protected String stream;
    protected URL url;
    
    public String getRequest (String _stream, URL _url){
        url = _url;
        stream = _stream;
        return url + " HTTP/1.1 " + stream;
    }
}
