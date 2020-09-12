/**
 * @authors Anthony Triolo and Biyun Wu
 */

public class ShoppingBag {
	private GroceryItem[] bag;
	private int size;
	private int capacity;

	public ShoppingBag() { // Constructor
		int INITIAL_CAPACITY = 5;
		this.bag = new GroceryItem[INITIAL_CAPACITY];
		this.capacity = bag.length;
		this.size = 0;
	}

	/**
	 * Helper method to find an item.
	 * @param item: GroceryItem to be searched in ShoppingBag.
	 * @return index of the item or -1 if not found.
	 */
	private int find(GroceryItem item) {
		for (int i = 0; i < size; i++) {
			if (bag[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	private void grow() { // Helper method to grow the capacity.
		int INCREMENT = 5; // "If the bag is full, the bag automatically grows the capacity by 5."
		GroceryItem[] tempBag = new GroceryItem[capacity + INCREMENT];
		if (capacity >= 0)
			System.arraycopy(bag, 0, tempBag, 0, capacity);
		bag = tempBag;
		capacity++;
	}

	/**
	 * @param item to be added to ShoppingBag
	 */
	public void add(GroceryItem item) {
		if (size == capacity) {
			grow();
		}
		bag[size] = item; // Index of `size` is always the first position available to add new item.
		size++;
	}

	/**
	 * Find the target in `GroceryItem[] bag` and replace it with the last no-NULL object in the array.
	 * @param item object to be removed in the bag.
	 * @return success -> true / fail -> false.
	 */
	public boolean remove(GroceryItem item) {
		int removeIndex = find(item);
		if (removeIndex == -1) {
			return false;
		}
		bag[removeIndex] = bag[size - 1];
		bag[size - 1] = null;
		size--;
		return true;
	}

	public double salesPrice() {
		double totalSale = 0.0;
		for (int i = 0; i < size; i++) {
			totalSale += bag[i].getPrice();
		}
		return totalSale;
	}

	public double salesTax() {
		double TAX_RATE = 0.06625;
		double totalTax = 0.0;
		for (int i = 0; i < size; i++) {
			if (bag[i].getTaxable()) {
				totalTax += (bag[i].getPrice() * TAX_RATE);
			}
		}
		return totalTax;
	}

	public void print() { // Print items in bag.
		for (int i = 0; i < size; i++) {
			System.out.println("\u2022 " + bag[i].toString());
		}
	}

	public int getSize() {
		return size;
	}
}
