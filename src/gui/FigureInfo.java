package gui;

import figure.PlayerFigure;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import map.GameMap;
import utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class FigureInfo implements Initializable {
    private static PlayerFigure figure;
    private static int id;
    @FXML
    private GridPane grid10x10;
    @FXML
    private GridPane grid7x7;
    @FXML
    private GridPane grid8x8;
    @FXML
    private GridPane grid9x9;
    @FXML
    private Label title;
    private static GridPane gridRef;

    public static void setFigure(PlayerFigure figure) {
        FigureInfo.figure = figure;
    }

    public static void setId(int id) {
        FigureInfo.id = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch (GameMap.dimensions) {
            case 7 -> {
                grid7x7.setVisible(true);
                gridRef = grid7x7;
            }
            case 8 -> {
                grid8x8.setVisible(true);
                gridRef = grid8x8;
            }
            case 9 -> {
                grid9x9.setVisible(true);
                gridRef = grid9x9;
            }
            case 10 -> {
                grid10x10.setVisible(true);
                gridRef = grid10x10;
            }
        }
        String colorHex = Simulation.colorHexByName(figure.getColor());
        title.setText(figure.info());
        title.setTextFill(Color.web(colorHex));
        for (var pos : figure.getPath()) {
            int index = Utils.calculateNumberField(pos.second, pos.first, GameMap.dimensions);
            Label label = (Label) gridRef.getChildren().get(index - 1);
            label.setStyle("-fx-background-color:" + colorHex + " ;");
        }
    }


}
