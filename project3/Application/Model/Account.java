package Application.Model;

/**
 * Definition of Account. It has 3 member variables: holder which is the Profile
 * of the account holder, balance which is the balance of the account, and
 * dateOpen which is the date the account was opened.
 * 
 * @author Biyun Wu, Anthony Triolo
 */

public abstract class Account {
	private Profile holder;
	private double balance;
	private Date dateOpen;

	/**
	 * Constructor with parameters
	 * 
	 * @param holder:   The profile of the account holder
	 * @param balance:  The current balance of the account
	 * @param dateOpen: The date the account was opened
	 */
	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}

	/**
	 * Helper method to take money out of an account
	 * @param amount: The amount of money to take out
	 */
	public void debit(double amount) {
		balance -= amount;
	}

	/**
	 * Helper method to add money to an account
	 * @param amount: The amount of money to add
	 */
	public void credit(double amount) {
		balance += amount;
	}

	/**
	 * Format the account stats as a string
	 * 
	 * @return The account stats as a formatted string
	 */
	@Override
	public String toString() {
		return String.format("%s* $%,.2f*%s", holder.toString(), balance, dateOpen.toString());
	}

	/**
	 * Helper method to compare the last names of two accounts
	 * @param account: The account to compare to
	 * @return The result of comparing the profiles of each account
	 */
	public int compareLastNameTo(Account account) {
		return holder.compareTo(account.holder);
	}

	/**
	 * Helper method to compare the dates of two accounts
	 * @param account: The account to compare to
	 * @return The result of comparing the dates of each account
	 */
	public int compareDateTo(Account account) {
		return dateOpen.compareTo(account.dateOpen);
	}

	/**
	 * Gets the balance of an account
	 * @return the balance of the calling account
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Gets the profile of an account
	 * @return the profile associated with the calling account
	 */
	public Profile getProfile() {
		return holder;
	}

	/**
	 * Calculates the monthly interest for an account
	 * 
	 * @return The amount of monthly interest
	 */
	public abstract double monthlyInterest();

	/**
	 * Calculates the monthly fee for an account
	 * 
	 * @return The monthly fee, 0 if no fee
	 */
	public abstract double monthlyFee();

	
	/**
	 * Check if two accounts are the same
	 * 
	 * @param account: The account to compare to
	 * @return true if the two accounts are equal, false if not
	 */
	public abstract boolean equals(Account account);

//	/**
//	 * Stringify account info.
//	 * @return account info in string.
//	 */
//	public String export() {
//		return holder.toStringSeparateByComma() + "," + balance + "," + dateOpen.toString() + ",";
//	};
}