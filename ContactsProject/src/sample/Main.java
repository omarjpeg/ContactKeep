package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ContactData.getInstance().loadContacts();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 740, 650));
        primaryStage.show();
    }
        public void stop(){
        ContactData.getInstance().saveContacts();
        }

    public static void main(String[] args) {
        launch(args);
    }
}
