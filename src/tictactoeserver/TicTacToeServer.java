package tictactoeserver;

import tictactoeserver.data.Server;
import java.net.ServerSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeserver.presentation.ServerViewController;
import tictactoeserver.presentation.ServerViewModel;


public class TicTacToeServer extends Application {
   static ServerSocket serverSocket;
    @Override
    public void start(Stage stage) throws Exception {
      
        Server.getInstance().start();
        Parent root = new ServerViewController(new ServerViewModel());

        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }

    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.

    }

   
    

    public static void main(String[] args) {
        launch(args);

    }
    
    
    
    
    
}
