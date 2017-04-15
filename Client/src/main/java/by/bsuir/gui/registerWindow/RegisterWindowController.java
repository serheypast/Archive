package by.bsuir.client.gui.registerWindow;

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

    }

    /**
     *  Set some Action for button Back
     *  -set LoginWindow scene
     * @param registerWindow
     */
    public static void setOnActionButtonBack(final RegisterWindow registerWindow){

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
