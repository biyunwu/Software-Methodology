/**
 * Definition of ShoppingBag.
 * It has 2 member variables: an object array to store grocery items, and the number of the grocery items in the array.
 * @author Anthony Triolo and Biyun Wu
 */

public class ShoppingBag {
	private GroceryItem[] bag;
	private int size;

	/** Constructor */
	public ShoppingBag() {
		int INITIAL_CAPACITY = 5;
		this.bag = new GroceryItem[INITIAL_CAPACITY];
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

	/** Helper method to increase the bag's capacity. */
	private void grow() { // Helper method to grow the capacity.
		int INCREMENT = 5; // "If the bag is full, the bag automatically grows the capacity by 5."
		GroceryItem[] tempBag = new GroceryItem[bag.length + INCREMENT]; // Null is the default value for obj cells.
		System.arraycopy(bag, 0, tempBag, 0, bag.length);
		bag = tempBag;
	}

	/** @param item to be added to ShoppingBag */
	public void add(GroceryItem item) {
		if (size == bag.length) {
			grow();
		}
		bag[size] = item; // Index of `size` is always the first position available to add new item.
		size++;
	}

	/**
	 * Find the target in `GroceryItem[] bag` and replace it with the last no-NULL object in the array.
	 * @param item object to be removed in the bag.
	 * @return true if removed successfully, otherwise return false.
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

	/** @return the total price of items in the bag without tax. */
	public double salesPrice() {
		double totalSale = 0.0;
		for (int i = 0; i < size; i++) {
			totalSale += bag[i].getPrice();
		}
		return totalSale;
	}

	/** @return the total tax of items in the bag. */
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

	/** Helper method to print detailed info of items in the bag. */
	public void print() { // Print items in bag.
		for (int i = 0; i < size; i++) {
			System.out.println("\u2022 " + bag[i].toString());
		}
	}

	/**
	 * Getter
	 * @return number of items in the bag.
	 * */
	public int getSize() {
		return size;
	}
	
	public static void main(String[] args) { //test bed main
		ShoppingBag testBag = new ShoppingBag(); //test the constructor
		System.out.println(testBag.bag.length + "," + testBag.size); //print initial capacity of bag and initial number of items in bag
		testBag.add(new GroceryItem("milk", 2.90, false)); //test add() method
		testBag.print(); //output contents of testBag to prove add worked
		System.out.println(testBag.remove(new GroceryItem("milk", 2.90, false))); //test remove() method; true case
		System.out.println(testBag.remove(new GroceryItem("juice", 2.90, false))); //test remove() method; false case
		System.out.println("Initial capacity: " + testBag.bag.length); //get capacity of bag before grow() is called
		testBag.grow(); //test grow() method
		System.out.println("New capacity: " + testBag.bag.length); //get capacity of bag after grow() is called
		testBag.add(new GroceryItem("milk", 2.90, false));
		System.out.println(testBag.salesTax()); //test salesTax() method; case where nothing is taxable
		testBag.add(new GroceryItem("apple", 3.90, true));
		System.out.println(testBag.salesTax()); //test salesTax() method; case where there are taxable items
	}
}
