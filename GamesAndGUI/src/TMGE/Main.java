package TMGE;

import TMGE.GUI.BejeweledUI;
import TMGE.GUI.TenTenGUI;
import TMGE.Game.BejeweledGame;
import TMGE.Game.TenTenGame;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import TMGE.GUI.LoginGUI;
import UserProfiles.UserDatabase;

public class Main extends Application {

    Stage window;
    Scene scene1, scene2;
    public static Scene MAIN_MENU_SCENE= null;
    public static Stage PRIMARY_STAGE = null;
    public static UserDatabase GLOBAL_USER_DATABASE = null;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

//        primaryStage.setScene(new Scene(loginGUI.createContent(),500,500));
//        primaryStage.show();


        window = primaryStage;
        window.setTitle("Tile Matching Game Environment");

        GridPane mainMenuGrid = new GridPane();
        mainMenuGrid.setPadding(new Insets(50,50,50,50));
        mainMenuGrid.setVgap(30);
        mainMenuGrid.setHgap(30);
        Scene mainMenuScene = new Scene(mainMenuGrid, 530,400);

        GridPane bejeweledMenuGrid = new GridPane();
        bejeweledMenuGrid.setPadding(new Insets(50,50,50,50));
        bejeweledMenuGrid.setVgap(30);
        bejeweledMenuGrid.setHgap(55);
        Scene bejeweledMenuScene = new Scene(bejeweledMenuGrid, 530,400);

        GridPane bejeweledHowGrid = new GridPane();
        bejeweledHowGrid.setPadding(new Insets(50,50,50,50));
        bejeweledHowGrid.setVgap(30);
        bejeweledHowGrid.setHgap(30);
        Scene bejeweledHowScene = new Scene(bejeweledHowGrid, 530,400);

        GridPane tentenMenuGrid = new GridPane();
        tentenMenuGrid.setPadding(new Insets(50,50,50,50));
        tentenMenuGrid.setVgap(30);
        tentenMenuGrid.setHgap(55);
        Scene tentenMenuScene = new Scene(tentenMenuGrid, 530,400);

        GridPane tentenHowGrid = new GridPane();
        tentenHowGrid.setPadding(new Insets(50,50,50,50));
        tentenHowGrid.setVgap(30);
        tentenHowGrid.setHgap(30);
        Scene tentenHowScene = new Scene(tentenHowGrid, 530,400);

        Label helloLabel = new Label("      Sup Playa!  ");
        GridPane.setConstraints(helloLabel, 3,2);
        Label chooseLabel = new Label("Choose a game");
        GridPane.setConstraints(chooseLabel, 3,3);
        Button bejeweledButton = new Button("Bejeweled");
        GridPane.setConstraints(bejeweledButton, 2,4);
        bejeweledButton.setOnAction(e -> window.setScene(bejeweledMenuScene));
        Button tentenButton = new Button("      1010!      ");
        GridPane.setConstraints(tentenButton, 4,4);
        tentenButton.setOnAction(e -> window.setScene(tentenMenuScene));

        mainMenuGrid.getChildren().addAll(helloLabel,chooseLabel,bejeweledButton,tentenButton);

        Label bejeweledTitleLabel = new Label("       Bejeweled");
        GridPane.setConstraints(bejeweledTitleLabel, 3,1);
        Button bejeweledStartButton = new Button("  Start Game  ");
        bejeweledStartButton.setOnMouseClicked(mouseEvent -> {
            BejeweledGame game = new BejeweledGame(8,8);
            game.initValidBoard();
            BejeweledUI ui = new BejeweledUI(game);
            primaryStage.setScene(new Scene(ui.createContent()));
            primaryStage.show();
        });
        GridPane.setConstraints(bejeweledStartButton, 3,2);
        //bejeweledStartButton.setOnAction(e -> window.setScene(INSERT BEJEWELED SCENE HERE));
        Button bejeweledHowButton = new Button("  How to Play  ");
        GridPane.setConstraints(bejeweledHowButton, 3,3);
        bejeweledHowButton.setOnAction(e -> window.setScene(bejeweledHowScene));
        Button bejeweledBackButton = new Button("Back to Menu");
        GridPane.setConstraints(bejeweledBackButton, 3,4);
        bejeweledBackButton.setOnAction(e -> window.setScene(mainMenuScene));

