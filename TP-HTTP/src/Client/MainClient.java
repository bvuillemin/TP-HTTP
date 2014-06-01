package Client;

import Client.Core.ClientObservable;
import Client.UI.Navigateur;

public class MainClient {

    /**
     * Main du client, va demander une URL et la traiter
     * @param args 
     */
    public static void main(String[] args){
        ClientObservable client = new ClientObservable();
        Navigateur fenetre = new Navigateur(client);
        client.addObserver(fenetre);
    }
}
