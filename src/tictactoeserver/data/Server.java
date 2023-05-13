/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import static tictactoeserver.data.ClientHandler.closeAllSockets;

/**
 *
 * @author ASUS
 */
public class Server extends Thread{
    
   private static Server instance = null;
    private ServerSocket server;
    SimpleBooleanProperty state = new SimpleBooleanProperty();

    @Override
    public void run() {
        super.run(); 
        
        
         
            try {
        
                System.out.println("up");
                server = new ServerSocket(4004);
                 while(true){
            
                        Socket accepted = server.accept();
                    
                  new ClientHandler(accepted);
            
                      }
            } catch (Exception ex) {
                closeAllSockets();
            }
       
    }

  

    @Override
    public void interrupt() {
        super.interrupt();
       try {
           System.out.println("closing");
           server.close();
       } catch (IOException ex) {
           Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    

    public SimpleBooleanProperty getServerState() {
        return state;
    }

    public void setState(Boolean state) {
        System.out.println(state);
        this.state.set(state);
    }

   
    
    
    

    
    
    
        
        
      private Server()
      {
    
      
      }
      
      
      public static synchronized Server getInstance()
      {
      
         
             if(instance == null)
             {
             
                 instance = new Server(); 
             
             }
            
             return instance;
      }
  
    
}