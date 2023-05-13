
package tictactoeserver.presentation;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import tictactoeserver.data.ClientHandler;
import tictactoeserver.data.DataAccessLayer;

public  class ServerViewController extends BorderPane {

    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints;
    protected final RowConstraints rowConstraints;
    protected final GridPane gridPane0;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final Label label;
    protected final PieChart pieChart;
    protected final ListView listView;
    private SwitchButton switchToggle ;
   private ServerViewModel viewModel; 
     
     
    

    public ServerViewController(ServerViewModel viewModel) {
 
        this.viewModel = viewModel;

        gridPane = new GridPane();
        columnConstraints = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        gridPane0 = new GridPane();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        label = new Label();
        pieChart = new PieChart();
        listView = new ListView();
        

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(gridPane, javafx.geometry.Pos.CENTER);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMaxWidth(600.0);
        columnConstraints0.setMinWidth(600.0);
        columnConstraints0.setPrefWidth(600.0);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setValignment(javafx.geometry.VPos.CENTER);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(label, 1);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setText("Server is active");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        GridPane.setMargin(label, new Insets(0.0));
        setTop(gridPane);

        BorderPane.setAlignment(pieChart, javafx.geometry.Pos.CENTER);
        setCenter(pieChart);

        BorderPane.setAlignment(listView, javafx.geometry.Pos.CENTER);
        listView.setPrefHeight(200.0);
        listView.setPrefWidth(200.0);
        setRight(listView);

        gridPane.getColumnConstraints().add(columnConstraints);
        gridPane.getRowConstraints().add(rowConstraints);
        gridPane0.getColumnConstraints().add(columnConstraints0);
        gridPane0.getRowConstraints().add(rowConstraints0);
        gridPane0.getRowConstraints().add(rowConstraints1);
        gridPane0.getChildren().add(label);
        gridPane.getChildren().add(gridPane0);

        switchToggle = new SwitchButton();
       
    
    
//    button
        
    HBox hbox = new HBox(switchToggle);
    hbox.setAlignment(Pos.TOP_CENTER);
    
  //hbox.setAlignment(Pos.TOP_CENTER);
gridPane.add(hbox, 0, 2, 2, 2);

/*VBox hbox = new VBox(label, button);
hbox.setMaxWidth(150);
hbox.setAlignment(Pos.CENTER);

gridPane.add(hbox, 0, 1, 2, 1);
*/

 //  gridPane.add(hbox, 0, 2, 2, 2);
    
 
      setPieChart();
      serverStateListener();
      switchToggleObserver();
      onlinePlayersObserver();
        listView.setVisible(false);
        pieChart.setVisible(false);
   
    }
    
    
    
    
    private void serverStateListener()
    {
    
        viewModel.getServerState().addListener((observable, oldValue, newValue) -> {

                listView.setVisible(newValue);
                pieChart.setVisible(newValue);

        });
    }
    
    
    private void onlinePlayersObserver()
    {
        try {
            listView.getItems().addAll(DataAccessLayer.getAllPlayers());
        } catch (SQLException ex) {
            Logger.getLogger(ServerViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
     viewModel.getOnlinePlayersNames().addListener((ListChangeListener.Change<? extends String> change) -> {
       
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> {
                         listView.getItems().addAll(change.getAddedSubList());
                    });
                   
                }
            }
           
        });
    
    
    }
    
    private void switchToggleObserver()
    {
    
        
        switchToggle.getSwitchState().addListener((observable, oldValue, newValue) -> {
           
             
           
               viewModel.setServerState(newValue);
           
           
        });
              
               

    
        
    }
    
    
    
    private void setPieChart()
    {
    
    
            
         pieChart.setData(viewModel.getPieChartData());
         
    
    
    }
    
    
    
   
    
    
    
    
    
}