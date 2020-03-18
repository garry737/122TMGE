package login.signup;

import TMGE.UserProfiles.UserDatabase;
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

public class SignUpGUI {

//    Stage window;
    UserDatabase userDatabase;
    Stage primaryStage;
    Scene mainMenuScene;

    public SignUpGUI(UserDatabase userDatabase, Stage primaryStage, Scene mainMenuScene){
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

        Text welcomeText = new Text("Create a New Account!");
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

        Label chooseIconLabel = new Label("Choose Icon:   ");
//        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        grid.add(chooseIconLabel, 0, 3);

        ProfilePictureGUI profilePictureGUI = new ProfilePictureGUI();
        Parent profilePictureGUIParent =profilePictureGUI.createChooseProfilePicture();
        grid.add(profilePictureGUIParent, 1,3);

        Button backbutton = new Button("< Back");
//        GridPane.setHalignment(backbutton, HPos.RIGHT);
        grid.add(backbutton, 0,4);

        Button creatAccButton = new Button("Create Account");
        grid.add(creatAccButton, 1,4);



        Label fillOutLabel = new Label("*Please fill out ALL text fields!");
        fillOutLabel.setTextFill(Color.RED);

        Label usernameExist = new Label("*Username already exist!");
        usernameExist.setTextFill(Color.RED);

        Label invalidInput = new Label("*Please dont put spaces in your Username or Password!");
        invalidInput.setTextFill(Color.RED);



        creatAccButton.setOnAction(e->{
            if (usernameTextField.getText().equals("") || passwordTextField.getText().equals("")){
                System.out.println("Please fill out ALL text fields!");

                if (grid.getChildren().contains(usernameExist)){
                    grid.getChildren().remove(usernameExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(invalidInput)){
                    grid.getChildren().remove(invalidInput);
                }

                grid.add(fillOutLabel, 1, 4);
                GridPane.setConstraints(backbutton, 0,5);
                GridPane.setConstraints(creatAccButton, 1,5);
            }
            else if (!this.userDatabase.checkValidUsernamePassword(usernameTextField.getText(), passwordTextField.getText())){

                if (grid.getChildren().contains(usernameExist)){
                    grid.getChildren().remove(usernameExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(invalidInput)){
                    grid.getChildren().remove(invalidInput);
                }

                grid.add(invalidInput, 1, 4);
                GridPane.setConstraints(backbutton, 0,5);
                GridPane.setConstraints(creatAccButton, 1,5);

            }

            else if(this.userDatabase.addUser(usernameTextField.getText(), passwordTextField.getText(), ProfilePictureGUI.ICON)){
                UserDatabase.CURRENT_USER = usernameTextField.getText().toLowerCase();
                UserDatabase.CURRENT_USER_PROFILE_PICTURE = ProfilePictureGUI.ICON;
                System.out.println("NEW USER CREATED");
                System.out.println("Username: " + UserDatabase.CURRENT_USER);
                System.out.println("Password: " + passwordTextField.getText());
                System.out.println("Username: " + UserDatabase.CURRENT_USER_PROFILE_PICTURE);
                this.primaryStage.setScene(this.mainMenuScene);
            }

            else {
                if (grid.getChildren().contains(usernameExist)){
                    grid.getChildren().remove(usernameExist);
                }
                if (grid.getChildren().contains(fillOutLabel)){
                    grid.getChildren().remove(fillOutLabel);
                }
                if (grid.getChildren().contains(invalidInput)){
                    grid.getChildren().remove(invalidInput);
                }
                GridPane.setRowIndex(passwordLabel, 3);
                GridPane.setRowIndex(passwordTextField, 3);

                GridPane.setRowIndex(chooseIconLabel, 4);
                GridPane.setRowIndex(profilePictureGUIParent, 4);

                GridPane.setConstraints(backbutton, 0,5);
                GridPane.setConstraints(creatAccButton, 1,5);

                grid.add(usernameExist, 1, 2);
            }

        });

        backbutton.setOnAction(e-> {
            LoginGUI loginGUI = new LoginGUI(this.userDatabase, this.primaryStage, this.mainMenuScene);
            this.primaryStage.setScene(new Scene(loginGUI.createContent(),700,500));
        });

        return grid;
    }
}
