package app.model.order;

/**
 * Definition of the OrderLine class. It has 3 member variables: 
 * an int to store the line number, the sandwich in the line, and the price of the sandwich
 *
 * @author Biyun Wu, Anthony Triolo
 */

import app.model.sandwich.Sandwich;

public class OrderLine {
	private int lineNumber;
	private Sandwich sandwich;
	private double price;
	
	/**
	 * Constructor
	 * @param sandwich: The sandwich associated with the line.
	 */
	public OrderLine(Sandwich sandwich) {
		lineNumber = ++(Order.lineNumber);
		this.sandwich = sandwich;
		price = sandwich.price();
	}

	/**
	 * Stringfy the information about the line.
	 */
	@Override
	public String toString() {
		return lineNumber + " " + sandwich.toString();
	}
	
	/**
	 * Helper method to get the total price of the line
	 * @return The price of the line
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Helper method to get the sandwich associated with the line
	 * @return The sandwich associated with the line
	 */
	public Sandwich getSandwich() {
		return sandwich;
	}
	
	/**
	 * Helper method to change the lineNumber when an item is removed from an order
	 */
	public void shiftLineUp() {
		lineNumber--;
	}
}
