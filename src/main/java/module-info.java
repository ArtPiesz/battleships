module com.example.battleships {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.battleships to javafx.fxml;
    exports com.example.battleships;
}