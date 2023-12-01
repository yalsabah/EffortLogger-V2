package effortloggerv2;

import javafx.application.Application;
import javafx.stage.Stage;

public class EffortLoggerDemo extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        LoginPage1 loginPage = new LoginPage();
        mainStage.setTitle("EffortLogger V2");
        mainStage.setScene(loginPage.getScene(mainStage));
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
