package HTTP;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Requete {
    protected String request;
    protected String header;
    public static final String VERSION="HTTP/1.1";
    public static final String TYPE = "text/html";
    public static final String GAP = " ";
    public static final String NL = "\n";
    public static final String CLOSE = "Connection: close";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    
    public String getRequest (){
        return request;
    }
    
    public static String get_header(String _reponse){
        String [] params;    
        String header = "";
        
        params = _reponse.split(NL + NL);
        params = params[0].split(NL);
         
        for(int i = 0; i < params.length; i++){
            header += params[i] + " ";
        }
        
        return header;
    }
    
    public String get_header(){
        return header;
    }
    
    public Date StringToDate (String _date){
        String temp = _date.replaceAll("Date : ", "");//On retire le texte du départ
        try {
            return DATE_FORMAT.parse(temp);
        } catch (ParseException ex) {
            System.out.println("Problème convertion date");
            return null;
        }
    }
    
    public String DateToString(Date date) {
        return "Date : " + DATE_FORMAT.format(date);
    }
}
