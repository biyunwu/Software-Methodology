package app.model.order;

import app.model.sandwich.Sandwich;

public class OrderLine {
	private int lineNumber;
	private Sandwich sandwich;
	private double price;

	public OrderLine(int lineNumber, Sandwich sandwich) {
		this.lineNumber = lineNumber;
		this.sandwich = sandwich;
		price = sandwich.price();
	}

	@Override
	public String toString() {
		return lineNumber + " " + sandwich.toString() + "$" + price;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof OrderLine && lineNumber == ((OrderLine) obj).lineNumber;
	}
}
