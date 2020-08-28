package application;
	
import org.controlsfx.glyphfont.FontAwesome;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
	static Stage mainstage;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,1400,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.getIcons().add(new Image("/youtube_PNG2.png"));
			primaryStage.setTitle("AFPlayer");
			PauseTransition idle = new PauseTransition(Duration.seconds(1));
			idle.setOnFinished(e -> scene.setCursor(Cursor.NONE));
			scene.addEventHandler(Event.ANY, e -> {
			    idle.playFromStart();
			    scene.setCursor(Cursor.DEFAULT);
			    
			    
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
