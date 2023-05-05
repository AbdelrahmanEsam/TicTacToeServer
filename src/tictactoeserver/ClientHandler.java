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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientHandler extends Thread{
    
   static Vector<ClientHandler> clients = new Vector();
    DataInputStream ear = null;
    PrintStream mouth = null;
  
   public ClientHandler(Socket newClient)
   {
   
       
       try {
           ear = new DataInputStream(newClient.getInputStream());
           mouth = new PrintStream(newClient.getOutputStream());
           
           ClientHandler.clients.add(this);
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
                comingMessage = ear.readLine();
                 mouth.println("hi");
       
           }
          
       
       } catch (IOException ex) {
           Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
      
   }
   
   
   private void sendMessageToAllClients(String comingMessage)
   {
   
       for(ClientHandler client : clients){   
           client.mouth.println(comingMessage);

       }
   }

}