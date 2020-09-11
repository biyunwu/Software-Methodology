import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 
 * @authors Anthony Triolo and Biyun Wu
 *
 */
public class Shopping {
	public void run() {
		System.out.println("Let's start shopping!");
		ShoppingBag bag = new ShoppingBag();
		Scanner in = new Scanner(System.in);
		String input;
		while ((input = in.nextLine()).equals("Q") == false) {
			String[] seperatedInput = input.split(" ");
			switch (seperatedInput[0]) {
			case "A":
				if (seperatedInput.length < 4) {
					System.out.println("Invalid command!");
					break;
				}
				String newitemName = seperatedInput[1];
				double newitemPrice = Double.parseDouble(seperatedInput[2]);
				boolean newitemTaxable = Boolean.valueOf(seperatedInput[3]);
				GroceryItem newItem = new GroceryItem(newitemName, newitemPrice, newitemTaxable);
				bag.add(newItem);
				System.out.println(newitemName + " added to the bag");
				break;
			case "R":
				if (seperatedInput.length < 4) {
					System.out.println("Invalid command!");
					break;
				}
				String removeitemName = seperatedInput[1];
				double removeitemPrice = Double.parseDouble(seperatedInput[2]);
				boolean removeitemTaxable = Boolean.valueOf(seperatedInput[3]);
				GroceryItem removeItem = new GroceryItem(removeitemName, removeitemPrice, removeitemTaxable);
				if (bag.remove(removeItem) == false) {
					System.out.println("Unable to remove, this item is not in the bag.");
					break;
				} else {
					System.out.println(removeitemName + " " + removeitemPrice + " removed.");
					break;
				}
			case "P":
				if (bag.getSize() == 0) {
					System.out.println("The bag is empty!");
					break;
				}
				System.out.println("**You have " + bag.getSize() + " items in the bag.");
				bag.print();
				System.out.println("**End of list");
				break;
			case "C":
				if (bag.getSize() == 0) {
					System.out.println("Unable to check out, the bag is empty!");
					break;
				}
				System.out.println("Checking out " + bag.getSize() + " items.");
				bag.print();
				DecimalFormat df = new DecimalFormat("0.00");
				double salesPrice = bag.salesPrice();
				System.out.println("*Sales total: $" + df.format(salesPrice));
				double salesTax = bag.salesTax();
				System.out.println("*Sales tax: $" + df.format(salesTax));
				double totalPrice = salesPrice + salesTax;
				System.out.println("*Total amount paid: $" + df.format(totalPrice));
				bag = new ShoppingBag();
				break;
			default:
				System.out.println("Invalid command!");
				break;
			}
		}
		if (bag.getSize() != 0) {
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
		System.out.println("Thanks for shopping with us!");
		in.close();
		return;
	}
}
