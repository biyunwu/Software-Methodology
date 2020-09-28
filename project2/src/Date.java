/* @author Biyun Wu, Anthony Triolo */

public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	@Override
	public int compareTo(Date date) {
//		if (year < date.getYear()) {
//			return -1;
//		} else if (year > date.getYear()) {
//			return 1;
//		}
//
//		if (month < date.getMonth()) {
//			return -1;
//		} else if (month > date.getMonth()) {
//			return 1;
//		}
//
//		if (day < date.getDay()) {
//			return -1;
//		} else if (day > date.getDay()) {
//			return 1;
//		}
//
//		return 0;
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
		// What is the valid range for year? Is B.C.E acceptable?
		// Case 1: leap year, Feb.29th. case 2: ordinary year, Feb.28th
		boolean isLeapYear = (year % 100 == 0) ? (year % 400 == 0) : (year % 4 == 0);
		boolean isDayLowerBounded = day >= 1; // Check lower bound.
		boolean isDayUpperBounded;
		// !!! Need to declare constants first !!!
		switch(month) { // Check upper bound.
			case 1,3,5,7,8,10,12 -> isDayUpperBounded = day <= 31;
			case 4,6,9,11 -> isDayUpperBounded = day <= 30;
			case 2 -> isDayUpperBounded = (isLeapYear ? day <= 29 : day <= 28);
			default -> isDayUpperBounded = false;
		}
		return isDayLowerBounded && isDayUpperBounded;
	}

	public static void main (String[] args) { // Testbed
		Date date = new Date(1600, 2, 29);
		System.out.println(date.toString() + ": isValid -> " + date.isValid());
		Date date2 = new Date(1600, 2, 30);
		System.out.println(date2.toString() + ": isValid -> " + date2.isValid());
		System.out.println(date2 + " is larger than " + date + ": " + date2.compareTo(date));
	}
}
