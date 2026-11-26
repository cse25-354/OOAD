import Controller.AccountDetailsController;
import Controller.AccountsOverviewController;
import Controller.CustomerOperationsController;
import Controller.LoginController;
import Controller.OpenNewAccountController;
import Controller.RegisterCustomerController;
import DAO.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseConnection.createTables();

        FXMLLoader overviewLoader = new FXMLLoader(getClass().getResource("/Boundary/AccountsOverviewView.fxml"));
        AccountsOverviewController overviewController = new AccountsOverviewController(stage);
        overviewLoader.setController(overviewController);
        Scene accountsOverviewScene = new Scene(overviewLoader.load());

        FXMLLoader openAccLoader = new FXMLLoader(getClass().getResource("/Boundary/OpenNewAccountView.fxml"));
        OpenNewAccountController openNewAccountController = new OpenNewAccountController(stage);
        openAccLoader.setController(openNewAccountController);
        Scene openNewAccountScene = new Scene(openAccLoader.load());

        FXMLLoader detailsLoader = new FXMLLoader(getClass().getResource("/Boundary/AccountDetailsView.fxml"));
        AccountDetailsController accountDetailsController = new AccountDetailsController(stage);
        detailsLoader.setController(accountDetailsController);
        Scene accountDetailsScene = new Scene(detailsLoader.load());

        FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/Boundary/RegisterCustomerView.fxml"));
        RegisterCustomerController registerController = new RegisterCustomerController(stage, openNewAccountScene, openNewAccountController);
        registerLoader.setController(registerController);
        Scene registerCustomerScene = new Scene(registerLoader.load());

        FXMLLoader operationsLoader = new FXMLLoader(getClass().getResource("/Boundary/CustomerOperationsView.fxml"));
        CustomerOperationsController operationsController = new CustomerOperationsController(stage, registerCustomerScene, accountsOverviewScene, overviewController);
        operationsLoader.setController(operationsController);
        Scene operationsScene = new Scene(operationsLoader.load());

        overviewController.setNavigation(operationsScene, openNewAccountScene, openNewAccountController, accountDetailsScene, accountDetailsController);
        openNewAccountController.setNavigation(operationsScene, accountsOverviewScene, overviewController);
        accountDetailsController.setNavigation(operationsScene, accountsOverviewScene, overviewController);
        registerController.setOperationsScene(operationsScene);
        operationsController.setOperationsScene(operationsScene);

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/Boundary/LoginView.fxml"));
        loginLoader.setController(new LoginController(stage, operationsScene));

        stage.setTitle("Staff Login");
        stage.setScene(new Scene(loginLoader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
