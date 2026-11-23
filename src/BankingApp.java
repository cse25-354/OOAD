import Boundary.AccountView;
import Boundary.CustomerView;
import Boundary.LoginView;
import Controller.AccountController;
import Controller.CustomerController;
import Controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class BankingApp extends Application {

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        CustomerView customerView = new CustomerView();
        AccountView accountView = new AccountView();

        new LoginController(loginView, stage, customerView);
        CustomerController customerController = new CustomerController(customerView, stage, accountView);
        new AccountController(accountView, customerController);

        stage.setTitle("Banking System - Login");
        stage.setScene(loginView.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
