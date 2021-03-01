module Graphical.UserInterface {

    requires javafx.controls;
    requires javafx.fxml;
    requires core;

    opens com.elpresidente.ui.graphical to javafx.fxml;
    exports com.elpresidente.ui.graphical;
}