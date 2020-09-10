/**
 * 
 * @author Anthony Triolo and Biyun Wu
 *
 */
public class GroceryItem {
	private String name;
	private double price;
	private boolean taxable;

	public GroceryItem(String name, double price, boolean taxable) {
		this.name = name;
		this.price = price;
		this.taxable = taxable;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		return false;

	}

	public String toString() {
		return name + ": " + price + " : " + taxable;
	}

}
