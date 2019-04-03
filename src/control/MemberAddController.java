package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.domain.Address;
import model.domain.LibraryMember;
import model.dataaccess.DataAccess;
import utility.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MemberAddController implements Initializable {

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private TextField txtPhone;

	@FXML
	private TextField txtStreet;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtZip;

	@FXML
	private TextField txtMemberID;

	@FXML
	private ComboBox<String> cmbState;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnAddMember;

	@FXML
	private Label lblFirstNameError;

	@FXML
	private Label lblLastNameError;

	@FXML
	private Label lblStreetError;

	@FXML
	private Label lblCityError;

	@FXML
	private Label lblZipError;

	@FXML
	private Label lblPhoneError;

	@FXML
	private Label lblMemberIDError;

	public void setData() {
		String[] states = { "Iowa", "Alabama", "Arkansas", "Arizona", "Alaska", "Colorado", "Connecticut", "Delaware",
				"Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "California", "Kansas", "Kentucky",
				"Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
				"Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York",
				"North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
				"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
				"West Virginia", "Wisconsin", "Wyoming" };
		cmbState.getItems().clear();
		cmbState.getItems().addAll(states);
		cmbState.setValue(states[0]);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setData();

		Utility.checkEmptyTextField(txtFirstName, lblFirstNameError);
		Utility.checkEmptyTextField(txtLastName, lblLastNameError);
		Utility.checkEmptyTextField(txtCity, lblCityError);
		Utility.checkEmptyTextField(txtPhone, lblPhoneError);
		Utility.checkEmptyTextField(txtStreet, lblStreetError);

		Utility.checkEmptyTextField(txtMemberID, lblMemberIDError);
		Utility.checkExactlyNumberLengthTextField(txtZip, lblZipError, 5);
	}

	@FXML
	void goActionCancel(ActionEvent event) throws IOException {
		txtFirstName.setText("");
		txtCity.setText("");
		txtLastName.setText("");
		txtMemberID.setText("");
		txtPhone.setText("");
		txtStreet.setText("");
		txtZip.setText("");
		Utility.goToMainScreen(btnAddMember, getClass());
	}

	@FXML
	void goAddMember(ActionEvent event) throws IOException {
		try {
			if (Utility.isEmpty(lblFirstNameError.getText()) && Utility.isEmpty(lblCityError.getText())
					&& Utility.isEmpty(lblLastNameError.getText()) && Utility.isEmpty(lblPhoneError.getText())
					&& Utility.isEmpty(lblStreetError.getText()) && Utility.isEmpty(lblZipError.getText())) {
				String street = txtStreet.getText();
				String city = txtCity.getText();
				String state = cmbState.getSelectionModel().getSelectedItem().toString();

				// End validate
				String zip = txtZip.getText();
				Address address = new Address(street, city, state, zip);

				String memberId = txtMemberID.getText();
				String personId = Utility.getRandom();
				String firstName = txtFirstName.getText();
				String lastName = txtLastName.getText();
				String phoneNumber = txtPhone.getText();

				LibraryMember libraryMember = new LibraryMember(personId, firstName, lastName, phoneNumber, address,
						memberId);

				DataAccess dataAccess = SystemController.getDataAccessInstance();
				dataAccess.saveNewMember(libraryMember);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("Success");
				alert.setContentText("Member is added successful !");
				alert.showAndWait();

				Utility.goToMainScreen(btnAddMember, getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
