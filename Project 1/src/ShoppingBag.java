/* @author Anthony Triolo and Biyun Wu */

/**
 * Definition of ShoppingBag.
 * It has 2 member variables: an object array to store grocery items, and the number of the grocery items in the array.
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
		int INITIAL_CAPACITY = 5;
		int INCREMENT = 5;
		System.out.print("Test 1, check constrictor: ");
		ShoppingBag testBag = new ShoppingBag(); //test the constructor
		//print initial capacity of bag and initial number of items in bag
		System.out.println("\t" + testBag.bag.length + "," + testBag.getSize());
		System.out.println("\t" + (testBag.bag.length == INITIAL_CAPACITY
									&& testBag.getSize() == 0
									? "pass" : "fail"));

		System.out.print("Test 2, add(): ");
		double price = 2.90;
		testBag.add(new GroceryItem("milk", price, false)); //test add() method
		testBag.print(); //output contents of testBag to prove add worked
		System.out.println("\t" + (testBag.bag.length == INITIAL_CAPACITY
									&& testBag.getSize() == 1
									&& testBag.bag[0].toString().equals("milk: $2.90 : tax free")
									? "pass" : "fail"));

		System.out.print("Test 3, remove(): ");
		boolean remove1 = testBag.remove(new GroceryItem("milk", price, false)); // true case
		boolean remove2 = testBag.remove(new GroceryItem("juice", price, false)); // false case
		System.out.println(remove1 + ", " + remove2);
		System.out.println("\t" + (remove1 && !remove2 ? "pass" : "fail"));

		System.out.print("Test 4, grow(): ");
		int initialCapacity = testBag.bag.length;
		testBag.grow();
		int newCapacity = testBag.bag.length;
		System.out.println("Initial capacity " + initialCapacity + ", " + "New capacity " + newCapacity);
		System.out.println("\t" + (newCapacity - initialCapacity == INCREMENT ? "pass" : "fail"));

		System.out.print("Test 5, salesTax() without taxable item: ");
		testBag.add(new GroceryItem("milk", price, false));
		System.out.println(testBag.salesTax()); //test salesTax() method; case where nothing is taxable
		System.out.println("\t" + (testBag.salesTax() == 0 ? "pass" : "fail"));

		System.out.print("Test 5, salesTax() with taxable item: ");
		price = 3.90;
		testBag.add(new GroceryItem("apple", price, true));
		System.out.println(testBag.salesTax()); //test salesTax() method; case where there are taxable items
		double TAX = 0.06625;
		System.out.println("\t" + (testBag.salesTax() == price * TAX ? "pass" : "fail"));
	}
}
