/* @author Biyun Wu, Anthony Triolo */

public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;

	public Date(int month, int day, int year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public int compareTo(Date date) {
		if (year < date.year) {
			return -1;
		} else if (year > date.year) {
			return 1;
		}

		if (month < date.month) {
			return -1;
		} else if (month > date.month) {
			return 1;
		}

		if (day < date.day) {
			return -1;
		} else if (day > date.day) {
			return 1;
		}

		return 0;
	}

	@Override
	public String toString() {
		return String.format("%d/%d/%d", month, day, year);
	}

	public boolean isValid() {
		// Case 1: leap year, Feb.29th. case 2: ordinary year, Feb.28th
		boolean isLeapYear = (year % Num.CENTURY == 0)
								? (year % Num.FOUR_CENTURY == 0)
								: (year % Num.LEAP_YEAR_DENOMINATOR == 0);
		boolean isDayLowerBounded = day >= Num.FIRST_DAY; // Check lower bound.
		boolean isDayUpperBounded;
		switch(month) { // Check upper bound.
			case Num.JAN,Num.MAR,Num.MAY,Num.JUL,Num.AUG,Num.OCT,Num.DEC ->
					isDayUpperBounded = day <= Num.DAYS_ODD; // 31
			case Num.APR,Num.JUN,Num.SEP,Num.NOV ->
					isDayUpperBounded = day <= Num.DAYS_EVEN; // 30
			case Num.FEB ->
					isDayUpperBounded = isLeapYear ? day <= (Num.DAYS_FEB_LEAP) : (day <= Num.DAYS_FEB); // 29 or 28.
			default -> isDayUpperBounded = false;
		}
		return isDayLowerBounded && isDayUpperBounded;
	}
}