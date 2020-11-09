package app.model.order;

import app.model.sandwich.Sandwich;

public class OrderLine {
	private int lineNumber;
	private Sandwich sandwich;
	private double price;

	public OrderLine(Sandwich sandwich) {
		lineNumber = ++(Order.lineNumber);
		this.sandwich = sandwich;
		price = sandwich.price();
	}

	public Sandwich getSandwich() {
		return sandwich;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public String toString() {
		return lineNumber + " " + sandwich.toString() + "Price: $" + price;
	}
}
