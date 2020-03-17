package TMGE.GUI;

import TMGE.Game.GamePieces.Piece;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import TMGE.Game.TenTenGame;
import TMGE.GridControl.Coordinates;
import javafx.scene.paint.Color;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.*;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

// TEN TEN GUI CLASSES

public class TenTenGUI
{
    public TileManager TM;
    public PieceQueueGUI PQG;
    public Pane layout;
    public TenTenGame game;
    public TenTenGUI(TenTenGame game)
    {
        this.game = game;
        game.initValidBoard();
        PQG = new PieceQueueGUI(3, game);
        TM = new TileManager(10, 10, PQG, game);
        Text scoreText = new Text();
        scoreText.setTranslateX((7.5 * 50));
        scoreText.setTranslateY((75));
        scoreText.setFont(Font.font((68)));
        scoreText.textProperty().bind(game.scoreProperty);
        game.scoreProperty.setValue(String.format("Score: %d", game.score));
        //layout = new BorderPane(TM.gp, null, null, PQG.itemQueue, null);
        TM.gp.setTranslateX(2.5 * 100);
        TM.gp.setTranslateY(100);
        PQG.itemQueue.setTranslateY(7.5 * 100);
        PQG.itemQueue.setTranslateX(1.75 * 100);
        layout = new Pane();
        layout.getChildren().add(TM.gp);
        layout.getChildren().add(PQG.itemQueue);
        layout.getChildren().add(scoreText);

        Button button = new Button("Play");
        button.setMaxWidth(200);
        button.setMaxHeight(100);
        button.setPrefHeight(100);
        button.setPrefWidth(200);
        button.setFont(Font.font(30));
        button.setOnMouseClicked(mouseEvent -> {
            if (!game.hasStarted){
                game.hasStarted = true;
                game.initValidBoard();
                PQG.resetAllPieces();
                TM.resetBoard();
                button.setText("Quit");
                game.score = 0;
                game.scoreProperty.setValue(String.format("Score: %d", game.score));
            } else{
                game.hasStarted = false;
                button.setText("Retry");
            }
        });
        game.infoObj = button;
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(button);
        borderPane.setTranslateX(25);
        borderPane.setTranslateY((200));
        layout.getChildren().add(borderPane);

    }
}

class PieceQueueGUI
{
    TenTenGame game;
    VBox itemQueue;
    HBox itemHBox;

    Piece selectedPiece;
    int selectedPieceIndex;
    //ArrayList<Piece> queueOfPieces = new ArrayList<Piece>();

    int itemCount;

    PieceQueueGUI(int pieceCount, TenTenGame game)
    {
        this.game = game;
        itemCount = pieceCount;
        selectedPieceIndex = -1; // set default
        createVBoxQueue();
    }

    // creates visual representation of queue
    VBox createVBoxQueue()
    {
        itemQueue = new VBox();
        itemHBox = new HBox();
        if (game.hasStarted){

        }
        var queueOfPieces = new ArrayList<Piece>();
        for (int i = 1; i < itemCount+1; i++)
        {
            Piece pieceN = game.getPieceFromQueue(i);
            pieceN.setPadding(new Insets(0, 75, 0, 75 ));
            addQueueSelectionProperties(pieceN, i);
            queueOfPieces.add(pieceN);
        }
        itemHBox.getChildren().addAll(queueOfPieces);
        itemQueue.getChildren().add(itemHBox);

        return itemQueue;
    }

    // piece is placed, remove selected piece
    void placePiece()
    {
        removePieceFromQueue(selectedPieceIndex);
        selectedPiece = null;
        selectedPieceIndex = -1;

        // if all possible pieces gone, reset
        if (isEmpty())
        {
            resetAllPieces();
        }
    }

    // remove piece by index from queue
    void removePieceFromQueue(int index)
    {
        // remove piece from queue list
        Piece piece = game.getPieceFromQueue(index);
        removeQueueSelectionProperties(piece);
        game.removePieceFromQueue(index);

        // update visuals
        updatePieceQueue();
    }

    // reset entire queue of pieces (generate new pieces) & update visual piece queue
    void resetAllPieces()
    {
        game.generateQueue();
        itemHBox.getChildren().clear();
        itemQueue.getChildren().clear();
        selectedPiece = null;
        selectedPieceIndex = -1;
        var queueOfPieces = new ArrayList<Piece>();
        for (int i = 1; i < itemCount + 1; i++)
        {
            Piece pieceN = game.getPieceFromQueue(i);
            pieceN.setPadding(new Insets(0, 75, 0, 75 ));
            addQueueSelectionProperties(pieceN, i);
            queueOfPieces.add(pieceN);
        }
        itemHBox.getChildren().addAll(queueOfPieces);
        itemQueue.getChildren().add(itemHBox);
    }

    // keep visual piece queue up to date
    void updatePieceQueue()
    {
        // reset visuals
        itemHBox.getChildren().clear();
        itemQueue.getChildren().clear();

        // add padding
        for (int i = 1; i < itemCount + 1; i++)
        {
            Piece piece = game.getPieceFromQueue(i);
            if (piece != null) {
                piece.setPadding(new Insets(0, 75, 0, 75));
                itemHBox.getChildren().add(piece);
            }
        }

        // re-add visuals
        itemQueue.getChildren().add(itemHBox);
    }

