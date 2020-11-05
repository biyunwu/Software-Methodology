package app.model.sandwich;

import app.model.Customizable;

import java.util.ArrayList;

public abstract class Sandwich implements Customizable {
	static final int MAX_EXTRAS = 6;
	static final double PER_EXTRA = 1.99;
	protected ArrayList<Extra> extras;

	public abstract double price();

	public abstract String[] getBaseIngredients();

	public ArrayList<Extra> getExtras() {
		return extras;
	}

	public void removeExtras() {
		extras = new ArrayList<>();
	}

	@Override
	public boolean add(Object obj) {
		if (extras.size() < MAX_EXTRAS && obj instanceof Extra && !extras.contains(obj)) {
			return extras.add((Extra) obj);
		}
		return false; // invalid obj or reached maximum.
	}

	@Override
	public boolean remove(Object obj) {
		if (obj instanceof Extra) {
			return extras.remove(obj);
		}
		return false;
	}

	@Override
	public String toString() {
		if (!extras.isEmpty()) {
			String s = String.join(", ", extras.toString());
			return s.substring(1, s.length() - 1); // get rid of heading and trailing angle brackets.
		}
		return "none";
	}
}
