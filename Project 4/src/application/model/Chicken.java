package application.model;

public class Chicken extends Sandwich{

	private static final double BASE_PRICE = 8.99;
	
	@Override
	public double price() {
		return BASE_PRICE + (extras.size()*PER_EXTRA);
	}

	@Override
	public String toString() {
		String base = "Base: Fried Chicken, Spicy Sauce, Pickles. ";
		String extra = "Extras: ";
		for(Extra e : extras) {
			extra += e.toString();
		}
		extra += ". ";
		return "Chicken Sandwich. " + base + extra + "Price: $" + price(); 
	}

}
