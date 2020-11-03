package app.model.sandwich;

import java.util.ArrayList;

public class Beef extends Sandwich{
	private double totalPrice;
	private static final double BASE_PRICE = 10.99;

	public Beef() {
		extras = new ArrayList<>();
	}

	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size(); // if null exists in ArrayList, does it count to size?
	}

	@Override
	public String toString() {
		String baseIngres = "Base Ingredients: Roast Beef, Provolone Cheese, Mustard\n";
		String extraIngres = "Extra Ingredients: " + super.toString() + "\n";
		return "Beef Sandwich\n " + baseIngres + extraIngres;
	}
}

