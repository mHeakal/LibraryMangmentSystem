package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*import com.library.entity.Book;
import com.library.entity.BookCopy;
import com.library.model.DataAccess;
import com.library.utility.Utility;
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.dataaccess.DataAccess;
import model.domain.Book;
import model.domain.BookCopy;
import utility.Utility;

public class AddCopyViewController implements Initializable {
	@FXML
	TextField txtISBN;

	@FXML
	TextField txtCopyNumber;

	@FXML
	Label lblISBNError;

	@FXML
	Label lblCopyNumberError;

	@FXML
	Button btnAdd;

	@FXML
	Button btnCancel;

	@FXML
	void goActionAdd(ActionEvent event) throws IOException {

		if (Utility.isEmpty(lblISBNError.getText().trim()) && Utility.isEmpty(lblCopyNumberError.getText().trim())) {
			String isbn = txtISBN.getText().trim();
			int copyNumber = Integer.parseInt(txtCopyNumber.getText().trim());

			DataAccess dataAccess = SystemController.getDataAccessInstance();
			Book book = dataAccess.searchBook(isbn);
			if (book == null)
				Utility.showAlerMessage("Information Dialog", "Add New Book Copy Error",
						"ISBN does not exist. Please input correct ISBN!", AlertType.ERROR);
			else {
				BookCopy newBookCopy = new BookCopy(book, copyNumber, true);
				// book.addBookCopy(newBookCopy);
				BookCopy bc = dataAccess.searchCopyNumberByISBN(isbn, newBookCopy);
				if (bc == null) {
					dataAccess.addBookCopy(isbn, newBookCopy);

					Utility.showAlerMessage("Information Dialog", "Success", "Book Copy is added successful!",
							AlertType.INFORMATION);
					Utility.goToMainScreen(btnAdd, getClass());
				} else {
					Utility.showAlerMessage("Information Dialog", "Add New Book Copy Error",
							"Book Copy Number existed!", AlertType.ERROR);
				}
			}

		}

	}

	@FXML
	void goActionCancel(ActionEvent event) throws IOException {
		txtISBN.setText("");
		txtCopyNumber.setText("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Utility.checkEmptyTextField(txtISBN, lblISBNError);
		Utility.checkNumberTextField(txtCopyNumber, lblCopyNumberError);
	}
}
