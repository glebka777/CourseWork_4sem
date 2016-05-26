package com.controllers;

import com.exceptions.WrongInformationException;
import com.tools.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.regex.Pattern;

import static com.UserApplication.*;

public class RegisterPageController {

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void keyListener(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            registerButton.fire();
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            backButton.fire();
        }
    }

    @FXML
    private void registerButtonClick(ActionEvent event) {
        if (!validateInput()) return;
        try {
            getBookShop().registerUser(firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText(),
                    addressField.getText(), emailField.getText(), loginField.getText(), passwordField.getText());
        } catch(WrongInformationException e) {
            AlertBox.display("Ошибка регистрации", e.getMessage());
            return;
        }
        showLoginPage(loginField.getText(), passwordField.getText());
        firstNameField.clear();
        lastNameField.clear();
        phoneNumberField.clear();
        addressField.clear();
        emailField.clear();
        loginField.clear();
        passwordField.clear();
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        returnToMainWindow();
    }

    private boolean validateInput() {
        String s;
        if ((s = firstNameField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Введите имя.");
            return false;
        }
        if ((s = phoneNumberField.getText()) == null || s.trim().equals("") || !Pattern.matches("[0-9]+", s)) {
            AlertBox.display("Неверный ввод", "Введите правильный номер телефона.");
            return false;
        }
        if ((s = addressField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Введите адрес.");
            return false;
        }
        if ((((s = emailField.getText())) == null) || s.trim().equals("") || !Pattern.matches("[a-zA-Z0-9]+@" +
                "[a-zA-Z]+\\.[a-zA-Z]+", s)) {
            AlertBox.display("Неверный ввод", "Введите правильный email.");
            return false;
        }
        if ((s = loginField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Введите имя пользователя.");
            return false;
        }
        else if ((s = passwordField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Введите пароль.");
            return false;
        }
        return true;
    }

}
