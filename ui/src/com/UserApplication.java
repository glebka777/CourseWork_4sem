package com;

import com.controllers.BookViewPageController;
import com.entities.Book;
import com.entities.Client;
import com.controllers.LoginPageController;
import com.controllers.MainWindowController;
import com.controllers.PersonalPageController;
import com.controllers.RegisterPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class UserApplication extends Application {

    public static double WIDTH;
    public static double HEIGHT;
    public static PersonalPageController personalPageController;
    static Stage primaryStage;
    static Stage secondaryStage;
    static Scene mainPage;
    static Scene personalPage;
    static Scene loginPage;
    static Scene registerPage;
    static MainWindowController mainWindowController;
    static LoginPageController loginPageController;
    static RegisterPageController registerPageController;

    static BookShop bookShop;
    static Client client = null;

    public static void main(String[] args) {
        launch(args);
    }

    public static void setMainPage() {
        mainWindowController.refreshClient();
        primaryStage.setScene(mainPage);
        primaryStage.centerOnScreen();
    }

    public static void showLoginPage() {
        secondaryStage = new Stage();
        secondaryStage.setScene(loginPage);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.centerOnScreen();
        secondaryStage.showAndWait();
    }

    public static void showBookStage(Book book) {
        FXMLLoader fxmlLoader = new FXMLLoader(UserApplication.class.getResource("/com/fxml/bookview_page.fxml"));
        Scene bookViewPage = null;
        try {
            bookViewPage = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        } catch(IOException e) {
            e.printStackTrace();
        }
        BookViewPageController bookViewPageController = fxmlLoader.getController();
        bookViewPageController.setFields(book, WIDTH / 200 * 85, HEIGHT / 100 * 80);
        Stage stage = new Stage();
        stage.setTitle(book.getAuthor() + " :: " + book.getName());
        stage.setScene(bookViewPage);

        Client c;
        if ((c = getClient()) != null) {
            bookViewPageController.addToCartButton.setDisable(false);
            if (c.getCart().contains(book.getIsbn())) {
                bookViewPageController.removeFromCartButton.setDisable(false);
            }
        }

        stage.show();
    }

    public static void returnToMainWindow() {
        secondaryStage.hide();
        mainWindowController.refreshClient();
    }

    public static void showLoginPage(String login, String password) {
        secondaryStage.setScene(loginPage);
        loginPageController.loginField.setText(login);
        loginPageController.passwordField.setText(password);
        secondaryStage.centerOnScreen();
    }

    public static void showRegisterPage() {
        secondaryStage = new Stage();
        secondaryStage.setScene(registerPage);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.centerOnScreen();
        secondaryStage.showAndWait();
    }

    public static void setPersonalPage() {
        personalPageController.clientRefresh();
        primaryStage.setScene(personalPage);
        primaryStage.centerOnScreen();
    }

    public static void logOutClient() {
        client = null;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        UserApplication.client = client;
    }

    public static BookShop getBookShop() {
        return bookShop;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        bookShop = new BookShop();

        secondaryStage = new Stage();
        UserApplication.primaryStage = primaryStage;
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        WIDTH = visualBounds.getWidth() / 100 * 75;
        HEIGHT = visualBounds.getHeight() / 100 * 75;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/personal_page.fxml"));
        personalPage = new Scene(fxmlLoader.load(),
                WIDTH / 100 * 150, HEIGHT);
        personalPageController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/login_page.fxml"));
        loginPage = new Scene(fxmlLoader.load(), WIDTH / 100 * 45, HEIGHT / 100 * 45);
        loginPageController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/register_page.fxml")
        );
        registerPage = new Scene(fxmlLoader.load(), WIDTH / 100 * 50, HEIGHT / 100 * 80);
        registerPageController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(getClass().getResource("/com/fxml/main_window.fxml"));
        mainPage = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        mainWindowController = fxmlLoader.getController();

        primaryStage.setScene(mainPage);
        primaryStage.show();
        mainWindowController.refreshClient();
    }

}

