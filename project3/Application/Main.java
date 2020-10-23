package Application;

import Application.Model.AccountDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/window.fxml"));
        AccountDatabase db = new AccountDatabase(); // Model
        loader.setControllerFactory((Class<?> type) -> { // Dependency Injection: pass model to all controllers.
            try {
                // find controllers which have AccountDatabase as the only parameter.
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1) {
                        if (c.getParameterTypes()[0] == AccountDatabase.class) {
                            return c.newInstance(db);
                        }
                    }
                }
                // cannot find appropriate constructor, use default constructor.
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        });
        // Parent root = FXMLLoader.load(getClass().getResource("View/window.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Transaction Interface");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
