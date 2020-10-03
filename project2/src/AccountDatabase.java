/**
 * Definition of AccountDatabase
 * It has 2 member variables: an object array to store accounts, and the number of accounts in the array.
 * @author Biyun Wu, Anthony Triolo 
 */

public class AccountDatabase {
	private Account[] accounts;
	private int size;

	/** Constructor */
	public AccountDatabase() {
		int INITIAL_CAPACITY = 5;
		accounts = new Account[INITIAL_CAPACITY];
		size = 0;
	}

	/**
	 * Helper method to find index of an account in the database
	 * 
	 * @param account: The account to be searched for in the database
	 * @return index of item or -1 if not found.
	 */
	private int find(Account account) {
		for (int i = 0; i < size; i++) {
			if (accounts[i].equals(account)) {
				return i;
			}
		}
		return -1;
	}

	/** Helper method to increase the database's capacity. */
	private void grow() {
		int INCREMENT = 5; // "If the database is full, the bag automatically grows the capacity by 5."
		Account[] temp = new Account[size + INCREMENT];
		System.arraycopy(accounts, 0, temp, 0, size); // Copy accounts from smaller database into new larger one
		accounts = temp;
	}

	/**
	 * Add a new account to the database
	 * 
	 * @param account: The account to be added to the database
	 * @return true if account is successfully added, false if the account already
	 *         exists
	 */
	public boolean add(Account account) {
		for (int i = 0; i < size; i++) { // Check if account exists.
			if (accounts[i].equals(account)) {
				return false;
			}
		}

		if (accounts.length == size) { // Check if array is full.
			grow();
		}

		accounts[size] = account;
		size++;

		return true;
	}

	/**
	 * Removes an account from the database
	 * 
	 * @param account: The account to be removed from the database
	 * @return true if successfully removed, false if account doesn't exist
	 */
	public boolean remove(Account account) {
		int idx = find(account);
		if (idx != -1) { // Check if account exists
			accounts[idx] = accounts[size - 1];
			accounts[size - 1] = null;
			size--;
			return true;
		}
		return false;
	}

	/**
	 * Deposits a specified amount of money into a specified account
	 * 
	 * @param account: The account to deposit money into
	 * @param amount:  The amount of money to deposit
	 * @return true if the money was successfully deposited, false if the account
	 *         doesn't exist
	 */
	public boolean deposit(Account account, double amount) {
		int idx = find(account);
		if (idx != -1) {
			accounts[idx].credit(amount);
			return true;
		}
		return false; // Account not found.
	}

	/**
	 * Withdraws a specified amount of money into a specified account
	 * 
	 * @param account: The account to withdraw from
	 * @param amount:  The amount of money to withdraw
	 * @return 0 if the withdrawal is successful, -1 if the account doesn't have
	 *         enough money in it, 1 if the account doesn't exist
	 */
	public int withdrawal(Account account, double amount) {
		int i = find(account);
		if (i == -1) {
			return -1;
		}
		if (accounts[i].getBalance() >= amount) { // Check if account has enough money to withdraw
			if (accounts[i] instanceof MoneyMarket) {
				((MoneyMarket) accounts[i]).addWithdrawal();
			}
			accounts[i].debit(amount);
			return 0;
		}
		return 1;
	}

	/** Helper method to sort database by the date an account was opened */
	private void sortByDateOpen() {
		binaryInsertionSort(false);
	}

	/** Helper method to sort database by the last name of the account owner */
	private void sortByLastName() {
		binaryInsertionSort(true);
	}

	/** Helper method to print accounts in order of date opened */
	public void printByDateOpen() {
		if (size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		sortByDateOpen();
		System.out.println("\n--Printing statements by date opened--");
		printDetail();

	}

	/** Helper method to print accounts in order of last name */
	public void printByLastName() {
		if (size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		sortByLastName();
		System.out.println("\n--Printing statements by last name--");
		printDetail();
	}

	/** Helper method to print accounts in database */
	public void printAccounts() {
		if (size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		System.out.println("--Listing accounts in the database--");
		for (int i = 0; i < size; i++) {
			System.out.println(accounts[i].toString());
		}
		System.out.println("--end of listing--");
	}

	/** Helper method to print account balance, as well as any interest or fees */
	private void printDetail() {
		for (int i = 0; i < size; i++) {
			System.out.println("\n" + accounts[i].toString());
			double interest = accounts[i].monthlyInterest();
			double fee = accounts[i].monthlyFee();
			double difference = interest - fee;
			if (difference >= 0) {
				accounts[i].credit(difference);
			} else {
				accounts[i].debit(Math.abs(difference));
			}
			System.out.printf("-interest: $ %,.2f\n-fee: $ %.2f\n-new balance: $ %,.2f\n", interest, fee,
					accounts[i].getBalance());
		}
		System.out.println("--end of printing--\n");
	}

	/**
	 * Helper method to do binary insertion sort based on either last name or date
	 * created
	 * 
	 * @param toSortByLastName: Whether or not to sort by last name
	 */
	private void binaryInsertionSort(boolean toSortByLastName) {
		int n = size;
		for (int i = 1; i < n; i++) {
			// binary search to determine index j at which to insert accounts[i]
			Account currAccount = accounts[i];
			int lo = 0, hi = i;
			while (lo < hi) {
				int mid = lo + (hi - lo) / 2;
				int comparator = toSortByLastName ? currAccount.compareLastNameTo(accounts[mid])
						: currAccount.compareDateTo(accounts[mid]);
				if (comparator < 0) {
					hi = mid;
				} else {
					lo = mid + 1;
				}
			}

			// insertion sort with "half exchanges": insert a[i] at index j and shift a[j],
			// ..., a[i-1] to right.
			if (i >= lo) {
				System.arraycopy(accounts, lo, accounts, lo + 1, i - lo);
			}

			accounts[lo] = currAccount;
		}
	}
}