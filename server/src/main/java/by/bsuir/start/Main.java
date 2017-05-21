package by.bsuir.start;


import by.bsuir.socket.implementations.ServerSocketDaoSingleton;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocketDaoSingleton server = new ServerSocketDaoSingleton();
                server.createServerSocket();
                server.waitClient();
            }
        }).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
