package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static String ICON_PATH = "file:resources/images/icon.png";
    private static Stage guiStage;
    private static Image icon;

    public static Stage getStage() {
        return guiStage;
    }
    public static Image getIcon() {
        return icon;
    }

    @Override
    public void start(Stage stage) throws IOException {
        guiStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Entry.class.getResource("entry.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        guiStage.setTitle("Diamond Circle");
        icon = new Image(ICON_PATH);
        guiStage.getIcons().add(icon);
        guiStage.setScene(scene);
        guiStage.setResizable(false);
        guiStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
