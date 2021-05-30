module TicTacToe {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.web;

    opens com.slemmer to javafx.fxml;

    exports com.slemmer;
    exports com.slemmer.controller;
    opens com.slemmer.controller to javafx.fxml;
}