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

	@Override
	public double monthlyInterest() {
		double INTEREST_RATE = 0.0065;
		double MONTHS = 12;
		return this.getBalance() * INTEREST_RATE / MONTHS;
	}

	@Override
	public double monthlyFee() {
		double MIN_BALANCE_FOR_NO_FEE = 2500;
		double MONTHLY_FEE = 12;
		return (withdrawals <= 6 && this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	@Override
	public boolean equals(Account account) { // Compare account type and profile.
		return account instanceof MoneyMarket && account.getProfile().equals(this.getProfile());
	}

	@Override
	public String toString() {
		return withdrawals == 1 ? "*Money Market*" + super.toString() + "*" + withdrawals + " withdrawal*"
				: "*Money Market*" + super.toString() + "*" + withdrawals + " withdrawals*";
	}

	/**
	 * Helper method to increase the withdrawal count
	 */
	public void addWithdrawal() {
		withdrawals++;
	}
}