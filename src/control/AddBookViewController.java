package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.dataaccess.DataAccess;
import model.domain.Author;
import model.domain.Address;
import model.domain.Book;
import utility.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddBookViewController implements Initializable {
	@FXML
	TextField txtISBN;

	@FXML
	TextField txtTitle;

	@FXML
	TextField txtBorrowDuration;

	@FXML
	TextField txtFirstNameOfAuthor;

	@FXML
	TextField txtLastNameOfAuthor;

	@FXML
	TextField txtCredentialOfAuthor;

	@FXML
	TextField txtPhoneOfAuthor;

	@FXML
	TextArea txtAreaShortBio;

	@FXML
	Label lblISBNError;

	@FXML
	Label lblTitleError;

	@FXML
	Label lblBorrowDurationError;

	@FXML
	Label lblFirstNameOfAuthorError;

	@FXML
	Label lblLastNameOfAuthorError;

	@FXML
	Label lblCredentialOfAuthorError;

	@FXML
	Label lblPhoneOfAuthorError;

	@FXML
	Button btnAdd;

	@FXML
	Button btnCancel;

	@FXML
	void goActionCancel(ActionEvent event) throws IOException {
		txtISBN.setText("");
		txtTitle.setText("");
		txtBorrowDuration.setText("");
		txtFirstNameOfAuthor.setText("");
		txtLastNameOfAuthor.setText("");
		txtCredentialOfAuthor.setText("");
		txtPhoneOfAuthor.setText("");
		txtFirstNameOfAuthor.setText("");
		txtLastNameOfAuthor.setText("");
		txtCredentialOfAuthor.setText("");
		txtPhoneOfAuthor.setText("");
		txtAreaShortBio.setText("");
	}

	@FXML
	void goActionAdd(ActionEvent event) throws IOException {

		if (Utility.isEmpty(lblISBNError.getText().trim()) && Utility.isEmpty(lblTitleError.getText().trim())
				&& Utility.isEmpty(lblBorrowDurationError.getText().trim())
				&& Utility.isEmpty(lblPhoneOfAuthorError.getText().trim())) {

			String isbn = txtISBN.getText().trim();
			DataAccess dataAccess = SystemController.getDataAccessInstance();
			Book book = dataAccess.searchBook(isbn);
			if (book != null)
				Utility.showAlerMessage("Information Dialog", "Add New Book Error", "This book is not available!",
						AlertType.ERROR);
			else {
				String title = txtTitle.getText().trim();
				Integer borrowDuration = Integer.parseInt(txtBorrowDuration.getText().trim());

				// Author
				String firstNameOfAuthor = txtFirstNameOfAuthor.getText();
				String lastNameOfAuthor = txtLastNameOfAuthor.getText();
				String credentialOfAuthor = txtCredentialOfAuthor.getText();
				String phoneOfAuthor = txtPhoneOfAuthor.getText();
				String shortBio = txtAreaShortBio.getText();

				Author author = new Author(Utility.getRandom(), firstNameOfAuthor, lastNameOfAuthor, phoneOfAuthor,
						new Address("4th", "Fairfeild", "Iowa", "52557"), credentialOfAuthor, shortBio);
				Book newbook = new Book(isbn, title, borrowDuration, author);
				dataAccess.addBook(newbook);

				Utility.showAlerMessage("Information Dialog", "Success", "Book is added successful!",
						AlertType.INFORMATION);

				Utility.goToMainScreen(btnAdd, getClass());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utility.checkEmptyTextField(txtISBN, lblISBNError);
		Utility.checkEmptyTextField(txtTitle, lblTitleError);
		Utility.checkNumberTextField(txtBorrowDuration, lblBorrowDurationError);
	}
}
