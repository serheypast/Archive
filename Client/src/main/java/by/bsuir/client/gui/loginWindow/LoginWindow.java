package by.bsuir.client.gui.loginWindow;


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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by Сергей on 22.02.2017.
 */
public class LoginWindow {

    protected Stage stage;
    protected Scene scene;
    protected GridPane grid;
    protected PasswordField passwordField;
    protected TextField textField;
    protected Button buttonGuest;
    protected Button buttonRegister;
    protected Button buttonSignIn;

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
    public LoginWindow(Stage stage) {
        grid = new GridPane();
        setGridPane();
        this.stage = stage;
    }

    /**
     * The set some properties and actions for field(object GridPane) gridPane
     * also set all their children and set Scene
     */
    private void setGridPane(){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        setWelcomeText();
        setPasswordField();
        setLoginField();
        setButtonSignIn();
        setButtonUser();
        setButtonRegister();

        scene = new Scene(grid,400,220);
    }

    /**
     * The set some properties for field(object Text) scenetitle
     */
    private void setWelcomeText(){
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Bodoni MT Italic", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.BLUEVIOLET);
        grid.add(scenetitle, 1, 0, 2, 1);
    }

    /**
     * * The set some properties for field(object PasswordField) passwordField
     */
    private void setPasswordField(){
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);
    }

    /**
     * * The set some properties for field(object TextField) textField
     */
    private void setLoginField(){
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        textField = new TextField();
        grid.add(textField, 1, 1);
    }

    /**
     * * The set some properties and actions for field(object Button) buttonGuest
     */
    private void setButtonUser(){
        buttonGuest = new Button("Sign in how Guest");
        buttonGuest.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonGuest,0,4);

        LoginWindowController.setOnActionButtonGuest(this);
    }

    /**
     * * The set some properties and actions for field(object Button) buttonRegister
     */
    private void setButtonRegister(){
        buttonRegister = new Button("Register");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(buttonRegister);
        grid.add(hbBtn, 1, 5);

        LoginWindowController.setOnActionButtonRegister(this);
    }

    /**
     * * The set some properties and actions for field(object Button) buttonSignIn
     */
    private  void setButtonSignIn(){
        buttonSignIn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(buttonSignIn);
        grid.add(hbBtn, 1, 4);

        LoginWindowController.setOnActionButtonSignIn(this);
    }
}