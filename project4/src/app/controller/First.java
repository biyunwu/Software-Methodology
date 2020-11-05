package app.controller;

import app.model.order.Order;
import app.model.order.OrderLine;
import app.model.sandwich.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.NoSuchElementException;

public class First {
	private Sandwich currentSandwich;
	private ObservableList<Extra> extrasOL;
	private final Order order = new Order();

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
			for (Extra selectedItem : selectedItems) {
				if (!currentSandwich.add(selectedItem)) {
					throw new Exception("ERROR: More than 6 extra ingredients are not allowed.");
				}
				extrasOL.remove(selectedItem);
			}
		} catch (Exception e) {
			feedback.appendText(e.getMessage() + "\n");
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
				extrasOL.add(item);
				currentSandwich.remove(item);
			}
		} catch (Exception e) {
			feedback.appendText(e + " " + (e instanceof NoSuchElementException) + "\n");
		} finally {
			updateListViews();
		}
	}

	@FXML
	void addToOrder() {
		for (Extra obj : currentSandwich.getExtras()) {
			currentSandwich.add(obj);
		}
		order.add(new OrderLine(currentSandwich));
		initialize(); // reset UI.
		// for test only
		for(OrderLine ol : order.getOrderlines()) {
			System.out.println(ol.toString()); // remove!!!
		}
	}

	@FXML
	void clear() {
		resetExtraListViews();
		feedback.clear();
	}

	private void updateSandwich(String newVal) {
		try {
			switch (newVal) {
				case "Chicken Sandwich" -> {
					currentSandwich = new Chicken();
					imageView.setImage(new Image("file:src/app/images/chicken.jpg"));
				}
				case "Beef Sandwich" -> {
					currentSandwich = new Beef();
					imageView.setImage(new Image("file:src/app/images/beef.jpg"));
				}
				case "Fish Sandwich" -> {
					currentSandwich = new Fish();
					imageView.setImage(new Image("file:src/app/images/fish.jpg"));
				}
				default -> throw new Exception("ERROR: Unknown sandwich type.");
			}
		} catch (Exception e) {
			feedback.appendText(e.toString() + "\n");
		} finally {
			String[] base = currentSandwich.getBaseIngredients();
			baseVL.setItems(FXCollections.observableArrayList(base));
			resetExtraListViews();
		}
	}

	private void resetExtraListViews() {
		extrasOL = FXCollections.observableArrayList(Extra.getExtraList()); // fetch extra objects.
		currentSandwich.removeExtras();
		updateListViews();
	}

	private void updateListViews() {
		extraVL.setItems(extrasOL);
		selectedVL.setItems(FXCollections.observableArrayList(currentSandwich.getExtras()));
		selectedVL.getSelectionModel().selectFirst();
		priceLabel.setText(String.format("%.2f", currentSandwich.price()));
	}

	public void initialize() {
		// add choices to the combobox, set default choice to chicken sandwich.
		String[] sandwiches = {"Chicken Sandwich", "Beef Sandwich", "Fish Sandwich"};
		combobox.setItems(FXCollections.observableArrayList(sandwiches));
		combobox.getSelectionModel().selectedItemProperty().addListener( // change sandwich based on user selection.
				(option, oldVal, newVal) -> updateSandwich(newVal)
		);
		combobox.getSelectionModel().selectFirst(); // Set default to chicken sandwich.
		resetExtraListViews();
	}
}
