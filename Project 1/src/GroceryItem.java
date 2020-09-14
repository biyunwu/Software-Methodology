import java.text.DecimalFormat;

/**
 * @authors Anthony Triolo and Biyun Wu
 */

public class GroceryItem {
	private String name;
	private double price;
	private boolean taxable;

	/**
	 * Constructor
	 * @param name of GroceryItem
	 * @param price of GroceryItem
	 * @param taxable tax free or not
	 */
	public GroceryItem(String name, double price, boolean taxable) {
		this.name = name;
		this.price = price;
		this.taxable = taxable;
	}

	/**
	 * Compare the given item's attributes with `this`.
	 * @param obj given to be compared with `this`.
	 * @return true is they have identical name, price and taxable. Otherwise return false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof GroceryItem
				&& ((GroceryItem) obj).name.equals(this.name)
				&& ((GroceryItem) obj).price == this.price
				&& ((GroceryItem) obj).taxable == this.taxable;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String tax = taxable ? "is taxable" : "tax free";
		return name + ": $" + df.format(price) + " : " + tax;
	}

	public double getPrice() {
		return price;
	}

	public boolean getTaxable() {
		return taxable;
	}
}
