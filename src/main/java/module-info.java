module random {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.logging;
    requires org.knowm.xchart;
    exports random;
    opens random.gui;
}