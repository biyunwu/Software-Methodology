import java.text.DecimalFormat;

/**
 * @author Anthony Triolo and Biyun Wu
 */

/**
 * Definition of grocery items (such as apple, tomato, etc.).
 * It has 3 member variables: the grocery item's name, price and info of whether it is taxable.
 */
public class GroceryItem {
	private String name;
	private double price;
	private boolean taxable;

	/**
	 * Constructor with parameters.
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
	 * Compare the given object's attributes with this GroceryItem object.
	 * @param obj object given to be compared with this GroceryItem object.
	 * @return true if they have identical name, price and taxable. Otherwise, return false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof GroceryItem
				&& ((GroceryItem) obj).name.equals(this.name)
				&& ((GroceryItem) obj).price == this.price
				&& ((GroceryItem) obj).taxable == this.taxable;
	}

	/** @return string of detailed info of this object. */
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String tax = taxable ? "is taxable" : "tax free";
		return name + ": $" + df.format(price) + " : " + tax;
	}

	/**
	 * Getter method.
	 * @return price for the grocery item.
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Getter method.
	 * @return whether the grocery item is taxable.
	 */
	public boolean getTaxable() {
		return taxable;
	}
}
