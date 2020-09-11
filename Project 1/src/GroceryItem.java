/**
 *
 * @authors Anthony Triolo and Biyun Wu
 *
 */
public class GroceryItem {
    private final String name;
    private final double price;
    private final boolean taxable;

    public GroceryItem(String name, double price, boolean taxable){
        this.name = name;
        this.price = price;
        this.taxable = taxable;
    }

    public boolean equals(Object obj) {
        return obj instanceof GroceryItem
                && ((GroceryItem) obj).name.equals(this.name)
                && ((GroceryItem) obj).price == this.price
                && ((GroceryItem) obj).taxable == this.taxable;
    }

    public String toString() {
        String tax = taxable ? Constants.TAXABLE : Constants.TAX_FREE;
        return String.format(Constants.ITEM_STR, name, price, tax);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean getTaxable() {
        return taxable;
    }
}
