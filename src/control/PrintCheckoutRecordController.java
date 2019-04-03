package control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/*
import com.library.entity.Book;
import com.library.entity.BookCopy;
import com.library.entity.CheckoutRecord;
import com.library.entity.CheckoutRecordEntry;
import com.library.entity.LibraryMember;
import com.library.model.DataAccess;
//import com.sun.prism.impl.Disposer.Record;
*/
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.dataaccess.DataAccess;
import model.domain.LibraryMember;

public class PrintCheckoutRecordController implements Initializable {

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

	@FXML
	private TableColumn<CheckoutRecordEntry, String> isbnColumn;

	@FXML
	private TableColumn<CheckoutRecordEntry, String> titleColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		memberCheckoutRecords = FXCollections.observableArrayList();
		checkoutEntryTable.setItems(memberCheckoutRecords);
	}

	@FXML
	void printCheckoutRecord(ActionEvent event) {
		memberCheckoutRecords.clear();
		String id = txtMemberID.getText();
		boolean libraryMemberFound = false;

		LibraryMember libraryMember = dataAccess.getLibraryMemberById(id);
		if (libraryMember != null) {
			libraryMemberFound = true;
			txtName.setText(libraryMember.getFullName());
			txtAddress.setText(libraryMember.getAddress().getStringAddress());
		}

		if (!libraryMemberFound) {
			showAlerMessage("Information Dialog", "Print Checkout Error", "Please input correct member Id!",
					AlertType.ERROR);
		} else {
			// Update checked out book and related information
			CheckoutControl checkoutRecord = libraryMember.getCheckoutRecord();
			if (checkoutRecord == null) {
				checkoutRecord = new CheckoutControl();
			}

			List<CheckoutRecordEntry> entries = checkoutRecord.getRecordEntries();

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
			System.out.printf("%-30.30s  %-30.30s %-30.30s %-30.30s%n", "ISBN", "Title", "Checkout Date", "Due Date");
			for (CheckoutRecordEntry checkoutRE : entries) {
				System.out.printf("%-30.30s  %-30.30s %-30.30s %-30.30s%n",
						checkoutRE.getBookCopy().getBook().getIsbn(), checkoutRE.getBookCopy().getBook().getTitle(),
						checkoutRE.getCheckoutDate(), checkoutRE.getDueDate());
			}
		}
	}

	private void showAlerMessage(String title, String header, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
