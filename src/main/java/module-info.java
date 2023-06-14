module qu4lizz.diamond_circle.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;

    exports qu4lizz.diamond_circle.gui;
    opens qu4lizz.diamond_circle.gui to javafx.fxml;
}
