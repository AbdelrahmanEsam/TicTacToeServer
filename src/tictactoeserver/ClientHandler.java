/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientHandler extends Thread{
    
   static Vector<ClientHandler> onlinePlayers = new Vector();
    DataInputStream listener = null;
    PrintStream sender = null;
  
   public ClientHandler(Socket newClient)
   {
   
       
       try {
           listener = new DataInputStream(newClient.getInputStream());
           sender = new PrintStream(newClient.getOutputStream());
           
           ClientHandler.onlinePlayers.add(this);
           start();
           System.out.println("perfect");
       } catch (IOException ex) {
           Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   
   public void run()
   {
   
       String comingMessage = null ;
      
       try {
           while(true){
                comingMessage = listener.readLine();
                Vector<String> input = new Vector<>();
                if(comingMessage != null)
                {
                    input.add(comingMessage);
                    input.forEach((line) -> {
                      // System.out.println(line);
                           switch(line.split(" ")[0])
                {
                
                    case "first":
                    {
                    
                        firstMethod(line);
                        
                      
                      
                    }
                    
                    
                    case "second":
                    {
                        
                      secondMethod(line);
                     
                    }
                    
                    default:{
                    
                    System.out.println("defualt");
                    
                    }
                    
                }
                    });
              
    
       
           }
                }
              
          
       
       } catch (IOException ex) {
           Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
      
   }
   
   
   private void firstMethod(String ...params)
   { System.out.println(params[0]);
       sender.println("yes I am the first method and your param is" + params[0]);
   
   }
   
   
   private void secondMethod(String ...params)
   {
    System.out.println(params[0]);
      sender.println("yes I am the socond method and your param is : " + params[0]);
   }

}