package application.model;

public class OrderLine {
	private int lineNumber;
	private Sandwich sandwich;
	private double price;
	
	public OrderLine(int lineNumber, Sandwich sandwich, double price) {
		this.lineNumber = ++(Order.lineNumber);
		this.sandwich = sandwich;
		this.price = price;
	}
	
}
