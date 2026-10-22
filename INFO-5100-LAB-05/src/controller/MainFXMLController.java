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
import model.UserValidator;
import model.User;

/**
 * FXML Controller class
 *
 * @author amaan
 */
public class MainFXMLController implements Initializable {
    
    private static final int DEFAULT_AGE = 18;
    private static final String FIELD_ERROR_STYLE = "-fx-border-color: #c62828; -fx-border-radius: 3;";

    @FXML private TextField nameTextField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private TextField emailTextField;
    @FXML private Label nameErrorLabel;
    @FXML private Label ageErrorLabel;
    @FXML private Label emailErrorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                UserValidator.MIN_AGE, UserValidator.MAX_AGE, DEFAULT_AGE));

        // Only digits (max 3) may be typed into the spinner's text box
        ageSpinner.getEditor().textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d{0,3}")) {
                ageSpinner.getEditor().setText(oldText);
            }
        });

        // Validate each field when it loses focus
        nameTextField.focusedProperty().addListener((obs, was, isNow) -> {
            if (!isNow) checkName();
        });
        ageSpinner.getEditor().focusedProperty().addListener((obs, was, isNow) -> {
            if (!isNow) checkAge();
        });
        emailTextField.focusedProperty().addListener((obs, was, isNow) -> {
            if (!isNow) checkEmail();
        });
        
    }

    @FXML
    public void submit() {
        // Run all checks so every problem is shown at once
        boolean nameOk = checkName();
        boolean ageOk = checkAge();
        boolean emailOk = checkEmail();

        if (!(nameOk && ageOk && emailOk)) {
            showAlert("Please correct the highlighted fields.", Alert.AlertType.WARNING, "Invalid Input");
            return;
        }

        User user = new User(
                nameTextField.getText().trim(),
                Integer.parseInt(ageSpinner.getEditor().getText().trim()),
                emailTextField.getText().trim());

        showAlert("Registration Successful!\n\n" + user, Alert.AlertType.INFORMATION, "Success");
        clearForm();
    }

    private boolean checkName() {
        return show(nameTextField, nameErrorLabel, UserValidator.validateName(nameTextField.getText()));
    }

    private boolean checkAge() {
        return show(ageSpinner.getEditor(), ageErrorLabel, UserValidator.validateAge(ageSpinner.getEditor().getText()));
    }

    private boolean checkEmail() {
        return show(emailTextField, emailErrorLabel, UserValidator.validateEmail(emailTextField.getText()));
    }

    /** Shows or clears an error; returns true when the field is valid. */
    private boolean show(TextField field, Label label, String error) {
        label.setText(error == null ? "" : error);
        field.setStyle(error == null ? "" : FIELD_ERROR_STYLE);
        return error == null;
    }

    private void clearForm() {
        nameTextField.clear();
        emailTextField.clear();
        ageSpinner.getValueFactory().setValue(DEFAULT_AGE);
        ageSpinner.getEditor().setText(String.valueOf(DEFAULT_AGE));
        show(nameTextField, nameErrorLabel, null);
        show(ageSpinner.getEditor(), ageErrorLabel, null);
        show(emailTextField, emailErrorLabel, null);
        nameTextField.requestFocus();
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
