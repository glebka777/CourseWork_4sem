package com.controllers;

import com.entities.Client;
import com.exceptions.WrongInformationException;
import com.tools.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import static com.UserApplication.*;

public class LoginPageController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;

    @FXML
    private void keyListener(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loginButton.fire();
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            backButton.fire();
        }
    }

    @FXML
    private void loginButtonClick(ActionEvent event) {
        if (!validateInput()) return;
        Client client;
        try {
            client = getBookShop().loginUser(loginField.getText(), passwordField.getText());
        } catch(WrongInformationException e) {
            AlertBox.display("Неверный ввод", e.getMessage());
            return;
        }
        setClient(client);
        returnToMainWindow();
        loginField.clear();
        passwordField.clear();
    }

    @FXML
    private void backButtonClick(ActionEvent event) {
        returnToMainWindow();
    }

    private boolean validateInput() {
        String s;
        if ((s = loginField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Enter correct login.");
            return false;
        }
        else if ((s = passwordField.getText()) == null || s.trim().equals("")) {
            AlertBox.display("Неверный ввод", "Enter correct password.");
            return false;
        }
        return true;
    }

}
