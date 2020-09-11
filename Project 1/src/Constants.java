/**
 *
 * @authors Anthony Triolo and Biyun Wu
 *
 */

public class Constants {
    public static final int INIT_CAPACITY = 5;
    public static final int INCREMENT = 5;
    public static final double TAX_RATE = 0.0625;
    public static final String ITEM_STR= "·%s: $%.2f : %s";
    public static final String SUCCESS_REMOVE = "%s %.2f removed.";
    public static final String FAIL_REMOVE = "Unable to remove, this item is not in the bag.";
    public static final String SUCCESS_ADD = "%s added to the bag.";
    public static final String EMPTY_BAG = "The bag is empty!";
    public static final String LIST_START = "**You have %d items in the bag.";
    public static final String LIST_END = "**End of list";
    public static final String CHECKOUT_EMPTY_BAG = "Unable to check out,the bag is empty!";
    public static final String CHECKOUT_START = "**Checking out %d item(s):";
    public static final String CHECKOUT_END = "*Sales total: $%.2f\n"
            + "*Sales tax: $%.2f\n"
            + "*Total amount paid: $%.2f.";
    public static final String THANKS = "Thanks for shopping with us!";
    public static final String INVALID = "Invalid command!";
    public static final String TAXABLE = "is taxable";
    public static final String TAX_FREE = "tax free";
}
