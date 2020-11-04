package application.model;

public class Fish extends Sandwich{

	private static final double BASE_PRICE = 12.99;
	
	@Override
	public double price() {
		return BASE_PRICE + (extras.size()*PER_EXTRA);
	}

	@Override
	public String toString() {
		String base = "Base: Grilled Snapper, Cilantro, Lime. ";
		String extra = "Extras: " + super.toString() + ". ";
		return "Fish Sandwich. " + base + extra + "Price: $" + price(); 
	}

}
