package Application.Model;

/**
 * Definition of Savings which extends Account. It has 1 member variable:
 * isLoyal which is a boolean that tells if an account is owned by a loyal
 * customer
 * 
 * @author Biyun Wu, Anthony Triolo
 */

public class Savings extends Account {
	private boolean isLoyal;

	/**
	 * Constructor with parameters
	 * 
	 * @param holder:   The profile of the account holder
	 * @param balance:  The current balance of the account
	 * @param dateOpen: The date the account was opened
	 * @param isLoyal:  Whether or not the account is owned by a loyal customer
	 */
	public Savings(Profile holder, double balance, Date dateOpen, boolean isLoyal) {
		super(holder, balance, dateOpen);
		this.isLoyal = isLoyal;
	}

	/**
	 * Calculates the monthly interest on a savings account
	 * 
	 * @return The monthly interest amount
	 */
	@Override
	public double monthlyInterest() {
		double LOYAL_RATE = 0.0035;
		double NON_LOYAL_RATE = 0.0025;
		double interestRate = isLoyal ? LOYAL_RATE : NON_LOYAL_RATE;
		double MONTHS = 12;
		return this.getBalance() * interestRate / MONTHS;
	}

	/**
	 * Calculates the monthly fee on a Savings account
	 * 
	 * @return The monthly fee, 0 if no fee
	 */
	@Override
	public double monthlyFee() {
		double MIN_BALANCE_FOR_NO_FEE = 300;
		double MONTHLY_FEE = 5;
		return (this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	/**
	 * Check if two accounts are the same
	 * 
	 * @param account: The account to compare to
	 * @return true if the two accounts are equal, false if not
	 */
	@Override
	public boolean equals(Account account) {
		return account instanceof Savings && account.getProfile().equals(this.getProfile());
	}

	/**
	 * Format the account stats as a string
	 * 
	 * @return The account stats as a formatted string
	 */
	@Override
	public String toString() {
		return "*Savings*" + super.toString() + (isLoyal ? "*special Savings account*" : "");
	}
}