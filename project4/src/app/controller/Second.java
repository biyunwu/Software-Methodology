package app.controller;

/**
 * Definition of the JavaFX Second controller class. 
 * This class defines all of the elements within the Second UI and 
 * contains all of the methods that are used by each UI element.
 *
 * @author Biyun Wu, Anthony Triolo
 */

import app.model.order.Order;
import app.model.order.OrderLine;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Second {
	
	private Order order;
	private ArrayList<OrderLine> lines;
	DecimalFormat df = new DecimalFormat("0.00");
	
	@FXML
    private ListView<OrderLine> orderList;

    @FXML
    private TextField orderTotal;

    /** Clears the order list. */
    @FXML
    void clearOrder() {
    	order = new Order();
    	lines.clear();
    	orderList.getItems().clear();
    	orderTotal.setText(calculateTotal());
    }

    /** Add a copy of the selected line to the order. */
    @FXML
    void copyLine() {
    	OrderLine selection = orderList.getSelectionModel().getSelectedItem();
    	if(selection == null) {
    		Alert alert = new Alert(AlertType.WARNING, "Please select a line to copy", ButtonType.OK);
    		alert.showAndWait();
    	} else {
    		OrderLine newLine = new OrderLine(selection.getSandwich());
    		order.add(newLine);
    		orderList.getItems().add(newLine);
    		orderTotal.setText(calculateTotal());
    	}
    }

    /** Remove the selected line from the order. */
    @FXML
    void removeLine() {
    	OrderLine selection = orderList.getSelectionModel().getSelectedItem();
    	if(orderList.getItems().size() == 0) {
    		Alert alert = new Alert(AlertType.WARNING, "Nothing to remove", ButtonType.OK);
    		alert.showAndWait();
    		return;
    	}
    	if(selection == null) {
    		Alert alert = new Alert(AlertType.WARNING, "Please select a line to remove", ButtonType.OK);
    		alert.showAndWait();
    	}else {
    		for(int i = orderList.getSelectionModel().getSelectedIndex(); i < orderList.getItems().size(); i++) {
    			orderList.getItems().get(i).shiftLineUp();
    		}
    		Order.lineNumber--;
    		order.remove(selection);
    		orderList.getItems().remove(selection);
    		orderTotal.setText(calculateTotal());
    	}
    }

    /** Close the current window. */
	@FXML
	void goBack() {
		Stage s = (Stage) orderTotal.getScene().getWindow(); // from any component to get a reference to the stage.
		s.close();
	}

    /** Export the order to a file. */
    @FXML
    void saveToFile() {
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
			for(OrderLine line: order.getOrderLines()) {
				writer.append(line.toString()).append("\n");
			}
			writer.close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, "Export Failed!", ButtonType.OK);
    		alert.showAndWait();
		}
    }
    
    /**
     * Set this controller's order
     * @param order: The order we want to set as this controller's order
     */
    public void setOrder(Order order) {
    	this.order = order;
    	lines = order.getOrderLines();
    	orderList.setItems(FXCollections.observableArrayList(lines));
    	orderTotal.setText(calculateTotal());
    }
    
    /**
     * Calculates the price of every item in the order
     * @return: The order price as a string
     */
    public String calculateTotal() {
    	double orderPrice = 0;
    	for(OrderLine line : lines) {
    		orderPrice+=line.getPrice();
    	}
    	return df.format(orderPrice);
    }
    
    /**
     * Gets this controller's order
     * @return: This controller's order
     */
    public Order getOrder() {
    	return this.order;
    }
}
