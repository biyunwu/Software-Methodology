import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Testing class for the Date class
 * 
 * @author Biyun Wu, Anthony Triolo
 *
 */
class DateTest {

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
	void testIsValid() {
		Date date1 = new Date("1/1/2020");
		assertTrue(date1.isValid()); // test valid date
		Date date2 = new Date("11/31/2020");
		assertFalse(date2.isValid()); // test 31 days in month with 30 days
		Date date3 = new Date("13/1/2020");
		assertFalse(date3.isValid()); // test invalid month
		Date date4 = new Date("2/29/2020");
		assertTrue(date4.isValid()); // test valid leap year
		Date date5 = new Date("2/29/2019");
		assertFalse(date5.isValid()); // test invalid leap year
		Date date6 = new Date("2/29/2000");
		assertTrue(date6.isValid()); // test valid leap year divisible by 100 and 400
		Date date7 = new Date("2/29/1800");
		assertFalse(date7.isValid()); //test invalid leap year divisible by 100 but not 400
		
	}

}
