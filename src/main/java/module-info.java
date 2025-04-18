module com.example.pupsis_main_dashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;

    opens com.example.pupsis_main_dashboard to javafx.fxml;
    exports com.example.pupsis_main_dashboard;
    exports com.example.pupsis_main_dashboard.controllers;
    opens com.example.pupsis_main_dashboard.controllers to javafx.fxml;
}