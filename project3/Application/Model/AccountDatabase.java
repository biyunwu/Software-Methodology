package Application.Model;

/**
 * Definition of AccountDatabase It has 2 member variables: an object array to
 * store accounts, and the number of accounts in the array.
 * 
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
			accounts[i].debit(amount);
			return 0;
		}
		return 1;
	}

	/** Helper method to sort database by the date an account was opened */
	private void sortByDateOpen() {
		mergeSort(false, 0, size);
	}

	/** Helper method to sort database by the last name of the account owner */
	private void sortByLastName() {
		mergeSort(true, 0, size);
	}

	/**
	 * Method to list accounts in order of date opened
	 * 
	 * @return String that contains list of accounts in order of date opened
	 */
	public String printByDateOpen() {
		StringBuilder sb = new StringBuilder();
		if (size == 0) {
			sb.append("Database is empty.\n");
			return sb.toString();
		}
		sortByDateOpen();
		sb.append("--Printing statements by date opened--\n");
		sb.append(printDetail());
		return sb.toString();
	}

	/**
	 * Method to list accounts in order of last name
	 * 
	 * @return String that contains list of accounts in order of last name
	 */
	public String printByLastName() {
		StringBuilder sb = new StringBuilder();
		if (size == 0) {
			sb.append("Database is empty.\n");
			return sb.toString();
		}
		sortByLastName();
		sb.append("--Printing statements by last name--\n");
		sb.append(printDetail());
		return sb.toString();
	}

	/**
	 * Method to list accounts in database
	 * 
	 * @return String that contains list of accounts
	 */
	public String printAccounts() {
		StringBuilder sb = new StringBuilder();
		if (size == 0) {
			sb.append("Database is empty.\n");
			return sb.toString();
		}
		sb.append("--Listing accounts in the database--\n");
		for (int i = 0; i < size; i++) {
			sb.append(accounts[i].toString()).append("\n");
		}
		sb.append("--end of listing--\n");
		return sb.toString();
	}

	/**
	 * Stringify accounts.
	 * 
	 * @return string that contains accounts info (1 account per line).
	 */
	public String export() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(accounts[i].export()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Helper method to print account balance, as well as any interest or fees
	 * 
	 * @return StringBuilder that contains all of the details of the accounts in the
	 *         database
	 */
	private StringBuilder printDetail() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append("\n").append(accounts[i].toString()).append("\n");
			double interest = accounts[i].monthlyInterest();
			double fee = accounts[i].monthlyFee();
			double difference = interest - fee;
			accounts[i].credit(difference);
			String s = String.format("-interest: $ %,.2f\n-fee: $ %.2f\n-new balance: $ %,.2f\n", interest, fee,
					accounts[i].getBalance());
			sb.append(s);
		}
		sb.append("--end of printing--\n");
		return sb;
	}

	/**
	 * Helper recursive mergesort method.
	 *
	 * @param toSortByLastName Whether or not to sort by last name
	 * @param low              first index of the array to be sorted
	 * @param high             (last index of the array to be sorted) + 1
	 */
	private void mergeSort(boolean toSortByLastName, int low, int high) { // Sort accounts[low, high)
		if (high - low <= 1) { // Base case.
			return;
		}
		int mid = low + (high - low) / 2;
		mergeSort(toSortByLastName, low, mid);
		mergeSort(toSortByLastName, mid, high);
		mergeSubArrays(toSortByLastName, low, mid, high);
	}

	/**
	 * Helper method to merge two sub-arrays for mergesort
	 *
	 * @param toSortByLastName Whether or not to sort by last name
	 * @param low              first index of the first sub-array
	 * @param mid              first index of the second sub-array
	 * @param high             (last index of the second sub-array) + 1
	 */
	private void mergeSubArrays(boolean toSortByLastName, int low, int mid, int high) {
		// Merge accounts[lo, mid) with accounts[mid, hi) into temp[0, hi-lo)
		int n = high - low;
		Account[] temp = new Account[n];
		int i = low;
		int j = mid;
		for (int k = 0; k < n; k++) {
			if (i == mid) {
				temp[k] = accounts[j++];
			} else if (j == high) {
				temp[k] = accounts[i++];
			} else if ((toSortByLastName ? accounts[j].compareLastNameTo(accounts[i])
					: accounts[j].compareDateTo(accounts[i])) < 0) {
				temp[k] = accounts[j++];
			} else {
				temp[k] = accounts[i++];
			}
		}
		System.arraycopy(temp, 0, accounts, low, n); // Copy sorted sub-array back to accounts.
	}
}