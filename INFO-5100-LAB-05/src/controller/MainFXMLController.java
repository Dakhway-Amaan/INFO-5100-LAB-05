/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import model.User;

/**
 * FXML Controller class
 *
 * @author amaan
 */
public class MainFXMLController implements Initializable {

    private static final int DEFAULT_AGE = 18;
    private static final String FIELD_ERROR_STYLE = "-fx-border-color: #c62828; -fx-border-radius: 3;";

    @FXML
    private TextField nameTextField;
    @FXML
    private Spinner<Integer> ageSpinner;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label ageErrorLabel;
    @FXML
    private Label emailErrorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, 18));

    }

    @FXML
    public void submit() {
        String name = nameTextField.getText().trim();
        String ageText = ageSpinner.getEditor().getText().trim();
        String email = emailTextField.getText().trim();

        // ----- Name: required, 2-50 chars, letters, spaces, hyphens, apostrophes -----
        if (name.isEmpty()) {
            reject("Name is required.", "Missing Data", nameTextField);
            return;
        }
        if (name.length() < 2 || name.length() > 50) {
            reject("Name must be between 2 and 50 characters.", "Name Error", nameTextField);
            return;
        }
        if (!name.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*")) {
            reject("Name can only contain letters, spaces, hyphens and apostrophes.",
                    "Name Error", nameTextField);
            return;
        }

        // ----- Age: required, whole number, 1-120 -----
        // Reads the typed text, because getValue() can be out of date if the user typed a value
        if (ageText.isEmpty()) {
            reject("Age is required.", "Missing Data", ageSpinner);
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            reject("Age must be a whole number.", "Age Error", ageSpinner);
            return;
        }
        if (age < 1 || age > 120) {
            reject("Age must be between 1 and 120.", "Age Error", ageSpinner);
            return;
        }

        // ----- Email: required, valid format -----
        if (email.isEmpty()) {
            reject("Email is required.", "Missing Data", emailTextField);
            return;
        }
        if (email.length() > 254 || email.contains("..")
                || !email.matches("[A-Za-z0-9._%+\\-]+@[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?"
                        + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?)*\\.[A-Za-z]{2,}")) {
            reject("Please enter a valid email address (e.g. name@example.com).",
                    "Email Error", emailTextField);
            return;
        }

        // ----- All valid -----
        User user = new User(name, age, email);
        showAlert("Registration Successful!\n\n" + user, Alert.AlertType.INFORMATION, "Success");

        // Reset the form
        nameTextField.clear();
        emailTextField.clear();
        ageSpinner.getValueFactory().setValue(18);
        ageSpinner.getEditor().setText("18");
        nameTextField.requestFocus();
    }

    /**
     * Shows a warning dialog, then puts the cursor in the field that needs
     * fixing.
     */
    private void reject(String message, String header, javafx.scene.Node field) {
        showAlert(message, Alert.AlertType.WARNING, header);
        field.requestFocus();
    }

    private void showAlert(String message, Alert.AlertType type, String header) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                getClass().getResource("/ui/style.css").toExternalForm());
        alert.showAndWait();
    }
}
