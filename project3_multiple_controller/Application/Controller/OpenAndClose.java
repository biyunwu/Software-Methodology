package Application.Controller;

import Application.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OpenAndClose {
	private final AccountDatabase db;
	private final ToggleGroup serviceTG, accountTG;
	private boolean isOpenAccount;
	private int accountType; // 1: checking, 2: savings, 3: money market

	public OpenAndClose(AccountDatabase db) {
		this.db = db; // Model
		serviceTG = new ToggleGroup();
		accountTG = new ToggleGroup();
		// set default values.
		isOpenAccount = true;
		accountType = 1;
	}

	@FXML
	private RadioButton openAccountRadio, closeAccountRadio, savingRadio, checkingRadio, moneyMarketRadio;

	@FXML
	private TextField firstNameTF, lastName, newBalance, monthTF, dayTF, yearTF;

	@FXML
	private CheckBox policyCheckBox;

	@FXML
	private Label balanceLabel, dateLabel, checkLabel;

	@FXML
	private TextArea feedback;

	@FXML
	void selectOpenAccount() {
		toggleBalanceAndDate(false);
		if(!moneyMarketRadio.isSelected()) hideCheckBox(false);
	}

	@FXML
	void selectCloseAccount() {
		toggleBalanceAndDate(true);
		hideCheckBox(true);
	}

	@FXML
	void enableCheckBoxRow() {
		if(openAccountRadio.isSelected()) hideCheckBox(false);
		if (accountType == 1) {
			policyCheckBox.setText("Direct Deposit");
		} else if (accountType == 2) {
			policyCheckBox.setText("Loyal Customer");
		}
	}

	@FXML
	void disableCheckBoxRow() {
		checkLabel.setDisable(true);
		policyCheckBox.setDisable(true);
	}

	private void hideCheckBox(boolean bool) {
		checkLabel.setDisable(bool);
		policyCheckBox.setDisable(bool);
	}

	@FXML
	void clearTab1 () {
		firstNameTF.clear();
		lastName.clear();
		newBalance.clear();
		monthTF.clear();
		dayTF.clear();
		yearTF.clear();
		feedback.clear();
	}

	@FXML
	void processAccount() {
		if (isOpenAccount) {
			try { // check whether balance and date strings are valid.
				Profile holder = getHolder(firstNameTF.getText(), lastName.getText());
				double balance = Double.parseDouble(newBalance.getText());
				if (balance < 0) throw new IllegalArgumentException("");
				Date date = new Date(Integer.parseInt(monthTF.getText()), Integer.parseInt(dayTF.getText()),
										Integer.parseInt(yearTF.getText()));
				if (!date.isValid()) throw new IllegalArgumentException("ERROR: invalid date!");
				openAccount(holder, balance, date);
			} catch (NumberFormatException e) {
				feedback.setText("ERROR: balance and date fields require integer input!");
			} catch (Exception e) {
				feedback.setText(e.toString());
			}
		} else { // close account
			try {
				closeAccount(getHolder(firstNameTF.getText(), lastName.getText()));
			} catch (Exception e) {
				feedback.setText(e.toString());
			}
		}
	}

	protected static Profile getHolder(String first, String last) { // This method is also used in Tab 2.
		if (first == null || last == null || first.length() == 0 || last.length() == 0) {
			throw new IllegalArgumentException("ERROR! First name and last name CANNOT be empty!!!");
		}
		return new Profile(first, last);
	}

	private void openAccount(Profile holder, double balance, Date date) {
		boolean added;
		switch (accountType) {
			case 1 -> added = db.add(new Checking(holder, balance, date, policyCheckBox.isSelected()));
			case 2 -> added = db.add(new Savings(holder, balance, date, policyCheckBox.isSelected()));
			default -> added = db.add(new MoneyMarket(holder, balance, date)); // case 3
		}
		feedback.setText(added? "Added to database!" : "ERROR: account already Exist!");
	}

	private void closeAccount(Profile holder) {
		boolean removed; // if found, delete; else, report error.
		switch (accountType) {
			case 1 -> removed = db.remove(new Checking(holder, 0, null, false));
			case 2 -> removed = db.remove(new Savings(holder, 0, null, false));
			default -> removed = db.remove(new MoneyMarket(holder, 0, null)); // case 3
		}
		feedback.setText(removed ? "Account Removed!" : "ERROR: account doesn't exist!");
	}

	private void toggleBalanceAndDate(boolean bool) {
		balanceLabel.setDisable(bool);
		newBalance.setDisable(bool);
		dateLabel.setDisable(bool);
		monthTF.setDisable(bool);
		dayTF.setDisable(bool);
		yearTF.setDisable(bool);
	}

	@FXML
	public void initialize() { // initialize toggle groups and set components' default properties.
		openAccountRadio.setSelected(true);
		openAccountRadio.setToggleGroup(serviceTG);
		closeAccountRadio.setToggleGroup(serviceTG);
		serviceTG.selectedToggleProperty().addListener(
				(observable, preToggle, thisToggle) -> isOpenAccount = (thisToggle == openAccountRadio)
		);
		checkingRadio.setSelected(true);
		checkingRadio.setToggleGroup(accountTG);
		savingRadio.setToggleGroup(accountTG);
		moneyMarketRadio.setToggleGroup(accountTG);
		accountTG.selectedToggleProperty().addListener(
				(observable, preToggle, thisToggle) -> {
					if (thisToggle == checkingRadio) {
						accountType = 1;
					} else if (thisToggle == savingRadio) {
						accountType = 2;
					} else if (thisToggle == moneyMarketRadio) {
						accountType = 3;
					}
				}
		);
		policyCheckBox.setSelected(false);
	}
}