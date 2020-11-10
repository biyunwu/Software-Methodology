package app.controller;

import app.model.order.Order;
import app.model.order.OrderLine;
import app.model.sandwich.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class First {

	private ArrayList<Extra> extras = Extra.getExtraList();
	private Order order = new Order();
	private Sandwich sandwich = new Chicken();
	DecimalFormat df = new DecimalFormat("0.00");

	@FXML
	private TextArea basicIngredients;

	@FXML
	private ImageView sandwichImage;

	@FXML
	private ListView<Extra> notOnSandwich, onSandwich;

	@FXML
	private TextField totalPrice;
	
	@FXML
    private ComboBox<String> sandwichSelector;

	/** Change details within UI based on sandwich selection. */
	@FXML
	void changeDetails() {
		notOnSandwich.setItems(FXCollections.observableArrayList(extras));
		onSandwich.getItems().clear();
		String selectedSandwich = sandwichSelector.getSelectionModel().getSelectedItem();
		switch(selectedSandwich) {
			case "Chicken" -> {
				sandwich = new Chicken();
				totalPrice.setText(df.format(sandwich.price()));
				basicIngredients.setText("Fried Chicken\nSpicy Sauce\nPickles");
				Image image = new Image("file:src/ChickenSandwich.jpg");
				sandwichImage.setImage(image);
			}
			case "Beef" -> {
				sandwich = new Beef();
				totalPrice.setText(df.format(sandwich.price()));
				basicIngredients.setText("Roast Beef\nProvolone Cheese\nMustard");
				Image image = new Image("file:src/BeefSandwich.jpg");
				sandwichImage.setImage(image);
			}
			case "Fish" -> {
				sandwich = new Fish();
				totalPrice.setText(df.format(sandwich.price()));
				basicIngredients.setText("Grilled Snapper\nCilantro\nLime");
				Image image = new Image("file:src/FishSandwich.jpg");
				sandwichImage.setImage(image);
			}
		}
	}

	/** Add an ingredient to the selected sandwich. */
	@FXML
	void addIngredient() {
		Extra selection = notOnSandwich.getSelectionModel().getSelectedItem();
		if (selection == null) {
			Alert alert = new Alert(AlertType.WARNING, "Please select an ingredient to add", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		if (!sandwich.add(selection)) {
			Alert alert = new Alert(AlertType.WARNING, "Cannot have more than 6 extra ingredients", ButtonType.OK);
			alert.showAndWait();
		} else {
			onSandwich.getItems().add(selection);
			notOnSandwich.getItems().remove(selection);
			totalPrice.setText(df.format(sandwich.price()));
		}
	}

	/** Remove an ingredient from the selected sandwich. */
	@FXML
	void removeIngredient() {
		Extra selection = onSandwich.getSelectionModel().getSelectedItem();
		if (selection == null) {
			Alert alert = new Alert(AlertType.WARNING, "Please select an ingredient to remove", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		sandwich.remove(selection);
		onSandwich.getItems().remove(selection);
		notOnSandwich.getItems().add(selection);
		totalPrice.setText(df.format(sandwich.price()));
	}

	/** Add the selected sandwich and its ingredients to the order. */
	@FXML
	void addToOrder() {
		OrderLine line = new OrderLine(sandwich);
		order.add(line);
		clearFields();
	}

	/** Reset all fields back to default. */
	@FXML
	void clearFields() {
		sandwichSelector.getSelectionModel().selectFirst();
		changeDetails();
	}

	/** Opens a separate window that shows the order list. */
	@FXML
	void openOrderList() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/second.fxml"));
			Parent root = loader.load();
			Second controller2 = loader.getController();
			controller2.setOrder(order);
			Scene scene = new Scene(root, 620, 520);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL); // disable primary stage when child stage is open.
			stage.setTitle("View Order");
			stage.setOnCloseRequest(arg0 -> order = controller2.getOrder());
			stage.show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, "Error opening order list.", ButtonType.OK);
			alert.showAndWait();
		}
	}
	

	/**
	 * Initialize the user interface through grouping radio buttons by category and
	 * setting default states.
	 **/
	public void initialize() {
		basicIngredients.setEditable(false);
		// set default choice to chicken sandwich.
		ObservableList<String> sandwichTypes = FXCollections.observableArrayList("Chicken", "Beef", "Fish");
		sandwichSelector.setItems(sandwichTypes);
		sandwichSelector.getSelectionModel().selectFirst();

		// set details of sandwich
		changeDetails();
	}
}
