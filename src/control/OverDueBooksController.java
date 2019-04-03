package control;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
/*
import com.library.entity.Book;
import com.library.entity.CheckoutRecord;
import com.library.entity.CheckoutRecordEntry;
import com.library.entity.LibraryMember;
import com.library.model.DataAccess;
import com.library.utility.Utility;
*/
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.dataaccess.DataAccess;
import model.domain.Book;
import model.domain.LibraryMember;
import utility.Utility;

public class OverDueBooksController implements Initializable {

	DataAccess dataAccess = SystemController.getDataAccessInstance();

	ObservableList<CheckoutRecordEntry> memberCheckoutRecords;

	@FXML
	private TableView<CheckoutRecordEntry> checkoutEntryTable;
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
	void getOverDueBook(ActionEvent event) {
		memberCheckoutRecords.clear();
		String isbn = txtISBN.getText();
		HashMap<String, Book> bookMap = dataAccess.getBook();
		Book book = bookMap.get(isbn);
		boolean bookFound = book != null ? true : false;

		if (!bookFound) {
			Utility.showAlerMessage("Information Dialog", "Print Checkout Error", "Please input correct ISBN!",
					AlertType.ERROR);
		} else {
			// Update checked out book and related information
			List<CheckoutRecordEntry> allOverDueEntries = new ArrayList<CheckoutRecordEntry>();
			HashMap<String, LibraryMember> allLibraryMember = dataAccess.getLibraryMember();
			for (String key : allLibraryMember.keySet()) {
				LibraryMember libraryMember = allLibraryMember.get(key);
				CheckoutControl checkoutRecord = libraryMember.getCheckoutRecord();
				if (checkoutRecord != null) {
					List<CheckoutRecordEntry> overDueEntries = checkoutRecord.getOverDueRecordEntry();
					if (overDueEntries != null && overDueEntries.isEmpty() == false) {
						allOverDueEntries.addAll(overDueEntries);
					}
				}
			}

			memberCheckoutRecords.addAll(allOverDueEntries);
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
		}
	}
}
