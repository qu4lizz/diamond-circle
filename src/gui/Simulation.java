package gui;

import card.Card;
import card.NumberCard;
import card.SpecialCard;
import diamond.Diamond;
import figure.*;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import map.GameMap;
import player.Player;
import simulation.CurrentPlay;
import simulation.ExecutionTime;
import simulation.Game;
import utils.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation implements Initializable {

    private static String IMAGES_PATH = "resources/images/";
    @FXML
    private ImageView cardImage = new ImageView();
    @FXML
    private Label currNumberOfPlayedGames;
    private Image diamondImage;
    @FXML
    private Label figure10Label;
    @FXML
    private Label figure11Label;
    @FXML
    private Label figure12Label;
    @FXML
    private Label figure13Label;
    @FXML
    private Label figure14Label;
    @FXML
    private Label figure15Label;
    @FXML
    private Label figure16Label;
    @FXML
    private Label figure1Label;
    @FXML
    private Label figure2Label;
    @FXML
    private Label figure3Label;
    @FXML
    private Label figure4Label;
    @FXML
    private Label figure5Label;
    @FXML
    private Label figure6Label;
    @FXML
    private Label figure7Label;
    @FXML
    private Label figure8Label;
    @FXML
    private Label figure9Label;
    @FXML
    private Label gameTimeLabel;
    @FXML
    private GridPane grid10x10;
    @FXML
    private GridPane grid7x7;
    @FXML
    private GridPane grid8x8;
    @FXML
    private GridPane grid9x9;
    private GridPane gridRef;
    private Image holeImage;

    private Image card1;
    private Image card2;
    private Image card3;
    private Image card4;
    private Image cardSpec;

    @FXML
    private Label messageLabel;

    @FXML
    private Label player1outOf2;
    @FXML
    private Label player1outOf3;
    @FXML
    private Label player1outOf4;
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
    private ToggleButton startPauseToggleButton;
    private LinkedHashMap<PlayerFigure, ImageView> figureImageMap;
    private HashMap<Pair<Integer, Integer>, ImageView> diamondsImages;
    private ImageView[] holesImages;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Entry.class.getResource("filesList.fxml"));
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
        imagesInit();
    }


    public void moveFigureOnMapGrid(PlayerFigure figure) {
        ImageView currImage = figureImageMap.get(figure);
        gridRef.getChildren().remove(currImage);
        var currField = figure.getPath().get(figure.getPath().size() - 1);
        currImage.fitHeightProperty().bind(gridRef.heightProperty().divide(GameMap.dimensions));
        currImage.fitWidthProperty().bind(gridRef.widthProperty().divide(GameMap.dimensions));
        gridRef.add(currImage, currField.second, currField.first);
        if (!currImage.isVisible())
            currImage.setVisible(true);
    }
    public void figureFinished(PlayerFigure figure) {
        ImageView currImage = figureImageMap.get(figure);
        currImage.setVisible(false);
    }

    public void showDiamondsOnMapGrid(HashSet<Pair<Integer, Integer>> diamondsPos) {
        diamondsImages = new HashMap<>();
        for (var pos : diamondsPos) {
            ImageView imageView = new ImageView(diamondImage);
            diamondsImages.put(pos, imageView);
            imageView.fitHeightProperty().bind(gridRef.heightProperty().divide(GameMap.dimensions * 2));
            imageView.fitWidthProperty().bind(gridRef.widthProperty().divide(GameMap.dimensions * 2));
            GridPane.setHalignment(imageView, HPos.CENTER);
            GridPane.setValignment(imageView, VPos.CENTER);
            gridRef.add(imageView, pos.second, pos.first);
            imageView.setVisible(true);
        }
    }
    public void removeDiamondsFromMapGrid() {
        for (var diam : diamondsImages.values()) {
            diam.setVisible(false);
        }
        diamondsImages = null;
    }
    public void removeSingleDiamondFromMapGrid(Pair<Integer, Integer> pos) {
        var img = diamondsImages.get(pos);
        img.setVisible(false);
        diamondsImages.remove(pos, img);
    }

    public void showHolesOnMapGrid(HashSet<Pair<Integer, Integer>> holesPos) {
        holesImages = new ImageView[holesPos.size()];
        int i = 0;
        for (var pos : holesPos) {
            ImageView imageView = new ImageView(holeImage);
            holesImages[i] = imageView;
            imageView.fitHeightProperty().bind(gridRef.heightProperty().divide(GameMap.dimensions));
            imageView.fitWidthProperty().bind(gridRef.widthProperty().divide(GameMap.dimensions));
            gridRef.add(imageView, pos.second, pos.first);
            imageView.setVisible(true);
            i++;
        }
    }
    public void devourFigure(PlayerFigure figure) {
        var img = figureImageMap.get(figure);
        img.setVisible(false);
    }
    public void removeHolesFromMapGrid() {
        for (var hole : holesImages) {
            hole.setVisible(false);
        }
        holesImages = null;
    }

    public void executionTimeRefresh(int time) {
        gameTimeLabel.setText("Game time: " + time);
    }

    public void setNumberOfPlayedGames(int num) {
        currNumberOfPlayedGames.setText("Current number of played games: " + num);
    }

    public void descriptionRefresh(String message) {
        messageLabel.setVisible(true);
        messageLabel.setText(message);
    }

    public void cardRefresh(Card card) {
        Image image = null;
        if (card instanceof NumberCard) {
            switch (((NumberCard) card).getValue()) {
                case 1 -> image = card1;
                case 2 -> image = card2;
                case 3 -> image = card3;
                case 4 -> image = card4;
            }
        }
        else if (card instanceof SpecialCard) {
            image = cardSpec;
        }
        cardImage.setImage(image);
    }

    private void imagesInit() {
        ImageView[] figuresImages = new ImageView[Player.NUM_OF_FIGURES * Game.getPlayers().size()];
        figureImageMap = new LinkedHashMap<>();
        for (int i = 0; i < Player.NUM_OF_FIGURES; i++){
            for (int j = 0; j < Game.getPlayers().size(); j++) {
                var player = Game.getPlayers().get(j);
                var fig = player.getFigures()[i];
                String imagePath = figureImagePath(fig);
                figuresImages[j * Player.NUM_OF_FIGURES + i] = new ImageView(new Image(imagePath));
                figureImageMap.put(fig, figuresImages[j * Player.NUM_OF_FIGURES + i]);
            }
        }
        card1 = new Image("file:" + IMAGES_PATH + "card1.png");
        card2 = new Image("file:" + IMAGES_PATH + "card2.png");
        card3 = new Image("file:" + IMAGES_PATH + "card3.png");
        card4 = new Image("file:" + IMAGES_PATH + "card4.png");
        cardSpec = new Image("file:" + IMAGES_PATH + "card_spec.png");

        holeImage = new Image("file:" + IMAGES_PATH + "hole.png");
        diamondImage = new Image("file:" + IMAGES_PATH + "diamond.png");
    }

    private String figureImagePath(PlayerFigure fig) {
        StringBuilder imagePath = new StringBuilder(50);
        imagePath.append("file:" + IMAGES_PATH);
        if (fig instanceof WalkingFigure)
            imagePath.append("walking_figure_");
        else if (fig instanceof RunningFigure)
            imagePath.append("running_figure_");
        else if (fig instanceof FlyingFigure)
            imagePath.append("flying_figure_");
        switch (fig.getColor()) {
            case "BLUE" ->
                    imagePath.append("blue");
            case "RED" ->
                    imagePath.append("red");
            case "GREEN" ->
                    imagePath.append("green");
            case "YELLOW" ->
                    imagePath.append("yellow");
        }
        imagePath.append(".png");
        return imagePath.toString();
    }

    private void mapGuiInit(int dim) {
        switch (dim) {
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