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
        if (sb.getSize() != 0) {
            checkoutNonEmptyBag();
        }
        System.out.println(Constants.THANKS); // line: "Q", quit.
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
                    case "P" -> display();
                    case "C" -> checkout();
                    default -> System.out.println(Constants.INVALID);
                }
            }
            case 4 -> { // Add/Remove + itemName + price + taxable
                String operation = inputs[0];
                String item = inputs[1];
                double price = Double.parseDouble(inputs[2]);
                boolean taxable = Boolean.parseBoolean(inputs[3]);
                GroceryItem itemObj = new GroceryItem(item, price, taxable);
                switch(operation) {
                    case "A" -> add(itemObj);
                    case "R" -> remove(itemObj);
                    default -> System.out.println(Constants.INVALID);
                }
            }
            default -> System.out.println(Constants.INVALID);
        }
    }

    private void display() {
        int size = sb.getSize();
        if (sb.getSize() == 0) {
            System.out.println(Constants.EMPTY_BAG);
        } else {
            System.out.printf((Constants.LIST_START) + "%n", size);
            sb.print();
            System.out.println(Constants.LIST_END);
        }
    }

    private void checkout() {
        if (sb.getSize() == 0) {
            System.out.println(Constants.CHECKOUT_EMPTY_BAG);
        } else {
            checkoutNonEmptyBag();
        }
    }

    private void checkoutNonEmptyBag() {
        System.out.printf((Constants.CHECKOUT_START) + "%n", sb.getSize());
        sb.print();
        double sales = sb.salesPrice();
        double tax = sb.salesTax();
        System.out.printf((Constants.CHECKOUT_END) + "%n", sales, tax, sales + tax);
        sb.initShoppingBag(); // Empty bag after checking out.
    }

    /**
     * Helper method.
     * @param item to be added to bag.
     */
    private void add(GroceryItem item) {
        sb.add(item);
        System.out.printf((Constants.SUCCESS_ADD) + "%n", item.getName());
    }

    /**
     * Helper method.
     * @param item to be searched and removed from bag.
     */
    private void remove(GroceryItem item) {
        if (sb.remove(item)){
            System.out.printf((Constants.SUCCESS_REMOVE) + "%n", item.getName(), item.getPrice());
        } else {
            System.out.println(Constants.FAIL_REMOVE);
        }
    }
}