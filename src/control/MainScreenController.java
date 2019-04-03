package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.dataaccess.Auth;
import model.domain.LoggedUser;
import model.domain.User;
import utility.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {

	@FXML
	Button btnViewLibraryMember;

	@FXML
	Button btnAddLibraryMember;

	@FXML
	Button btnOverdueBook;

	@FXML
	Button btnCheckoutBook;

	@FXML
	Button btnAddACopyExsitingBook;

	@FXML
	Button btnAddABook;

	@FXML
	Button btnPrintCheckoutRecord;

	@FXML
	Button btnLogout;

	@FXML
	BorderPane rightPlitBorderPanel;

	User loggedUser = LoggedUser.getInstance().getUser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String authen = loggedUser.getAuthorization().name();
		if (authen.equals(Auth.ADMIN.toString())) {
			btnAddABook.setDisable(false);
			btnViewLibraryMember.setDisable(false);
			btnAddLibraryMember.setDisable(false);
			btnAddACopyExsitingBook.setDisable(false);
			btnAddABook.setDisable(false);

			btnCheckoutBook.setDisable(true);
			btnPrintCheckoutRecord.setDisable(true);
			btnOverdueBook.setDisable(true);
		} else if (authen.equals(Auth.LIBRARIAN.toString())) {
			btnAddABook.setDisable(true);
			btnViewLibraryMember.setDisable(true);
			btnAddLibraryMember.setDisable(true);
			btnAddACopyExsitingBook.setDisable(true);
			btnAddABook.setDisable(true);

			btnCheckoutBook.setDisable(false);
			btnPrintCheckoutRecord.setDisable(false);
			btnOverdueBook.setDisable(false);
		} else if (authen.equals(Auth.BOTH.toString())) {
			btnAddABook.setDisable(false);
			btnViewLibraryMember.setDisable(false);
			btnAddLibraryMember.setDisable(false);
			btnAddACopyExsitingBook.setDisable(false);
			btnAddABook.setDisable(false);

			btnCheckoutBook.setDisable(false);
			btnPrintCheckoutRecord.setDisable(false);
			btnOverdueBook.setDisable(false);
		}
	}

	@FXML
	void goActionLogout(ActionEvent event) throws IOException {
		LoggedUser.getInstance().setUser(null);

		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(Resource.LOGIN));

		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().addAll(this.getClass().getResource(Resource.CSS).toExternalForm());

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void goViewLibraryMember(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.MEMBERVIEW));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goAddLibraryMember(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.MEMBERADD));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goOverdueBook(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.OVERDUEBOOK));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goCheckoutBook(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.CHECKOUTBOOKFORM));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goAddACopyExsitingBook(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.NEWBOOKCOPYVIEW));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goAddABook(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.NEWBOOKVIEW));
		rightPlitBorderPanel.setCenter(pane);
	}

	@FXML
	void goPrintCheckoutRecord(ActionEvent event) throws IOException {
		rightPlitBorderPanel.getChildren().clear();
		AnchorPane pane = FXMLLoader.load(getClass().getResource(Resource.PRINTCHECKOUTRECORD));
		rightPlitBorderPanel.setCenter(pane);
	}
}
