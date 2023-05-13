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
        
        
         
            try {
        
                System.out.println("up");
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