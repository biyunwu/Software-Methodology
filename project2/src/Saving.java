/* @author Biyun Wu, Anthony Triolo */

public class Saving extends Account{
	private boolean isLoyal;

	public Saving(Profile holder, double balance, Date dateOpen, boolean isLoyal) {
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
		return account instanceof Saving && account.getProfile().equals(this.getProfile());
	}

	@Override
	public String toString() {
		return "*Savings*" + super.toString() + (isLoyal? "*special Saving account*" : "");
	}
}