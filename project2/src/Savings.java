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

	@Override
	public double monthlyInterest() {
		double LOYAL_RATE = 0.0035;
		double NON_LOYAL_RATE = 0.0025;
		double interestRate = isLoyal ? LOYAL_RATE : NON_LOYAL_RATE;
		double MONTHS = 12;
		return this.getBalance() * interestRate / MONTHS;
	}

	@Override
	public double monthlyFee() {
		double MIN_BALANCE_FOR_NO_FEE = 300;
		double MONTHLY_FEE = 5;
		return (this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	@Override
	public boolean equals(Account account) { // Compare account type and profile.
		return account instanceof Savings && account.getProfile().equals(this.getProfile());
	}

	@Override
	public String toString() {
		return "*Savings*" + super.toString() + (isLoyal ? "*special Savings account*" : "");
	}
}