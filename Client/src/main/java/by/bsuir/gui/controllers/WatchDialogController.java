package by.bsuir.gui.controllers;

import by.bsuir.gui.mainWindow.MainWindowController;
import by.bsuir.logger.AppLogger;
import by.bsuir.models.person.implementations.PersonDao;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;



/**
 * Created by Сергей on 29.04.2017.
 */
public class WatchDialogController {
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label email;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label company;

    @FXML
    private Label position;

    @FXML
    private Label experience;

    @FXML
    private Label salary;

    @FXML
    private Button btnOk;


    @FXML
    private void initialize(){
        try {
            PersonDao personDao = MainWindowController.currentPerson;
            firstName.setText(personDao.getFirstName());
            lastName.setText(personDao.getLastName());
            email.setText(personDao.getEmail());
            phoneNumber.setText(personDao.getNumberPhone());
            company.setText(personDao.getInfoAboutWork().getNameOfCompany());
            position.setText(personDao.getInfoAboutWork().getNameOfPosition());
            experience.setText(Integer.toString(personDao.getInfoAboutWork().getExperience()));
            salary.setText(Integer.toString(personDao.getInfoAboutWork().getSalary()));
        }catch (NullPointerException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

}