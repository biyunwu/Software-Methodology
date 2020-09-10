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
		while ((input = in.nextLine()) != "Q") {
			String[] seperatedInput = input.split(" ");
			switch (seperatedInput[0]) {
			case "A":
				if (seperatedInput.length < 4) {
					System.out.println("Invalid command!");
				}
				String itemName = seperatedInput[1];
				double itemPrice = Double.parseDouble(seperatedInput[2]);
				boolean itemTaxable = Boolean.valueOf(seperatedInput[3]);
				GroceryItem newItem = new GroceryItem(itemName, itemPrice, itemTaxable);
				bag.add(newItem);
			case "R":

			}
		}
	}
}
