/* @author Biyun Wu, Anthony Triolo */

public class AccountDatabase {
	private Account[] accounts;
	private int size;

	public AccountDatabase() {
		int INITIAL_CAPACITY = 5;
		accounts = new Account[INITIAL_CAPACITY];
		size = 0;
	}

	private int find(Account account) {
		for(int i = 0; i < size; i++) {
			if (accounts[i].equals(account)) {
				return i;
			}
		}
		return -1;
	}

	private void grow() {
		int INCREMENT = 5;
		Account[] temp = new Account[size + INCREMENT];
		System.arraycopy(accounts, 0, temp, 0, size);
		accounts = temp;
	}

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

	public boolean remove(Account account) {
		int idx = find(account);
		if (idx != -1) {
			accounts[idx] = accounts[size - 1];
			accounts[size - 1] = null;
			size--;
			return true; // Removed successfully.
		}
		return false; // Account does not exist.
	}

	public boolean deposit(Account account, double amount) {
		int idx = find(account);
		if (idx != -1) {
			accounts[idx].credit(amount);
			return true;
		}
		return false; // Account not found.
	}

	public int withdrawal(Account account, double amount) {
		for (int i = 0; i < size; i++) {
			if (accounts[i].equals(account)) {
				if (accounts[i].getBalance() >= amount) {
					if(accounts[i] instanceof MoneyMarket) {
						((MoneyMarket) accounts[i]).addWithdrawal();
					}
					accounts[i].debit(amount);
					return 0; // Withdrawal successful.
				} else {
					return 1; // Insufficient fund.
				}
			}
		}
		return -1; // Account does not exist.
	}

	private void sortByDateOpen() {
		binaryInsertionSort(false);
	}

	private void sortByLastName() {
		binaryInsertionSort(true);
	}

	public void printByDateOpen() {
		if(size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		sortByDateOpen();
		System.out.println("\n--Printing statements by date opened--");
		printDetail();

	}

	public void printByLastName() {
		if(size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		sortByLastName();
		System.out.println("\n--Printing statements by last name--");
		printDetail();
	}

	public void printAccounts() {
		if(size == 0) {
			System.out.println("Database is empty.");
			return;
		}
		System.out.println("--Listing accounts in the database--");
		for (int i = 0; i < size; i++) {
			System.out.println(accounts[i].toString());
		}
		System.out.println("--end of listing--");
	}

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
			System.out.printf("-interest: $ %,.2f\n-fee: $ %.2f\n-new balance: $ %,.2f\n",
								interest,
								fee,
								accounts[i].getBalance());
		}
		System.out.println("--end of printing--\n");
	}

	private void binaryInsertionSort(boolean toSortByLastName) {
		int n = size;
		for (int i = 1; i < n; i++) {
			// binary search to determine index j at which to insert a[i]
			Account currAccount = accounts[i];
			int lo = 0, hi = i;
			while (lo < hi) {
				int mid = lo + (hi - lo) / 2;
				int comparator = toSortByLastName
									? currAccount.compareLastNameTo(accounts[mid])
									: currAccount.compareDateTo(accounts[mid]);
				if (comparator < 0) {
					hi = mid;
				} else {
					lo = mid + 1;
				}
			}

			// insertion sort with "half exchanges": insert a[i] at index j and shift a[j], ..., a[i-1] to right.
			if (i >= lo) {
				System.arraycopy(accounts, lo, accounts, lo + 1, i - lo);
			}

			accounts[lo] = currAccount;
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}
/*
	public static void main(String[] args) { // Testbed
		AccountDatabase db = new AccountDatabase();
		db.add(new Checking(new Profile("John", "Smith"),1000, new Date(2020, 9,1), true));
		db.add(new Savings(new Profile("John", "Lee"),1000, new Date(2020, 8,1), true));
		db.add(new MoneyMarket(new Profile("Jonathan", "Ty"),990, new Date(2020, 7,1)));
		db.add(new MoneyMarket(new Profile("Jessica", "Hsie"),880, new Date(2020, 6,9)));
		db.add(new Savings(new Profile("Jerseyca", "Liu"),1000, new Date(2020, 8,1), false));
		db.add(new Savings(new Profile("Simon", "Robert"),1000, new Date(-189, 1,1), true));
		db.printByLastName();
		db.printByDateOpen();
	}*/
}