package by.bsuir.gui.mainWindow;

import by.bsuir.XMLParsers.implementations.ParserDaoSingleton;
import by.bsuir.gui.loginWindow.LoginWindow;
import by.bsuir.gui.loginWindow.LoginWindowController;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.EParser;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eRole.Role;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import by.bsuir.models.user.implementations.UserDao;
import by.bsuir.start.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Сергей on 14.04.2017.
 */
public class MainWindowController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnWatch;

    @FXML
    private Button btnChange;

    @FXML
    private Label labelName;

    @FXML
    private Label labelParser;

    @FXML
    private Label labelRole;

    @FXML
    private TableView<PersonDao> tblPerson;

    @FXML
    private Hyperlink hlBrowser;

    @FXML
    private Hyperlink hlUserAction;

    @FXML
    private ComboBox cBoxParser;

    @FXML
    private Button btnChangeUserRole;

    @FXML
    private TableColumn<PersonDao, String> tcFirstName;

    @FXML
    private TableColumn<PersonDao, String> tcLastName;

    @FXML
    private TableColumn<PersonDao, String> tcPhone;

    @FXML
    private TableColumn<PersonDao, String> tcEmail;

    public static TableView<PersonDao> tableView;
    private UserDao user;
    public  static PersonDao currentPerson;
    private Stage stage;
    public static ObservableList<PersonDao> persons;
    public static EParser parser = EParser.DOM;

    @FXML
    /**
     * initializes entered users name and visible elements according users role,
     * also method sets type of cell properties in columns of treeTableView object
     * and initiate event filter for Mouse event in treeTable view object
     */
    private void initialize(){
        System.out.println("initialize mainWIndow");
        this.user = LoginWindowController.userDao;
        labelName.setText("Login: " + user.getName());
        labelRole.setText("Role: " +user.getRole().getRole());
        hlUserAction.setText("Log out");
        if(LoginWindowController.userDao.getRole() == Role.GUEST){
            btnAdd.setVisible(false);
            btnChange.setVisible(false);
            btnDelete.setVisible(false);
            cBoxParser.setVisible(false);
            btnChangeUserRole.setVisible(false);
            labelParser.setVisible(false);
            hlUserAction.setText("Sign in");
        }else{
            if(user.getRole() == Role.USER){
                btnDelete.setVisible(false);
                cBoxParser.setVisible(false);
                btnChangeUserRole.setVisible(false);
                labelParser.setVisible(false);
            }
        }
        try {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setNameOfRequest(ERequest.GET_PERSONS);
            SocketDaoSingleton.getSocketSingelton().sendMessage(clientRequest);
            ServerResponse response = (ServerResponse) SocketDaoSingleton.getSocketSingelton().getMessage();
            persons = FXCollections.observableArrayList();
            persons.addAll((ArrayList<PersonDao>) response.getResponseInfo());
            initTable();
            initCParser();
            tblPerson.setItems(persons);
            tableView = tblPerson;
        }catch (Exception e){
            AppLogger.getLogger().error(e.getMessage());
        }

    }


    private void initCParser(){
        ObservableList<EParser> eParsers = FXCollections.observableArrayList();
        eParsers.add(EParser.DOM);
        eParsers.add(EParser.JDOM);
        eParsers.add(EParser.SAX);
        eParsers.add(EParser.STAX);
        cBoxParser.setItems(eParsers);
        cBoxParser.setValue(parser);
    }

    private void initTable(){
        tcFirstName.setCellValueFactory(new PropertyValueFactory<PersonDao, String>("firstName"));
        tcLastName.setCellValueFactory(new PropertyValueFactory<PersonDao, String>("lastName"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<PersonDao, String>("email"));
    }

    @FXML
    private void setBtnAddAction(){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.show();
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @FXML
    private void setBtnWatchAction(){
        if(tblPerson.getSelectionModel().getSelectedItem() != null) {
            PersonDao personDao = tblPerson.getSelectionModel().getSelectedItem();
            currentPerson = personDao;
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("watchDialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(new Scene(root));
                dialogStage.show();
            } catch (IOException e) {
                AppLogger.getLogger().error(e.getMessage());
            }
        }else {
            System.out.println("aa");
        }
    }

    @FXML
    private void setBtnDeleteAction(){
        PersonDao personDao = tblPerson.getSelectionModel().getSelectedItem();
        if(personDao != null){

            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setNameOfRequest(ERequest.DELETE_PERSON);

            ParseDataDao parseDataDao = new ParseDataDao();
            parseDataDao.setType(TypeXml.PERSON);
            parseDataDao.setData(personDao);
            clientRequest.setRequest(ParserDaoSingleton.getParser().getCurrentParser().parseToXml(parseDataDao));
            SocketDaoSingleton.getSocketSingelton().sendMessage(clientRequest);
            ServerResponse serverResponse = (ServerResponse)SocketDaoSingleton.getSocketSingelton().getMessage();
            if(serverResponse.getNameOfResponse() == EResponse.REQUEST_COMPLETE){
                tblPerson.getItems().remove(personDao);
                persons.remove(personDao);
            } else{
                AppLogger.getLogger().error("Person not a delete");
            }
        }
    }

    @FXML
    private void setBtnChangeAction(){
        try {
            currentPerson = tblPerson.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("changePersonDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.show();
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
    }


    @FXML
    private void setBtnChangeUserRole(){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ChangeRoleWindow.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.show();
        } catch (IOException e) {
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @FXML
    private void setCBoxParserChange(){
        ClientRequest request = new ClientRequest();
        request.setNameOfRequest(ERequest.CHANGE_PARSER);
        request.setRequest(cBoxParser.getValue().toString());
        SocketDaoSingleton.getSocketSingelton().sendMessage(request);
        ServerResponse response = (ServerResponse)SocketDaoSingleton.getSocketSingelton().getMessage();
        if(response.getNameOfResponse() == EResponse.REQUEST_NOT_COMPLETE){
            AppLogger.getLogger().error("Parser dont changed");
        }
        parser = (EParser)cBoxParser.getValue();
    }

    @FXML
    private void setHlBrowserAction(){

    }

    @FXML
    private void setHlUserAction(){
        Main.stage.setScene((new LoginWindow(Main.stage)).getScene());
    }


}
