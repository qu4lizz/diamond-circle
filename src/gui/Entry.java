package gui;

import gui.components.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import simulation.Game;
import utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entry implements Initializable {

    @FXML
    private ChoiceBox<String> dimensionsChoiceBox;
    private String[] dimensionOptions = {"7x7", "8x8", "9x9", "10x10"};

    @FXML
    private ChoiceBox<String> numOfPlayersChoiceBox;
    private String[] numberOptions = {"2", "3", "4"};
    @FXML
    private Label player1Label;

    @FXML
    private TextField player1TextField;

    @FXML
    private Label player2Label;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label player3Label;

    @FXML
    private TextField player3TextField;

    @FXML
    private Label player4Label;

    @FXML
    private TextField player4TextField;
    @FXML
    private Button startGameButton;

    private int numOfPlayers;
    private int dimensions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numOfPlayersChoiceBox.getItems().addAll(numberOptions);
        numOfPlayersChoiceBox.setOnAction(this::getNumOfPlayers);
        dimensionsChoiceBox.getItems().addAll(dimensionOptions);
        dimensionsChoiceBox.setOnAction(this::getDimensions);
        hideGridComponents();
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        String player1, player2, player3, player4;
        player1 = player1TextField.getText();
        player2 = player2TextField.getText();
        player3 = player3TextField.getText();
        player4 = player4TextField.getText();

        String[] playersNonFiltered = {player1, player2, player3, player4};
        String[] players = Arrays.stream(playersNonFiltered).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);
        if (numOfPlayersChoiceBox.getValue() == null) {
            AlertBox.display("Error", "You must choose number of players");
        }
        else if (dimensionsChoiceBox.getValue() == null) {
            AlertBox.display("Error", "You must choose dimensions");
        }
        else if (player1.isEmpty() || player2.isEmpty() || (numOfPlayers >= 3 && player3.isEmpty()) || (numOfPlayers == 4 && player4.isEmpty())) {
            AlertBox.display("Error", "Players names can't be empty");
        }
        else if (Utils.multipleStringEquals(players)) {
            AlertBox.display("Error", "Players must have unique names");
        }
        else {
            Game game = new Game(numOfPlayers, dimensions, players);
            FXMLLoader fxmlLoader = new FXMLLoader(Simulation.class.getResource("simulation.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 1280, 900);
                Game.setSimulation(fxmlLoader.getController());

                Thread gameThread = new Thread(game);
                gameThread.start();

                Main.getStage().setScene(scene);
                Main.getStage().centerOnScreen();
                Main.getStage().setResizable(true);
                Main.getStage().setMinHeight(900);
                Main.getStage().setMinWidth(1300);
                Main.getStage().setOnCloseRequest(evt -> System.exit(0));
            } catch (IOException e) {
                Main.logger.log(Level.WARNING, e.fillInStackTrace().toString());
            }

        }
    }

    public void getDimensions(ActionEvent event) {
        String dimStr = dimensionsChoiceBox.getValue();
        String[] split = dimStr.split("x");
        dimensions = Integer.parseInt(split[0]);
    }
    public void getNumOfPlayers(ActionEvent event) {
        hideGridComponents();
        String numStr = numOfPlayersChoiceBox.getValue();
        numOfPlayers = Integer.parseInt(numStr);
        player1Label.setVisible(true);
        player1TextField.setVisible(true);
        player2Label.setVisible(true);
        player2TextField.setVisible(true);
        if (numOfPlayers >= 3) {
            player3Label.setVisible(true);
            player3TextField.setVisible(true);
        }
        if (numOfPlayers == 4) {
            player4Label.setVisible(true);
            player4TextField.setVisible(true);
        }
    }


    private void hideGridComponents() {
        player1Label.setVisible(false);
        player1TextField.setVisible(false);
        player2Label.setVisible(false);
        player2TextField.setVisible(false);
        player3Label.setVisible(false);
        player3TextField.setVisible(false);
        player4Label.setVisible(false);
        player4TextField.setVisible(false);
        player1TextField.setText("");
        player2TextField.setText("");
        player3TextField.setText("");
        player4TextField.setText("");
    }
}

