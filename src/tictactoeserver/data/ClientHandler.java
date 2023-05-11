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
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import static jdk.nashorn.tools.ShellFunctions.input;
import tictactoeserver.data.DTOS.MatchDto;
import tictactoeserver.data.DTOS.PlayerDto;
import tictactoeserver.data.stream_messages.ClientMessage;
import tictactoeserver.data.stream_messages.ServerMessage;

class ClientHandler extends Thread {

    static Vector<Pair<String, ClientHandler>> onlinePlayers = new Vector();
    static Vector<String> onlineMatches = new Vector();
    DataInputStream listener = null;
    PrintStream sender = null;
    Socket newClient;

    public ClientHandler() {
    }

    public ClientHandler(Socket newClient) {
        this.newClient = newClient;

        try {
            listener = new DataInputStream(newClient.getInputStream());
            sender = new PrintStream(newClient.getOutputStream());

            //todo 
            start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {

        String comingMessage = null;

        try {
            while (true) {
                if (!newClient.isClosed()) {

                    comingMessage = listener.readLine();
                    if (comingMessage != null) {

                        switch (comingMessage.split(" ")[0]) {

                            case "acceptMoveRequest": {

                                requestGameMove(comingMessage);
                                break;
                            }

                            case "acceptGameResult": {

                                acceptGameResult(comingMessage);
                                break;
                            }
                            case "Register": {

                                validateRegistrationCredentials(comingMessage);
                                break;

                            }
                            case "Login": {

                                validateLoginCredentials(comingMessage);
                                break;

                            }
                            case "AvailablePlayers":
                                handleOnlinePlayersEvents(comingMessage);
                                break;
                            case ClientMessage.SEND_GAME_REQUEST:
                                sendGameRequest(comingMessage);
                                break;
                            case ClientMessage.ACCEPT_GAME_REQUEST:
                                acceptGameResponse(comingMessage);
                                break;
                            case ClientMessage.REJECT_GAME_REQUEST:
                                rejectGameResponse(comingMessage);
                                break;

                            default: {

                                System.out.println("defualt");

                            }

                        }
                    }
                }
            }
        } catch (IOException ex) {
            try {
                listener.close();
                sender.close();
            } catch (IOException e) {

            }
        }

    }

    private void requestGameMove(String request) {
        String secondPlayerName = request.split(" ")[1];
        ClientHandler clientHandler = getClientHandler(secondPlayerName);

        if (clientHandler != null) {
            clientHandler.sendMoveResponse(Integer.valueOf(request.split(" ")[2]),
                    Integer.valueOf(request.split(" ")[3]));
        } else {
            opponentIsOffline();
        }
    }

    private void opponentIsOffline() {

        sender.println("your opponent is offline you are the winner");
        //todo add a game one to him in database and handle
        //if the first player is offline too then drop the game 

    }

    private void sendMoveResponse(int row, int column) {

        sender.println("acceptMoveResponse" + " " + row + " " + column);

    }

    private void acceptGameResult(String comingMessage) {
        String playerOne = comingMessage.split(" ")[1];
        String playerTwo = comingMessage.split(" ")[2];
        onlineMatches.remove(playerOne);
        onlineMatches.remove(playerTwo);
        //todo add the result to database
        ClientHandler clientHandlerOne = getClientHandler(playerOne);
        ClientHandler clientHandlerTwo = getClientHandler(playerTwo);
        clientHandlerOne.sendGameResult("gameResultResponse" + " " + comingMessage.split(" ")[3]);
        clientHandlerTwo.sendGameResult("gameResultResponse" + " " + comingMessage.split(" ")[3]);

    }

    private void sendGameResult(String comingMessage) {
        sender.println(comingMessage);
    }

    private ClientHandler getClientHandler(String playerName) {

        int index = 0;
        while (index < onlinePlayers.size()) {
            if (onlinePlayers.get(index).getKey().equals(playerName)) {
                return onlinePlayers.get(index).getValue();

            }
            index++;
        }
        return null;
    }

    private void validateRegistrationCredentials(String line) {
        //To DO check whether username is already registered in DB
        String credentials[] = line.split(" ");
        PlayerDto player = new PlayerDto(credentials[1], credentials[2]);
        String registrationResponse = DataAccessLayer.register(player);
        switch (registrationResponse) {
            case SQLMessage.PLAYER_REGISTERED_SUCCESSFULLY:
                registrationResponse = "Successful";
                break;
            case SQLMessage.PLAYER_NAME_ALREADY_EXISTS:
                registrationResponse = "Failed Username";
                break;
            case SQLMessage.INCORRECT_PASSWORD:
                registrationResponse = "Failed Password";
                break;
            default:
                registrationResponse = "Error";
        }
        sendRegistartionResponse(registrationResponse);
    }

    private void sendRegistartionResponse(String response) {
        System.out.println("Register " + response);
        sender.println("Register " + response);
    }

    private void validateLoginCredentials(String line) {
        //To DO check whether username is already registered in DB
        String credentials[] = line.split(" ");
        PlayerDto player = new PlayerDto(credentials[1], credentials[2]);
        String loginResponse = DataAccessLayer.login(player);
        switch (loginResponse) {
            case SQLMessage.LOGIN_SUCCESSFULLY:
                onlinePlayers.add(new Pair(player.getPlayerName(), this));
                loginResponse = "Successful";
                break;
            case SQLMessage.NO_SUCH_PLAYER:
                loginResponse = "Failed Username";
                break;
            case SQLMessage.INCORRECT_PASSWORD:
                loginResponse = "Failed Password";
                break;
            default:
                loginResponse = "Error";
        }
        sendLoginResponse(loginResponse);
    }

    private void sendLoginResponse(String str) {
        sender.println("Login" + " " + str);
    }

    private void handleOnlinePlayersEvents(String line) {
        String clientMessage = line.split(" ")[1];
        switch (clientMessage) {
            case ClientMessage.GET:
                sendPlayersList();
                break;

        }

    }

    private void sendPlayersList() {
        if (onlinePlayers.isEmpty()) {
            sender.println(ServerMessage.AVILABLE_PLAYERS + " " + ServerMessage.NO_DATA);
        } else {
            StringBuilder players = new StringBuilder();
            players.append(ServerMessage.AVILABLE_PLAYERS + " ");
            for (Pair<String, ClientHandler> player : onlinePlayers) {
                String playerName = player.getKey();
                System.out.println(playerName);
                if (!onlineMatches.contains(playerName)) {

                    players.append(playerName + " ");
                }
            }sender.println(players.toString());
            
        }
    }

    private void sendGameRequest(String comingMessage) {
        System.out.println("SendRequest On Server");
        String senderName = comingMessage.split(" ")[1];
        String receiverName = comingMessage.split(" ")[2];
        getClientHandler(receiverName).sender.println(ServerMessage.SEND_GAME_REQUEST + " " + senderName);
    }

    private void acceptGameResponse(String comingMessage) {
        System.out.println("acceptGameResponse on server");
        String senderName = comingMessage.split(" ")[1];
        String receiverName = comingMessage.split(" ")[2];
        onlineMatches.add(senderName);
        onlineMatches.add(receiverName);

        getClientHandler(receiverName).sender.println(ServerMessage.SEND_GAME_REQUEST + " " + senderName);
    }

    private void rejectGameResponse(String comingMessage) {
        System.out.println("rejectGameResponse on server");
        String senderName = comingMessage.split(" ")[1];
        String receiverName = comingMessage.split(" ")[2];
        getClientHandler(receiverName).sender.println(ServerMessage.DENIED_GAME_RESPONSE + " " + senderName);
    }

}
