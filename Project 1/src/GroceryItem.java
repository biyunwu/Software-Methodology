import java.text.DecimalFormat;

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
		if (obj instanceof GroceryItem) {
			if (((GroceryItem) obj).name.equals(this.name) && ((GroceryItem) obj).price == this.price
					&& ((GroceryItem) obj).taxable == this.taxable) {
				return true;
			}
		}

		return false;

	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		String output = name + ": $" + df.format(price) + " : ";
		if (this.taxable == false) {
			output = output + "tax free";
		} else {
			output = output + "is taxable";
		}

		return output;
	}

	public double getPrice() {
		return price;
	}

	public boolean getTaxable() {
		return taxable;
	}

}
