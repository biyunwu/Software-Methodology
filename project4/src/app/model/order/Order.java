package app.model.order;

import app.model.Customizable;
import app.model.sandwich.Extra;

import java.util.ArrayList;

public class Order implements Customizable {
	public static int lineNumber;
	private ArrayList<OrderLine> orderlines;

	public Order() {
		lineNumber = 0;
		orderlines = new ArrayList<>();
	}

	@Override
	public boolean add(Object obj) {
		if (obj instanceof OrderLine && !orderlines.contains(obj)) {
			return orderlines.add((OrderLine) obj);
		}
		return false;
	}

	@Override
	public boolean remove(Object obj) {
		if (obj instanceof OrderLine) {
			return orderlines.remove(obj);
		}
		return false;
	}
}
