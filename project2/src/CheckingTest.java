import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Testing class for the Checking class
 * 
 * @author Biyun Wu, Anthony Triolo
 *
 */
class CheckingTest {

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
		Profile profile = new Profile("John", "Doe");
		Account checking = new Checking(profile, 1000.00, new Date("1/1/2020"), true);
		assertTrue(checking.monthlyInterest() == (1000 * 0.0005 / 12)); // test proper monthly interest amount
	}

	@Test
	void testMonthlyFee() {
		Profile profile1 = new Profile("John", "Doe");
		Account checking1 = new Checking(profile1, 1000.00, new Date("1/1/2020"), false);
		assertTrue(checking1.monthlyFee() == 25); // test fee for account with balance under 1500
		Profile profile2 = new Profile("Jane", "Doe");
		Account checking2 = new Checking(profile2, 1500.00, new Date("1/1/2020"), false);
		assertTrue(checking2.monthlyFee() == 0); // test fee for account with balance over 1500
		Profile profile3 = new Profile("Jake", "Doe");
		Account checking3 = new Checking(profile3, 500.00, new Date("1/1/2020"), true);
		assertTrue(checking3.monthlyFee() == 0); // test fee for account with direct deposit
	}

}
