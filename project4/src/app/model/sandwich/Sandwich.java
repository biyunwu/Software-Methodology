package app.model.sandwich;

/**
 * Definition of the Sandwich class. It has three member variables:
 * the max number of extra ingredients that can be on a sandwich, the price per extra,
 * and a list of the extra ingredients on the current sandwich
 *
 * @author Biyun Wu, Anthony Triolo
 */

import app.model.Customizable;

import java.util.ArrayList;

public abstract class Sandwich implements Customizable {
	static final int MAX_EXTRAS = 6;
	static final double PER_EXTRA = 1.99;
	protected ArrayList<Extra> extras;

	/**
	 * Calculates the price of a sandwich with extra ingredients
	 * 
	 * @return The total price of a sandwich
	 */
	public abstract double price();

	/**
	 * Adds extra ingredients to a sandwich
	 * 
	 * @return true if the ingredient is added, false otherwise
	 */
	@Override
	public boolean add(Object obj) {
		if (extras.size() < MAX_EXTRAS && obj instanceof Extra && !extras.contains(obj)) {
			return extras.add((Extra) obj);
		}
		return false; // invalid obj or reached maximum.
	}

	/**
	 * Removes extra ingredients from a sandwich
	 * 
	 * @return true if the ingredient is removed, false otherwise
	 */
	@Override
	public boolean remove(Object obj) {
		if (obj instanceof Extra) {
			return extras.remove(obj);
		}
		return false;
	}

	/**
	 * Stringify the information about a sandwich
	 * 
	 * @return The formatted string of information
	 */
	@Override
	public String toString() {
		if (!extras.isEmpty()) {
			String s = String.join(", ", extras.toString());
			return s.substring(1, s.length() - 1); // get rid of heading and trailing angle brackets.
		}
		return "none";
	}
}
