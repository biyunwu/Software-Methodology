/**
 * @authors Anthony Triolo and Biyun Wu
 */

import java.util.Scanner;

public class Shopping {
    private final ShoppingBag sb;

    public Shopping() {
        sb = new ShoppingBag();
    }

    public void run() { // Scan user input line by line.
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine().trim();
        while (!line.equals("Q")) {
            readInput(line);
            line = sc.nextLine();
        }
        print(Constants.THANKS); // line: "Q", quit.
        sc.close();
    }

    /**
     * Check whether user input is valid. Then do the corresponding operation.
     * @param input user input (1 line)
     */
    private void readInput(String input) {
        String[] inputs = input.split("\\s+");
        switch(inputs.length) {
            case 1 -> { // Display/Checkout
                switch (inputs[0]) {
                    case "P" -> sb.display();
                    case "C" -> sb.checkout();
                    default -> print(Constants.INVALID);
                }
            }
            case 4 -> { // Add/Remove + itemName + price + taxable
                String operation = inputs[0];
                String item = inputs[1];
                double price = Double.parseDouble(inputs[2]);
                boolean taxable = Boolean.parseBoolean(inputs[3]);
                GroceryItem itemObj = new GroceryItem(item, price, taxable);
                switch(operation) {
                    case "A" -> sb.add(itemObj);
                    case "R" -> sb.remove(itemObj);
                    default -> print(Constants.INVALID);
                }
            }
            default -> print(Constants.INVALID);
        }
    }

    /**
     * Helper method.
     * @param s string to be printed.
     */
    private void print(String s){
        System.out.println(s);
    }
}