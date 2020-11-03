package application.model;

import java.util.ArrayList;

public class Order implements Customizable{

	public static int lineNumber;
	private ArrayList<OrderLine> orderlines;
	
	@Override
	public boolean add(Object obj) {
		orderlines.add((OrderLine) obj);
		return true;
	}

	@Override
	public boolean remove(Object obj) {
		orderlines.remove((OrderLine) obj);
		return true;
	}

}
