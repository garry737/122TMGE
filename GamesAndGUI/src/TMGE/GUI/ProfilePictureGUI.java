package TMGE.GUI;

import UserProfiles.UserDatabase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ProfilePictureGUI {
    private static final int ICON_SIZE = 50;
    public static String ICON = "";
    private ImageView selectedIcon;

    Image bullbasaur = new Image("file:assets/icons/bullbasaur.png");
    Image charmander = new Image("file:assets/icons/charmander.png");
    Image greatball = new Image("file:assets/icons/great-ball.png");
    Image masterball = new Image("file:assets/icons/master-ball.png");
    Image pepe = new Image("file:assets/icons/pepe.png");
    Image pikachu = new Image("file:assets/icons/pikachu.png");
    Image pokeball = new Image("file:assets/icons/pokeball.png");
    Image squirtle = new Image("file:assets/icons/squirtle.png");
    Image ultraball = new Image("file:assets/icons/ultra-ball.png");

    private Image[] icons = new Image[]{
            bullbasaur, charmander, greatball, masterball, pepe, pikachu, pokeball, squirtle, ultraball
    };

    public ProfilePictureGUI(){
        this.selectedIcon = new ImageView();
    }

    public Parent createChooseProfilePicture(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.BLACK);
        dropShadow.setHeight(5);
        dropShadow.setWidth(5);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(4);
//        dropShadow.setSpread(2);

        ImageView bullbasaurView = new ImageView();
        ImageView charmanderView = new ImageView();
        ImageView greatballView = new ImageView();
        ImageView masterballView = new ImageView();
        ImageView pepeView = new ImageView();
        ImageView pikachuView = new ImageView();
        ImageView pokeballView = new ImageView();
        ImageView squirtleView = new ImageView();
        ImageView ultraballView = new ImageView();

        //default icon
        pepeView.setEffect(dropShadow);
        ICON = "file:assets/icons/pepe.png";
        selectedIcon = pepeView;

        bullbasaurView.setImage(bullbasaur);
        bullbasaurView.setFitWidth(ICON_SIZE);
        bullbasaurView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(bullbasaurView,0,0);
        bullbasaurView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = bullbasaurView;
                ICON = bullbasaurView.getImage().getUrl();
                System.out.println(ICON);
                bullbasaurView.setEffect(dropShadow);
            }else {
                selectedIcon.setEffect(null);
                selectedIcon = bullbasaurView;
                ICON = bullbasaurView.getImage().getUrl();
                System.out.println(ICON);
                bullbasaurView.setEffect(dropShadow);
            }
        });

        charmanderView.setImage(charmander);
        charmanderView.setFitWidth(ICON_SIZE);
        charmanderView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(charmanderView,1,0);
        charmanderView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = charmanderView;
                ICON = charmanderView.getImage().getUrl();
                System.out.println(ICON);
                charmanderView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = charmanderView;
                ICON = charmanderView.getImage().getUrl();
                System.out.println(ICON);
                charmanderView.setEffect(dropShadow);
            }

        });

        greatballView.setImage(greatball);
        greatballView.setFitWidth(ICON_SIZE);
        greatballView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(greatballView,2,0);
        greatballView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = greatballView;
                ICON = greatballView.getImage().getUrl();
                System.out.println(ICON);
                greatballView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = greatballView;
                ICON = greatballView.getImage().getUrl();
                System.out.println(ICON);
                greatballView.setEffect(dropShadow);
            }
        });

        masterballView.setImage(masterball);
        masterballView.setFitWidth(ICON_SIZE);
        masterballView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(masterballView,3,0);
        masterballView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = masterballView;
                ICON = masterballView.getImage().getUrl();
                System.out.println(ICON);
                masterballView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = masterballView;
                ICON = masterballView.getImage().getUrl();
                System.out.println(ICON);
                masterballView.setEffect(dropShadow);
            }

        });

        pepeView.setImage(pepe);
        pepeView.setFitWidth(ICON_SIZE);
        pepeView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(pepeView,0,1);
        pepeView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = pepeView;
                ICON = pepeView.getImage().getUrl();
                System.out.println(ICON);
                pepeView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = pepeView;
                ICON = pepeView.getImage().getUrl();
                System.out.println(ICON);
                pepeView.setEffect(dropShadow);
            }

        });

        pikachuView.setImage(pikachu);
        pikachuView.setFitWidth(ICON_SIZE);
        pikachuView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(pikachuView,1,1);
        pikachuView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = pikachuView;
                ICON = pikachuView.getImage().getUrl();
                System.out.println(ICON);
                pikachuView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = pikachuView;
                ICON = pikachuView.getImage().getUrl();
                System.out.println(ICON);
                pikachuView.setEffect(dropShadow);
            }

        });

        pokeballView.setImage(pokeball);
        pokeballView.setFitWidth(ICON_SIZE);
        pokeballView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(pokeballView,2,1);
        pokeballView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = pokeballView;
                ICON = pokeballView.getImage().getUrl();
                System.out.println(ICON);
                pokeballView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = pokeballView;
                ICON = pokeballView.getImage().getUrl();
                System.out.println(ICON);
                pokeballView.setEffect(dropShadow);
            }

        });

        squirtleView.setImage(squirtle);
        squirtleView.setFitWidth(ICON_SIZE);
        squirtleView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(squirtleView,3,1);
        squirtleView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = squirtleView;
                ICON = squirtleView.getImage().getUrl();
                System.out.println(ICON);
                squirtleView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = squirtleView;
                ICON = squirtleView.getImage().getUrl();
                System.out.println(ICON);
                squirtleView.setEffect(dropShadow);
            }

        });

        ultraballView.setImage(ultraball);
        ultraballView.setFitWidth(ICON_SIZE);
        ultraballView.setFitHeight(ICON_SIZE);
        GridPane.setConstraints(ultraballView,0,2);
        ultraballView.setOnMouseClicked(e -> {
            if (selectedIcon == null){
                selectedIcon = ultraballView;
                ICON = ultraballView.getImage().getUrl();
                System.out.println(ICON);
                ultraballView.setEffect(dropShadow);
            }else{
                selectedIcon.setEffect(null);
                selectedIcon = ultraballView;
                ICON = ultraballView.getImage().getUrl();
                System.out.println(ICON);
                ultraballView.setEffect(dropShadow);
            }

        });

        grid.getChildren().addAll(bullbasaurView, charmanderView,
                greatballView, masterballView, pepeView,
                pikachuView, pokeballView, squirtleView, ultraballView);

//        Label usernameLabel = new Label("Choose Icon:   ");
////        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
//        grid.add(usernameLabel, 0, 1);

//        TextField usernameTextField = new TextField();
//        usernameTextField.setPromptText("username");
//        grid.add(usernameTextField, 1, 1);


        return grid;
    }

    public static Parent createProfileView(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));

        Image profilePicture = new Image(UserDatabase.CURRENT_USER_PROFILE_PICTURE);
        ImageView profilePictureView = new ImageView();
        profilePictureView.setImage(profilePicture);
        profilePictureView.setFitHeight(100);
        profilePictureView.setFitWidth(100);
        grid.add(profilePictureView, 0,0);

        Label user = new Label(UserDatabase.CURRENT_USER);
        user.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        grid.add(user, 1,0);

        return grid;
    }

}
