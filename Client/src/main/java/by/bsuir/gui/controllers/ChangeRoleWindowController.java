package by.bsuir.gui.controllers;

import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import by.bsuir.models.user.implementations.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Сергей on 29.04.2017.
 */
public class ChangeRoleWindowController {


    @FXML
    private ListView<UserDao> listViewUser;

    @FXML
    private Button btnChangeRole;

    @FXML
    private Button btnBack;

    public static UserDao userDao;

    protected static ObservableList<UserDao> userDaos;
    protected static ListView<UserDao> listView;

    @FXML
    private void initialize(){
        try {
            ClientRequest request = new ClientRequest();
            request.setNameOfRequest(ERequest.GET_USERS);
            SocketDaoSingleton.getSocketSingelton().sendMessage(request);
            ServerResponse response = (ServerResponse) SocketDaoSingleton.getSocketSingelton().getMessage();
            ArrayList<UserDao> users = (ArrayList<UserDao>) response.getResponseInfo();
            final ObservableList<UserDao> usersArr = FXCollections.observableArrayList();
            usersArr.addAll(users);
            listViewUser.setItems(usersArr);
            userDaos = usersArr;
            listView = listViewUser;
        }catch (NullPointerException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @FXML
    private void setBtnChangeRoleAction(){
        UserDao user = listViewUser.getSelectionModel().getSelectedItem();
        if(user != null) {
            try {
                userDao = user;
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("changeRoleDialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(new Scene(root));
                dialogStage.show();
            } catch (IOException e) {

            }
        }
    }

    @FXML
    private void setBtnBackAction(){
        Stage stage = (Stage)btnBack.getScene().getWindow();
        stage.close();
    }
}
