import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @authors Anthony Triolo and Biyun Wu
 */

public class Shopping {
	public void run() {
		System.out.println("Let's start shopping!");
		ShoppingBag bag = new ShoppingBag();
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while (!input.equals("Q")) {
			String[] separatedInput = input.split(" ");
			switch (separatedInput.length) {
				case 1 -> {
					switch (separatedInput[0]) {
						case "P" -> {
							if (bag.getSize() == 0) {
								System.out.println("The bag is empty!");
							} else {
								System.out.println("**You have " + bag.getSize() + " items in the bag.");
								bag.print();
								System.out.println("**End of list");
							}
						}
						case "C" -> {
							if (bag.getSize() == 0) {
								System.out.println("Unable to check out, the bag is empty!");
							} else {
								checkout(bag);
								bag = new ShoppingBag(); // Empty bag after checking out.
							}
						}
						default -> System.out.println("Invalid command!");
					}
				}
				case 4 -> {
					String itemName = separatedInput[1];
					double itemPrice = Double.parseDouble(separatedInput[2]);
					boolean itemTaxable = Boolean.parseBoolean(separatedInput[3]);
					GroceryItem itemObj = new GroceryItem(itemName, itemPrice, itemTaxable);
					switch (separatedInput[0]) {
						case "A" -> {
							bag.add(itemObj);
							System.out.println(itemName + " added to the bag");
						}
						case "R" -> {
							if (!bag.remove(itemObj)) {
								System.out.println("Unable to remove, this item is not in the bag.");
							} else {
								System.out.println(itemName + " " + itemPrice + " removed.");
							}
						}
						default -> System.out.println("Invalid command!");
					}
				}
				default -> System.out.println("Invalid command!");
			}
			input = in.nextLine();
		}

		if(bag.getSize() != 0) {
			checkout(bag);
		}
		in.close();
	}

	/**
	 * Helper method to print checkout details.
	 * @param bag ShoppingBag to be checked out.
	 */
	private void checkout(ShoppingBag bag) {
		System.out.println("Checking out " + bag.getSize() + " items.");
		bag.print();
		DecimalFormat df = new DecimalFormat("0.00");
		double salesPrice = bag.salesPrice();
		System.out.println("*Sales total: $" + df.format(salesPrice));
		double salesTax = bag.salesTax();
		System.out.println("*Sales tax: $" + df.format(salesTax));
		double totalPrice = salesPrice + salesTax;
		System.out.println("*Total amount paid: $" + df.format(totalPrice));
	}
}