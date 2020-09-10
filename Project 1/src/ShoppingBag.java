/**
 * 
 * @authors Anthony Triolo and Biyun Wu
 *
 */
public class ShoppingBag {
	private GroceryItem[] bag;
	private int size;
	private int capacity;

	public ShoppingBag() {
		
		this.bag = new GroceryItem[5];
		this.capacity = bag.length;
	}

	private int find(GroceryItem item) {
		for (int i = 0; i < size; i++) {
			if (bag[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	private void grow() {
		GroceryItem[] tempBag = new GroceryItem[capacity + 1];
		for (int i = 0; i < capacity; i++) {
			tempBag[i] = bag[i];
		}
		bag = tempBag;
		capacity++;
	}

	public void add(GroceryItem item) {
		if(size == capacity) {
			grow();
		}
		for (int i = 0; i < capacity; i++) {
			if (bag[i] == null) {
				bag[i] = item;
				size++;
				return;
			}
		}
	}

	public boolean remove(GroceryItem item) {
		if(find(item) == -1) {
			return false;
		}
		int removeIndex = find(item);
		bag[removeIndex] = bag[size-1];
		size--;
		return true;
	}

	public double salesPrice() {
		double totalSale = 0.0;
		for(int i = 0; i < size; i++) {
			totalSale += bag[i].getPrice();
		}
		return totalSale;
	}

	public double salesTax() {
		double totalTax = 0.0;
		for(int i = 0; i < size; i++) {
			if(bag[i].getTaxable() == true) {
				totalTax += (bag[i].getPrice()*0.06625);
			}
		}
		return totalTax;
	}

	public void print() {
		for(int i = 0; i < size; i++) {
			System.out.println("\u2022 " + bag[i].toString());
		}
	}
	
	public int getSize() {
		return size;
	}

}
