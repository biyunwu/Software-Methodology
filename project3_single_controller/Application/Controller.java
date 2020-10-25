package Application;

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

	@FXML
	void selectOpenAccount() {
		toggleBalanceAndDate(false);
		if(!moneyMarketRadio.isSelected()) {
			hideCheckBoxes(false);
			directDepositCheckBox.setDisable(savingRadio.isSelected());
			loyaltyCheckBox.setDisable(checkingRadio.isSelected());
		}
	}

	@FXML
	void selectCloseAccount() {
		toggleBalanceAndDate(true);
		hideCheckBoxes(true);
	}

	@FXML
	void enableCheckBoxRow() {
		hideCheckBoxes(!openAccountRadio.isSelected());
		if (openAccountRadio.isSelected()) { // toggle checkbox when opening account.
			if (checkingRadio.isSelected()) {
				directDepositCheckBox.setDisable(false);
				loyaltyCheckBox.setDisable(true);
			} else if (savingRadio.isSelected()) {
				directDepositCheckBox.setDisable(true);
				loyaltyCheckBox.setDisable(false);
			}
		}
	}

	@FXML
	void disableCheckBoxRow() {
		hideCheckBoxes(true);
	}

	private void hideCheckBoxes(boolean bool) {
		checkLabel.setDisable(bool);
		directDepositCheckBox.setDisable(bool);
		loyaltyCheckBox.setDisable(bool);
	}

	@FXML
	void clearTab1 () {
		firstNameTF.clear();
		lastName.clear();
		balanceTF.clear();
		monthTF.clear();
		dayTF.clear();
		yearTF.clear();
		feedback.clear();
	}

	@FXML
	void processAccount() {
		try {
			if (openAccountRadio.isSelected()) {
				Profile holder = getHolder(firstNameTF.getText(), lastName.getText());
				double balance = getBalance(balanceTF.getText());
				Date date = getDate(monthTF.getText(), dayTF.getText(), yearTF.getText());
				openAccount(holder, balance, date);
			} else if (closeAccountRadio.isSelected()){
				closeAccount(getHolder(firstNameTF.getText(), lastName.getText()));
			}
		} catch (Exception e) {
			feedback.setText(e.toString());
		}
	}

	private Profile getHolder(String first, String last) { // This method is also used in Tab 2.
		if (first == null || last == null || first.length() == 0 || last.length() == 0) {
			throw new IllegalArgumentException("ERROR! First name and last name CANNOT be empty!!!");
		}
		return new Profile(first, last);
	}

	private double getBalance(String s) {
		try {
			double balance = Double.parseDouble(balanceTF.getText());
			if (balance < 0) throw new IllegalArgumentException("ERROR: negative number is not allowed!");
			return balance;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("ERROR: Balance filed requires integer or decimal input!");
		}
	}

	private Date getDate(String month, String day, String year) {
		try {
			Date date = new Date(Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(year));
			if (!date.isValid()) throw new IllegalArgumentException("ERROR: invalid date!");
			return date;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("ERROR: date fields require integer input!");
		}
	}

	private void openAccount(Profile holder, double balance, Date date) {
		boolean added = false;
		if (checkingRadio.isSelected()) {
			added = db.add(new Checking(holder, balance, date, directDepositCheckBox.isSelected()));
		} else if (savingRadio.isSelected()) {
			added = db.add(new Savings(holder, balance, date, directDepositCheckBox.isSelected()));
		} else if (moneyMarketRadio.isSelected()) {
			added = db.add(new MoneyMarket(holder, balance, date));
		}
		feedback.setText(added? "Added to database!" : "ERROR: account already Exist!");
	}

	private void closeAccount(Profile holder) {
		boolean removed = false; // if found, delete; else, report error.
		if (checkingRadio.isSelected()) {
			removed = db.remove(new Checking(holder, 0, null, false));
		} else if (savingRadio.isSelected()) {
			removed = db.remove(new Savings(holder, 0, null, false));
		} else if (moneyMarketRadio.isSelected()) {
			removed = db.remove(new MoneyMarket(holder, 0, null));
		}
		feedback.setText(removed ? "Account Removed!" : "ERROR: account doesn't exist!");
	}

	private void toggleBalanceAndDate(boolean bool) {
		balanceLabel.setDisable(bool);
		balanceTF.setDisable(bool);
		dateLabel.setDisable(bool);
		monthTF.setDisable(bool);
		dayTF.setDisable(bool);
		yearTF.setDisable(bool);
	}

	// Tab 2: deposit and withdraw
