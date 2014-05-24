package Commun;

import java.net.URL;

public abstract class Requete {
    protected String stream;
    protected URL url;
    
    public String getRequest (String _stream, URL _url){
        url = _url;
        stream = _stream;
        return url + " HTTP/1.1 " + stream;
    }
}
