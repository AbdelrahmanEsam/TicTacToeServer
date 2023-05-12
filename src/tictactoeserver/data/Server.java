/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.beans.property.SimpleBooleanProperty;

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
        
        
           state.set(true);
            try {
              
                server = new ServerSocket(4004);
                 while(true){
            
                        Socket accepted = server.accept();
                    
                  new ClientHandler(accepted);
            
                      }
            } catch (IOException ex) {
                           
            }
       
    }

    public SimpleBooleanProperty getServerState() {
        return state;
    }

    public void setState(SimpleBooleanProperty state) {
        this.state = state;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        state.set(false);
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