        bejeweledMenuGrid.getChildren().addAll(bejeweledTitleLabel, bejeweledStartButton, bejeweledHowButton, bejeweledBackButton);

        Label bejeweledHowLabel = new Label("How to Play: Bejeweled");
        GridPane.setConstraints(bejeweledHowLabel, 1,1);
        Label bejeweledInstructionsLabel = new Label("In Bejeweled, you must swap gems to make a match of three or more gems in a vertical or horizontal row. Clear gems to gain points. Try and get the highest possible points you can in the given time.\n\nHave Fun!");
        bejeweledInstructionsLabel.setWrapText(true);
        GridPane.setConstraints(bejeweledInstructionsLabel, 1,2);
        Button bejeweledHowBackButton = new Button("< Back");
        GridPane.setConstraints(bejeweledHowBackButton, 1,3);
        bejeweledHowBackButton.setOnAction(e -> window.setScene(bejeweledMenuScene));

        bejeweledHowGrid.getChildren().addAll(bejeweledHowLabel, bejeweledInstructionsLabel, bejeweledHowBackButton);

        Label tentenTitleLabel = new Label("           1010!");
        GridPane.setConstraints(tentenTitleLabel, 3,1);
        Button tentenStartButton = new Button("  Start Game  ");
        GridPane.setConstraints(tentenStartButton, 3,2);
        tentenStartButton.setOnMouseClicked(mouseEvent -> {
            TenTenGame game = new TenTenGame(10,10);
            TenTenGUI tentenGUI = new TenTenGUI(game);
            primaryStage.setScene(new Scene(tentenGUI.layout, 1000, 1000));
            primaryStage.show();
        });
        //tentenStartButton.setOnAction(e -> window.setScene(INSERT 1010 SCENE HERE));
        Button tentenHowButton = new Button("  How to Play  ");
        GridPane.setConstraints(tentenHowButton, 3,3);
        tentenHowButton.setOnAction(e -> window.setScene(tentenHowScene));
        Button tentenBackButton = new Button("Back to Menu");
        GridPane.setConstraints(tentenBackButton, 3,4);
        tentenBackButton.setOnAction(e -> window.setScene(mainMenuScene));

        tentenMenuGrid.getChildren().addAll(tentenTitleLabel, tentenStartButton, tentenHowButton, tentenBackButton);

        Label tentenHowLabel = new Label("How to Play: 1010!");
        GridPane.setConstraints(tentenHowLabel, 1,1);
        Label tentenInstructionsLabel = new Label("In 1010, you must fill a blank grid with different-shaped blocks, presented three at a time. The colors are irrelevant, but blocks must fit together so you complete a row or a column. Every time you complete a line, the line disappears and you get more space to work in. The more space you create, the more points you get. Try and get the highest possible points you can in the given time. \n\nHave Fun!");
        tentenInstructionsLabel.setWrapText(true);
        GridPane.setConstraints(tentenInstructionsLabel, 1,2);
        Button tentenHowBackButton = new Button("< Back");
        GridPane.setConstraints(tentenHowBackButton, 1,3);
        tentenHowBackButton.setOnAction(e -> window.setScene(tentenMenuScene));

        tentenHowGrid.getChildren().addAll(tentenHowLabel, tentenInstructionsLabel, tentenHowBackButton);


//        window.setScene(mainMenuScene);
//        window.show();
        MAIN_MENU_SCENE = mainMenuScene;
        PRIMARY_STAGE = window;
        UserDatabase userDatabase = new UserDatabase("src/TMGE/UserProfiles/UserDataBaseFile.txt");
        GLOBAL_USER_DATABASE = userDatabase;
        LoginGUI loginGUI = new LoginGUI(userDatabase, window, mainMenuScene);
        window.setScene(new Scene(loginGUI.createContent(),700,500));
        window.show();
    }

}