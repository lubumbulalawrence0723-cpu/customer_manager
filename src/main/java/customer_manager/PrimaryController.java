package customer_manager;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

public class PrimaryController {
    @FXML private TextField nameField;
    @FXML private ComboBox<String> provinceBox;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> provinceColumn;
    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private final ObservableList<Customer> customers =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        provinceBox.getItems().addAll(
                "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western"
        );

        customerTable.setItems(customers);
        nameColumn.setCellValueFactory(cell ->
                cell.getValue().nameProperty());
        provinceColumn.setCellValueFactory(cell ->
                cell.getValue().provinceProperty());

        addButton.setDefaultButton(true);
        deleteButton.setDisable(true);

        customerTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldCustomer, selectedCustomer) ->
                        deleteButton.setDisable(selectedCustomer == null));
    }

    @FXML
    private void addCustomer() {
        String name = nameField.getText().trim();
        String province = provinceBox.getValue();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing name",
                    "Enter the customer's name.");
            nameField.requestFocus();
            return;
        }

        if (province == null) {
            showAlert(Alert.AlertType.ERROR, "Missing province",
                    "Choose a province.");
            provinceBox.requestFocus();
            return;
        }

        customers.add(new Customer(name, province));
        nameField.clear();
        provinceBox.getSelectionModel().clearSelection();
        nameField.requestFocus();
    }

    @FXML
    private void deleteCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm deletion");
        confirmation.setHeaderText("Delete this customer?");
        confirmation.setContentText(
                "Remove " + selected.getName() + " from the list?"
        );

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            customers.remove(selected);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}