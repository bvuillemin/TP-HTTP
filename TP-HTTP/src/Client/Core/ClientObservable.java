/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client.Core;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;

/**
 *
 * @author Dimitri
 */
public class ClientObservable extends Observable {
    private Client client;
    
    public ClientObservable(){
        try {
            System.out.println("Démarrage client");
            client = new Client(InetAddress.getLocalHost(), 1086);
        } catch (ErreurClient er) {
            System.out.println("Client: " + er.getMessage());
        } catch (UnknownHostException ex) {
            System.out.println("Client : Impossible de créer la socket");
        }
    }
    
    public void lancerRequete(URL url) throws ErreurClient{
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
