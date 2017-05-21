package by.bsuir.gui.loginWindow;

import by.bsuir.XMLParsers.implementations.ParserDaoSingleton;
import by.bsuir.gui.registerWindow.RegisterWindow;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import by.bsuir.models.user.implementations.UserDao;
import by.bsuir.models.user.interfaces.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;



/**
 * Created by Сергей on 05.03.2017.
 */
public class LoginWindowController {

    public static UserDao userDao;

    /**
     * Set action in Buttuon Sign In
     * - check correct input data
     * - if data is correctly set MainWidnod scene
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonSignIn(final LoginWindow loginWindow){
       loginWindow.buttonSignIn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               String login = loginWindow.textField.getText();
               String password = loginWindow.passwordField.getText();
               User user = new UserDao();
               user.setRole(Role.USER);
               user.setName(login);
               user.setPassword(password);
               if(!(login.equals("") || password.equals(""))){
                   ClientRequest clientRequest = new ClientRequest();
                   clientRequest.setNameOfRequest(ERequest.CHECK_USER);
                   ParseData parseData = new ParseDataDao();
                   parseData.setType(TypeXml.USER);
                   parseData.setData(user);
                   String xmlDoc = ParserDaoSingleton.getParser().getCurrentParser().parseToXml(parseData);
                   clientRequest.setRequest(xmlDoc);
                   SocketDaoSingleton.getSocketSingelton().sendMessage(clientRequest);
                   ServerResponse response = (ServerResponse)SocketDaoSingleton.getSocketSingelton().getMessage();
                   switch (response.getNameOfResponse()){
                       case USER_NOT_EXIST:
                           break;
                       case USER_EXIST:
                           userDao = (UserDao)response.getResponseInfo();
                           try {
                               Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
                               Scene scene = new Scene(root,645,409);
                               loginWindow.stage.setScene(scene);
                           }catch (Exception e){
                               AppLogger.getLogger().error(e.getMessage());
                           }
                           break;
                       default:
                           System.out.println("err");
                           break;
                   }
               }
           }
       });
    }

    /**
     *Set action in Buttuon Register
     * - set RegisterWindow scene
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonRegister(final LoginWindow loginWindow){
        loginWindow.buttonRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginWindow.stage.setScene((new RegisterWindow(loginWindow.stage)).getScene());
            }
        });

    }

    /**
     * *Set action in Buttuon sign how a Guest
     * - set some Limitations in next scene
     * - set MainWindow scene with some limitations
     * @param loginWindow object LoginWidnow for set some Actions
     */
    public static void setOnActionButtonGuest(final LoginWindow loginWindow){
        userDao = new UserDao();
        userDao.setRole(Role.GUEST);
        userDao.setName("GUEST");
        loginWindow.buttonGuest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    final UserDao user = new UserDao();
                    user.setRole(Role.GUEST);
                    user.setName(Role.GUEST.getRole());
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
                    Scene scene = new Scene(root,645,409);
                    loginWindow.stage.setScene(scene);
                }catch (Exception e){
                    AppLogger.getLogger().error(e.getMessage());
                }
            }
        });

    }

}
