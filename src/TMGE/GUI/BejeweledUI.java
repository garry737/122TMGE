package TMGE.GUI;

import TMGE.Main;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import TMGE.Game.BejeweledGame;
import TMGE.Game.GamePieces.Gem;
import TMGE.GridControl.Coordinates;
import TMGE.Networking.Client;
import TMGE.Networking.Multiplayer;
import TMGE.Networking.Server;

import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BejeweledUI {

    private class TimerThread extends Thread {
        long time;
        Timer timer = new Timer();

        TimerThread(long time) {
            this.time = time;
        }

        public void run() {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    time -= 1000;
                    delay(0, startButton.updateTimerLabel());
                    if (time <= 0) {
                        timer.cancel();
                        startButton.hasStarted = false;
                        delay(0, startButton.updateButtonText("Retry?"));
                        currentState = UISTATE.NOTPLAYING;
                        multiplayer.sendDone(score.get());
                    }
                }
            };

            timer.scheduleAtFixedRate(task, 0, 1000);

        }
    }

    private class HandleSwapThread extends Thread {
        Jewel j1;
        Jewel j2;

        HandleSwapThread(Jewel j1, Jewel j2) {
            this.j1 = j1;
            this.j2 = j2;
        }

        public void run() {
            while (currentState != UISTATE.NOTHING) {
                System.out.println(currentState.toString());
                switch (currentState) {
                    case SWAP:
                        currentState = UISTATE.WAITING;
                        if (handleSwapLogic(j1, j2)) {
                            currentState = UISTATE.CLEAR;
                        } else {
                            currentState = UISTATE.SWAPBACK;
                        }
                        break;
                    case CLEAR:
                        currentState = UISTATE.WAITING;
                        handleClear();
                        currentState = UISTATE.DROP;
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DROP:
                        currentState = UISTATE.WAITING;
                        handleDrop();
                        currentState = UISTATE.FILL;
                        break;
                    case FILL:
                        currentState = UISTATE.WAITING;
                        handleFill();
                        currentState = UISTATE.ADDITIONALCLEAR;
                        break;
                    case ADDITIONALCLEAR:
                        int tempScore = game.clearMatchingTiles();
                        if (tempScore > 0)
                            score.set(score.get() + tempScore);
                        if (game.hasEmptyTile())
                            currentState = UISTATE.CLEAR;
                        else
                            currentState = UISTATE.NOTHING;
                        break;
                    case SWAPBACK:
                        delay(200, swapBack(j1, j2));
                        break;
                    default:
                        break;

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            multiplayer.sendScore(score.get());
        }
    }

    enum UISTATE {
        SELECT, CLEAR, DROP, SWAPBACK, WAITING, NOTHING, SWAP, FILL, ADDITIONALCLEAR, NOTPLAYING;
    }

    private static BejeweledGame game;
    private static final int SIZE = 100;
    private static final int GEM_SIZE = 80;
    private static final long TIMER_COUNT = 180000;
    private static UISTATE currentState = UISTATE.NOTPLAYING;
    private static BackgroundFill background_select = new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY);
    private static BackgroundFill background_selectable = new BackgroundFill(Color.RED, CornerRadii.EMPTY,
            Insets.EMPTY);

    public BejeweledUI(BejeweledGame game) {
        this.game = game;
    }

    private Jewel selected = null;

    private HashMap<Coordinates, Jewel> jewels = new HashMap<>();

    private IntegerProperty score = new SimpleIntegerProperty();
    private StringProperty countdown = new SimpleStringProperty();
    private StartButton startButton;
    private Pane root;
    private Multiplayer multiplayer;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");

    public Parent createContent() {
        root = new Pane();
        int row = game.row;
        int column = game.column;

        Image bg = new Image("/assets/bejeweled/bg2.png");
        ImageView bgView = new ImageView();
        bgView.setImage(bg);
        bgView.setFitWidth(row * SIZE);
        bgView.setFitHeight(column * SIZE);

        root.setPrefSize(row * SIZE + 500, column * SIZE);
        root.getChildren().add(bgView);

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                var coordinates = new Coordinates(x, y);
                var gem = game.gemAtCoordinate(coordinates);
                var point = new Point2D(y, x);
                var jewel = new Jewel(point, gem);
                jewels.put(coordinates, jewel);
            }
        }

        root.getChildren().addAll(jewels.values());

        Parent profile = ProfilePictureGUI.createProfileView();
        profile.setTranslateX((row * SIZE));
        profile.setTranslateY((0));

        Text textScore = new Text();
        textScore.setTranslateX((row * SIZE));
        textScore.setTranslateY((200));
        textScore.setFont(Font.font((68)));
        textScore.textProperty().bind(score.asString("Score: [%d]"));

        Text timerInfo = new Text();
        timerInfo.setTranslateX((row * SIZE));
        timerInfo.setTranslateY((300));
        timerInfo.setFont(Font.font((68)));
        timerInfo.textProperty().bind(countdown);
        Timestamp timestamp = new Timestamp(TIMER_COUNT);
        LocalDateTime ld = timestamp.toLocalDateTime();
        countdown.setValue(String.format("Time: %s", ld.format(formatter)));
        startButton = new StartButton((row + 1) * SIZE, 350);
        MultiplayerButton multiplayerBtn = new MultiplayerButton((row) * SIZE + 10, 550);
        LogoutButton logoutButton = new LogoutButton((row + 3) * SIZE + 25, 20);
        MainMenuButton mainMenuButton = new MainMenuButton((row + 3) * SIZE + 25, 80);

        root.getChildren().add(profile);
        root.getChildren().add(textScore);
        root.getChildren().add(timerInfo);
        root.getChildren().add(startButton);
        root.getChildren().add(multiplayerBtn);
        root.getChildren().add(logoutButton);
        root.getChildren().add(mainMenuButton);
        return root;
    }

    private Runnable swapBack(Jewel j1, Jewel j2) {
        Runnable updater = new Runnable() {

            @Override
            public void run() {
                swap(j1, j2);
                currentState = UISTATE.NOTHING;
            }
        };
        return updater;
    }

    private void swap(Jewel j1, Jewel j2) {
        Gem gem1 = j1.gem;
        Gem gem2 = j2.gem;
        j1.setGem(gem2);
        j2.setGem(gem1);
        System.out.println("SWAPPING");
    }

    boolean handleSwapLogic(Jewel j1, Jewel j2) {

        var coords1 = j1.getCoordinates();
        var coords2 = j2.getCoordinates();
        int tempScore = game.swapGemWithClear(coords1, coords2);
        if (tempScore > 0) {
            swap(j1, j2);
            score.set(score.get() + tempScore);
        } else
            return false;
        return true;
    }

    void handleClear() {
        var emptyCoordinatesList = game.getAllEmptyTile();
        for (var coords : emptyCoordinatesList) {
            var jewel = jewels.get(coords);
            jewel.clearGem();
        }
    }

    void handleDrop() {
        game.dropGem();
        updateBoard();

    }

    void handleFill() {
        game.fillEmptyTile();
        updateBoard();

    }

    void updateBoard() {
        for (int x = 0; x < game.row; x++) {
            for (int y = 0; y < game.column; y++) {
                var coordinates = new Coordinates(x, y);
                var gem = game.gemAtCoordinate(coordinates);
                var jewel = jewels.get(coordinates);
                if (gem == null)
                    jewel.clearGem();
                else
                    jewel.setGem(gem);
            }
        }
    }

    public static void delay(long delayMs, Runnable toRun) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ignored) {
            }
            Platform.runLater(toRun);
        });
        t.setDaemon(true);
        t.start();
    }

    private class LogoutButton extends Parent {
        Button button = new Button("Logout");

        public LogoutButton(int x, int y) {
            button.setMaxWidth(150);
            button.setMaxHeight(50);
            button.setPrefHeight(50);
            button.setPrefWidth(150);
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(button);
            setTranslateX(x);
            setTranslateY(y);
            getChildren().add(borderPane);

            button.setOnMouseClicked(mouseEvent -> {

                LoginGUI loginGUI = new LoginGUI(Main.GLOBAL_USER_DATABASE, Main.PRIMARY_STAGE, Main.MAIN_MENU_SCENE);
                Main.PRIMARY_STAGE.setScene(new Scene(loginGUI.createContent(), 700, 500));
            });
        }
    }

    private class MainMenuButton extends Parent {
        Button button = new Button("Main Menu");

        public MainMenuButton(int x, int y) {
            button.setMaxWidth(150);
            button.setMaxHeight(50);
            button.setPrefHeight(50);
            button.setPrefWidth(150);
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(button);
            setTranslateX(x);
            setTranslateY(y);
            getChildren().add(borderPane);

            button.setOnMouseClicked(mouseEvent -> {
                Main.PRIMARY_STAGE.setScene(Main.MAIN_MENU_SCENE);
            });
        }
    }

    private class StartButton extends Parent {
        Button button = new Button("Start");
        TimerThread timerThread;
        boolean hasStarted = false;

        public StartButton(int x, int y) {
            button.setMaxWidth(200);
            button.setMaxHeight(100);
            button.setPrefHeight(100);
            button.setPrefWidth(200);
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(button);
            setTranslateX(x);
            setTranslateY(y);
            getChildren().add(borderPane);

            button.setOnMouseClicked(mouseEvent -> {
                if (hasStarted) {
                    timerThread.timer.cancel();
                    timerThread.interrupt();
                    button.setText("Retry");
                    hasStarted = false;
                    currentState = UISTATE.NOTPLAYING;
                    game.initValidBoard();
                    updateBoard();
                } else {
                    if (multiplayer != null) {
                        multiplayer.sendReady();
                        if (!multiplayer.opponentReady) {
                            button.setText("Waiting for opponent");
                        }
                        return;
                    }
                    delay(0, startGame());
                }
            });
        }

        public Runnable startGame() {
            Runnable run = new Runnable() {

                @Override
                public void run() {
                    button.setText("Cancel");
                }
            };
            timerThread = new TimerThread(TIMER_COUNT);
            updateCountDown();
            timerThread.start();
            score.setValue(0);
            hasStarted = true;
            currentState = UISTATE.NOTHING;
            return run;
        }

        void updateCountDown() {
            Timestamp timestamp = new Timestamp(timerThread.time);
            LocalDateTime ld = timestamp.toLocalDateTime();
            countdown.setValue(String.format("Time: %s", ld.format(formatter)));
        }

        public Runnable updateTimerLabel() {
            Runnable updater = new Runnable() {

                @Override
                public void run() {
                    updateCountDown();
                }
            };
            return updater;
        }

        public Runnable updateButtonText(String text) {
            Runnable updater = new Runnable() {

                @Override
                public void run() {
                    button.setText(text);
                }
            };
            return updater;
        }
    }

    public interface ScoreListener {
        void opponentIsReady();

        void bothAreReady();

        void opponentConnected();

        void opponentScoreUpdated();

        void opponentDone();
    }

    private class MultiplayerButton extends Parent implements ScoreListener {
        Button button = new Button("Multiplayer");
        Button serverBtn = new Button("Create Room");
        Button clientBtn = new Button("Join Room");
        private StringProperty opponentScore = new SimpleStringProperty();
        boolean hasClicked = false;

        public MultiplayerButton(int x, int y) {
            button.setMaxWidth(200);
            button.setMaxHeight(100);
            button.setPrefHeight(100);
            button.setPrefWidth(200);
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(button);
            setTranslateX(x);
            setTranslateY(y);
            getChildren().add(borderPane);

            serverBtn.setMaxWidth(100);
            serverBtn.setMaxHeight(50);
            serverBtn.setPrefHeight(50);
            serverBtn.setPrefWidth(100);
            serverBtn.setTranslateX(x + (SIZE * 2) + 25);
            serverBtn.setTranslateY(y + 25);

            clientBtn.setMaxWidth(100);
            clientBtn.setMaxHeight(50);
            clientBtn.setPrefHeight(50);
            clientBtn.setPrefWidth(100);
            clientBtn.setTranslateX(x + (SIZE * 3) + 50);
            clientBtn.setTranslateY(y + 25);

            Text textScore = new Text();
            textScore.setTranslateX(x);
            textScore.setTranslateY((y + 150));
            textScore.setFont(Font.font((50)));
            textScore.textProperty().bind(opponentScore);

            button.setOnMouseClicked(mouseEvent -> {
                if (hasClicked) {
                    button.setText("Multiplayer");
                    removeButtons();
                    root.getChildren().remove(textScore);
                    if (multiplayer != null) {
                        multiplayer.closeConnection();
                        multiplayer = null;
                    }
                    hasClicked = false;
                } else {
                    button.setText("Cancel");
                    root.getChildren().add(serverBtn);
                    root.getChildren().add(clientBtn);
                    hasClicked = true;
                }
            });

            serverBtn.setOnMouseClicked(mouseEvent -> {
                if (multiplayer != null) {
                    multiplayer.closeConnection();
                    multiplayer = null;
                }
                removeButtons();

                startMultiplayer(true);
                opponentScore.setValue("Waiting For\nOpponent...");
                root.getChildren().add(textScore);
            });

            clientBtn.setOnMouseClicked(mouseEvent -> {
                if (multiplayer != null) {
                    multiplayer.closeConnection();
                    multiplayer = null;
                }
                if (startMultiplayer(false)) {
                    opponentScore.setValue("Opponent Score:\n[0]");
                    removeButtons();
                } else {
                    opponentScore.setValue("Failed to connect");
                }
                if (!root.getChildren().contains(textScore))
                    root.getChildren().add(textScore);
            });

        }

        void removeButtons() {
            root.getChildren().remove(serverBtn);
            root.getChildren().remove(clientBtn);
        }

        boolean startMultiplayer(Boolean isServer) {
            if (isServer) {
                multiplayer = new Server(5000);
            } else {
                try {
                    multiplayer = new Client("127.0.0.1", 5000);
                } catch (IOException e) {
                    // e.printStackTrace();
                    return false;
                }
            }
            multiplayer.setListener(this);
            multiplayer.ready();
            return true;
        }

        @Override
        public void opponentIsReady() {
            System.out.println("O is ready");
        }

        @Override
        public void bothAreReady() {
            delay(0, startButton.startGame());
        }

        @Override
        public void opponentConnected() {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    opponentScore.setValue("Opponent Score:\n[0]");
                }
            };
            delay(0, run);
        }

        @Override
        public void opponentScoreUpdated() {
            String scoreString = String.format("Opponent Score:\n[%d]", multiplayer.opponentScore);
            Runnable run = new Runnable() {
                @Override
                public void run() {

                    opponentScore.setValue(scoreString);
                }
            };
            delay(0, run);

        }

        @Override
        public void opponentDone() {
            while (startButton.timerThread.time > 0)
                ;
            String winStr = "";
            if (multiplayer.opponentScore < score.get()) {
                winStr = "You won!";
            } else {
                winStr = "You Lost!";
            }
            String scoreString = String.format("Opponent Score:\n[%d] %s", multiplayer.opponentScore, winStr);
            Runnable run = new Runnable() {
                @Override
                public void run() {

                    opponentScore.setValue(scoreString);
                }
            };
            delay(0, run);

        }
    }

    private class Jewel extends Parent {
        ImageView imageView = new ImageView();
        BorderPane borderPane;
        Gem gem;

        public Jewel(Point2D point, Gem gem) {
            borderPane = new BorderPane();
            this.gem = gem;
            imageView.setImage(gem.getImage());
            imageView.setFitWidth(GEM_SIZE);
            imageView.setFitHeight(GEM_SIZE);
            borderPane.setCenter(imageView);
            setTranslateX(point.getX() * SIZE);
            setTranslateY(point.getY() * SIZE);
            getChildren().add(borderPane);

            setOnMouseClicked(event -> {
                if (currentState == UISTATE.NOTHING) {
                    selected = this;
                    selected.setSelection();
                    currentState = UISTATE.SELECT;
                    var directions = selected.getCoordinates().getDirections();
                    for (var direction : directions) {
                        var j = jewels.get(direction);
                        if (j != null)
                            j.setSelectable();
                    }
                } else if (currentState == UISTATE.SELECT) {
                    if (this == selected) {
                        selected.removeSelection();
                        var directions = selected.getCoordinates().getDirections();
                        for (var direction : directions) {
                            var j = jewels.get(direction);
                            if (j != null)
                                j.removeSelection();
                        }
                        selected = null;
                        currentState = UISTATE.NOTHING;
                        return;
                    }
                    var coords1 = selected.getCoordinates();
                    var coords2 = this.getCoordinates();
                    if (!coords1.contains(coords2)) {
                        selected.removeSelection();
                        var directions = selected.getCoordinates().getDirections();
                        for (var direction : directions) {
                            var j = jewels.get(direction);
                            if (j != null)
                                j.removeSelection();
                        }
                        selected = this;
                        selected.setSelection();
                        directions = selected.getCoordinates().getDirections();
                        for (var direction : directions) {
                            var j = jewels.get(direction);
                            if (j != null)
                                j.setSelectable();
                        }
                        return;
                    }
                    currentState = UISTATE.SWAP;
                    var directions = selected.getCoordinates().getDirections();
                    for (var direction : directions) {
                        var j = jewels.get(direction);
                        if (j != null)
                            j.removeSelection();
                    }
                    selected.removeSelection();
                    HandleSwapThread swapThread = new HandleSwapThread(selected, this);
                    swapThread.start();
                }
            });
        }

        public int getColumn() {
            return (int) getTranslateX() / SIZE;
        }

        public int getRow() {
            return (int) getTranslateY() / SIZE;
        }

        public Gem getGem() {
            return gem;
        }

        public void clearGem() {
            gem = null;
            imageView.setImage(null);
        }

        public void setSelectable() {
            Background background = new Background(background_selectable);
            borderPane.setBackground(background);
        }

        public void setSelection() {
            Background background = new Background(background_select);
            borderPane.setBackground(background);
        }

        public void removeSelection() {
            borderPane.setBackground(null);
        }

        public void setGem(Gem newGem) {
            this.gem = newGem;
            imageView.setImage(gem.getImage());
        }

        public Coordinates getCoordinates() {
            return new Coordinates(getRow(), getColumn());
        }
    }
}
