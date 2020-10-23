package Application.Controller;

import Application.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class OpenAndClose {
	private final AccountDatabase db;
	private final ToggleGroup serviceTG, accountTG, policyTG;
	private boolean isOpenAccount;
	private int accountType; // 1: checking, 2: savings, 3: money market
	private boolean isDirectOrLoyal;

	public OpenAndClose(AccountDatabase db) {
		this.db = db; // Model
		serviceTG = new ToggleGroup();
		accountTG = new ToggleGroup();
		policyTG = new ToggleGroup();
		// set default values.
		isOpenAccount = true;
		accountType = 1;
		isDirectOrLoyal = true;
	}

	@FXML
	private RadioButton openAccountRadio, closeAccountRadio, savingRadio, checkingRadio, moneyMarketRadio, yesRadio, noRadio;

	@FXML
	private TextField firstName, lastName, newBalance, monthTF, dayTF, yearTF;

	@FXML
	private HBox policyGroup;

	@FXML
	private Label balanceLabel, policyLabel, dateLabel;

	@FXML
	private TextArea feedback;

	@FXML
	void selectOpenAccount() {
		toggleBalanceAndDate(false);
		if(!moneyMarketRadio.isSelected()) policyGroup.setDisable(false);
	}

	@FXML
	void selectCloseAccount() {
		toggleBalanceAndDate(true);
		policyGroup.setDisable(true);
	}

	@FXML
	void enablePolicyGroup() {
		if(openAccountRadio.isSelected()) policyGroup.setDisable(false);
		if (accountType == 1) {
			policyLabel.setText("Direct Deposit:");
		} else if (accountType == 2) {
			policyLabel.setText("Loyal Customer:");
		}
	}

	@FXML
	void disablePolicyGroup() {
		policyGroup.setDisable(true);
	}

	@FXML
	void processAccount() {
		if (isOpenAccount) {
			try { // check whether balance and date strings are valid.
				Profile holder = getHolder(firstName.getText(), lastName.getText());
				double balance = Double.parseDouble(newBalance.getText());
				if (balance < 0) throw new IllegalArgumentException("");
				Date date = new Date(Integer.parseInt(monthTF.getText()), Integer.parseInt(dayTF.getText()),
										Integer.parseInt(yearTF.getText()));
				if (!date.isValid()) throw new IllegalArgumentException("ERROR: invalid date!");
				openAccount(holder, balance, date);
			} catch (NumberFormatException e) {
				feedback.setText("ERROR: date fields require integer input!");
			} catch (Exception e) {
				feedback.setText(e.toString());
			}
		} else { // close account
			try {
				closeAccount(getHolder(firstName.getText(), lastName.getText()));
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
			case 1 -> added = db.add(new Checking(holder, balance, date, isDirectOrLoyal));
			case 2 -> added = db.add(new Savings(holder, balance, date, isDirectOrLoyal));
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
				// use grid column index to determine open account or close account.
				(observable, preToggle, thisToggle) -> isOpenAccount = !isOpenAccount
		);
		checkingRadio.setSelected(true);
		checkingRadio.setToggleGroup(accountTG);
		savingRadio.setToggleGroup(accountTG);
		moneyMarketRadio.setToggleGroup(accountTG);
		accountTG.selectedToggleProperty().addListener(
				// use grid column index to determine open account or close account.
				(observable, preToggle, thisToggle) -> accountType = (int) thisToggle.getProperties().get("gridpane-column")
		);
		yesRadio.setSelected(true);
		yesRadio.setToggleGroup(policyTG);
		noRadio.setToggleGroup(policyTG);
		policyTG.selectedToggleProperty().addListener(
				(observable, preToggle, thisToggle) -> isDirectOrLoyal = !isDirectOrLoyal
		);
	}
}