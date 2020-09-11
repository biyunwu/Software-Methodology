/**
 *
 * @authors Anthony Triolo and Biyun Wu
 *
 */
import java.util.Arrays;

public class ShoppingBag {
    private GroceryItem[] bag; // array-based implementation of the bag
    private int size;          // number of items currently in the bag
    private int capacity;      // current capacity


    public ShoppingBag() {
        initShoppingBag();
    }

    private void initShoppingBag() {
        bag = initBag(Constants.INIT_CAPACITY);
        size = 0;
        capacity = Constants.INIT_CAPACITY;
    }

    private int find(GroceryItem item) { // helper method to find an item.
        for (int i = 0; i < bag.length; i++) {
            if (item.equals(bag[i])) {
                return i;
            }
        }
        return -1;
    }

    private void grow() { // helper method to grow the capacity
        GroceryItem[] newBag = initBag(capacity + Constants.INCREMENT);
        System.arraycopy(bag, 0, newBag, 0, bag.length);

        capacity = newBag.length;

        bag = newBag;
    }

    public void add(GroceryItem item) {
        if (size < capacity) {
            bag[size] = item;
        } else {
            grow();
            bag[size] = item; // After grow(), the first available position is size, which is the old last index + 1.
        }

        print(String.format(Constants.SUCCESS_ADD, item.getName()));

        size++;
    }

    /**
     * Find the target in `GroceryItem[] bag` and replace it with the last no-NULL object in the array.
     * @param item object to be removed in the bag.
     * @return success -> true / fail -> false.
     */
    public boolean remove(GroceryItem item) {
        int found = find(item);
        if (found < 0) {
            print(Constants.FAIL_REMOVE);
            return false;
        }

        for (int i = bag.length - 1; i >= 0; i--) { // Find the last non-null item in the array.
            if (bag[i] != null) {
                bag[found] = bag[i]; // Replace.
                bag[i] = null;
                break;
            }
        }

        print(String.format(Constants.SUCCESS_REMOVE, item.getName(), item.getPrice()));
        size--;
        return true;
    }

    public double salesPrice() {
        double total = 0;
        for (int i = 0; i < size; i++){
            total += bag[i].getPrice();
        }
        return total;
    }

    public double salesTax() {
        double tax = 0;
        for (int i = 0; i < size; i++){
            if (bag[i].getTaxable()) {
                tax += bag[i].getPrice() * Constants.TAX_RATE;
            }
        }
        return tax;
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void display() {
        if (size == 0) {
            print(Constants.EMPTY_BAG);
        } else {
            print(String.format(Constants.LIST_START, size));
            listItems();
            print(Constants.LIST_END);
        }
    }

    public void checkout() {
        if (size == 0) {
            print(Constants.CHECKOUT_EMPTY_BAG);
        } else {
            print(String.format(Constants.CHECKOUT_START, size));
            listItems();
            double sales = salesPrice();
            double tax = salesTax();
            print(String.format(Constants.CHECKOUT_END, sales, tax, sales + tax));
            initShoppingBag(); // Empty bag after checking out.
        }
    }

    private void listItems() {
        for (int i = 0; i < size; i++){
            print(bag[i].toString());
        }
    }

    private GroceryItem[] initBag(int length) {
        GroceryItem[] newBag = new GroceryItem[length];
        Arrays.fill(newBag, null);
        return newBag;
    }
}
