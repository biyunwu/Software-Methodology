package app.model.order;

/**
 * Definition of the Order class. It has 2 member variables: 
 * a static int to store the current line number, and an ArrayList that contains the order lines
 *
 * @author Biyun Wu, Anthony Triolo
 */

import app.model.Customizable;
import app.model.sandwich.Extra;

import java.util.ArrayList;

public class Order implements Customizable {
	public static int lineNumber;
	private ArrayList<OrderLine> orderlines;

	/** Constructor */
	public Order() {
		lineNumber = 0;
		orderlines = new ArrayList<>();
	}

	/**
	 * Adds a new order line to the order
	 * 
	 * @return true if line added, false otherwise
	 */
	@Override
	public boolean add(Object obj) {
		if (obj instanceof OrderLine && !orderlines.contains(obj)) {
			return orderlines.add((OrderLine) obj);
		}
		return false;
	}

	/**
	 * Remove a line from the order
	 * 
	 * @return true if the line was removed, false otherwise
	 */
	@Override
	public boolean remove(Object obj) {
		if (obj instanceof OrderLine) {
			return orderlines.remove(obj);
		}
		return false;
	}

	/**
	 * Helper method to return the orderlines list
	 * @return The orderlines list
	 */
	public ArrayList<OrderLine> getOrderLines() {
		return orderlines;
	}
}
