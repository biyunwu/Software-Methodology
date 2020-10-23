package Application.Model;

/**
 * Definition of MoneyMarket which extends Account. It has 1 member variable:
 * withdrawals which tells how many withdrawals the account has made
 * 
 * @author Biyun Wu, Anthony Triolo
 */

public class MoneyMarket extends Account {
	private int withdrawals;

	/**
	 * Constructor with parameters
	 * 
	 * @param holder:   The profile of the account holder
	 * @param balance:  The current balance of the account
	 * @param dateOpen: The date the account was opened
	 */
	public MoneyMarket(Profile holder, double balance, Date dateOpen) {
		super(holder, balance, dateOpen);
		this.withdrawals = 0;
	}

	/**
	 * Takes money out of an account
	 * 
	 * @param amount: The amount of money to take out
	 */
	@Override
	public void debit(double amount) {
		super.debit(amount);
		this.withdrawals++;
	}

	@Override
	public double monthlyInterest() {
		double INTEREST_RATE = 0.0065;
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
		double MIN_BALANCE_FOR_NO_FEE = 2500;
		double MONTHLY_FEE = 12;
		return (withdrawals <= 6 && this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	/**
	 * Check if two accounts are the same
	 * 
	 * @param account: The account to compare to
	 * @return true if the two accounts are equal, false if not
	 */
	@Override
	public boolean equals(Account account) {
		return account instanceof MoneyMarket && account.getProfile().equals(this.getProfile());
	}

	/**
	 * Format the account stats as a string
	 * 
	 * @return The account stats as a formatted string
	 */
	@Override
	public String toString() {
		return withdrawals == 1 ? "*Money Market*" + super.toString() + "*" + withdrawals + " withdrawal*"
				: "*Money Market*" + super.toString() + "*" + withdrawals + " withdrawals*";
	}
}