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
		if (obj instanceof Extra) {
			for (OrderLine ol: orderlines) {
				if (ol.equals(obj)) {
					orderlines.remove(ol);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object obj) {
		if (obj instanceof Extra) {
			for (OrderLine ol: orderlines) {
				if (ol.equals(obj)) {
					orderlines.remove(ol);
					return true;
				}
			}
		}
		return false;
	}
}
