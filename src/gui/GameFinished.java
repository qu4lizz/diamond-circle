package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import player.Player;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class GameFinished implements Initializable{
    @FXML
    private Button exitButton;
    @FXML
    private Label place1;
    @FXML
    private Label place2;
    @FXML
    private Label place3;
    @FXML
    private Label place4;
    private static LinkedList<Player> playerOrdered= new LinkedList<>();

    public static void addPlayer(Player player) {
        playerOrdered.add(player);
    }
    @FXML
    void exitOnMouseClicked(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label[] playersPlaceLabel = {place1, place2, place3, place4};
        for (int i = 0; i < playerOrdered.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("Place ").append(i + 1).append(" - ").append("Player ").append(playerOrdered.get(i).getId()).
                    append(" (").append(playerOrdered.get(i).getName()).append(")\n");
            playersPlaceLabel[i].setText(sb.toString());
            playersPlaceLabel[i].setTextFill(Color.web(Simulation.colorHexByName(playerOrdered.get(i).getColor())));
            playersPlaceLabel[i].setVisible(true);
        }

    }
}
