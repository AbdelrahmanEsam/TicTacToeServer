
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.presentation;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import tictactoeserver.data.ClientHandler;
import tictactoeserver.data.DataAccessLayer;

public class ServerViewModel {
    
    SimpleBooleanProperty serverState = new SimpleBooleanProperty();
    ObservableList<String> onlinePlayersNames = FXCollections.observableArrayList();
    ObservableList<String> allPlayers = FXCollections.observableArrayList();
    SimpleIntegerProperty numberOfOfflinePlayers = new SimpleIntegerProperty();

    public SimpleIntegerProperty getNumberOfOfflinePlayers() {
        return numberOfOfflinePlayers;
    }

    public void setNumberOfOfflinePlayers(SimpleIntegerProperty numberOfOfflinePlayers) {
        this.numberOfOfflinePlayers = numberOfOfflinePlayers;
    }

    public ObservableList<String> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ObservableList<String> allPlayers) {
        this.allPlayers = allPlayers;
    }
    public ObservableList<String> getOnlinePlayersNames() {
        return onlinePlayersNames;
    }

    public void setOnlinePlayersNames(ObservableList<String> onlinePlayersNames) {
        this.onlinePlayersNames = onlinePlayersNames;
    }
    
    
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    


    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }
    
    
    public  ServerViewModel()
    {
       
    
     getPlayersDataFromServer();
      getAllPlayersFromDatabase();
    
    }

    public SimpleBooleanProperty getServerState() {
        return serverState;
    }

    public void setServerState(Boolean serverState) {
        this.serverState.set(serverState);
        ClientHandler.serverState.set(serverState);
        
    }
    
  
    
    
    private void getPlayersDataFromServer()
    {
    
        
       ClientHandler.onlinePlayersNames.addListener((ListChangeListener.Change<? extends String> change) -> {
       
            while (change.next()) {
                if (change.wasAdded()) {
                    onlinePlayersNames.addAll(change.getAddedSubList());
                }
            }
           
        });
    
    
    }
    
    
    private void getAllPlayersFromDatabase()
    {
    
        try {
          pieChartData.add(new PieChart.Data("Offline",DataAccessLayer.getAllPlayers().size() - onlinePlayersNames.size()));
          pieChartData.add(new PieChart.Data("Online",onlinePlayersNames.size()));

        } catch (SQLException ex) {
            Logger.getLogger(ServerViewModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
    
  
    
    
    
}