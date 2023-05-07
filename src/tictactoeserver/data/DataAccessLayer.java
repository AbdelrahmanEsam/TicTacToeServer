/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
import tictactoeserver.data.DTOS.PlayerDto;

/**
 *
 * @author 3WD
 */
public class DataAccessLayer {

    private static Connection connection;

    public static void connect() throws SQLException {
        DriverManager.registerDriver(new ClientDriver());
        connection = DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/TicTacToe", "root", "root");
        System.out.println("Connected to DB");
    }

    public static void disconnect() throws SQLException {
        System.out.println("Disconnected from DB");
        connection.close();
    }

    public static List<PlayerDto> getAllPlayers() throws SQLException {
        List<PlayerDto> allPlayers = new ArrayList<>();

        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERS");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            PlayerDto c = new PlayerDto(resultSet.getString("USERNAME"),
                    resultSet.getString("PASSWORD"),
                    resultSet.getInt("SCORE"),
                    resultSet.getInt("GAMESWON"),
                    resultSet.getInt("GAMESLOST"),
                    resultSet.getInt("GAMESDRAWN"));
            allPlayers.add(c);

        }
        preparedStatement.close();
        return allPlayers;

    }

    public static String login(PlayerDto player) {
        String loginMessage = null;
        if (!isUserExist(player.getPlayerName())) {
            loginMessage = "NO_SUCH_PLAYER";
        } else {

            loginMessage = isPasswordCorrect(player.getPlayerName(), player.getPassword())
                    ? "LOGIN_SUCCESSFULLY" : "INCORRECT_PASSWORD";
        }
        return loginMessage;
    }

    public static String register(PlayerDto player) {
        String registerMessage = null;
        int insertStatus = 0;
        if (isUserExist(player.getPlayerName())) {
            registerMessage = "PLAYER_NAME_ALREADY_EXISTS";
        } else {
            try {
                insertStatus = insertNewPlayerIntoDB(player);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            registerMessage = insertStatus == 0
                    ? "DB_INSERTION_ERROR" : "PLAYER_REGISTERED_SUCCESSFULLY";

        }
        return registerMessage;
    }

    static private int insertNewPlayerIntoDB(PlayerDto player) throws SQLException {
        int result = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO PLAYERS VALUES (?, ?)");
        preparedStatement.setString(1, player.getPlayerName());
        preparedStatement.setString(2, player.getPassword());
        result = preparedStatement.executeUpdate();
        preparedStatement.close();
        System.out.println(player.getPlayerName() + " Inserted Successfully");
        return result;
    }

    private static boolean isUserExist(String userName) {
        boolean isExist = false;
        try {
            connection = DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/TicTacToe", "root", "root");
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERS WHERE USERNAME = ?");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            isExist = resultSet.next();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;

    }

    private static boolean isPasswordCorrect(String userName, String password) {
        boolean isCorrect = false;
        try {
            connection = DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/TicTacToe", "root", "root");
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERS WHERE USERNAME = ?  AND PASSWORD = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            isCorrect = resultSet.next();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isCorrect;

    }

    public static int update(PlayerDto contact) throws SQLException {
        int result = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET ...TBD");

        result = preparedStatement.executeUpdate();
        System.out.println(contact.toString() + " Updated Successfully");
        preparedStatement.close();
        return result;
    }

    public static int delete(String userName) throws SQLException {
        int result = -1;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("DELETE FROM PLAYERS WHERE USERNAME = ?");
        preparedStatement.setString(1, userName);
        result = preparedStatement.executeUpdate();
        preparedStatement.close();
        return result;
    }

}
