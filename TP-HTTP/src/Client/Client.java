package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private int portEcoute;
    private ServerSocket socket;
    private boolean fonctionnementClient;
    
    public Client() throws ErreurClient{
        this.portEcoute = 80;
        this.fonctionnementClient = true;
        try {
            this.socket = new ServerSocket(80, 5);
        } catch (IOException ex) {
            throw new ErreurClient();
        }
    }
    
    public void accept() throws ErreurClient{
        Socket connexion = null;
        try {
            ServerSocket client = new ServerSocket(0);
            
            while(this.fonctionnementClient){
                connexion = client.accept();
                
            }
        } 
        catch (IOException ex) {
            throw new ErreurClient();
        }
        finally{
            try{
                if(connexion != null)
                    connexion.close();
            }
            catch(IOException ex){
                throw new ErreurClient();
            }
        }
        
    }
    
    
}
