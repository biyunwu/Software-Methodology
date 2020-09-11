/**
 *
 * @authors Anthony Triolo and Biyun Wu
 *
 */

import java.util.Scanner;

public class Shopping {
    private final ShoppingBag sb;

    // Constructor
    public Shopping() {
        sb = new ShoppingBag();
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            readInput(sc.nextLine());
        }
    }

    private void readInput(String input) {
        String[] inputs = input.split("\\s+");
        if (inputs.length == 1) {
            switch (inputs[0]) {
                case "P" -> sb.display();
                case "C" -> sb.checkout();
                case "Q" -> {
                    print(Constants.THANKS);
                    System.exit(0); // Exit the program.
                }
                default -> print(Constants.INVALID);
            }
        } else if (inputs.length == 4) {
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
        } else {
            print(Constants.INVALID);
        }
    }

    private void print(String s){
        System.out.println(s);
    }
}
