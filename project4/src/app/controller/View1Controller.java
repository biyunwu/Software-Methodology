package app.controller;

import app.model.order.Order;
import app.model.order.OrderLine;
import app.model.sandwich.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View1Controller implements Initializable {
	private Sandwich currentSandwich = new Chicken();
	private static final String[] sandwiches = {"Chicken Sandwich", "Beef Sandwich", "Fish Sandwich"};
	private final Image[] images = {new Image("file:src/app/images/chicken.jpg"),
			new Image("file:src/app/images/beef.jpg"), new Image("file:src/app/images/fish.jpg")};
	private Order order = new Order();

	@FXML
	private ComboBox<String> combobox;

	@FXML
	private ImageView imageView;

	@FXML
	private ListView<String> baseVL;

	@FXML
	private ListView<Extra> extraVL, selectedVL;

	@FXML
	private Label priceLabel;

	@FXML
	private TextArea feedback;

	@FXML
	void add() {
		try {
			ObservableList<Extra> selectedItems = extraVL.getSelectionModel().getSelectedItems();
			if (selectedItems.isEmpty()) throw new Exception("ERROR: Nothing to add.");
			ArrayList<Extra> tempList = new ArrayList<>();
			for (Extra selectedItem : selectedItems) {
				if (!currentSandwich.add(selectedItem)) {
					// remove already added ingredients before throw exception.
					for (Extra obj : tempList) extraVL.getItems().remove(obj);
					throw new Exception("ERROR: More than 6 extra ingredients are not allowed.");
				}
				tempList.add(selectedItem);
			}
			for (Extra obj : tempList) extraVL.getItems().remove(obj);
		} catch (Exception e) {
			feedback.appendText("ERROR:" + e.getMessage() + "\n");
		} finally {
			updateListViews();
		}
	}

	@FXML
	void remove() {
		try {
			ObservableList<Extra> selectedItems = selectedVL.getSelectionModel().getSelectedItems();
			if (selectedItems.isEmpty()) throw new Exception("ERROR: Nothing to remove.");
			for (Extra item : selectedItems) {
				extraVL.getItems().add(item);
				currentSandwich.remove(item);
			}
		} catch (Exception e) {
			feedback.appendText("ERROR: " + e + "\n");
		} finally {
			updateListViews();
		}
	}

	@FXML
	void clear() {
		resetExtraListViews();
		feedback.clear();
	}

	@FXML
	void addToOrder() {
		try {
			OrderLine ol = new OrderLine(currentSandwich);
			order.add(ol);
			feedback.appendText("Added: " + ol.toString() + "\n");
			combobox.getSelectionModel().selectFirst(); // set default to chicken.
			updateSandwich(sandwiches[0]); // set default to chicken.
		} catch (Exception e) {
			feedback.appendText("ERROR: " + e.toString() + "\n");
		}
	}

	@FXML
	void showOrder() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/view2.fxml"));
			View2Controller second = new View2Controller(); // instantiate the 2nd controller
			second.setParentController(this); // pass this controller to the 2nd controller
			loader.setController(second); // assign the 2nd controller to view2.fxml

			Stage childStage = new Stage();
			childStage.setTitle("Order Detail");
			childStage.setScene(new Scene(loader.load(), 700, 650));
			childStage.initModality(Modality.APPLICATION_MODAL); // disable primary stage when child stage is open.
			childStage.show();
		} catch (Exception e) {
			feedback.appendText("ERROR: " + e.toString() + "\n");
		}
	}

	private void updateSandwich(String newVal) {
		try {
			if (newVal.equals(sandwiches[0])) {
				currentSandwich = new Chicken();
				imageView.setImage(images[0]);
			} else if (newVal.equals(sandwiches[1])) {
				currentSandwich = new Beef();
				imageView.setImage(images[1]);
			} else if (newVal.equals(sandwiches[2])) {
				currentSandwich = new Fish();
				imageView.setImage(images[2]);
			} else {
				throw new Exception("ERROR: Unknown sandwich type.");
			}
		} catch (Exception e) {
			feedback.appendText("ERROR: " + e.toString() + "\n");
		} finally {
			baseVL.setItems(FXCollections.observableArrayList(currentSandwich.getBaseIngredients()));
			resetExtraListViews();
		}
	}

	private void resetExtraListViews() {
		extraVL.setItems(FXCollections.observableArrayList(Extra.getExtraList())); // fetch extra objects.
		extraVL.getSelectionModel().selectFirst();
		currentSandwich.resetExtraList();
		updateListViews();
	}

	private void updateListViews() {
		selectedVL.setItems(FXCollections.observableArrayList(currentSandwich.getExtras()));
		selectedVL.getSelectionModel().selectFirst();
		priceLabel.setText(String.format("%.2f", currentSandwich.price()));
	}

	public Order getOrder() {
		return order;
	}

	public boolean addOrderLine(OrderLine ol) {
		if (ol != null) {
			return order.add(new OrderLine(ol.getSandwich()));
		}
		return false;
	}

	public boolean removeOrderLine(OrderLine ol) {
		return order.remove(ol);
	}

	public void clearOrder() {
		order = new Order();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// add choices to the combobox, set default choice to chicken sandwich.
		combobox.setItems(FXCollections.observableArrayList(sandwiches));
		combobox.getSelectionModel().selectedItemProperty().addListener( // change sandwich based on user selection.
				(observableValue, oldVal, newVal) -> updateSandwich(newVal)
		);
		combobox.getSelectionModel().selectFirst(); // Set default to chicken sandwich.
		extraVL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		selectedVL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		resetExtraListViews();
	}
}
