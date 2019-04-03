package control;

import java.net.URL;
import java.util.ResourceBundle;

import model.domain.LoggedUser;
import model.domain.User;
import model.dataaccess.DataAccess;
import utility.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	@FXML
	TextField txtUserName;

	@FXML
	TextField txtPassword;

	@FXML
	Button btnLogin;

	@FXML
	Label lblUserNameError;

	@FXML
	Label lblPasswordError;

	@FXML
	public void goActionlogin(ActionEvent event) {
		if (Utility.isEmpty(lblUserNameError.getText().trim()) && Utility.isEmpty(lblPasswordError.getText().trim())) {
			String username = txtUserName.getText().trim();
			String password = txtPassword.getText().trim();
			DataAccess dataAccess = SystemController.getDataAccessInstance();
			User userValue = dataAccess.getUsers().get(username);

			if (null == userValue) {
				Utility.showAlerMessage("Information Dialog", "Login Error",
						"UserName does not exist. Please input password again.", AlertType.ERROR);
			} else if (userValue.getPassword().equals(password)) {
				LoggedUser.getInstance().setUser(userValue);
				Utility.goToMainScreen(btnLogin, getClass());
			} else {
				Utility.showAlerMessage("Information Dialog", "Login Error",
						"Password does not exist. Please input password again!", AlertType.ERROR);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utility.checkEmptyTextField(txtUserName, lblUserNameError);
		Utility.checkEmptyTextField(txtPassword, lblPasswordError);
	}
}
