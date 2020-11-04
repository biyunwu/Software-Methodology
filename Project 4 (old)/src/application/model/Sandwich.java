package application.model;

import java.util.ArrayList;

public abstract class Sandwich implements Customizable{

	static final int MAX_EXTRAS = 6;
	static final double PER_EXTRA = 1.99;
	protected ArrayList<Extra> extras;
	
	@Override
	public boolean add(Object obj) {
		if(extras.size() < MAX_EXTRAS) {
			return extras.add((Extra) obj);
		}
		return false;
	}

	@Override
	public boolean remove(Object obj) {
		return extras.remove((Extra) obj);
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
	
	public abstract double price();
	
}
