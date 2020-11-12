package app.model.sandwich;

/**
 * Definition of the Chicken class. It extends the Sandwich class and has one member variable:
 * the base price of a chicken sandwich
 *
 * @author Biyun Wu, Anthony Triolo
 */

import java.util.ArrayList;

public class Chicken extends Sandwich {
	private static final double BASE_PRICE = 8.99;

	/**
	 * Constructor
	 */
	public Chicken() {
		extras = new ArrayList<>();
	}

	/**
	 * Calculates the price of a chicken sandwich with extra ingredients
	 * 
	 * @return The total price of a chicken sandwich
	 */
	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size(); // if null exists in ArrayList, does it count to size?
	}

	/**
	 * Stringify the information about a chicken sandwich
	 * 
	 * @return The formatted string of information
	 */
	@Override
	public String toString() {
		String baseIngres = "Base: " + String.join(", ", this.getBaseIngredients()) + ". ";
		String extraIngres = "Extras: " + super.toString() + ". ";
		String price = "Price: $" + price();
		return "Chicken Sandwich. " + baseIngres + extraIngres + price;
	}

	/**
	 * Helper method to get the base ingredients of a chicken sandwich
	 * 
	 * @return An array of the base ingredients
	 */
	public String[] getBaseIngredients() {
		return new String[] { "Fried Chicken", "Spicy Sauce", "Pickles" };
	}
}
