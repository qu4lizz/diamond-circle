package qu4lizz.diamond_circle.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main extends Application {
    public static String ICON_PATH = "file:resources" + File.separator + "images" + File.separator + "icon.png";
    public static final String LOGGER_PATH = "database" + File.separator + "logger" + File.separator + "simulation.log";
    private static Stage guiStage;
    private static Image icon;
    public static Handler handler;
    public static Logger logger;
    static {
        try {
            logger = Logger.getLogger(Main.class.getName());
            handler = new FileHandler(LOGGER_PATH);
            logger.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
