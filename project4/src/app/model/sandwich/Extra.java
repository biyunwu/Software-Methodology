package app.model.sandwich;

import java.util.ArrayList;

public class Extra {
	private final String name;

	private Extra(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

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
