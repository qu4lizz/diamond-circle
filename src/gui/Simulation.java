package gui;

import card.Card;
import card.NumberCard;
import card.SpecialCard;
import figure.GhostFigure;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import player.Player;
import simulation.CurrentPlay;
import simulation.ExecutionTime;
import simulation.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation implements Initializable {

    private static String IMAGES_PATH = "resources/images/";

    @FXML
    private static ImageView cardImage;

    @FXML
    private static Label currNumberOfPlayedGames;

    @FXML
    private ImageView diamondImage;

    @FXML
    private static Label gameTimeLabel;

    @FXML
    private ImageView figure10Image;

    @FXML
    private Label figure10Label;

    @FXML
    private ImageView figure11Image;

    @FXML
    private Label figure11Label;

    @FXML
    private ImageView figure12Image;

    @FXML
    private Label figure12Label;

    @FXML
    private ImageView figure13Image;

    @FXML
    private Label figure13Label;

    @FXML
    private ImageView figure14Image;

    @FXML
    private Label figure14Label;

    @FXML
    private ImageView figure15Image;

    @FXML
    private Label figure15Label;

    @FXML
    private ImageView figure16Image;

    @FXML
    private Label figure16Label;

    @FXML
    private ImageView figure1Image;

    @FXML
    private Label figure1Label;

    @FXML
    private ImageView figure2Image;

    @FXML
    private Label figure2Label;

    @FXML
    private ImageView figure3Image;

    @FXML
    private Label figure3Label;

    @FXML
    private ImageView figure4Image;

    @FXML
    private Label figure4Label;

    @FXML
    private ImageView figure5Image;

    @FXML
    private Label figure5Label;

    @FXML
    private ImageView figure6Image;

    @FXML
    private Label figure6Label;

    @FXML
    private ImageView figure7Image;

    @FXML
    private Label figure7Label;

    @FXML
    private ImageView figure8Image;

    @FXML
    private Label figure8Label;

    @FXML
    private ImageView figure9Image;

    @FXML
    private Label figure9Label;

    @FXML
    private GridPane grid7x7;

    @FXML
    private GridPane grid8x8;

    @FXML
    private GridPane grid9x9;

    @FXML
    private GridPane grid10x10;

    @FXML
    private ImageView holeImage;

    @FXML
    private static Label messageLabel;

    @FXML
    private Label player1outOf3;

    @FXML
    private Label player1outOf4;

    @FXML
    private Label player1outOf2;

    @FXML
    private Label player2outOf2;

    @FXML
    private Label player2outOf3;

    @FXML
    private Label player2outOf4;

    @FXML
    private Label player3outOf3;

    @FXML
    private Label player3outOf4;

    @FXML
    private Label player4outOf4;

    @FXML
    private static ToggleButton startPauseToggleButton;

    @FXML
    void figure10OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure11OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure12OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure13OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure14OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure15OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure16OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure1OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure2OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure3OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure4OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure5OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure6OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure7OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure8OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void figure9OnMouseClicked(MouseEvent event) {

    }

    @FXML
    void listOfFilesOnMouseClicked(MouseEvent event) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Entry.class.getResource("entry.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            Logger.getLogger(IOException.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        stage.setTitle("Simulation files");
        stage.getIcons().add(new Image(Main.ICON_PATH));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void startPauseOnMouseClicked(MouseEvent event) {
        if (startPauseToggleButton.isSelected()) {
            startPauseToggleButton.setText("Unpause");
            Game.pause();
            GhostFigure.pause();
            CurrentPlay.pause();
            ExecutionTime.pause();
        }
        else {
            startPauseToggleButton.setText("Pause");
            Game.resume();
            GhostFigure.resume();
            CurrentPlay.resume();
            ExecutionTime.resume();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startPauseToggleButton.setText("Pause");
        playersAndFiguresBar(Game.getPlayers());
        mapGuiInit(Game.getDimensions());
    }


    public static void refreshMap() {

    }

    public static void executionTimeRefresh(int time) {
        gameTimeLabel.setText("Game time: " + time);
    }

    public static void setNumberOfPlayedGames(int num) {
        currNumberOfPlayedGames.setText("Current number of played games: " + num);
    }

    public static void descriptionRefresh(String message) {
        messageLabel.setText(message);
    }

    public static void cardRefresh(Card card) throws IOException {
        Image image = null;
        if (card instanceof NumberCard) {
            image = new Image(IMAGES_PATH + "card" + ((NumberCard) card).getValue() + ".png");
        }
        else if (card instanceof SpecialCard) {
            image = new Image(IMAGES_PATH + "cardSpec.png");
        }
        else {
            throw new IOException("Couldn't open card file");
        }
        cardImage.setImage(image);
    }

    private void mapGuiInit(int dim) {
        switch (dim) {
            case 7 ->
                    grid7x7.setVisible(true);
            case 8 ->
                    grid8x8.setVisible(true);
            case 9 ->
                    grid9x9.setVisible(true);
            case 10 ->
                    grid10x10.setVisible(true);
        }
    }

    private void playersAndFiguresBar(LinkedList<Player> players) {
        Label[][] figuresLabels = { {figure1Label, figure2Label, figure3Label, figure4Label},
                                    {figure5Label, figure6Label, figure7Label, figure8Label},
                                    {figure9Label, figure10Label, figure11Label, figure12Label},
                                    {figure13Label, figure14Label, figure15Label, figure16Label}};
        switch (players.size()) {
            case 2 -> {
                playersLabelGuiInit(player1outOf2, players.get(0));
                playersLabelGuiInit(player2outOf2, players.get(1));
            }
            case 3 -> {
                playersLabelGuiInit(player1outOf3, players.get(0));
                playersLabelGuiInit(player2outOf3, players.get(1));
                playersLabelGuiInit(player3outOf3, players.get(2));
            }
            case 4 -> {
                playersLabelGuiInit(player1outOf4, players.get(0));
                playersLabelGuiInit(player2outOf4, players.get(1));
                playersLabelGuiInit(player3outOf4, players.get(2));
                playersLabelGuiInit(player4outOf4, players.get(3));
            }
        }
        figuresLabelGuiInit(figuresLabels[0], players.get(0));
        figuresLabelGuiInit(figuresLabels[1], players.get(1));
        if (players.size() >= 3)
            figuresLabelGuiInit(figuresLabels[2], players.get(2));
        if (players.size() == 4)
            figuresLabelGuiInit(figuresLabels[3], players.get(3));

    }
    private void playersLabelGuiInit(Label label, Player player) {
        label.setText(player.getName());
        label.setTextFill(Color.web(colorHexByName(player.getColor())));
        label.setVisible(true);
    }
    private void figuresLabelGuiInit(Label[] labels, Player player){
        var color = Color.web(colorHexByName(player.getColor()));
        for (var figLabel : labels) {
            figLabel.setTextFill(color);
            figLabel.setVisible(true);
        }
    }

    private String colorHexByName(String color) {
        switch (color) {
            case "RED" -> { return "#CC0000"; }
            case "BLUE" -> { return "#3333FF"; }
            case "GREEN" -> { return "#99FF00"; }
            case "YELLOW" -> { return "#FFCC00"; }
        }
        return null;
    }
}