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
import tictactoeserver.data.DTOS.GameDto;
import tictactoeserver.data.DTOS.PlayerDto;

/**
 *
 * @author 3WD
 */
public class DataAccessLayer {

    private static Connection connection;
    private static final int SOCRE_FACTOR = 10; //Player score will be incremented by this factor on win

    static {
        try {
            connection = DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/TicTacToe", "root", "root");

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void connect() throws SQLException {
        DriverManager.registerDriver(new ClientDriver());

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
            loginMessage = SQLMessage.NO_SUCH_PLAYER;
        } else {

            loginMessage = isPasswordCorrect(player.getPlayerName(), player.getPassword())
                    ? SQLMessage.LOGIN_SUCCESSFULLY : SQLMessage.INCORRECT_PASSWORD;
        }
        return loginMessage;
    }

    public static String register(PlayerDto player) {
        String registerMessage = null;
        int insertStatus = 0;
        if (isUserExist(player.getPlayerName())) {
            registerMessage = SQLMessage.PLAYER_NAME_ALREADY_EXISTS;
        } else {
            try {
                insertStatus = insertNewPlayerIntoDB(player);
            } catch (SQLException ex) {
                Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            registerMessage = insertStatus == 0
                    ? SQLMessage.INSERTION_ERROR : SQLMessage.PLAYER_REGISTERED_SUCCESSFULLY;
        }
        return registerMessage;
    }

    static private int insertNewPlayerIntoDB(PlayerDto player) throws SQLException {
        int result = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT INTO PLAYERS VALUES (?,?,0,0,0,0)");
        preparedStatement.setString(1, player.getPlayerName());
        preparedStatement.setString(2, player.getPassword());
        result = preparedStatement.executeUpdate();
        preparedStatement.close();
//        System.out.println(player.getPlayerName() + " Inserted Successfully");
        return result;
    }

    private static boolean isUserExist(String userName) {
        boolean isExist = false;
        try {
//    
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
//    
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

    public static String UpdatePlayerScore(String playerName) {
        String updateMessage = null;
        int result = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET SCORE = ? WHERE USERNAME = ?");
            int oldScore = getPlayerOldScore(playerName);
            int newScore = oldScore + SOCRE_FACTOR;
            preparedStatement.setInt(1, newScore);
            preparedStatement.setString(2, playerName);
            result = preparedStatement.executeUpdate();
            updateMessage = result == 0 ? SQLMessage.UPDATE_ERROR : SQLMessage.SCORE_UPDATED;
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updateMessage;
    }

    public static String UpdatePlayerGamesWonNumber(String playerName) {
        String updateMessage = null;
        int result = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET GAMESWON = ? WHERE USERNAME = ?");
            int oldNumber = getPlayerGamesWonNumber(playerName);
            int newNumber = oldNumber + 1;
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setString(2, playerName);
            result = preparedStatement.executeUpdate();
            updateMessage = result == 0 ? SQLMessage.UPDATE_ERROR : SQLMessage.GAMES_WON_NUMBER_UPDATED;
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updateMessage;
    }

    public static String UpdatePlayerGamesLostNumber(String playerName) {
        String updateMessage = null;
        int result = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET GAMESLOST = ? WHERE USERNAME = ?");
            int oldNumber = getPlayerGamesLostNumber(playerName);
            int newNumber = oldNumber + 1;
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setString(2, playerName);
            result = preparedStatement.executeUpdate();
            updateMessage = result == 0 ? SQLMessage.UPDATE_ERROR : SQLMessage.GAMES_LOST_NUMBER_UPDATED;
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updateMessage;
    }

    public static String UpdatePlayerGamesDrawnNumber(String playerName) {
        String updateMessage = null;
        int result = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET GAMESDRAWN = ? WHERE USERNAME = ?");
            int oldNumber = getPlayerGamesDrawnNumber(playerName);
            int newNumber = oldNumber + 1;
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setString(2, playerName);
            result = preparedStatement.executeUpdate();
            updateMessage = result == 0 ? SQLMessage.UPDATE_ERROR : SQLMessage.GAMES_DRAWN_NUMBER_UPDATED;
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updateMessage;
    }

    private static int getPlayerOldScore(String playerName) {
        int storedScore = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT SCORE FROM PLAYERS WHERE USERNAME = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                storedScore = resultSet.getInt("SCORE");
                System.out.println("OldScore: " + resultSet.getInt("SCORE"));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storedScore;
    }

    private static int getPlayerGamesWonNumber(String playerName) {
        int gamesWon = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT GAMESWON FROM PLAYERS WHERE USERNAME = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gamesWon = resultSet.getInt("GAMESWON");
                System.out.println("Games won: " + resultSet.getInt("GAMESWON"));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gamesWon;
    }

    private static int getPlayerGamesLostNumber(String playerName) {
        int gamesLost = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT GAMESLOST FROM PLAYERS WHERE USERNAME = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gamesLost = resultSet.getInt("GAMESLOST");
                System.out.println("Games Lost: " + resultSet.getInt("GAMESLOST"));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gamesLost;
    }

    private static int getPlayerGamesDrawnNumber(String playerName) {
        int gamesDrawn = 0;
        try {

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("SELECT GAMESDRAWN FROM PLAYERS WHERE USERNAME = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gamesDrawn = resultSet.getInt("GAMESDRAWN");
                System.out.println("Games Drawn: " + resultSet.getInt("GAMESDRAWN"));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gamesDrawn;
    }

    public static String insertNewGame(String gameId) {
        String insertMessage = null;
        try {
            int result = 0;

            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement("INSERT INTO GAME VALUES (?) WHERE NOT EXISTS SELECT 1 FROM GAME WHERE GAME_ID = ?");
            preparedStatement.setString(1, gameId);
            preparedStatement.setString(2, gameId);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Game " + gameId + " Inserted Successfully");
            insertMessage = result == 0 ? SQLMessage.INSERTION_ERROR : SQLMessage.GAME_ADDED_SUCCESSFULLY;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insertMessage;
    }

    public static int update(PlayerDto player) throws SQLException {
        int result = 0;
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("UPDATE PLAYERS SET ...TBD");

        result = preparedStatement.executeUpdate();
        System.out.println(player.toString() + " Updated Successfully");
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
