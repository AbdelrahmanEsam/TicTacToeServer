/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoeserver.data.DTOS.MatchDto;
import tictactoeserver.data.DTOS.PlayerDto;

class ClientHandler extends Thread{
     static Vector<ClientHandler> handlers = new Vector();
    static Vector<PlayerDto> onlinePlayers = new Vector();
    static Vector<MatchDto> onlineMatches = new Vector();
    DataInputStream listener = null;
    PrintStream sender = null;
  
   public ClientHandler(Socket newClient)
   {
   
       
       try {
           listener = new DataInputStream(newClient.getInputStream());
           sender = new PrintStream(newClient.getOutputStream());
           
           ClientHandler.handlers.add(this);
           start();
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
                if(comingMessage != null)
                {
                   
                 switch(comingMessage.split(" ")[0])
                {
                
                    case "online":
                    {
                    
                        online(comingMessage);
                        
                      
                      break;
                    }
                    
                    
                    case "offline":
                    {
                        
                      offline(comingMessage);
                        break;
                    }
                    
                    case "acceptGameRequest":
                    {
                        
                      sendGameRequestPlayerOne(comingMessage);
                        break;
                    }
                    
                    
                    case "sendGameRequest":
                    {
                        
                      sendGameRequestToPlayerTwo(comingMessage);
                        break;
                    }
                    
                    
                    
                    case "acceptMoveRequest":
                    {
                        
                      requestGameMove(comingMessage);
                        break;
                    }
                    
                    
                    case "acceptGameResult":
                    {
                    
                        break;
                    }
                    
                    default:{
                    
                    System.out.println("defualt");
                    
                    }
                    
                }
                  
              
    
       
           }
                }
              
          
       
       } catch (IOException ex) {
           Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
       }
      
   }
   
   
   private void online(String ...params)
   {
       onlinePlayers.add(new PlayerDto());
   
   }
   
   
   private void offline(String ...params)
   {
      onlinePlayers.removeIf(player -> params[1].equals(player.getPlayerName()));
      //todo notify the other (if there) that the other player is offline now
   }
   
   
     private void sendGameRequestPlayerOne(String ...params)
     {
   
         
   
     }
   
   
     private void sendGameRequestToPlayerTwo(String ...params)
     {
   
         
   
   
     }
   
   
   
     private void sendTheGameResponseToTheFirstPlayer(String ...params)
      {
   
   
   
     }
   
   
 
   
     private void gameHandler(String ...params)
     {
   
   
   
     }
   
   
    private void requestGameMove(String ...params)
    {
     
     
     
    }
    
    private void sendGameResult(String ...params)
    {
     
     
     
    }
    
    
    private void updateDatabase(String ...params)
    {
     
     
     
    }
    
    
    private void gameMoveObserver()
    {
    
    
    
    }
    
     
     
    
   
   

}