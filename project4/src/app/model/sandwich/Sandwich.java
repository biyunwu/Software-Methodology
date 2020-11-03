package app.model.sandwich;

import app.model.Customizable;

import java.util.ArrayList;

public abstract class Sandwich implements Customizable {
	static final int MAX_EXTRAS = 6;
	static final double PER_EXTRA = 1.99;
	protected ArrayList<Extra> extras;
	public abstract double price();

	@Override
	public boolean add(Object obj) {
		if (extras.size() < MAX_EXTRAS && obj instanceof Extra) {
			for (Extra extra: extras) { // check whether the ingredient is already in the list.
				if (extra.equals(obj)) {
					return false;
				}
			}
			extras.add((Extra) obj);
			return true;
		}
		return false; // invalid obj or reached maximum.
	}

	@Override
	public boolean remove(Object obj) {
		if (obj instanceof Extra) {
			for (Extra e: extras) {
				if (e.equals(obj)) {
					extras.remove(e);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		if (!extras.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			String separator = ", ";
			for (Extra extra: extras) {
				sb.append(extra.toString()).append(separator);
			}
			return sb.substring(0, sb.length() - separator.length()); // get rid of the trailing comma.
		}
		return "no extra ingredient";
	}
}