//	private final ToggleGroup transactionGroupDW = new ToggleGroup(), accountGroupDW = new ToggleGroup();

	@FXML
	private RadioButton depositRadio, withdrawRadio, checkingRadioDW, savingRadioDW, moneyMarketRadioDW;

	@FXML
	private TextField firstNameTextField, lastNameTextField, amountTextFiled;

	@FXML
	void clearTab2() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		amountTextFiled.clear();
		feedback.clear();
	}

	@FXML
	void doTransaction() {
		try {
			Profile holder = getHolder(firstNameTextField.getText(), lastNameTextField.getText());
			double amount = Double.parseDouble(amountTextFiled.getText());
			if (depositRadio.isSelected()) { // deposit
				deposit(holder, amount);
			} else if (withdrawRadio.isSelected()) { // withdraw
				withdraw(holder, amount);
			}
		} catch (NumberFormatException e) {
			feedback.setText("ERROR: invalid amount, only numeric value is accepted!");
		} catch (Exception e) {
			feedback.setText(e.toString());
		}
	}

	private void deposit(Profile holder, double amount) {
		boolean isSuccess = false;
		if (checkingRadioDW.isSelected()) {
			isSuccess = db.deposit(new Checking(holder, 0, null, false), amount);
		} else if (savingRadioDW.isSelected()) {
			isSuccess = db.deposit(new Savings(holder, 0, null, false), amount);
		} else if (moneyMarketRadioDW.isSelected()) {
			isSuccess = db.deposit(new MoneyMarket(holder, 0, null), amount);
		}
		feedback.setText(isSuccess ? "Deposited $" + amount : "ERROR: account does not exist!");
	}

	private void withdraw(Profile holder, double amount) {
		int isSuccess = -1;
		if (checkingRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new Checking(holder, 0, null, false), amount);
		} else if (savingRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new Savings(holder, 0, null, false), amount);
		} else if (moneyMarketRadioDW.isSelected()) {
			isSuccess = db.withdrawal(new MoneyMarket(holder, 0, null), amount);
		}
		feedback.setText(isSuccess == 0 ? "Withdrew $" + amount : "ERROR: account does not exist or no sufficient fund!");
	}

	// Tab 3: Account Database
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
			feedback.setText("importing...\n");
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
			feedback.setText("Failed opening file!\n" + e.toString());
		} catch (IllegalArgumentException e) {
			feedback.setText(e.toString());
		} catch (Exception e) {
			feedback.setText("import stopped!");
		}
	}

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
			feedback.setText("export finished!");
		} catch (IllegalArgumentException e) {
			feedback.setText(e.toString());
		} catch (Exception e) {
			feedback.setText("export stopped: \n" + e.toString());
		}
	}

	@FXML
	void printAccounts() {
		feedback.setText(db.printAccounts());
	}

	@FXML
	void printByDate() {
		feedback.setText(db.printByDateOpen());
	}

	@FXML
	void printByLastName() {
		feedback.setText(db.printByLastName());
	}

	@FXML
	void clearTab3() {
		feedback.clear();
	}


	@FXML
	public void initialize() { // initialize toggle groups and set components' default properties.
		// initialize tab 1
		ToggleGroup serviceTG = new ToggleGroup(); // service toggle group.
		openAccountRadio.setToggleGroup(serviceTG);
		closeAccountRadio.setToggleGroup(serviceTG);
		ToggleGroup accountTG = new ToggleGroup(); // account category toggle group.
		checkingRadio.setToggleGroup(accountTG);
		savingRadio.setToggleGroup(accountTG);
		moneyMarketRadio.setToggleGroup(accountTG);
		// set default value
		openAccountRadio.setSelected(true);
		checkingRadio.setSelected(true);
		directDepositCheckBox.setSelected(false);
		loyaltyCheckBox.setDisable(true);

		// initialize tab 2
		ToggleGroup transactionGroupDW = new ToggleGroup(); // transaction toggle group.
		depositRadio.setToggleGroup(transactionGroupDW);
		withdrawRadio.setToggleGroup(transactionGroupDW);
		ToggleGroup accountGroupDW = new ToggleGroup(); // account category toggle group.
		checkingRadioDW.setToggleGroup(accountGroupDW);
		savingRadioDW.setToggleGroup(accountGroupDW);
		moneyMarketRadioDW.setToggleGroup(accountGroupDW);
		// set default value
		depositRadio.setSelected(true);
		checkingRadioDW.setSelected(true);
	}
}