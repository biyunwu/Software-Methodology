package Application.Controller;

import Application.Model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class DatabaseController {
	private final AccountDatabase db; // Model

	@FXML
	private TextArea feedback;

	public DatabaseController(AccountDatabase db) {
		this.db = db;
	}

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
	void clearTextArea() {
		feedback.clear();
	}
}
