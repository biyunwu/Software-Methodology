package app.controller;

import java.io.File;
import java.util.ArrayList;

import app.model.order.Order;
import app.model.sandwich.Beef;
import app.model.sandwich.Chicken;
import app.model.sandwich.Extra;
import app.model.sandwich.Fish;
import app.model.sandwich.Sandwich;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class First {
	
	private ArrayList<Extra> extras = Extra.getExtraList();
	private Order order = new Order();
	private Sandwich sandwich = new Chicken();
	
	@FXML
    private RadioButton beefButton, chickenButton, fishButton;

    @FXML
    private TextArea basicIngredients;

    @FXML
    private ImageView sandwichImage;

    @FXML
    private ListView<Extra> notOnSandwich, onSandwich;

    @FXML
    private Button rightButton, leftButton, addToOrderButton, viewOrderButton, clearButton;

    @FXML
    private TextField totalPrice;

    
    @FXML
    void changeDetails(ActionEvent event) {
    	if(chickenButton.isSelected()) {
    		sandwich = new Chicken();
    		totalPrice.setText(Double.toString(sandwich.price()));
    		basicIngredients.setText("Fried Chicken\nSpicy Sauce\nPickles");
    		File file = new File("src/ChickenSandwich.jpg");
    		Image image = new Image(file.toURI().toString());
    		sandwichImage.setImage(image);
    	} else if (beefButton.isSelected()) {
    		sandwich = new Beef();
    		totalPrice.setText(Double.toString(sandwich.price()));
    		basicIngredients.setText("Roast Beef\nProvolone Cheese\nMustard");
    		File file = new File("src/BeefSandwich.jpg");
    		Image image = new Image(file.toURI().toString());
    		sandwichImage.setImage(image);
    	} else {
    		sandwich = new Fish();
    		totalPrice.setText(Double.toString(sandwich.price()));
    		basicIngredients.setText("Grilled Snapper\nCilantro\nLime");
    		File file = new File("src/FishSandwich.jpg");
    		Image image = new Image(file.toURI().toString());
    		sandwichImage.setImage(image);
    	}
    }

    @FXML
    void addIngredient(ActionEvent event) {
    	Extra selection = notOnSandwich.getSelectionModel().getSelectedItem();
    	if(selection == null) {
    		Alert alert = new Alert(AlertType.WARNING, "Please select an ingredient to add", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
    	if(sandwich.add(selection) == false) {
    		Alert alert = new Alert(AlertType.WARNING, "Cannot have more than 6 extra ingredients", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}else {
    		onSandwich.getItems().add(selection);
    		notOnSandwich.getItems().remove(selection);
    		totalPrice.setText(Double.toString(sandwich.price()));
    	}
    }

    @FXML
    void removeIngredient(ActionEvent event) {
    	Extra selection = onSandwich.getSelectionModel().getSelectedItem();
    	if(selection == null) {
    		Alert alert = new Alert(AlertType.WARNING, "Please select an ingredient to remove", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
    	sandwich.remove(selection);
		onSandwich.getItems().remove(selection);
		notOnSandwich.getItems().add(selection);
		totalPrice.setText(Double.toString(sandwich.price()));
    }
    
    @FXML
    void clearFields(ActionEvent event) {
    	notOnSandwich.setItems(FXCollections.observableArrayList(extras));
    	onSandwich.getItems().clear();
    	sandwich = new Chicken();
		totalPrice.setText(Double.toString(sandwich.price()));
		basicIngredients.setText("Fried Chicken\nSpicy Sauce\nPickles");
		File file = new File("src/ChickenSandwich.jpg");
		Image image = new Image(file.toURI().toString());
		sandwichImage.setImage(image);
		chickenButton.setSelected(true);
    }

	public void initialize() {
		// add images to menu.

		// group radio buttons, set default choice to chicken sandwich.
		ToggleGroup sandwichTG = new ToggleGroup();
		chickenButton.setToggleGroup(sandwichTG);
		fishButton.setToggleGroup(sandwichTG);
		beefButton.setToggleGroup(sandwichTG);
		chickenButton.setSelected(true);
		
		notOnSandwich.setItems(FXCollections.observableArrayList(extras));
		
		basicIngredients.setEditable(false);
		basicIngredients.setText("Fried Chicken\nSpicy Sauce\nPickles");
		totalPrice.setEditable(false);
		totalPrice.setText(Double.toString(sandwich.price()));
		
		File file = new File("src/ChickenSandwich.jpg");
		Image image = new Image(file.toURI().toString());
		sandwichImage.setImage(image);
			
	}
}
