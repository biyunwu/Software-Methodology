package Application.Controller;

import Application.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DepositAndWithdraw {
	private final AccountDatabase db;
	private final ToggleGroup transactionTG, accountTG;
	private int transactionType; // 1: deposit, 2: withdraw
	private int accountType; // 1: checking, 2: savings, 3: money market

	public DepositAndWithdraw(AccountDatabase db) {
		this.db = db; // Model
		transactionTG = new ToggleGroup();
		transactionType = 1;
		accountTG = new ToggleGroup();
		accountType = 1;
	}

	@FXML
	private RadioButton depositRadio, withdrawRadio, checkingRadioDW, savingRadioDW, moneyMarketRadioDW;

	@FXML
	private TextField firstNameTextField, lastNameTextField, amountTextFiled;

	@FXML
	private TextArea feedback;

	@FXML
	void doTransaction(ActionEvent event) {
		try {
			Profile holder = OpenAndClose.getHolder(firstNameTextField.getText(), lastNameTextField.getText());
			double amount = Double.parseDouble(amountTextFiled.getText());
			if (transactionType == 1) { // deposit
				deposit(holder, amount);
			} else { // withdraw
				withdraw(holder, amount);
			}
		} catch (NumberFormatException e) {
			feedback.setText("ERROR: invalid amount, only numeric value is accepted!");
		} catch (Exception e) {
			feedback.setText(e.toString());
		}
	}

	private void deposit(Profile holder, double amount) {
		boolean isSuccess;
		switch (accountType) {
			case 1 -> isSuccess = db.deposit(new Checking(holder, 0, null, false), amount);
			case 2 -> isSuccess = db.deposit(new Savings(holder, 0, null, false), amount);
			default -> isSuccess = db.deposit(new MoneyMarket(holder, 0, null), amount); // case 3
		}
		feedback.setText(isSuccess ? "Deposited!" : "ERROR: account does not exist!");
	}

	private void withdraw(Profile holder, double amount) {
		int isSuccess;
		switch (accountType) {
			case 1 -> isSuccess = db.withdrawal(new Checking(holder, 0, null, false), amount);
			case 2 -> isSuccess = db.withdrawal(new Savings(holder, 0, null, false), amount);
			default -> isSuccess = db.withdrawal(new MoneyMarket(holder, 0, null), amount); // case 3
		}
		feedback.setText(isSuccess == 0 ? "Withdrew!" : "ERROR: account does not exist or no sufficient fund!");
	}

	private void checkNameAndAmount(String firstName, String lastName, double amount) {
		if ((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty())) {
			throw new IllegalArgumentException("ERROR: first name and last name cannot be empty");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("ERROR: only positive number is accepted!");
		}
	}

	@FXML
	public void initialize() {
		// service toggle group.
		depositRadio.setSelected(true);
		depositRadio.setToggleGroup(transactionTG);
		withdrawRadio.setToggleGroup(transactionTG);
		transactionTG.selectedToggleProperty().addListener(
				// use column index to determine deposit or withdraw.
				(observable, oldVal, newVal) -> transactionType = (int) newVal.getProperties().get("gridpane-column")
		);
		// account toggle group.
		checkingRadioDW.setSelected(true);
		checkingRadioDW.setToggleGroup(accountTG);
		savingRadioDW.setToggleGroup(accountTG);
		moneyMarketRadioDW.setToggleGroup(accountTG);
		accountTG.selectedToggleProperty().addListener(
				// use column index to determine account type.
				(observable, oldVal, newVal) -> accountType = (int) newVal.getProperties().get("gridpane-column")
		);
	}
}