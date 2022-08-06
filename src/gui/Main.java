package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static Stage guiStage;

    public static Stage getStage() {
        return guiStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        guiStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Entry.class.getResource("entry.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        guiStage.setTitle("Diamond Circle");
        guiStage.getIcons().add(new Image("file:resources/images/icon.png"));
        guiStage.setScene(scene);
        guiStage.setResizable(false);
        guiStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
