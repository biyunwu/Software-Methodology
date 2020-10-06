import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Testing class for the MoneyMarket class
 * 
 * @author Biyun Wu, Anthony Triolo
 *
 */
class MoneyMarketTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMonthlyInterest() {
		Profile profile1 = new Profile("John", "Doe");
		Account money1 = new MoneyMarket(profile1, 1000.00, new Date("1/1/2020"));
		assertTrue(money1.monthlyInterest() == (1000 * 0.0065 / 12)); // test proper monthly interest amount
	}

	@Test
	void testMonthlyFee() {
		Profile profile1 = new Profile("John", "Doe");
		Account money1 = new MoneyMarket(profile1, 1000.00, new Date("1/1/2020"));
		assertTrue(money1.monthlyFee() == 12); // test fee for account with balance under 2500
		Profile profile2 = new Profile("Jane", "Doe");
		Account money2 = new MoneyMarket(profile2, 2500.00, new Date("1/1/2020"));
		assertTrue(money2.monthlyFee() == 0); // test fee for account with balance over 2500
		Profile profile3 = new Profile("Jake", "Doe");
		Account money3 = new MoneyMarket(profile3, 3000.00, new Date("1/1/2020"));
		for (int i = 0; i <= 7; i++) { // simulate 8 withdrawals
			money3.debit(1);
		}
		assertFalse(money3.monthlyFee() == 0); // test fee for account with balance over 2500 but more than 6
												// withdrawals
	}

}
