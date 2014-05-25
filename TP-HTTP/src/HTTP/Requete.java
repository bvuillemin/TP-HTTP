package HTTP;

import java.net.URL;

public abstract class Requete {
    protected String request;
    public final String VERSION="HTTP/1.1";
    public final String TYPE = "text/html";
    public final String GAP = " ";
    public final String NL = "\n";
    
    public String getRequest (){
        return request;
    }
}
