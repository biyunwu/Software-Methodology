package app;

import app.model.order.OrderLine;
import app.model.sandwich.Chicken;
import app.model.sandwich.Extra;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/first.fxml"));
        primaryStage.setTitle("Sandwich Shop");
        primaryStage.setScene(new Scene(root, 700, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        // test
//        Chicken c = new Chicken();
//        ArrayList<Extra> extras = Extra.getExtraList();
//        extras.forEach(c::add);
//        OrderLine ol = new OrderLine(c);
//        System.out.println(ol.toString());
        launch(args);
    }
}
