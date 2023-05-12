
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.presentation;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public class ServerViewModel {
    
    SimpleBooleanProperty serverState = new SimpleBooleanProperty();
    
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Apple", 30),
                new PieChart.Data("Orange", 20),
                new PieChart.Data("Banana", 10),
                new PieChart.Data("Grape", 40)
        );

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }
    
    
    public  ServerViewModel()
    {
    
    serverState.set(false);
    
    }

    public SimpleBooleanProperty getServerState() {
        return serverState;
    }

    public void setServerState(SimpleBooleanProperty serverState) {
        this.serverState = serverState;
    }
    
    
    
}