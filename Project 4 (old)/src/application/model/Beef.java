package application.model;

public class Beef extends Sandwich{

	private static final double BASE_PRICE = 10.99;
	@Override
	public double price() {
		return BASE_PRICE + (extras.size()*PER_EXTRA);
	}

	@Override
	public String toString() {
		String base = "Base: Roast Beef, Provolone Cheese, Mustard. ";
		String extra = "Extras: " + super.toString() + ". ";
		return "Beef Sandwich. " + base + extra + "Price: $" + price(); 
	}

}
