package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import simulation.Game;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilesList implements Initializable {
    @FXML
    private ListView<String> listView;

    private String currentFile;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashSet<String> files = new HashSet<>();
        for (var file : Game.getSimulations()) {
            files.add(file.getName());
        }
        listView.getItems().addAll(files);

        listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                currentFile = listView.getSelectionModel().getSelectedItem();
                EventQueue.invokeLater(() -> {
                    try {
                        Runtime.getRuntime().exec(new String[]{"xdg-open", Game.SIMULATIONS_PATH + currentFile});
                    } catch (IOException e) {
                        Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                    }
                });
            }
        });
    }
}
