package Application;

/**
 * JavaFX controller class.
 *
 * @author Biyun Wu, Anthony Triolo
 */

import Application.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {
	private final AccountDatabase db = new AccountDatabase(); // Model: shared between tabs.

	// Tab 1
	@FXML
	private RadioButton openAccountRadio, closeAccountRadio, savingRadio, checkingRadio, moneyMarketRadio;

	@FXML
	private TextField firstNameTF, lastName, balanceTF, monthTF, dayTF, yearTF;

	@FXML
	private CheckBox directDepositCheckBox, loyaltyCheckBox;

	@FXML
	private Label balanceLabel, dateLabel, checkLabel;

	@FXML
	private TextArea feedback;

	/** Enable balance, date and 1 of the 2 checkboxes.*/
	@FXML
	void selectOpenAccount() {
		disableBalanceAndDate(false);
		if(!moneyMarketRadio.isSelected()) {
			enableCheckBoxRow();
		}
	}

	/** Disable balance, date and checkboxes.*/
	@FXML
	void selectCloseAccount() {
		disableBalanceAndDate(true);
		disableCheckBoxRow();
	}

	/** Enable 1 of the 2 checkboxes as well as the label in this row.*/
	@FXML
	void enableCheckBoxRow() {
		if (openAccountRadio.isSelected()) {
			checkLabel.setDisable(false);
			if (checkingRadio.isSelected()) {
				directDepositCheckBox.setDisable(false);
				loyaltyCheckBox.setDisable(true);
			} else if (savingRadio.isSelected()) {
				directDepositCheckBox.setDisable(true);
				loyaltyCheckBox.setDisable(false);
			} else {
				directDepositCheckBox.setDisable(true);
				loyaltyCheckBox.setDisable(true);
			}
		}
	}

	/** Disable the row contains direct deposit and loyalty checkboxes.*/
	@FXML
	void disableCheckBoxRow() {
		checkLabel.setDisable(true);
		directDepositCheckBox.setDisable(true);
		loyaltyCheckBox.setDisable(true);
	}

	/**
	 * Disable or enable the labels and text fields of the balance and date row.
	 * @param bool true: disable; false: enable.
	 */
	private void disableBalanceAndDate(boolean bool) {
		balanceLabel.setDisable(bool);
		balanceTF.setDisable(bool);
		dateLabel.setDisable(bool);
		monthTF.setDisable(bool);
		dayTF.setDisable(bool);
		yearTF.setDisable(bool);
	}

	/** Clear text fields and text area in tab 1 (open and close account).*/
	@FXML
	void clearTab1 () {
		clearTextFieldsTab1();
		feedback.clear();
	}

	/** Clear text fields in tab 1.*/
	private void clearTextFieldsTab1() {
		firstNameTF.clear();
		lastName.clear();
		balanceTF.clear();
		monthTF.clear();
		dayTF.clear();
		yearTF.clear();
	}

	/** Open or close account based on the user input.*/
	@FXML
	void processAccount() {
		try {
			if (openAccountRadio.isSelected()) {
				Profile holder = getHolder(getStr(firstNameTF), getStr(lastName));
				double balance = getBalance(getStr(balanceTF));
				Date date = getDate(getStr(monthTF), getStr(dayTF), getStr(yearTF));
				openAccount(holder, balance, date);
			} else if (closeAccountRadio.isSelected()){
				closeAccount(getHolder(getStr(firstNameTF), getStr(lastName)));
			}
		} catch (Exception e) {
			feedback.appendText(e.getMessage()+"\n");
		} finally {
			clearTextFieldsTab1();
		}
	}

	/**
	 * Get rid of whitespaces in the string.
	 * @param tf Text Field contains string to be processes.
	 * @return string without whitespaces.
	 */
	private String getStr(TextField tf) { // this method is also used in tab 2.
		return tf.getText().replaceAll("\\s+", "");
	}

	/**
	 * Helper method to generate account holder's profile.
	 * @param first first name
	 * @param last last name
	 * @return Profile object.
	 */
	private Profile getHolder(String first, String last) { // This method is also used in Tab 2.
		if (first == null || last == null || first.length() == 0 || last.length() == 0) {
			throw new IllegalArgumentException("ERROR! First name and last name CANNOT be empty!!!");
		}
		return new Profile(first, last);
	}

	/**
	 * Helper method to convert balance in string to balance in double.
	 * @param s string which represents the new balance to be deposited.
	 * @return balance in double.
	 */
	private double getBalance(String s) {
		try {
			double balance = Double.parseDouble(s);
			if (balance < 0) throw new IllegalArgumentException("ERROR: negative number is not allowed!");
			return balance;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("ERROR: Balance filed requires integer or decimal input!");
		}
	}

	/**
	 * Helper method to generate account's open date.
	 * @param month integer month in string
	 * @param day integer day in string
	 * @param year integer year in string
	 * @return  Date object
	 */
	private Date getDate(String month, String day, String year) {
		try {
			Date date = new Date(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
			if (!date.isValid()) throw new IllegalArgumentException("ERROR: invalid date!");
			return date;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("ERROR: date fields require integer input!");
		}
	}

	/**
	 * Helper method to open an account.
	 * @param holder a Profile object.
	 * @param balance new balance in double
	 * @param date a Date project.
	 */
	private void openAccount(Profile holder, double balance, Date date) {
		boolean added = false;
		if (checkingRadio.isSelected()) {
			added = db.add(new Checking(holder, balance, date, directDepositCheckBox.isSelected()));
		} else if (savingRadio.isSelected()) {
			added = db.add(new Savings(holder, balance, date, directDepositCheckBox.isSelected()));
		} else if (moneyMarketRadio.isSelected()) {
			added = db.add(new MoneyMarket(holder, balance, date));
		}
		String message = added? "Added to database!" : "ERROR: account already exist!";
		feedback.appendText(message + "\n");
	}

	/**
	 * Helper method to close an account.
	 * @param holder a Profile object which contains the account holder's info.
	 */
	private void closeAccount(Profile holder) {
		boolean removed = false; // if found, delete; else, report error.
		if (checkingRadio.isSelected()) {
			removed = db.remove(new Checking(holder, 0, null, false));
		} else if (savingRadio.isSelected()) {
			removed = db.remove(new Savings(holder, 0, null, false));
		} else if (moneyMarketRadio.isSelected()) {
			removed = db.remove(new MoneyMarket(holder, 0, null));
		}
		String message = removed ? "Account Removed!" : "ERROR: account doesn't exist!";
		feedback.appendText(message + "\n");
	}

	// Tab 2: deposit and withdraw
	@FXML
	private RadioButton depositRadio, withdrawRadio, checkingRadioDW, savingRadioDW, moneyMarketRadioDW;

	@FXML
	private TextField firstNameTextField, lastNameTextField, amountTextFiled;

	/** Clear text fields and the text area in Tab 2 (deposit and withdraw).*/
	@FXML
	void clearTab2() {
		clearTextFieldsTab2();
		feedback.clear();
	}

	/** Clear text fields in tab 2.*/
	private void clearTextFieldsTab2() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		amountTextFiled.clear();
	}

	/** Deposit or withdraw based on user input.*/
	@FXML
	void doTransaction() {
		try {
			Profile holder = getHolder(getStr(firstNameTextField), getStr(lastNameTextField));
			double amount = Double.parseDouble(getStr(amountTextFiled));
			if (depositRadio.isSelected()) { // deposit
				deposit(holder, amount);
			} else if (withdrawRadio.isSelected()) { // withdraw
				withdraw(holder, amount);
			}
		} catch (NumberFormatException e) {
			feedback.appendText("ERROR: invalid amount, only numeric value is accepted!\n");
		} catch (Exception e) {
			feedback.appendText(e.getMessage() + "\n");
		} finally {
			clearTextFieldsTab2();
		}
	}

	/**
	 * Helper method to deposit money into account.
	 * @param holder account holder's Profile object.
	 * @param amount amount in double to be deposited.
	 */
	private void deposit(Profile holder, double amount) {
		boolean isSuccess = false;
		if (checkingRadioDW.isSelected()) {
			isSuccess = db.deposit(new Checking(holder, 0, null, false), amount);
		} else if (savingRadioDW.isSelected()) {
			isSuccess = db.deposit(new Savings(holder, 0, null, false), amount);
		} else if (moneyMarketRadioDW.isSelected()) {
			isSuccess = db.deposit(new MoneyMarket(holder, 0, null), amount);
		}
		String message = isSuccess ? "Deposited $" + amount : "ERROR: account does not exist!";
		feedback.appendText(message + "\n");
	}

	/**
	 * Helper method to withdraw money from account.
	 * @param holder account holder's Profile object.
	 * @param amount amount in double to be withdrew.
	 */
	private void withdraw(Profile holder, double amount) {
		int isSuccess = -1;
		if (checkingRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new Checking(holder, 0, null, false), amount);
		} else if (savingRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new Savings(holder, 0, null, false), amount);
		} else if (moneyMarketRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new MoneyMarket(holder, 0, null), amount);
		}
		String message = isSuccess == 0 ? "Withdrew $" + amount : "ERROR: account does not exist or no sufficient fund!";
		feedback.appendText(message + "\n");
	}

	// Tab 3: Account Database
	/** Import data from text file to the database.*/
	@FXML
	void importFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose File to Import");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
		File sourceFile = chooser.showOpenDialog(stage);
		try {
			if (sourceFile == null) throw new IllegalArgumentException("no source file selected!");
			feedback.appendText("importing...\n");
			BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
			reader.lines().forEach(line -> {
				feedback.appendText(line + "\n"); // for test
				String[] params = line.split(",");
				Profile holder = new Profile(params[1], params[2]);
				double balance = Double.parseDouble(params[3]);
				Date date = new Date(params[4]); // need to verify date string here?
				switch (line.charAt(0)) {
					case 'C' -> db.add(new Checking(holder, balance, date, Boolean.parseBoolean(params[5])));
					case 'S' -> db.add(new Savings(holder, balance, date, Boolean.parseBoolean(params[5])));
					case 'M' -> db.add(new MoneyMarket(holder, balance, date, Integer.parseInt(params[5])));
					default -> throw new IllegalArgumentException("Invalid line in file: " + line);
				}
			});
			reader.close();
			feedback.appendText("finished!\n");
		} catch (FileNotFoundException e) {
			feedback.appendText("Failed opening file: " + e.getMessage() + "\n");
		} catch (IllegalArgumentException e) {
			feedback.appendText(e.getMessage() + "\n");
		} catch (Exception e) {
			feedback.appendText("import stopped!" + "\n");
		}
	}

	/** Export data from database to text file.*/
	@FXML
	void exportFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose Target File to Export");
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
		File targetFile = chooser.showSaveDialog(stage);
		try {
			if (targetFile == null) throw new IllegalArgumentException("export cancelled!");
			BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile));
			writer.append(db.export());
			writer.close();
			feedback.appendText("export finished!\n");
		} catch (IllegalArgumentException e) {
			feedback.appendText(e.getMessage() + "\n");
		} catch (Exception e) {
			feedback.appendText("export stopped: " + e.getMessage() + "\n");
		}
	}

	/** Display accounts in the database to the user interface.*/
	@FXML
	void printAccounts() {
		feedback.appendText(db.printAccounts() + "\n");
	}

	/** Display accounts in the database to the user interface by the order of open date.*/
	@FXML
	void printByDate() {
		feedback.appendText(db.printByDateOpen() + "\n");
	}

	/** Display accounts in the database to the user interface by the order of account holder's last name.*/
	@FXML
	void printByLastName() {
		feedback.appendText(db.printByLastName() + "\n");
	}

	/** Clear text fields and the text area in Tab 3 (import/export and print).*/
	@FXML
	void clearTab3() {
		feedback.clear();
	}

	/** Initialize the user interface through grouping radio buttons by category and setting default states*/
	@FXML
	public void initialize() {
		// initialize tab 1
		ToggleGroup serviceTG = new ToggleGroup(); // service toggle group.
		openAccountRadio.setToggleGroup(serviceTG);
		closeAccountRadio.setToggleGroup(serviceTG);
		ToggleGroup accountTG = new ToggleGroup(); // account category toggle group.
		checkingRadio.setToggleGroup(accountTG);
		savingRadio.setToggleGroup(accountTG);
		moneyMarketRadio.setToggleGroup(accountTG);
		// set default state
		openAccountRadio.setSelected(true);
		checkingRadio.setSelected(true);
		loyaltyCheckBox.setDisable(true);

		// initialize tab 2
		ToggleGroup transactionGroupDW = new ToggleGroup(); // transaction toggle group.
		depositRadio.setToggleGroup(transactionGroupDW);
		withdrawRadio.setToggleGroup(transactionGroupDW);
		ToggleGroup accountGroupDW = new ToggleGroup(); // account category toggle group.
		checkingRadioDW.setToggleGroup(accountGroupDW);
		savingRadioDW.setToggleGroup(accountGroupDW);
		moneyMarketRadioDW.setToggleGroup(accountGroupDW);
		// set default state
		depositRadio.setSelected(true);
		checkingRadioDW.setSelected(true);
	}
}