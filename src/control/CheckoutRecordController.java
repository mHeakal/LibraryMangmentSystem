package control;

import java.net.URL;
import java.time.LocalDate;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.dataaccess.DataAccess;
import model.domain.Book;
import model.domain.BookCopy;

import model.domain.LibraryMember;
import utility.Utility;

public class CheckoutRecordController implements Initializable {

	DataAccess dataAccess = SystemController.getDataAccessInstance();

	ObservableList<CheckoutRecordEntry> memberCheckoutRecords;

	@FXML
	private TableView<CheckoutRecordEntry> checkoutEntryTable;
	@FXML
	private TextField txtMemberID;
	@FXML
	private Text txtName;
	@FXML
	private Text txtAddress;
	@FXML
	private Text txtCheckoutDate;
	@FXML
	private TextField txtISBN;
//	@FXML
//	private Label lblISBNError;
//	@FXML
//	private Label lblIDError;
	@FXML
	private TableColumn<CheckoutRecordEntry, String> isbnColumn;
	@FXML
	private TableColumn<CheckoutRecordEntry, String> titleColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		memberCheckoutRecords = FXCollections.observableArrayList();
		checkoutEntryTable.setItems(memberCheckoutRecords);
//		Utility.checkEmptyTextField(txtISBN, lblISBNError);
//		Utility.checkEmptyTextField(txtMemberID, lblIDError);
	}

	@FXML
	void addCheckoutRecord(ActionEvent event) {
		memberCheckoutRecords.clear();
		String id = txtMemberID.getText();
		String isbn = txtISBN.getText();

		boolean bookCopyFound = false;
		boolean libraryMemberFound = false;

		Book book = dataAccess.getBook().get(isbn);
		BookCopy bookCopy = null;
		LibraryMember libraryMember = null;

		bookCopy = dataAccess.getAvailableBookCopy(isbn);
		if (bookCopy != null) {
			bookCopyFound = true;
		}

		libraryMember = dataAccess.getLibraryMemberById(id);
		if (libraryMember != null) {
			libraryMemberFound = true;
			txtName.setText(libraryMember.getFullName());
			txtAddress.setText(libraryMember.getAddress().getStringAddress());
		}

		if (!bookCopyFound) {
			Utility.showAlerMessage("Information Dialog", "Checkout Error", "This book is not available!!",
					AlertType.ERROR);
		} else if (!libraryMemberFound) {
			Utility.showAlerMessage("Information Dialog", "Checkout Error", "Please input correct member Id!",
					AlertType.ERROR);
		} else if (bookCopyFound && libraryMemberFound) {
			// Update checked out book and related information
			dataAccess.setBookCopyAsNotAvailable(isbn, bookCopy.getCopyNum());
			CheckoutControl checkoutRecord = libraryMember.getCheckoutRecord();
			if (checkoutRecord == null) {
				checkoutRecord = new CheckoutControl();
			}

			List<CheckoutRecordEntry> entries = checkoutRecord.getRecordEntries();
			CheckoutRecordEntry entry = new CheckoutRecordEntry(bookCopy, LocalDate.now(),
					LocalDate.now().plusDays(book.getMaxCheckoutLength()));
			entry.getBookCopy().setAvailability(false);
			entries.add(entry);
			libraryMember.setCheckoutRecord(checkoutRecord);
			dataAccess.saveNewMember(libraryMember);
			memberCheckoutRecords.addAll(entries);
			isbnColumn.setCellValueFactory(
					new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
							String isbnString = p.getValue().getBookCopy().getBook().getIsbn();
							return new SimpleStringProperty(isbnString);
						}
					});
			titleColumn.setCellValueFactory(
					new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
							String titleString = p.getValue().getBookCopy().getBook().getTitle();
							return new SimpleStringProperty(titleString);
						}
					});
			Utility.showAlerMessage("Information Dialog", "Checkout Success", " Book Checkout successful !",
					AlertType.INFORMATION);
		}
	}

}
