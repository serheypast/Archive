package by.bsuir.gui.registerWindow;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Сергей on 22.02.2017.
 */
public class RegisterWindow {
    protected Stage stage;


    protected Scene scene;
    protected GridPane grid;
    protected TextField loginField;
    protected PasswordField passwordField_1;
    protected PasswordField passwordField_2;
    protected Button buttonBack;
    protected Button buttonOk;
    protected Text errorText;
    private HBox hBox;


    /**
     * Return scene that is a window
     * @return Scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructor that initialize all fields
     * @param stage super Stage that allow swap scene
     */
    public RegisterWindow(Stage stage) {
        grid = new GridPane();
        setGrid();
        this.stage = stage;
        scene = new Scene(grid,300,190);
    }

    /**
     * The set some properties and actions for field(object GridPane) gridPane
     * also set all their children and set Scene
     */
    private void setGrid(){

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        setErrorText();
        setRegistrerText();
        setLoginField();
        setPasswordField_1();
        setPasswordField_2();
        sethBox();


    }

    /**
     * The set some properties for field(object Text) errorTezt
     * Message of incorrect input
     */
    private void setErrorText(){
        errorText = new Text();
        grid.add(errorText, 1, 6);
        errorText.setFill(Color.DARKRED);
    }

    /**
     * The set some properties for field(object Text) text
     */
    private void setRegistrerText(){
        Text text = new Text("Registration");
        grid.add(text,1,0);
    }

    /**
     * The set some properties for field(object Label) userName
     * The set some properties for field(object TextField) loginField
     */
    private void setLoginField(){
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        loginField = new TextField();
        grid.add(loginField, 1, 1);
    }

    /**
     * The set some properties for field(object Label) pw
     * The set some properties for field(object PasswordField) passwordField
     */
    private void setPasswordField_1(){
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        passwordField_1 = new PasswordField();
        grid.add(passwordField_1, 1, 2);
    }

    /**
     * The set some properties for field(object Label) pw
     * The set some properties for field(object PasswordField) passwordField_2
     */
    private void setPasswordField_2(){
        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        passwordField_2 = new PasswordField();
        grid.add(passwordField_2, 1, 3);
    }


    /**
     * * The set some properties and actions for field(object Button) buttonOk
     */
    private void setButtonOk(){
        buttonOk = new Button("Ok");
        RegisterWindowController.setOnActionButtonOk(this);

    }

    /**
     * * The set some properties and actions for field(object Button) buttonBack
     */
    private void setButtonBack() {
        buttonBack = new Button("Back");
        RegisterWindowController.setOnActionButtonBack(this);
    }

    /**
     * * The set some properties  for field(object HBox) hBox
     */
    private void sethBox(){
        hBox = new HBox(10);
        setButtonBack();
        setButtonOk();

        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(buttonBack);
        hBox.getChildren().add(buttonOk);
        grid.add(hBox, 1, 4);
    }
}
