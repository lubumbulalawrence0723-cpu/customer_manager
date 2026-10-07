module customer_manager {
    requires javafx.controls;
    requires javafx.fxml;

    opens customer_manager to javafx.fxml;
    exports customer_manager;
}
