module random {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.logging;
    requires org.knowm.xchart;
    exports random;
    exports random.utils;
    opens random.gui;
}