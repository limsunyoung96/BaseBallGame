package kr.or.nextit.BaseBallGameProject;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("BaseBallGame.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("야구게임");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
