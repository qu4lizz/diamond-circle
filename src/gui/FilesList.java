package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import simulation.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilesList{

    @FXML
    private ListView<File> listView;

    File currentFile;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(Game.getSimulations());
        //listView.getSelectionModel().selectedItemProperty().addListener(new Mou);

        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                currentFile = listView.getSelectionModel().getSelectedItem();
                try {
                    java.awt.Desktop.getDesktop().edit(currentFile);
                } catch (IOException e) {
                }
            }
        });
    }
}
