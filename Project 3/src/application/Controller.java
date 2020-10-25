package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controller {

	private final AccountDatabase db = new AccountDatabase();
	private final DecimalFormat df = new DecimalFormat("0.00");

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	@FXML
	private RadioButton savingsBtn;

	@FXML
	private ToggleGroup toggleAccount;

	@FXML
	private RadioButton checkingBtn;

	@FXML
	private RadioButton moneyBtn;

	@FXML
	private CheckBox directBtn;

	@FXML
	private CheckBox loyalBtn;

	@FXML
	private Button openBtn;

	@FXML
	private Button closeBtn;

	@FXML
	private TextField amountField;

	@FXML
	private TextField dateMonth;

	@FXML
	private TextField dateDay;

	@FXML
	private TextField dateYear;

	@FXML
	private TextArea outputArea;

	@FXML
	private MenuItem importBtn;

	@FXML
	private MenuItem exportBtn;

	@FXML
	private MenuItem printBtn;

	@FXML
	private MenuItem printByDateBtn;

	@FXML
	private MenuItem printByNameBtn;

	@FXML
	private TextField firstNameField2;

	@FXML
	private TextField lastNameField2;

	@FXML
	private RadioButton moneyBtn2;

	@FXML
	private ToggleGroup toggleAccount2;

	@FXML
	private RadioButton checkingBtn2;

	@FXML
	private RadioButton savingsBtn2;

	@FXML
	private Button clearBtn2;

	@FXML
	private TextField amountField2;

	@FXML
	private Button depositBtn;

	@FXML
	private Button withdrawBtn;

	@FXML
	void enableDirectDeposit(ActionEvent event) {
		directBtn.setDisable(false);
		loyalBtn.setDisable(true);
		loyalBtn.setSelected(false);
	}

	@FXML
	void enableLoyal(ActionEvent event) {
		loyalBtn.setDisable(false);
		directBtn.setDisable(true);
		directBtn.setSelected(false);
	}

	@FXML
	void disableOptions(ActionEvent event) {
		loyalBtn.setDisable(true);
		loyalBtn.setSelected(false);
		directBtn.setDisable(true);
		directBtn.setSelected(false);
	}

	@FXML
	void clearFields(ActionEvent event) {
		clearAll();
	}

	void clearAll() {
		firstNameField.clear();
		lastNameField.clear();
		firstNameField2.clear();
		lastNameField2.clear();
		dateMonth.clear();
		dateDay.clear();
		dateYear.clear();
		amountField.clear();
		amountField2.clear();
		Toggle toggled = toggleAccount.getSelectedToggle();
		if (toggled != null) {
			toggled.setSelected(false);
		}
		Toggle toggled2 = toggleAccount2.getSelectedToggle();
		if (toggled2 != null) {
			toggled2.setSelected(false);
		}
		directBtn.setSelected(false);
		loyalBtn.setDisable(true);
		loyalBtn.setSelected(false);
		directBtn.setDisable(true);
		directBtn.setSelected(false);
	}

	@FXML
	void closeAccount(ActionEvent event) {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
			outputArea.appendText("Please enter a valid first name and last name.\n");
			firstNameField.clear();
			lastNameField.clear();
			return;
		}
		Profile holder = new Profile(firstName, lastName);
		Account toBeClosed = null;
		if (toggleAccount.getSelectedToggle() == checkingBtn) {
			toBeClosed = new Checking(holder, 0, null, false);
		} else if (toggleAccount.getSelectedToggle() == savingsBtn) {
			toBeClosed = new Savings(holder, 0, null, false);
		} else if (toggleAccount.getSelectedToggle() == moneyBtn) {
			toBeClosed = new MoneyMarket(holder, 0, null);
		} else {
			outputArea.appendText("Please select an account type.\n");
			return;
		}
		if (db.remove(toBeClosed)) {
			outputArea.appendText("Account closed and removed from the database.\n");
			clearAll();
		} else {
			outputArea.appendText("Account does not exist.\n");
			clearAll();
		}
	}

	@FXML
	void openAccount(ActionEvent event) {
		Date openDate = null;
		String firstName = firstNameField.getText().replaceAll("\\s", "");
		String lastName = lastNameField.getText().replaceAll("\\s", "");
		if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
			outputArea.appendText("Please enter a valid first name and last name.\n");
			firstNameField.clear();
			lastNameField.clear();
			return;
		}
		Profile newProfile = new Profile(firstName, lastName);
		String date = dateMonth.getText() + "/" + dateDay.getText() + "/" + dateYear.getText();
		try {
			openDate = new Date(date);
		} catch (NumberFormatException e) {
			outputArea.appendText("Date may only include integers.\n");
			dateDay.clear();
			dateMonth.clear();
			dateYear.clear();
			return;
		} catch (ArrayIndexOutOfBoundsException e) {
			outputArea.appendText("Please enter a valid date.\n");
			dateDay.clear();
			dateMonth.clear();
			dateYear.clear();
			return;
		}
		if (!openDate.isValid()) {
			outputArea.appendText("Invalid Date.\n");
			dateDay.clear();
			dateMonth.clear();
			dateYear.clear();
			return;
		}
		if (toggleAccount.getSelectedToggle() == checkingBtn) {
			openChecking(newProfile, openDate);
		} else if (toggleAccount.getSelectedToggle() == savingsBtn) {
			openSavings(newProfile, openDate);
		} else if (toggleAccount.getSelectedToggle() == moneyBtn) {
			openMoneyMarket(newProfile, openDate);
		} else {
			outputArea.appendText("Please select an account type.\n");
		}
	}

	void openChecking(Profile profile, Date date) {
		double amount = 0;
		try {
			amount = Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			outputArea.appendText("Amount must be a number.\n");
			amountField.clear();
			return;
		}
		Account newChecking = new Checking(profile, amount, date, directBtn.isSelected());
		if (db.add(newChecking)) {
			outputArea.appendText("Account opened and added to the database.\n");
			clearAll();
			return;
		} else {
			outputArea.appendText("Account is already in the database.\n");
			clearAll();
		}
	}

	void openSavings(Profile profile, Date date) {
		double amount = 0;
		try {
			amount = Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			outputArea.appendText("Amount must be a number.\n");
			amountField.clear();
			return;
		}
		Account newSavings = new Savings(profile, amount, date, loyalBtn.isSelected());
		if (db.add(newSavings)) {
			outputArea.appendText("Account opened and added to the database.\n");
			clearAll();
		} else {
			outputArea.appendText("Account is already in the database.\n");
			clearAll();
		}
	}

	void openMoneyMarket(Profile profile, Date date) {
		double amount = 0;
		try {
			amount = Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			outputArea.appendText("Amount must be a number.\n");
			amountField.clear();
			return;
		}
		Account newMoneyMarket = new MoneyMarket(profile, amount, date);
		if (db.add(newMoneyMarket)) {
			outputArea.appendText("Account opened and added to the database.\n");
			clearAll();
		} else {
			outputArea.appendText("Account is already in the database.\n");
			clearAll();
		}
	}

	@FXML
	void exportFile(ActionEvent event) {
		outputArea.appendText("Exporting to file...\n");
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Target File for the Export");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Stage stage = new Stage();
			File targetFile = chooser.showSaveDialog(stage); // get the reference of the target file
			targetFile.createNewFile();
			FileWriter fileWriter = new FileWriter(targetFile.getAbsoluteFile());
			Account[] accounts = db.getAccounts();
			for (int i = 0; i < db.getSize(); i++) {
				String firstName = accounts[i].getProfile().getFirstName();
				String lastName = accounts[i].getProfile().getLastName();
				String amount = String.valueOf(accounts[i].getBalance());
				String date = accounts[i].getDate().toString();
				boolean isLoyalOrDesposit = accounts[i].loyalDepsoitOrWithdraws() == 0 ? false : true;
				if (accounts[i] instanceof Checking) {
					fileWriter.append("C," + firstName + "," + lastName + "," + amount + "," + date + ","
							+ isLoyalOrDesposit + "\n");
				} else if (accounts[i] instanceof Savings) {
					fileWriter.append("S," + firstName + "," + lastName + "," + amount + "," + date + ","
							+ isLoyalOrDesposit + "\n");
				} else {
					fileWriter.append("M," + firstName + "," + lastName + "," + amount + "," + date + ","
							+ accounts[i].loyalDepsoitOrWithdraws() + "\n");
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			outputArea.appendText("An error occurred exporting the file.\n");
		}
		outputArea.appendText("Exported to file.\n");
	}

	@FXML
	void importFile(ActionEvent event) {
		outputArea.appendText("Importing file...\n");
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Source File for the Import");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Stage stage = new Stage();
			File sourceFile = chooser.showOpenDialog(stage); 
			Scanner sc = null;
			try {
				sc = new Scanner(sourceFile);
			} catch (FileNotFoundException e) {
				outputArea.appendText("File not found.\n");
			}
			while (sc.hasNextLine()) {
				try {
					String line = sc.nextLine();
					String[] tokens = line.split(",");
					Profile profile = new Profile(tokens[1], tokens[2]);
					Date date = new Date(tokens[4]);
					if (!date.isValid()) {
						outputArea.appendText("Invalid Date.");
						continue;
					}
					if (tokens[0].equals("C")) {
						Account newChecking = new Checking(profile, Double.parseDouble(tokens[3]), date,
								Boolean.parseBoolean(tokens[5]));
						db.add(newChecking);
					} else if (tokens[0].equals("S")) {
						Account newSavings = new Savings(profile, Double.parseDouble(tokens[3]), date,
								Boolean.parseBoolean(tokens[5]));
						db.add(newSavings);
					} else {
						MoneyMarket newMoneyMarket = new MoneyMarket(profile, Double.parseDouble(tokens[3]), date);
						newMoneyMarket.setWithdrawals(Integer.parseInt(tokens[5]));
						db.add(newMoneyMarket);
					} 
				} catch (NumberFormatException e) {
					outputArea.appendText("Invalid parameter.\n");
				} catch (NullPointerException e) {
					outputArea.appendText("Invalid parameter.\n");
				}
			}
		sc.close();
		outputArea.appendText("Imported file.\n");
	}

	@FXML
	void printAccounts(ActionEvent event) {
		outputArea.appendText(db.printAccounts());
	}

	@FXML
	void printByDate(ActionEvent event) {
		outputArea.appendText(db.printByDateOpen());
	}

	@FXML
	void printByName(ActionEvent event) {
		outputArea.appendText(db.printByLastName());
	}

	@FXML
	void withdraw(ActionEvent event) {
		String firstName = firstNameField2.getText().replaceAll("\\s", "");
		String lastName = lastNameField2.getText().replaceAll("\\s", "");
		if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
			outputArea.appendText("Please enter a valid first name and last name.\n");
			firstNameField2.clear();
			lastNameField2.clear();
			return;
		}
		Profile holder = new Profile(firstName, lastName);
		Account toWithdraw = null;
		if (toggleAccount2.getSelectedToggle() == checkingBtn2) {
			toWithdraw = new Checking(holder, 0, null, false);
		} else if (toggleAccount2.getSelectedToggle() == savingsBtn2) {
			toWithdraw = new Savings(holder, 0, null, false);
		} else if (toggleAccount2.getSelectedToggle() == moneyBtn2) {
			toWithdraw = new MoneyMarket(holder, 0, null);
		} else {
			outputArea.appendText("Please select an account type.\n");
			return;
		}
		double amount = 0;
		try {
			amount = Double.parseDouble(amountField2.getText());
		} catch (NumberFormatException e) {
			outputArea.appendText("Amount must be a number.\n");
			amountField.clear();
			return;
		}
		if (db.withdrawal(toWithdraw, amount) == 0) {
			outputArea.appendText(df.format(amount) + " withdrawn from account.\n");
			clearAll();
		} else if (db.withdrawal(toWithdraw, amount) == 1) {
			outputArea.appendText("Insufficient funds.\n");
			clearAll();
		} else {
			outputArea.appendText("Account does not exist.\n");
			clearAll();
		}
	}

	@FXML
	void deposit(ActionEvent event) {
		String firstName = firstNameField2.getText().replaceAll("\\s", "");
		String lastName = lastNameField2.getText().replaceAll("\\s", "");
		if (!firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
			outputArea.appendText("Please enter a valid first name and last name.\n");
			firstNameField2.clear();
			lastNameField2.clear();
			return;
		}
		Profile holder = new Profile(firstName, lastName);
		Account toWithdraw = null;
		if (toggleAccount2.getSelectedToggle() == checkingBtn2) {
			toWithdraw = new Checking(holder, 0, null, false);
		} else if (toggleAccount2.getSelectedToggle() == savingsBtn2) {
			toWithdraw = new Savings(holder, 0, null, false);
		} else if (toggleAccount2.getSelectedToggle() == moneyBtn2) {
			toWithdraw = new MoneyMarket(holder, 0, null);
		} else {
			outputArea.appendText("Please select an account type.\n");
			return;
		}
		double amount = 0;
		try {
			amount = Double.parseDouble(amountField2.getText());
		} catch (NumberFormatException e) {
			outputArea.appendText("Amount must be a number.\n");
			amountField.clear();
			return;
		}
		if (db.deposit(toWithdraw, amount)) {
			outputArea.appendText(df.format(amount) + " deposited to account.\n");
			clearAll();
		} else {
			outputArea.appendText("Account does not exist.\n");
			clearAll();
		}
	}

}
