package TMGE.Game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import TMGE.GridControl.Coordinates;
import TMGE.Game.GamePieces.Piece;

import java.util.*;

public class TenTenGame extends Game {
    enum BoardState{
        FILLED("Filled"),
        EMPTY("Empty"),;

        private String text;

        BoardState(String text){
            this.text = text;
        }

        public static BoardState fromString(String text) {
            for (BoardState a : BoardState.values()) {
                if (a.text.equalsIgnoreCase(text))
                    return a;
            }
            return EMPTY;
        }
    }

    BoardState[][] board;
    HashMap<Integer, Piece> piecesQueue;
    public StringProperty scoreProperty = new SimpleStringProperty();
    public int score = 0;
    public boolean hasStarted = false;
    public Object infoObj;
    public TenTenGame(int row, int column) {
        super(row, column);
        piecesQueue = new HashMap<>();
    }

    @Override
    public void initValidBoard(){
        board = new BoardState[row][column];
        for (int x = 0; x < row;x++) {
            for (int y = 0; y < column; y++) {
                board[x][y] = BoardState.EMPTY;
            }
        }
        generateQueue();
    }

    @Override
    public Boolean isMovePossible(){
        var emptyCoordinates = findCoordinatesFor(BoardState.EMPTY);
        for (var piece:piecesQueue.values()){
            for (var coordinates:emptyCoordinates){
                var coordsList = piece.getCoordinates(coordinates);
                if (verifyEmptySpaces(coordsList))
                    return true;
            }
        }
        return false;
    }

    public void generateQueue(){
        for (int i = 1; i <= 3; i++){
            Integer x = i;
            piecesQueue.put(x, Piece.RandomPiece());
        }
    }

    public boolean isQueueEmpty(){
        return piecesQueue.isEmpty();
    }

    public Piece getPieceFromQueue(int i){
        Integer index = i;
        return piecesQueue.get(i);
    }

    public void removePieceFromQueue(int i){
        piecesQueue.remove(i);
    }

    public boolean placePieceAtCoordinate(Piece piece, Coordinates coordinates){
        var coordsList = piece.getCoordinates(coordinates);
        if (!verifyEmptySpaces(coordsList))
            return false;
        for (var c:coordsList){
            board[c.row][c.column] = BoardState.FILLED;
        }
        return true;
    }

    public boolean placePieceAtCoordinate(Piece piece, ArrayList<Coordinates> coordinatesList){
        if (!verifyEmptySpaces(coordinatesList))
            return false;
        for (var c:coordinatesList){
            board[c.row][c.column] = BoardState.FILLED;
        }
        return true;
    }


    public ArrayList<ArrayList<Coordinates>> checkClearedAtCoordinates(ArrayList<Coordinates> coordinatesList){

        HashSet<Integer> rList = new HashSet<>();
        HashSet<Integer> cList = new HashSet<>();
        for (var coords:coordinatesList){
            rList.add(coords.row);
            cList.add(coords.column);
        }
        HashMap<Integer, ArrayList<Coordinates>> allMatchingRows = new HashMap<>();
        for (var r:rList){
            //Use this to find any matches within a column
            ArrayList<Coordinates> rowCoords = getCoordinatesForRow(r);
            if (validateList(rowCoords))
                allMatchingRows.put(r,rowCoords);
        }

        HashMap<Integer, ArrayList<Coordinates>> allMatchingColumns = new HashMap<>();
        for (var c:cList){
            //Use this to find any matches within a row
            ArrayList<Coordinates> colCoords = getCoordinatesForColumn(c);
            if (validateList(colCoords))
                allMatchingColumns.put(c,colCoords);
        }

        ArrayList<ArrayList<Coordinates>> finalSet = new ArrayList<>();
        finalSet.addAll(allMatchingRows.values());
        finalSet.addAll(allMatchingColumns.values());
        if (finalSet.size() == 0)
            return null;
        return finalSet;

    }

    public int clearWithScore(ArrayList<ArrayList<Coordinates>> list){
        if (list == null)
            return 0;
        int num = list.size();
        for (var coordsList:list){
            for (var coords:coordsList){
                board[coords.row][coords.column] = BoardState.EMPTY;
            }
        }
        int score = 10 * num;
        return score;
    }

    boolean validateList(ArrayList<Coordinates> list){
        for (var c:list) {
            var state = getBoardState(c);
            if (state == null || state == BoardState.EMPTY)
                return false;
        }
        return true;
    }

    BoardState getBoardState(Coordinates coordinates){
        if (!isInBound(coordinates))
            return null;
        return board[coordinates.row][coordinates.column];

    }

    boolean verifyEmptySpaces(ArrayList<Coordinates> coordinatesList){
        for (var coordinates:coordinatesList){
            if (!isInBound(coordinates))
                return false;
            if (board[coordinates.row][coordinates.column] != BoardState.EMPTY)
                return false;
        }
        return true;
    }

    boolean verifyEmptySpacesViaGUI(ArrayList<Coordinates> coordinatesList){
        for (var coordinates:coordinatesList){
            if (!isInBound(coordinates))
                return false;
            if (board[coordinates.column][coordinates.row] != BoardState.EMPTY)
                return false;
        }
        return true;
    }

    ArrayList<Coordinates> getCoordinatesForRow(int r){
        ArrayList<Coordinates> coordinatesList = new ArrayList<>();
        for (int i = 0; i < column; i++){
            coordinatesList.add(new Coordinates(r, i));
        }
        return coordinatesList;
    }

    ArrayList<Coordinates> getCoordinatesForColumn(int c){
        ArrayList<Coordinates> coordinatesList = new ArrayList<>();
        for (int i = 0; i < row; i++){
            coordinatesList.add(new Coordinates(i, c));
        }
        return coordinatesList;
    }

    Coordinates[] findCoordinatesFor(BoardState boardState){
        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                var coords = new Coordinates(x, y);
                var state = board[x][y];
                if (state == boardState)
                    coordinatesArrayList.add(coords);
            }
        }

        return coordinatesArrayList.toArray(new Coordinates[coordinatesArrayList.size()]);
    }

    @Override
    public void printBoard(){
        System.out.print("|");
        for (int x = 0; x < row; x++){
            System.out.printf("\t  %d\t\t|", x);
        }
        System.out.println();
        for (int x = 0; x < row; x++){
            System.out.print("|");
            for (var s:board[x]){
                System.out.print("\t"+s.text + "\t|");
            }
            System.out.println(x);
        }
        System.out.println();
        for (int i = 1; i <= 3;i++){
            System.out.println(i + ":");
            Piece piece = null;
            if (piecesQueue.containsKey(i))
                piece = piecesQueue.get(i);
            if (piece != null)
                System.out.println(piece.getString());
            else
                System.out.println();
        }
        System.out.println("=========================");
    }



}
