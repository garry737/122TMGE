package TMGE.GUI;

import TMGE.UserProfiles.UserDatabase;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginGUI {
//    Stage window;
    UserDatabase userDatabase;
    Stage primaryStage;
    Scene mainMenuScene;

    public LoginGUI(UserDatabase userDatabase, Stage primaryStage, Scene mainMenuScene){
        this.userDatabase = userDatabase;
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }

    public Parent createContent(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(50,50,50,50));

        Text welcomeText = new Text("Welcome! Please Login!");
        welcomeText.setFont(Font.font(25));
        grid.add(welcomeText, 0, 0);
        GridPane.setColumnSpan(welcomeText, 2);

        Label usernameLabel = new Label("Username:   ");
//        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        grid.add(usernameLabel, 0, 1);

        TextField usernameTextField = new TextField();
        usernameTextField.setPromptText("username");
        grid.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:   ");
//        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        grid.add(passwordLabel, 0, 2);

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("password");
        grid.add(passwordTextField, 1, 2);

        Button loginButton = new Button("Login");
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        grid.add(loginButton, 0,3);
        Button signUpButton = new Button("Sign Up");
        grid.add(signUpButton, 1,3);

        Label usernameNotExist = new Label("*Username does not exist!");
        usernameNotExist.setTextFill(Color.RED);

        Label fillOutLabel = new Label("*Please fill out ALL text fields!");
        fillOutLabel.setTextFill(Color.RED);

        Label wrongPassword = new Label("*Incorrect Password!");
        wrongPassword.setTextFill(Color.RED);

        loginButton.setOnAction(e->{

            if (usernameTextField.getText().equals("") || passwordTextField.getText().equals("")){
                System.out.println("Please fill out ALL text fields!");

                if (grid.getChildren().contains(usernameNotExist)){
                    grid.getChildren().remove(usernameNotExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(wrongPassword)){
                    grid.getChildren().remove(wrongPassword);
                }

                grid.add(fillOutLabel, 1, 4);
            }
            else if(userDatabase.login(usernameTextField.getText())){
                if(this.userDatabase.checkPassword(usernameTextField.getText().toLowerCase(), passwordTextField.getText())){
                    UserDatabase.CURRENT_USER = usernameTextField.getText().toLowerCase();
                    UserDatabase.CURRENT_USER_PROFILE_PICTURE = this.userDatabase.getProfilePictures().get(UserDatabase.CURRENT_USER);
                    System.out.println("USER RETRIEVED");
                    System.out.println("Username: " + UserDatabase.CURRENT_USER);
                    System.out.println("Password: " + passwordTextField.getText());
                    System.out.println("Username: " + UserDatabase.CURRENT_USER_PROFILE_PICTURE);
                    this.primaryStage.setScene(this.mainMenuScene);
                }

                if (grid.getChildren().contains(usernameNotExist)){
                    grid.getChildren().remove(usernameNotExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(wrongPassword)){
                    grid.getChildren().remove(wrongPassword);
                }
                GridPane.setRowIndex(loginButton, 4);
                GridPane.setRowIndex(signUpButton, 4);

                grid.add(wrongPassword, 1, 3);

            }else{
//                GridPane.setColumnIndex(passwordLabel, 3);
                if (grid.getChildren().contains(usernameNotExist)){
                    grid.getChildren().remove(usernameNotExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(wrongPassword)){
                    grid.getChildren().remove(wrongPassword);
                }

                GridPane.setRowIndex(passwordLabel, 3);
                GridPane.setRowIndex(passwordTextField, 3);
                GridPane.setRowIndex(loginButton, 4);
                GridPane.setRowIndex(signUpButton, 4);

                grid.add(usernameNotExist, 1, 2);
            }
        });

        signUpButton.setOnAction(e->{
            SignUpGUI signUpGUI = new SignUpGUI(this.userDatabase, this.primaryStage, this.mainMenuScene);
            this.primaryStage.setScene(new Scene(signUpGUI.createContent(),700,500));
        });

        return grid;
    }
}
