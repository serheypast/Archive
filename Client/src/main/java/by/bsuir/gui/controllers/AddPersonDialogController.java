package by.bsuir.gui.controllers;

import by.bsuir.XMLParsers.implementations.ParserDaoSingleton;
import by.bsuir.gui.mainWindow.MainWindowController;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.enumKinds.eRequest.ERequest;
import by.bsuir.models.enumKinds.eResponse.EResponse;
import by.bsuir.models.enumKinds.eType.TypeXml;
import by.bsuir.models.parseData.implementations.ParseDataDao;
import by.bsuir.models.parseData.interfaces.ParseData;
import by.bsuir.models.person.implementations.PersonDao;
import by.bsuir.models.person.implementations.WorkPersonDao;
import by.bsuir.models.request.implementations.ClientRequest;
import by.bsuir.models.response.implementations.ServerResponse;
import by.bsuir.socket.implementations.SocketDaoSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Сергей on 29.04.2017.
 */
public class AddPersonDialogController {
    @FXML
    private TextField tFieldFirstName;

    @FXML
    private TextField tFieldLastName;

    @FXML
    private TextField tFieldEmail;

    @FXML
    private TextField tFieldPhoneNumber;

    @FXML
    private TextField tFieldCompany;

    @FXML
    private TextField tFieldPosition;

    @FXML
    private TextField tFieldExperience;

    @FXML
    private TextField tFieldSalary;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnBack;

    @FXML
    private void initialize(){

    }


    @FXML
    private void setBtnOkAction(){
        PersonDao personDao = new PersonDao();
        String firstName = tFieldFirstName.getText();
        String lastName = tFieldLastName.getText();
        String email = tFieldEmail.getText();
        String phoneNumber  = tFieldPhoneNumber.getText();
        String company = tFieldCompany.getText();
        String position = tFieldPosition.getText();
        String salary = tFieldSalary.getText();
        String experience = tFieldExperience.getText();

        if(firstName.equals("")||lastName.equals("")||email.equals("")||phoneNumber.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            String s ="Input Error. Fill in the required fields (marked with a star)";
            alert.setContentText(s);
            alert.show();

        }else {
            personDao.setFirstName(firstName);
            personDao.setLastName(lastName);
            personDao.setEmail(email);
            personDao.setNumberPhone(phoneNumber);
            WorkPersonDao workPersonDao = new WorkPersonDao();
            workPersonDao.setNameOfCompany(company.equals("")? "Unknown":company);
            workPersonDao.setNameOfPosition(position.equals("") ? "Unknown":position);
            workPersonDao.setExperience(experience.equals("") ? 0:Integer.parseInt(experience));
            workPersonDao.setSalary(salary.equals("") ? -1 : Integer.parseInt(salary));
            personDao.setInfoAboutWork(workPersonDao);

            ParseData parseData = new ParseDataDao();
            parseData.setType(TypeXml.PERSON);
            parseData.setData(personDao);

            ClientRequest request = new ClientRequest();
            request.setNameOfRequest(ERequest.ADD_PERSON);
            request.setRequest(ParserDaoSingleton.getParser().getCurrentParser().parseToXml(parseData));

            SocketDaoSingleton.getSocketSingelton().sendMessage(request);
            ServerResponse response = (ServerResponse)SocketDaoSingleton.getSocketSingelton().getMessage();

            if(response.getNameOfResponse() == EResponse.REQUEST_COMPLETE){
                MainWindowController.persons.add(personDao);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Information");
                String s ="Person has been added";
                alert.setContentText(s);
                alert.show();
            }else{
                AppLogger.getLogger().error("Person dont exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                String s ="Person has'n been added";
                alert.setContentText(s);
                alert.show();
            }
            Stage stage = (Stage)btnBack.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void setBtnBack(){
        Stage stage = (Stage)btnBack.getScene().getWindow();
        stage.close();
    }

}
