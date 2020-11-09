package app.controller;

import app.model.order.OrderLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

/** Controller for view2. */
public class View2Controller implements Initializable{
	private View1Controller parent;

	@FXML
	private ListView<OrderLine> orderList;

	@FXML
	private TextField totalPrice;

	@FXML
	private TextArea feedback;

	@FXML
	private Button backButton;

	/** Delete all order lines. */
	@FXML
	void deleteOrder() {
		feedback.clear();
		parent.clearOrder();
		updateView();
	}

	/** Duplicate selected order line(s) and append them to the end of the list. */
	@FXML
	void addOrderLine() {
		feedback.clear();
		try {
			ObservableList<OrderLine> lines = orderList.getSelectionModel().getSelectedItems();
			if (lines.isEmpty()) throw new Exception("ERROR: No order line is selected!\n");
			for (OrderLine ol : lines) {
				if (!parent.addOrderLine(ol)) {
					throw new Exception("ERROR: Not able to add to the order.\n");
				}
			}
		} catch (Exception e) {
			feedback.appendText(e.getMessage());
		} finally {
			updateView();
		}
	}

	/** Deleted selected order line(s) and update the serial numbers for remaining order lines. */
	@FXML
	void deleteOrderLine() {
		feedback.clear();
		try {
			ObservableList<OrderLine> lines = orderList.getSelectionModel().getSelectedItems();
			if (lines.isEmpty()) throw new Exception("ERROR: No order line is selected!\n");
			for (OrderLine ol : lines) {
				if (!parent.removeOrderLine(ol)) {
					parent.getOrder().serialize();
					throw new Exception("ERROR: Not able to remove the selected order line.\n");
				}
			}
			parent.getOrder().serialize();
		} catch (Exception e) {
			feedback.appendText(e.getMessage());
		} finally {
			updateView();
		}
	}

	/** Close this stage and back to the main stage. */
	@FXML
	void goBack() {
		Stage s = (Stage) backButton.getScene().getWindow(); // from any component to get a reference to the stage.
		s.close();
	}

	/** Export data of order to a text file. */
	@FXML
	void saveToFile() {
		feedback.clear();
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose Target File to Export");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
		File targetFile = chooser.showSaveDialog(stage);
		try {
			if (targetFile == null)
				throw new IllegalArgumentException("Export cancelled!");
			BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			parent.getOrder().getOrderLines().forEach(ol -> {
				try {
					writer.append(ol.toString()).append("\n");
				} catch (Exception e) {
					feedback.appendText("ERROR: " + e.getMessage() + "\n");
				}
			});
			writer.close();
			feedback.appendText("Export finished!\n");
		} catch (IllegalArgumentException e) {
			feedback.appendText(e.getMessage() + "\n");
		} catch (Exception e) {
			feedback.appendText("Export stopped: " + e.getMessage() + "\n");
		}
	}

	/**
	 * Setter method to inject outside controller.
	 *
	 * @param parent outside controller
	 */
	public void setParentController(View1Controller parent) {
		this.parent = parent;
	}

	/** Update the list view and recalculate the total price. */
	private void updateView() {
		orderList.setItems(FXCollections.observableArrayList(parent.getOrder().getOrderLines()));
		orderList.scrollTo(orderList.getItems().size() - 1); // scroll to the bottom
		double total = 0;
		for (OrderLine ol : parent.getOrder().getOrderLines()) {
			total += ol.getSandwich().price();
		}
		totalPrice.setText(String.format("%.2f", total));
	}

	/**
	 * Method to initialize view2, which connected to this controller.
	 *
	 * @param url url
	 * @param resourceBundle resource object for initialization.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		totalPrice.setEditable(false);
		orderList.getSelectionModel().selectFirst();
		orderList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		updateView();
	}
}
