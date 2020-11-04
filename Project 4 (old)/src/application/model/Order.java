package application.model;

import java.util.ArrayList;

public class Order implements Customizable{

	public static int lineNumber;
	private ArrayList<OrderLine> orderlines;
	
	@Override
	public boolean add(Object obj) {
		return orderlines.add((OrderLine) obj);
	}

	@Override
	public boolean remove(Object obj) {
		return orderlines.remove((OrderLine) obj);
	}

}
