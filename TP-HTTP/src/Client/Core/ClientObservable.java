package Client.Core;

import java.io.File;
import java.net.URL;
import java.util.Observable;

public class ClientObservable extends Observable {
    private Client client;
    
    public ClientObservable(){
        System.out.println("Démarrage client");
        client = new Client();
    }
    
    public void lancerRequete(URL url) throws ErreurClient{
        client.lancerRequete(url);
        updateObservers();
    }
    public void close (){
        client.close();
    }
    private void updateObservers() {
        setChanged();
        notifyObservers();
    }
    
    public File getFile(){
        return client.file;
    }
}
