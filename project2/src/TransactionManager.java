import java.util.Scanner;
/* @author Biyun Wu, Anthony Triolo */

public class TransactionManager {
	private final AccountDatabase db = new AccountDatabase();
	public void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Transaction processing starts.....");
		while (sc.hasNextLine()) {
			String[] inputs = sc.nextLine().split("\\s+");
			if (inputs != null && inputs.length > 0) {
				switch (inputs[0]) {
					case "OC", "OS", "OM" -> openAccount(inputs);
					case "CC", "CS", "CM" -> closeAccount(inputs);
					case "DC", "DS", "DM" -> deposit(inputs);
					case "WC", "WS", "WM" -> withdraw(inputs);
					case "PA", "PD", "PN" -> print(inputs);
					case "Q" -> {
						System.out.println("Transaction processing completed.");
						return;
					}
					default -> System.out.println("Command '" + inputs[0] + "' not supported!");
				}
			}
		}
	}

	private void openAccount(String[] inputs) {
		Profile holder = new Profile(inputs[1], inputs[2]);
		double amount = generateAmount(inputs[3]);
		Date date = generateDate(inputs[4]);
		if (amount == -1 || date == null) {
			printMismatchError();
			return;
		}

		boolean status;
		if (inputs[0].equals("OM")) { // Open money market account.
			status = db.add(new MoneyMarket(holder, amount, date));
		} else {
			boolean hasSpecialRate;
			if (inputs[5].toLowerCase().equals("true") || inputs[5].toLowerCase().equals("false")) {
				hasSpecialRate = Boolean.parseBoolean(inputs[5]);
			} else {
				printMismatchError();
				return;
			}

			status = (inputs[0].equals("OC")) // Open checking account or saving account;
					? db.add(new Checking(holder, amount, date, hasSpecialRate))
					: db.add(new Saving(holder, amount, date, hasSpecialRate));
		}
		printOpenAccountResult(status);
	}

	private void closeAccount(String[] inputs) {
		Profile holder = new Profile(inputs[1], inputs[2]);
		Date randomDate = generateDate("1/1/2000"); // Use random date and random boolean.
		Account toBeClosed;
		switch (inputs[0]) {
			case "CC" -> toBeClosed = new Checking(holder, 0, randomDate, false);
			case "CS" -> toBeClosed = new Saving(holder, 0, randomDate, false);
			default -> toBeClosed = new MoneyMarket(holder, 0, randomDate);
		}
		// Check account type and profile; other fields are not checked!!!
		boolean status = db.remove(toBeClosed);
		printCloseAccountResult(status);
	}

	private void deposit(String[] inputs) {
		Profile holder = new Profile(inputs[1], inputs[2]);
		double amount = generateAmount(inputs[3]);
		if (amount == -1) {
			printMismatchError();
			return;
		}

		Date randomDate = generateDate("1/1/2000");
		Account toBeDeposited;
		switch (inputs[0]) {
			case "DC" -> toBeDeposited = new Checking(holder, 0, randomDate, false);
			case "DS" -> toBeDeposited = new Saving(holder, 0, randomDate, false);
			default -> toBeDeposited = new MoneyMarket(holder, 0, randomDate); // "DM"
		}
		boolean status = db.deposit(toBeDeposited, amount);
		printDepositResult(amount, status);
	}

	private void withdraw(String[] inputs) {
		Profile holder = new Profile(inputs[1], inputs[2]);
		double amount = generateAmount(inputs[3]);
		if (amount == -1) {
			printMismatchError();
			return;
		}
		Date randomDate = generateDate("1/1/2000");
		Account toBeWithdrew;
		switch (inputs[0]) {
			case "WC" -> toBeWithdrew = new Checking(holder, 0, randomDate, false);
			case "WS" -> toBeWithdrew = new Saving(holder, 0, randomDate, false);
			default -> toBeWithdrew = new MoneyMarket(holder, 0, randomDate); // "WM"
		}
		int status = db.withdrawal(toBeWithdrew, amount);
		printWithdrawResult(amount, status);
	}

	private void print(String[] inputs) {
		if (db.isEmpty()) {
			System.out.println("Database is empty.");
		} else {
			switch(inputs[0]) {
				case "PA" -> db.printAccounts();
				case "PD" -> db.printByDateOpen();
				default -> db.printByLastName(); // "PN"
			}
		}
	}

	private Date generateDate(String input) {
			String[] dateNums = input.split("/");
			int year = Integer.parseInt(dateNums[2]);
			int month = Integer.parseInt(dateNums[0]);
			int day = Integer.parseInt(dateNums[1]);
			Date date = new Date(year, month, day);
			if (!date.isValid()) {
				System.out.println(input + " is not a valid date!");
				return null;
			} else {
				return date;
			}
	}

	// How about amount < 0 ???!!!
	private double generateAmount(String amount) {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private void printMismatchError() {
		System.out.println("Input data type mismatch.");
	}

	private void printOpenAccountResult(boolean status) {
		if (status) {
			System.out.println("Account opened and added to the database.");
		} else {
			System.out.println("Account is already in the database.");
		}
	}

	private void printCloseAccountResult(boolean status) {
		if (status) {
			System.out.println("Account closed and removed from the database.");
		} else {
			System.out.println("Account does not exist.");
		}
	}

	private void printDepositResult(double amount, boolean status) {
		if (status) {
			System.out.printf("%.1f deposited to account.\n", amount);
		} else {
			System.out.println("Account does not exist.");
		}
	}

	private void printWithdrawResult(double amount, int status) {
		if (status == 0) {
			System.out.printf("%.2f withdrawn from account.\n", amount);
		} else if (status == 1) {
			System.out.println("Insufficient funds.");
		} else {
			System.out.println("Account does not exist.");
		}
	}
}
