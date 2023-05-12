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
                  

               }).start();
        Parent root = new ServerViewController(new ServerViewModel());

            
      

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
    
    
    
    
    
}
