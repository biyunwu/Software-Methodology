package Application.Model;

/**
 * Definition of Checking which extends Account. It has 1 member variable:
 * directDeposit which is a boolean that tells if an account has direct deposit
 * enabled
 * 
 * @author Biyun Wu, Anthony Triolo
 */

public class Checking extends Account {
	private boolean directDeposit;

	/**
	 * Constructor with parameters
	 * 
	 * @param holder:          The profile of the account holder
	 * @param balance:         The current balance of the account
	 * @param dateOpen:        The date the account was opened
	 * @param isDirectDeposit: Whether or not the account has direct deposit
	 */
	public Checking(Profile holder, double balance, Date dateOpen, boolean isDirectDeposit) {
		super(holder, balance, dateOpen);
		this.directDeposit = isDirectDeposit;
	}

	/**
	 * Calculates the monthly interest on a Checking account
	 * 
	 * @return The amount of monthly interest
	 */
	@Override
	public double monthlyInterest() {
		double INTEREST_RATE = 0.0005;
		double MONTHS = 12;
		return this.getBalance() * INTEREST_RATE / MONTHS;
	}

	/**
	 * Calculates the monthly fee for an account
	 * 
	 * @return The monthly fee, 0 if no fee
	 */
	@Override
	public double monthlyFee() {
		double MIN_BALANCE_FOR_NO_FEE = 1500;
		double MONTHLY_FEE = 25;
		return (directDeposit || this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	/**
	 * Check if two accounts are the same
	 * 
	 * @param account: The account to compare to
	 * @return true if the two accounts are equal, false if not
	 */
	@Override
	public boolean equals(Account account) {
		return account instanceof Checking && account.getProfile().equals(this.getProfile());
	}

	/**
	 * Format the account stats as a string
	 * 
	 * @return The account stats as a formatted string
	 */
	@Override
	public String toString() {
		return "*Checking*" + super.toString() + (directDeposit ? "*direct deposit account*" : "");
	}

	/**
	 * Convert account info to string.
	 * @return account info in string.
	 */
	@Override
	public String export() {
		return "C," + super.export() + directDeposit;
	}
}
