
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver.presentation;

/**
 *
 * @author Aya
 */
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import tictactoeserver.data.Server;

public class SwitchButton extends StackPane {
	private final Rectangle back = new Rectangle(30, 10, Color.RED);
	private final Button button = new Button();

	private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";

	private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";

	
        SimpleBooleanProperty state = new SimpleBooleanProperty();

        private  Server server;
	private void init() {

               state.set(false);
		getChildren().addAll(back, button);

		setMinSize(30, 15);

		back.maxWidth(30);
		back.minWidth(30);

		back.maxHeight(10);
		back.minHeight(10);

		back.setArcHeight(back.getHeight());
		back.setArcWidth(back.getHeight());
		back.setFill(Color.valueOf("#ced5da"));

		Double r = 2.0;
		button.setShape(new Circle(r));


		setAlignment(button, Pos.CENTER_LEFT);
		button.setMaxSize(15, 15);
		button.setMinSize(15, 15);
		button.setStyle(buttonStyleOff);
	}

	public SwitchButton() {
		init();
                
		EventHandler<Event> click = new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
                            if(server == null)
                            {
                               getServerInstance();
                            }
				if (state.get()) {
                                      
					button.setStyle(buttonStyleOff);
					back.setFill(Color.valueOf("#ced5da"));
					setAlignment(button,Pos.CENTER_LEFT);
					state.set(false);
				} else {
                                    
                                   
					button.setStyle(buttonStyleOn);
					back.setFill(Color.valueOf("#80C49E"));
					setAlignment(button, Pos.CENTER_RIGHT);
					state.set(true);
				}
			}
		};

		button.setFocusTraversable(false);

		setOnMouseClicked(click);
		button.setOnMouseClicked(click);

	}
        
        public SimpleBooleanProperty getSwitchState()
        {
        
                return state;
        }
        
           public void setSwitchState(boolean  state)
        {
        
                this.state.set(state);
        }
        
        public Server getServerInstance()
        {
          server = Server.getInstance();
            
            return server; 
        
        }
        
}