package application.model;

import java.util.ArrayList;

public abstract class Sandwich implements Customizable{

	static final int MAX_EXTRAS = 6;
	static final double PER_EXTRA = 1.99;
	protected ArrayList<Extra> extras;
	
	@Override
	public boolean add(Object obj) {
		if(extras.size() < MAX_EXTRAS) {
			extras.add((Extra) obj);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Object obj) {
		extras.remove((Extra) obj);
		return true;
	}
	
	public abstract double price();
	public abstract String toString();
}
