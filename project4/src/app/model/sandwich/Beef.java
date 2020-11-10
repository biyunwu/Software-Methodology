package app.model.sandwich;

/**
 * Definition of the Beef class. It extends the Sandwich class and has one member variable:
 * the base price of a beef sandwich
 *
 * @author Biyun Wu, Anthony Triolo
 */

import java.util.ArrayList;

public class Beef extends Sandwich {
	private static final double BASE_PRICE = 10.99;

	/**
	 * Constructor
	 */
	public Beef() {
		extras = new ArrayList<>();
	}

	/**
	 * Calculates the price of a beef sandwich with extra ingredients
	 * 
	 * @return The total price of a beef sandwich
	 */
	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size();
	}

	/**
	 * Stringify the information about a beef sandwich
	 * 
	 * @return The formatted string of information
	 */
	@Override
	public String toString() {
		String baseIngres = "Base: " + String.join(", ", this.getBaseIngredients()) + ". ";
		String extraIngres = "Extras: " + super.toString() + ". ";
		String price = "Price: $" + price();
		return "Beef Sandwich. " + baseIngres + extraIngres + price;
	}

	/**
	 * Helper method to get the base ingredients of a beef sandwich
	 * 
	 * @return An array of the base ingredients
	 */
	public String[] getBaseIngredients() {
		return new String[] { "Roast Beef", "Provolone Cheese", "Mustard" };
	}
}
