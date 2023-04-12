module com.teamfour.iae {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teamfour.iae to javafx.fxml;
    exports com.teamfour.iae;
}