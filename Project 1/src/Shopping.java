import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Definition of Shopping. It is built upon ShoopingBag and GroceryItem classes.
 * It has 1 member variable which stores the ShoppingBag instance.
 * @author Anthony Triolo and Biyun Wu
 */

public class Shopping {
	ShoppingBag bag;

	/** Constructor without parameter. */
	public Shopping() {
		bag = new ShoppingBag();
	}

	/**
	 * Scan input line by line and pass input to doOperation()
	 * unless the input is "Q" which means quit the program.
	 */
	public void run() {
		System.out.println("Let's start shopping!");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while (!input.equals("Q")) {
			String[] separatedInput = input.split(" ");
			doOperation(separatedInput);
			input = in.nextLine();
		}

		if(bag.getSize() != 0) {
			checkoutNotEmptyBag();
		}

		System.out.println("Thanks for shopping with us!");
		in.close();
	}

	/**
	 * Do corresponding operation on ShoppingBag based on given array if it is valid.
	 * @param separatedInput array stores the inputs.
	 */
	private void doOperation(String[] separatedInput) {
		switch (separatedInput.length) {
			case 1 -> {
				switch (separatedInput[0]) {
					case "P" -> display();
					case "C" -> checkout();
					default -> System.out.println("Invalid command!");
				}
			}
			case 4 -> {
				String itemName = separatedInput[1];
				double itemPrice = Double.parseDouble(separatedInput[2]);
				boolean itemTaxable = Boolean.parseBoolean(separatedInput[3]);
				GroceryItem itemObj = new GroceryItem(itemName, itemPrice, itemTaxable);
				switch (separatedInput[0]) {
					case "A" -> add(itemObj, itemName);
					case "R" -> remove(itemObj, itemName, itemPrice);
					default -> System.out.println("Invalid command!");
				}
			}
			default -> System.out.println("Invalid command!");
		}
	}

	/** Helper method to display items in the bag.*/
	private void display() {
		if (bag.getSize() == 0) {
			System.out.println("The bag is empty!");
		} else {
			System.out.println("**You have " + bag.getSize() + " items in the bag.");
			bag.print();
			System.out.println("**End of list");
		}
	}

	/** Helper method to check out the shopping bag no matter it is empty or not.*/
	private void checkout() {
		if (bag.getSize() == 0) {
			System.out.println("Unable to check out, the bag is empty!");
		} else {
			checkoutNotEmptyBag();
		}
	}

	/** Helper method to check out NOT empty bag. */
	private void checkoutNotEmptyBag() {
		System.out.println("Checking out " + bag.getSize() + " items.");
		bag.print();
		DecimalFormat df = new DecimalFormat("0.00");
		double salesPrice = bag.salesPrice();
		System.out.println("*Sales total: $" + df.format(salesPrice));
		double salesTax = bag.salesTax();
		System.out.println("*Sales tax: $" + df.format(salesTax));
		double totalPrice = salesPrice + salesTax;
		System.out.println("*Total amount paid: $" + df.format(totalPrice));
		bag = new ShoppingBag(); // Empty bag after checking out.
	}

	/**
	 * Helper method to add GroceryItem to the bag.
	 * @param itemObj GroceryItem to be added to the bag.
	 * @param itemName name of GroceryItem.
	 */
	private void add(GroceryItem itemObj, String itemName) {
		bag.add(itemObj);
		System.out.println(itemName + " added to the bag.");
	}

	/**
	 * Helper method to remove a specific GroceryItem in the bag.
	 * @param itemObj GroceryItem to be removed from the bag.
	 * @param itemName name of the GroceryItem to be removed.
	 * @param itemPrice price of the GroceryItem to be removed.
	 */
	private void remove(GroceryItem itemObj, String itemName, double itemPrice) {
		if (!bag.remove(itemObj)) {
			System.out.println("Unable to remove, this item is not in the bag.");
		} else {
			System.out.println(itemName + " " + itemPrice + " removed.");
		}
	}
}