    // add queue selection properties to a piece and its index in the queue
    void addQueueSelectionProperties(Piece p, int index)
    {
        p.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
                if (!game.hasStarted)
                    return;
                // deselect piece
                if (selectedPiece != null)
                    selectedPiece.unhighlightPiece();
                selectedPiece = p;
                selectedPiece.highlightPiece();
                selectedPieceIndex = index;
            }
        });
    }

    // remove properties
    void removeQueueSelectionProperties(Piece p)
    {
        p.setOnMouseClicked(null);
    }

    // checks to see if the queue is completely empty
    Boolean isEmpty()
    {
        return  game.isQueueEmpty();
//        for (int i = 0; i < itemCount; i++)
//        {
//            if (queueOfPieces.get(i) != null)
//                return false;
//        }
//        return true;
    }

}

// class that manages all tiles of the grid
class TileManager {
    TenTenGame game;
    public static GridPane gp;
    public List<List<Tile>> tiles = new ArrayList<List<Tile>>();
    private PieceQueueGUI PQG;
    private ArrayList<Coordinates> potentialSpot;
    int rowMax;
    int colMax;

    TileManager(int r, int c, PieceQueueGUI PQ, TenTenGame game) {
        this.game = game;
        gp = new GridPane();
        PQG = PQ;
        gp.setHgap(1);
        gp.setVgap(1);
        rowMax = r;
        colMax = c;
        init();
    }

    void resetBoard(){
        for (var tArr:tiles){
            for (var t:tArr)
                t.unfillTile();
        }
    }

    void init() {
        potentialSpot = new ArrayList<Coordinates>();
        for (int r = 0; r < rowMax; r++) {
            // add row of ArrayList
            tiles.add(new ArrayList<Tile>());
            for (int c = 0; c < colMax; c++) {
                Tile t = new Tile(50, r, c, this);
                addHover(t);
                // add tile to 2D List
                tiles.get(r).add(t);
                gp.add(t, r, c);
            }
        }
    }

    // highlight tiles of the board with selected piece
    void highlightBoard(Tile t){
        if(PQG.selectedPiece != null & !t.isFilled){
            potentialSpot = PQG.selectedPiece.getCoordinates(t.coord);

            // check to make sure desired coordinates aren't already filled & in bound
            if (!notFilledAndInBound(potentialSpot))
                return;

            // then highlight tiles
            for(Coordinates coord : potentialSpot)
            {
                Tile tile = tiles.get(coord.row).get(coord.column);
                tile.highlightTile();
            }

        }
    }

    // unhighlight tiles of the board
    void unhighlightBoard(Tile t){
        if(!t.isFilled)
        {
            // check to make sure desired coordinates aren't already filled & in bound
            if (!notFilledAndInBound(potentialSpot))
                return;

            // then highlight tiles
            for(Coordinates coord : potentialSpot)
            {
                Tile tile = tiles.get(coord.row).get(coord.column);
                tile.unhighlightTile();
            }
        }
    }

    // place piece on board
    void placePiece(Tile t){
        if (!game.hasStarted)
            return;
        if(!t.isFilled)
        {
            // check to make sure desired coordinates aren't already filled & in bound
            if (!notFilledAndInBound(potentialSpot))
                return;

            // place
            var flipped = Coordinates.getFlippedCoordinates(potentialSpot);
            if (!game.placePieceAtCoordinate(PQG.selectedPiece, flipped)){
                return;
            }

            for(Coordinates coord : potentialSpot){
                Tile tile = tiles.get(coord.row).get(coord.column);
                tile.fillTile();
            }
            PQG.placePiece(); // manage in piece queue
            game.printBoard();
            ////Check if move is possible
            var clearList = game.checkClearedAtCoordinates(flipped);

            var score = game.clearWithScore(clearList);
            game.score += score;

            if (score > 0){
                clearTiles(clearList);
                game.scoreProperty.setValue(String.format("Score: %d", game.score));
            }

            // -------- LOOK HERE --------- use potentialSpot as piece's dropped coordinates as needed !
            //-------- LOOK HERE --------- add piece.score to the total score
            if (!game.isMovePossible()){
                game.hasStarted = false;
                ((Button) game.infoObj).setText("Game Over");
            }
        }

    }

    void clearTiles(ArrayList<ArrayList<Coordinates>>  coordinatesArrayList){

        for(var arr : coordinatesArrayList){
            var flipped = Coordinates.getFlippedCoordinates(arr);
            for (var coords:flipped){
                Tile tile = tiles.get(coords.row).get(coords.column);
                tile.unfillTile();
            }
        }
    }
    void addHover(Tile tile){
        tile.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> highlightBoard(tile) );
        tile.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> unhighlightBoard(tile));
        tile.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> placePiece(tile));
    }

    // checks to see if list of coordinates' tiles are not filled & are in bound
    Boolean notFilledAndInBound(ArrayList<Coordinates> PS)
    {
        for(Coordinates coord : PS)
        {
            if (!inBound(coord))
                return false;

            Tile tile = tiles.get(coord.row).get(coord.column);
            if (tile.isFilled)
            {
                return false;
            }
        }
        return true; // open for use!
    }
    Boolean inBound(Coordinates c)
    {
        return (c.row >= 0 && c.column >= 0 && c.row < rowMax && c.column < colMax);
    }
}

// individual tile of grid
class Tile extends Rectangle
{
    public Coordinates coord;
    public boolean isFilled;
    public TileManager tm;
    Tile(int size, int x, int y, TileManager t_m)
    {
        super(size, size, Color.GAINSBORO);
        coord = new Coordinates(x, y);
        setArcHeight(10);
        setArcWidth(10);
        isFilled = false;
        tm = t_m;
    }
    public void highlightTile()
    {
        setFill(Color.HONEYDEW);
    }
    public void unhighlightTile()
    {
        setFill(Color.GAINSBORO);
    }
    public void fillTile()
    {
        setFill(Color.MEDIUMAQUAMARINE);
        isFilled = true;
    }
    public void unfillTile()
    {
        setFill(Color.GAINSBORO);
        isFilled = false;
    }


}
