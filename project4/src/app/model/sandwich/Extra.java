package app.model.sandwich;

/**
 * Definition of the Extra class which contains definitions for the extra ingredients
 * that can be put on a sandwich. It has one member variable: the name of an extra ingredient 
 *
 * @author Biyun Wu, Anthony Triolo
 */

import java.util.ArrayList;

public class Extra {
	private final String name;

	/**
	 * Constructor
	 * @param name: The name of an extra ingredient
	 */
	private Extra(String name) {
		this.name = name;
	}

	/**
	 * Get a string version of the name of an extra ingredient.
	 * 
	 * @return The name of the extra ingredient.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Helper method to get a list of all possible extra ingredients
	 * 
	 * @return An ArrayList containing extra ingredients
	 */
	public static ArrayList<Extra> getExtraList() {
		String[] ingredients = {"Lettuce", "Tomato", "Onion", "Mayo", "American", "Swiss",
				"Ketchup", "Mustard", "Bacon", "Mushrooms"};
		ArrayList<Extra> extras = new ArrayList<>();
		for (String s: ingredients) {
			extras.add(new Extra(s));
		}
		return extras;
	}
}
