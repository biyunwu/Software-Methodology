package app.test;

import app.model.order.Order;
import app.model.order.OrderLine;
import app.model.sandwich.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
	@BeforeEach
	void setUp() {

	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void add() {
		final Order order = new Order();
		// cannot add non-orderline object or null to order.
		Extra.getExtraList().forEach(obj -> {
			assertFalse(order.add((new Chicken()).add(obj)));
			assertFalse(order.add(null));
		});

		// add 10000 sandwiches to order.
		getListOfSandwiches().forEach(sandwich -> assertTrue(order.add(new OrderLine(sandwich))));
	}

	@Test
	void remove() {
		Order order = new Order();
		// cannot remove anything from empty order.
		assertFalse(order.remove(new OrderLine(new Chicken())));
		assertFalse(order.remove(null));
		assertFalse(order.remove(new OrderLine(new Fish())));

		ArrayList<Sandwich> sandwiches = getListOfSandwiches();
		ArrayList<OrderLine> orderLines = new ArrayList<>();
		for (int i = 0; i <= 9999; i++) {
			OrderLine line = new OrderLine(sandwiches.get(i));
			orderLines.add(line);
			order.add(line);
		}

		// returns false if removing lines that are NOT in the order.
		sandwiches.forEach(sandwich -> assertFalse(order.remove(new OrderLine(sandwich))));
		// returns true if removing lines that are in the order.
		orderLines.forEach( line -> assertTrue(order.remove(line)));
		// all lines have been removed.
		assertTrue(order.getOrderLines().isEmpty());
	}

	@Test
	void getOrderLines() {
		final Order order = new Order();
		assertTrue(order.getOrderLines().isEmpty()); // no order line.
		ArrayList<Sandwich> sandwiches = getListOfSandwiches();
		sandwiches.forEach(sandwich -> order.add(new OrderLine(sandwich)));
		assertEquals(order.getOrderLines().size(), 10000); // check line amount.
		ArrayList<OrderLine> lines = order.getOrderLines();
		for (int i = 0; i <= 9999; i++) {
			OrderLine line = lines.get(i);
			assertTrue(line.getSandwich() instanceof Chicken); // check sandwich type in order line.
			assertEquals(line.getPrice(), (new Chicken()).price()); // check order line total price.
		}
	}

	private ArrayList<Sandwich> getListOfSandwiches() {
		ArrayList<Sandwich> sandwiches = new ArrayList<>();
		for (int i = 0; i <= 9999; i++) {
			sandwiches.add(new Chicken());
		}
		return sandwiches;
	}
}