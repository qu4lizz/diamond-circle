package gui.components;

import gui.Main;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.Style;


public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);
        window.getIcons().add(Main.getIcon());

        Label label = new Label(message);
        label.setTextFill(Color.web("#e0c097"));
        label.setFont(Font.font("JetBrainsMono Nerd Font Mono", FontPosture.REGULAR, 18));
        label.setWrapText(true);
        label.setMaxWidth(300);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        closeButton.setStyle("-fx-background-color:#2D2424");
        closeButton.setTextFill(Color.web("#e0c097"));
        closeButton.setFont(Font.font("JetBrainsMono Nerd Font", FontPosture.REGULAR, 20));

        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color:#5C3D2E");
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        scene.setFill(/*Paint.valueOf("#5C3D2E")*/ Color.web("#5C3D2E"));
        window.showAndWait();
    }
}
