/* @author Biyun Wu, Anthony Triolo */

public abstract class Account{
	private Profile holder;
	private double balance;
	private Date dateOpen;

	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}

	public void debit(double amount) {
		balance -= amount;
	}
	public void credit(double amount) {
		balance += amount;
	}

	@Override
	public String toString() {
		return String.format("%s* $%,.2f*%s", holder.toString(), balance, dateOpen.toString());
	}

	public int compareLastNameTo(Account account) {
		return holder.compareTo(account.holder);
	}

	public int compareDateTo(Account account) {
		return dateOpen.compareTo(account.dateOpen);
	}


	public double getBalance() {
		return balance;
	}

	public Profile getProfile() {
		return holder;
	}

	public abstract double monthlyInterest();

	public abstract double monthlyFee();

	public abstract boolean equals(Account account); // Added abstract method.
}