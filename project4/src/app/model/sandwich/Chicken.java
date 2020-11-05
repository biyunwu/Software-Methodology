package app.model.sandwich;

import java.util.ArrayList;

public class Chicken extends Sandwich{
	private static final double BASE_PRICE = 8.99;

	public Chicken() {
		extras = new ArrayList<>();
	}

	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size(); // if null exists in ArrayList, does it count to size?
	}

	@Override
	public String toString() {
		String baseIngres = "Base: " + String.join(", ", this.getBaseIngredients()) + ". ";
		String extraIngres = "Extra: " + super.toString() + ". ";
		return "Chicken Sandwich. " + baseIngres + extraIngres;
	}

	@Override
	public String[] getBaseIngredients() {
		return new String[]{"Fried Chicken", "Spicy Sauce", "Pickles"};
	}
}
