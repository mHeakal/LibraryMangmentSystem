package utility;

import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Utility {

	public static String getRandom() {
		String myRandomNumber;
		Random random = new Random();
		myRandomNumber = Long.toString(System.currentTimeMillis()) + Integer.toString(random.nextInt(10000));
		return myRandomNumber;
	}

	public static void showAlerMessage(String title, String header, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static boolean isEmpty(String input) {
		if (input.isEmpty())
			return true;
		return false;
	}

	public static boolean isNumber(String input) {
		try {
			Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static boolean isExactlyNumberLength(String input, int mylength) {
		if (input.length() == mylength && isNumber(input))
			return true;
		return false;
	}

	public static boolean isEqual(String input1, String input2) {
		if (input1.equals(input2))
			return true;
		return false;
	}

	public static void checkEmptyTextField(TextField txtField, Label lblField) {

		txtField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (txtField.isFocused() == false) {
				if (isEmpty(txtField.getText().trim())) {
					lblField.setVisible(true);
					setMessage(lblField, "You can't leave this empty.", Color.RED);
				} else {
					lblField.setVisible(false);
					lblField.setText("");
				}
			}
		});
	}

	public static void checkNumberTextField(TextField txtField, Label lblField) {

		txtField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (txtField.isFocused() == false) {
				if (!isNumber(txtField.getText())) {
					setMessage(lblField, "Please input number", Color.RED);
				} else {
					lblField.setVisible(false);
					lblField.setText("");
				}
			}
		});
	}

	public static void checkExactlyNumberLengthTextField(TextField txtField, Label lblField, int myLength) {

		txtField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (txtField.isFocused() == false) {
				if (!isExactlyNumberLength(txtField.getText(), myLength)) {
					setMessage(lblField, "Please input this field " + myLength + " number digits", Color.RED);
				} else {
					lblField.setVisible(false);
					lblField.setText("");
				}
			}
		});
	}

	public static void setMessage(Label l, String message, Color color) {
		l.setText(message);
		l.setTextFill(color);
		l.setVisible(true);
	}

	public static void goToMainScreen(Button btn, @SuppressWarnings("rawtypes") Class myClass) {
		try {
			Stage stage = (Stage) btn.getScene().getWindow();
			Parent root = FXMLLoader.load(myClass.getResource(Resource.SCREENTOMAINSCREEN));
			Scene scene = new Scene(root, 900, 700);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
