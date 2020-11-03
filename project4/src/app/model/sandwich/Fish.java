package app.model.sandwich;

import java.util.ArrayList;

public class Fish extends Sandwich{
	private double totalPrice;
	private static final double BASE_PRICE = 12.99;

	public Fish() {
		extras = new ArrayList<>();
	}

	@Override
	public double price() {
		return BASE_PRICE + PER_EXTRA * extras.size(); // if null exists in ArrayList, does it count to size?
	}

	@Override
	public String toString() {
		String baseIngres = "Base: Grilled Snapper, Cilantro, Lime. ";
		String extraIngres = "Extra: " + super.toString() + ". ";
		return "Fish Sandwich. " + baseIngres + extraIngres + "Price: $" + price();
	}
}
