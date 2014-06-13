package Client.Core;

import HTTP.Erreur;
import java.io.File;
import java.net.URL;
import java.util.Observable;

public class ClientObservable extends Observable {
    private Client client;
    
    public ClientObservable(){
        System.out.println("Démarrage client");
        client = new Client();
    }
    
    public void lancerRequete(URL url) throws Erreur{
        client.lancerRequete(url);
        updateObservers();
    }
    
    private void updateObservers() {
        setChanged();
        notifyObservers();
    }
    
    public File getFile(){
        return client.file;
    }
}
