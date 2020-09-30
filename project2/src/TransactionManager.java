import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
/* @author Biyun Wu, Anthony Triolo */

public class TransactionManager {
	private final AccountDatabase db = new AccountDatabase();
	private String command;
	private String firstName;
	private String lastName;
	private double newBal;
	private double depositAmount;
	private double withdrawAmount;
	private Date date;
	private boolean isLoyal;
	private boolean isDirect;
	DecimalFormat df = new DecimalFormat("0.00");
	
	public void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Transaction processing starts.....");
		while(sc.hasNext()) {
			try {
				command = sc.next();
			} catch (InputMismatchException e) {
				System.out.println("Input data type mismatch.");
			}
			switch(command) {
				case "OC", "OS", "OM" -> {
					try {
						firstName = sc.next();
						lastName = sc.next();
						newBal = sc.nextDouble();
						date = new Date(sc.next());
						if(date.isValid() == false) {
							System.out.println(date.toString() + " is not a valid date!");
							sc.nextLine();
							break;
						}
					}catch(InputMismatchException e) {
						System.out.println("Input data type mismatch.");
						sc.nextLine();
						break;
					}
					catch(NumberFormatException e) {
						System.out.println("Input data type mismatch.");
						sc.nextLine();
						break;
					}
					openAccount(sc); 
				}
				case "CC", "CS", "CM" -> {
					try {
						firstName = sc.next();
						lastName = sc.next();
					}catch(InputMismatchException e) {
						System.out.println("Input data type mismatch.");
						sc.nextLine();
						break;
					}
					closeAccount();
				}
				case "DC", "DS", "DM" -> {
					try {
						firstName = sc.next();
						lastName = sc.next();
						depositAmount = sc.nextDouble();
					}catch(InputMismatchException e) {
						System.out.println("Input data type mismatch.");
						sc.nextLine();
						break;
					}
					depositFunds();
				}
				case "WC", "WS", "WM" -> {
					try {
						firstName = sc.next();
						lastName = sc.next();
						withdrawAmount = sc.nextDouble();
					}catch(InputMismatchException e) {
						System.out.println("Input data type mismatch.");
						sc.nextLine();
						break;
					}
					withdrawFunds();
				}
				case "PA", "PD", "PN" -> print();
				case "Q" -> {
					System.out.println("Transaction processing completed.");
					return;
				}
				default -> {
					System.out.println("Command \'" + command  + "\' not supported!");
					sc.nextLine();
				}
			}
		}
	}


	private void openAccount(Scanner sc) {
		Profile holder = new Profile(firstName, lastName);
		Account tobeOpened;
		switch (command) {
			case "OC" -> {
				try {
					isDirect = sc.nextBoolean();
				}catch(InputMismatchException e) {
					System.out.println("Input data type mismatch.");
					sc.nextLine();
					return;
				}
				tobeOpened = new Checking(holder, newBal, date, isDirect);
			}
			case "OS" -> {
				try {
					isLoyal = sc.nextBoolean();
				}catch(InputMismatchException e) {
					System.out.println("Input data type mismatch.");
					sc.nextLine();
					return;
				}
				tobeOpened = new Savings(holder, newBal, date, isLoyal);
				
			}
			default -> tobeOpened = new MoneyMarket(holder, newBal, date); // "OM"
		}
		if(db.add(tobeOpened)) {
			System.out.println("Account opened and added to the database.");
		}else {
			System.out.println("Account is already in the database.");
		}
		
		
	}
	
	private void closeAccount() {
		Profile holder = new Profile(firstName, lastName);
		Account toBeClosed;
		switch (command) {
			case "CC" -> toBeClosed = new Checking(holder, 0, null, false);
			case "CS" -> toBeClosed = new Savings(holder, 0, null, false);
			default -> toBeClosed = new MoneyMarket(holder, 0, null); // "CM"
		}
		if(db.remove(toBeClosed)) {
			System.out.println("Account closed and removed from the database.");
		}else {
			System.out.println("Account does not exist.");
		}
	}
	
	private void depositFunds() {
		Profile holder = new Profile(firstName, lastName);
		Account toBeDeposited;
		switch (command) {
			case "DC" -> toBeDeposited = new Checking(holder, 0, null, false);
			case "DS" -> toBeDeposited = new Savings(holder, 0, null, false);
			default -> toBeDeposited = new MoneyMarket(holder, 0, null); // "DM"
		}
		if(db.deposit(toBeDeposited, depositAmount)) {
			System.out.println(df.format(depositAmount) + " deposited to account.");
		}else {
			System.out.println("Account does not exist.");
		}
	}
	
	private void withdrawFunds() {
		Profile holder = new Profile(firstName, lastName);
		Account toBeWithdrawn;
		switch (command) {
			case "WC" -> toBeWithdrawn = new Checking(holder, 0, null, false);
			case "WS" -> toBeWithdrawn = new Savings(holder, 0, null, false);
			default -> toBeWithdrawn = new MoneyMarket(holder, 0, null); // "WM"
		}
		if(db.withdrawal(toBeWithdrawn, withdrawAmount) == 0) {
			System.out.println(df.format(withdrawAmount) + " withdrawn from account.");
		}else if (db.withdrawal(toBeWithdrawn, withdrawAmount) == 1){
			System.out.println("Insufficient funds.");
		}else {
			System.out.println("Account does not exist.");
		}
	}
	
	private void print() {
		switch(command) {
			case "PA" -> db.printAccounts();
			case "PD" -> db.printByDateOpen();
			default -> db.printByLastName();
		}
	}
}