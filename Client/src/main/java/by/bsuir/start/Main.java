package by.bsuir.start;

import by.bsuir.gui.loginWindow.LoginWindow;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        stage = primaryStage;
        SocketDaoSingleton socketDaoSingleton = new SocketDaoSingleton();
        socketDaoSingleton.createConn();
        primaryStage.setScene((new LoginWindow(primaryStage)).getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
