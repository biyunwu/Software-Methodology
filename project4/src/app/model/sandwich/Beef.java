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
		return BASE_PRICE + PER_EXTRA * extras.size();
	}

	@Override
	public String toString() {
		String baseIngres = "Base: " + String.join(", ", this.getBaseIngredients()) + ". ";
		String extraIngres = "Extra: " + super.toString() + ". ";
		return "Beef Sandwich. " + baseIngres + extraIngres;
	}

	public String[] getBaseIngredients() {
		return new String[]{"Roast Beef", "Provolone Cheese", "Mustard"};
	}
}

