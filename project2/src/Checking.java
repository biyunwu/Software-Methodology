/* @author Biyun Wu, Anthony Triolo */

public class Checking extends Account{
	private boolean directDeposit;

	public Checking(Profile holder, double balance, Date dateOpen, boolean isDirectDeposit) {
		super(holder, balance, dateOpen);
		this.directDeposit = isDirectDeposit;
	}

	@Override
	public double monthlyInterest() {
		double INTEREST_RATE = 0.0005;
		double MONTHS = 12;
		return this.getBalance() * INTEREST_RATE / MONTHS;
	}

	@Override
	public double monthlyFee() {
		double MIN_BALANCE_FOR_NO_FEE = 1500;
		double MONTHLY_FEE = 25;
		return (directDeposit || this.getBalance() >= MIN_BALANCE_FOR_NO_FEE) ? 0 : MONTHLY_FEE;
	}

	@Override
	public boolean equals(Account account) { // Compare account type and profile.
		return account instanceof Checking && account.getProfile().equals(this.getProfile());
	}

	@Override
	public String toString() {
		return "*Checking*" + super.toString() + (directDeposit? "*direct deposit account*" : "");
	}
}
