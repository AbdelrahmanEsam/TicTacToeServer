/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ASUS
 */
public class Server {

    
        ServerSocket server;
        
      public Server()
      {
        System.out.println("init");
            try {
                System.out.println("init");
                server = new ServerSocket(4004);
                 while(true){
            
                        Socket accepted = server.accept();
                    
                        new ClientHandler(accepted);
            
                      }
            } catch (IOException ex) {
              
            }
       
      
      
      }
  
    
}