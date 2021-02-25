module com.elpresidente {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.elpresidente to javafx.fxml;
    exports com.elpresidente;
}