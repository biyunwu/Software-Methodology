package app.model.sandwich;

/**
 * Definition of the Fish class. It extends the Sandwich class and has one member variable:
 * the base price of a fish sandwich
 *
 * @author Biyun Wu, Anthony Triolo
 */

import java.util.ArrayList;

public class Fish extends Sandwich{
	private double totalPrice;
	private static final double BASE_PRICE = 12.99;

	/**
	 * Constructor
	 */
	public Fish() {
		extras = new ArrayList<>();
	}

	/**
	 * Calculates the price of a fish sandwich with extra ingredients
	 * 
	 * @return The total price of a fish sandwich
	 */
	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size(); // if null exists in ArrayList, does it count to size?
	}

	/**
	 * Stringify the information about a fish sandwich
	 * 
	 * @return The formatted string of information
	 */
	@Override
	public String toString() {
		String baseIngres = "Base: " + String.join(", ", this.getBaseIngredients()) + ". ";
		String extraIngres = "Extras: " + super.toString() + ". ";
		String price = "Price: $" + price();
		return "Fish Sandwich. " + baseIngres + extraIngres + price;
	}

	/**
	 * Helper method to get the base ingredients of a fish sandwich
	 * 
	 * @return An array of the base ingredients
	 */
	public String[] getBaseIngredients() {
		return new String[]{"Grilled Snapper", "Cilantro", "Lime"};
	}
}
