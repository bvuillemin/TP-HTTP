package HTTP;

public abstract class Requete {
    protected String request;
    public static final String VERSION="HTTP/1.1";
    public static final String TYPE = "text/html";
    public static final String GAP = " ";
    public static final String NL = "\n";
    
    public String getRequest (){
        return request;
    }
}
