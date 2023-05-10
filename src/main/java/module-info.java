module com.teamfour.iae {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    opens com.teamfour.iae to javafx.fxml,MaterialFX;
    exports com.teamfour.iae;
}