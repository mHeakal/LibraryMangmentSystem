package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utility.Resource;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(Resource.LOGIN));
			Scene scene = new Scene(root, 500, 500);
			scene.getStylesheets().addAll(this.getClass().getResource(Resource.CSS).toExternalForm());
			primaryStage.setTitle("Library Manngment System Group 2");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Resource.ICON)));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
