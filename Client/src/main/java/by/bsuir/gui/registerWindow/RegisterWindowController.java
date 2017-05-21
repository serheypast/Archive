package by.bsuir.gui.registerWindow;

import by.bsuir.XMLParsers.implementations.ParserDaoSingleton;
import by.bsuir.gui.loginWindow.LoginWindow;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import by.bsuir.models.user.implementations.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * Created by Сергей on 05.03.2017.
 */
public class RegisterWindowController {

    /**
     * Set some Action for button OK
     * - if data is correctly create user with login some login and password
     * - if login is not a free then get error message
     * @param registerWindow
     */
    public static void setOnActionButtonOk(final RegisterWindow registerWindow){
        registerWindow.buttonOk.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if (checkField(registerWindow)) {
                    UserDao userDao = new UserDao();
                    userDao.setName(registerWindow.loginField.getText());
                    userDao.setPassword(registerWindow.passwordField_1.getText());
                    userDao.setRole(Role.USER);

                    ClientRequest clientRequest = new ClientRequest();
                    clientRequest.setNameOfRequest(ERequest.ADD_USER);
                    ParseData parseData = new ParseDataDao();
                    parseData.setType(TypeXml.USER);
                    parseData.setData(userDao);
                    String xmlDoc = ParserDaoSingleton.getParser().getCurrentParser().parseToXml(parseData);
                    clientRequest.setRequest(xmlDoc);
                    SocketDaoSingleton.getSocketSingelton().sendMessage(clientRequest);
                    ServerResponse response = (ServerResponse) SocketDaoSingleton.getSocketSingelton().getMessage();
                    switch (response.getNameOfResponse()) {
                        case USER_NOT_EXIST:
                            registerWindow.errorText.setText("User with this name already exists");
                            break;
                        case REQUEST_COMPLETE:
                            registerWindow.stage.setScene((new LoginWindow(registerWindow.stage).getScene()));
                            break;
                        default:
                            registerWindow.errorText.setText("User with this name already exists");
                            break;
                    }
                } else {
                    registerWindow.errorText.setText("Your entered data is incorrect");
                }
            }
        });
    }

    /**
     *  Set some Action for button Back
     *  -set LoginWindow scene
     * @param registerWindow
     */
    public static void setOnActionButtonBack(final RegisterWindow registerWindow){
        registerWindow.buttonBack.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                registerWindow.stage.setScene((new LoginWindow(registerWindow.stage).getScene()));
            }
        });
    }


    /**
     * Check login in data base
     * @param registerWindow that look some field
     * @return true if user exist
     */
    private static boolean checkField(RegisterWindow registerWindow){
        return (!registerWindow.loginField.getText().equals("")   &&
                (registerWindow.passwordField_1.getText().equals(registerWindow.passwordField_2.getText()) &&
                !registerWindow.passwordField_1.getText().equals("")))
                ? true : false;
    }
}
