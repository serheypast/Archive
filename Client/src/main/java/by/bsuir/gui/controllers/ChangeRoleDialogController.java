package by.bsuir.gui.controllers;

import by.bsuir.XMLParsers.implementations.ParserDaoSingleton;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import by.bsuir.models.user.implementations.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Сергей on 29.04.2017.
 */
public class ChangeRoleDialogController {
    @FXML
    private Button btnChange;

    @FXML
    private Label userName;

    @FXML
    private Button btnBack;

    @FXML
    private ComboBox<Role> cBoxRole;

    @FXML
    private void initialize(){
        UserDao userDao = ChangeRoleWindowController.userDao;
        userName.setText(userDao.getName());
        cBoxRole.setValue(userDao.getRole());
        setcBoxRole();
    }

    private void setcBoxRole(){
        ObservableList<Role> roles = FXCollections.observableArrayList();
        ArrayList<Role> rl = new ArrayList<>();
        rl.add(Role.ADMIN);
        rl.add(Role.USER);
        roles.addAll(rl);
        cBoxRole.setItems(roles);
    }

    @FXML
    private void setBtnChangeAction(){
        ClientRequest request = new ClientRequest();
        request.setNameOfRequest(ERequest.CHANGE_ROLE);

        UserDao newUser = new UserDao();
        newUser.setName(userName.getText());
        newUser.setRole(cBoxRole.getValue());
        newUser.setPassword(ChangeRoleWindowController.userDao.getPassword());

        ParseDataDao parseData = new ParseDataDao();
        parseData.setType(TypeXml.USER);
        parseData.setData(newUser);

        request.setRequest(ParserDaoSingleton.getParser().getCurrentParser().parseToXml(parseData));
        SocketDaoSingleton.getSocketSingelton().sendMessage(request);

        ServerResponse response = (ServerResponse)SocketDaoSingleton.getSocketSingelton().getMessage();
        if(response.getNameOfResponse() != EResponse.REQUEST_COMPLETE){
            AppLogger.getLogger().error("Change role dont exist");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            String s ="Role hasn't been changed!";
            alert.setContentText(s);
            alert.show();
        }else {

            ChangeRoleWindowController.userDao.setRole(cBoxRole.getValue());
            ChangeRoleWindowController.listView.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Info");
            String s = "Person has been changed";
            alert.setContentText(s);
            alert.show();
        }
    }


    @FXML
    private void setBtnBack(){
        Stage stage = (Stage)btnBack.getScene().getWindow();
        stage.close();
    }
}
