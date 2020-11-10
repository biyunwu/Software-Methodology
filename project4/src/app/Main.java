package app;

/**
 * Main class which initiates the JavaFX application.
 *
 * @author Biyun Wu, Anthony Triolo
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * Start the JavaFX application by setting up user interface and injecting
	 * components.
	 * 
	 * @param primaryStage JavaFX Stage component.
	 * @throws Exception exceptions to be handled.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("view/first.fxml"));
		primaryStage.setTitle("Sandwich Selection");
		primaryStage.setScene(new Scene(root, 620, 520));
		primaryStage.show();
	}

	/**
	 * Main method to launch the program.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
