package Application.Controller;

import Application.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DepositAndWithdraw {
	private final AccountDatabase db;
	private final ToggleGroup transactionTG, accountTG;
	private boolean isDeposit; // true: deposit, false: withdraw
	private int accountType; // 1: checking, 2: savings, 3: money market

	public DepositAndWithdraw(AccountDatabase db) {
		this.db = db; // Model
		transactionTG = new ToggleGroup();
		isDeposit = true;
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
	void clearTab2() {
		firstNameTextField.clear();
		lastNameTextField.clear();
		amountTextFiled.clear();
		feedback.clear();
	}

	@FXML
	void doTransaction() {
		try {
			Profile holder = OpenAndClose.getHolder(firstNameTextField.getText(), lastNameTextField.getText());
			double amount = Double.parseDouble(amountTextFiled.getText());
			if (isDeposit) { // deposit
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

	@FXML
	public void initialize() {
		// service toggle group.
		depositRadio.setSelected(true);
		depositRadio.setToggleGroup(transactionTG);
		withdrawRadio.setToggleGroup(transactionTG);
		transactionTG.selectedToggleProperty().addListener(
				(observable, prevRadio, newRadio) -> isDeposit = (newRadio == depositRadio)
		);
		// account toggle group.
		checkingRadioDW.setSelected(true);
		checkingRadioDW.setToggleGroup(accountTG);
		savingRadioDW.setToggleGroup(accountTG);
		moneyMarketRadioDW.setToggleGroup(accountTG);
		accountTG.selectedToggleProperty().addListener(
				// use column index to determine account type.
				(observable, prevRadio, newRadio) -> {
					if (newRadio == checkingRadioDW) {
						accountType = 1;
					} else if (newRadio == savingRadioDW) {
						accountType = 2;
					} else if (newRadio == moneyMarketRadioDW) {
						accountType = 3;
					}
				}
		);
	}
}