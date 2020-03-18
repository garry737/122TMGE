package TMGE.Game;

import Game.Game;
import TMGE.Game.GamePieces.Gem;
import GridControl.Coordinates;

import java.util.*;

class SortByRow implements Comparator<Coordinates>
{
    public int compare(Coordinates a, Coordinates b)
    {
        return a.row - b.row;
    }
}

class SortByColumn implements Comparator<Coordinates>
{
    public int compare(Coordinates a, Coordinates b)
    {
        return a.column - b.column;
    }
}

public class BejeweledGame extends Game {

    public enum SwapDirection{
        UP("Up"),
        DOWN("Down"),
        RIGHT("Right"),
        LEFT("Left"),
        OTHER("");

        private String text;

        SwapDirection(String text){
            this.text = text;
        }

        public static SwapDirection fromString(String text) {
            for (SwapDirection a : SwapDirection.values()) {
                if (a.text.equalsIgnoreCase(text))
                    return a;
            }
            return OTHER;
        }
    }

    Gem[][] board;

    public BejeweledGame(int row, int column){
        super(row, column);
    }

    @Override
    public void initValidBoard(){
        board = new Gem[row][column];
        int x = 0;
        int y = 0;
        HashMap<Coordinates, HashSet<Gem.GemType>> gemTypeMap = new HashMap<>();
        while (x < row){
            while (y < column) {
                var coords = new Coordinates(x, y);
                HashSet<Gem.GemType> gemTypeSet;
                if (gemTypeMap.containsKey(coords)){
                    gemTypeSet = gemTypeMap.get(coords);
                } else{
                    gemTypeSet = new HashSet<>();
                    gemTypeMap.put(coords, gemTypeSet);
                }
                var gem = Gem.RandomGem();
                if (gem == null)
                    continue;
                if (gemTypeSet.contains(gem.type))
                    gem = Gem.getAvialableGem(gemTypeSet);
                board[x][y] = gem;
                var list = countGem(coords, gem.type);
                Coordinates[] coordsList = list.toArray(new Coordinates[list.size()]);
                var finalList = checkForMatch(coordsList);
                if (finalList == null || finalList.size() == 0){
                    y++;
                } else{
                    gemTypeSet.add(gem.type);
                    if (gemTypeSet.size() == 7){
                        if (y == 0){
                            y = column-1;
                            x--;
                        } else{
                            y--;
                        }
                    }
                }
            }
            x++;
            y = 0;
        }
        if (!isMovePossible()){
            initValidBoard();
        }
    }

    @Override
    public Boolean isMovePossible(){
        for (Gem.GemType gemType : Gem.GemType.values()) {
            var coordinatesList = findCoordinatesFor(gemType);
            var map = getPossibleMovesList(coordinatesList);
            if (map.containsKey("row")){
                if (hasPossibleMove(map.get("row"), gemType, true))
                    return true;
            }
            if (map.containsKey("column")){
                if (hasPossibleMove(map.get("row"), gemType, false))
                    return true;
            }

        }
        return false;
    }

    boolean validateList(ArrayList<Coordinates> list, int type, int matchNum){
        //Pass the following type: 0 to remove from Row which means this will check for column sequence
        //Pass the following type: 1 to remove from Row which means this will check for row sequence
        int typeNum = -1;
        int matchCount = 0;
        ArrayList<Coordinates> removeList = new ArrayList<>();
        for (int i = 0; i < list.size();i++){
            var coords = list.get(i);
            var checkNum = (type == 0) ? coords.column:coords.row;
            if (typeNum == -1){
                matchCount = 1;
            } else{
                if (typeNum + 1 == checkNum){
                    matchCount++;
                } else{
                    if (matchCount < matchNum){//Remove any matching less than 3
                        var z = i - 1;
                        while (matchCount != 0){
                            removeList.add(list.get(z));
                            matchCount--;
                            z--;
                        }
                    }
                    matchCount = 1;
                }
            }
            typeNum = checkNum;

        }
        if (matchCount < matchNum){
            var z = list.size() - 1;
            while (matchCount != 0){
                removeList.add(list.get(z));
                matchCount--;
                z--;
            }
        }
        list.removeAll(removeList);
        if (list.size() < matchNum){
            return false;
        }
        return true;
    }

    public int clearMatchingTiles(){
        int totalScore = 0;
        for (var gemType: Gem.GemType.values()){
            var coordsList = findCoordinatesFor(gemType);
            var list = checkForMatch(coordsList);
            if (list != null){
                for (var x: list){
                    int num = x.size();
                    int score = (int) Gem.getScore(num, gemType);
                    totalScore += score;
                }
                clearGemAt(list);
            }
        }
        return totalScore;
    }

    public void fillEmptyTile(){
        var emptyCoords = getAllEmptyTile();
        for (var coords:emptyCoords){
            var gem = Gem.RandomGem();
            placeGemAtCoordinates(gem,coords);
        }
    }

    public void dropGem(){
        var emptyCoords = getAllEmptyTile();
        for (var coords:emptyCoords){
            var current = coords;
            var upCoords = coords.getUpCoords();
            while (isInBound(upCoords)){
                swapGem(current, upCoords);
                current = upCoords;
                upCoords = upCoords.getUpCoords();
            }
        }
    }

    public int swapGemWithClear(Coordinates c1, SwapDirection direction){
        Coordinates c2 = null;
        switch (direction){
            case UP:
                c2 = c1.getUpCoords();
                break;
            case DOWN:
                c2 = c1.getDownCoords();
                break;
            case LEFT:
                c2 = c1.getLeftCoords();
                break;
            case RIGHT:
                c2 = c1.getRightCoords();
                break;
        }
        if (c2 == null)
            return -1;
        return swapGemWithClear(c1, c2);
    }

    public int swapGemWithClear(Coordinates c1, Coordinates c2){
        var gem1 = gemAtCoordinate(c1);
        var gem2 = gemAtCoordinate(c2);
        placeGemAtCoordinates(gem2, c1);
        placeGemAtCoordinates(gem1, c2);

        //Check for any matching;
        boolean didClear = false;
        int totalScore = 0;
        var list1 = checkMatchingAtCoordinates(c1);
        var list2 = checkMatchingAtCoordinates(c2);
        if (list1 != null){
            for (var x: list1){
                int num = x.size();
                int score = (int) Gem.getScore(num, gem2);
                totalScore += score;
                didClear = true;
            }
        }
        if (list2 != null){
            for (var x: list2){
                int num = x.size();
                int score = (int) Gem.getScore(num, gem1);
                totalScore += score;
                didClear = true;
            }
        }
        if (!didClear){
            placeGemAtCoordinates(gem1, c1);
            placeGemAtCoordinates(gem2, c2);
            return -1;
        }
        return totalScore;
    }

    public boolean hasEmptyTile(){
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                if (board[x][y] == null){
                    return true;
                }
            }
        }
        return false;
    }

    public Coordinates[] findCoordinatesFor(Gem.GemType type){
        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                var coords = new Coordinates(x, y);
                var gem = board[x][y];
                if (gem == null)
                    continue;
                if (gem.type == type)
                    coordinatesArrayList.add(coords);
            }
        }
        return coordinatesArrayList.toArray(new Coordinates[coordinatesArrayList.size()]);
    }

    void clearGemAt(ArrayList<ArrayList<Coordinates>> list){
        if (list == null)
            return;
        for (var coordinatesList:list){
            for (var coordinates:coordinatesList)
                placeGemAtCoordinates(null, coordinates);
        }
    }

    public Gem gemAtCoordinate(Coordinates coordinates){
        if (!isInBound(coordinates))
            return null;
        return board[coordinates.row][coordinates.column];
    }

    boolean placeGemAtCoordinates(Gem gem, Coordinates coordinates){
        if (!isInBound(coordinates))
            return false;
        board[coordinates.row][coordinates.column] = gem;
        return true;
    }

    ArrayList<ArrayList<Coordinates>> checkMatchingAtCoordinates(Coordinates coordinates){
        var gem = gemAtCoordinate(coordinates);
        if (gem == null)
            return null;
        var list = countGem(coordinates, gem.type);
        Coordinates[] coordsList = list.toArray(new Coordinates[list.size()]);
        var finalList = checkForMatch(coordsList);

        if (finalList == null || finalList.size() == 0){
            return null;
        } else{
            clearGemAt(finalList);
            return finalList;
        }
    }

    void swapGem(Coordinates c1, Coordinates c2){
        var gem1 = gemAtCoordinate(c1);
        var gem2 = gemAtCoordinate(c2);
        placeGemAtCoordinates(gem2, c1);
        placeGemAtCoordinates(gem1, c2);
    }

    public ArrayList<Coordinates> getAllEmptyTile(){
        ArrayList<Coordinates> coordinatesArrayList = new ArrayList<>();
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                if (board[x][y] == null){
                    var coords = new Coordinates(x, y);
                    coordinatesArrayList.add(coords);
                }
            }
        }
        return coordinatesArrayList;
    }

    Boolean hasPossibleMove(ArrayList<ArrayList<Coordinates>> list, Gem.GemType gemType, Boolean isRow){
        if (list == null)
            return false;
        for (var coordinatesList:list){
            for (var coords:coordinatesList){
                ArrayList<Coordinates> checkList = new ArrayList<>();
                if (isRow){
                    var leftOfCoords = coords.getLeftCoords();
                    var rightOfCoords = coords.getRightCoords();
                    if (!coordinatesList.contains(leftOfCoords))
                        checkList.add(leftOfCoords);
                    if (!coordinatesList.contains(rightOfCoords))
                        checkList.add(rightOfCoords);
                }else {
                    var upOfCoords = coords.getUpCoords();
                    var downOfCoords = coords.getDownCoords();
                    if (!coordinatesList.contains(upOfCoords))
                        checkList.add(upOfCoords);
                    if (!coordinatesList.contains(downOfCoords))
                        checkList.add(downOfCoords);
                }
                for (var check:checkList){
                    if (findOneInDirections(check, gemType, coords))
                        return true;
                }
            }
        }
        return false;
    }

    ArrayList<ArrayList<Coordinates>> checkForMatch(Coordinates[] coordinatesList){
        if (coordinatesList.length < 3){
            return null;
        }
        HashSet<Integer> rList = new HashSet<>();
        HashSet<Integer> cList = new HashSet<>();
        for (var coords:coordinatesList){
            rList.add(coords.row);
            cList.add(coords.column);
        }
        HashMap<Integer, ArrayList<Coordinates>> allMatchingRows = new HashMap<>();
        for (var r:rList){
            //Use this to find any matches within a column
            ArrayList<Coordinates> rowCoords = new ArrayList<>();
            for (var coords:coordinatesList){
                if (coords.row == r)
                    rowCoords.add(coords);
            }
            if (rowCoords.size() >= 3){
                Collections.sort(rowCoords, new SortByColumn());
                if (validateList(rowCoords, 0, 3))
                    allMatchingRows.put(r,rowCoords);
            }
        }
        //For every row check if it's matching in sequence otherwise remove any

        HashMap<Integer, ArrayList<Coordinates>> allMatchingColumns = new HashMap<>();
        for (var c:cList){
            //Use this to find any matches within a row
            ArrayList<Coordinates> colCoords = new ArrayList<>();
            for (var coords:coordinatesList){
                if (coords.column == c)
                    colCoords.add(coords);
            }
            if (colCoords.size() >= 3){
                Collections.sort(colCoords, new SortByRow());
                if (validateList(colCoords, 1, 3))
                    allMatchingColumns.put(c,colCoords);
            }
        }
        ArrayList<ArrayList<Coordinates>> finalSet = new ArrayList<>();
        finalSet.addAll(allMatchingRows.values());
        finalSet.addAll(allMatchingColumns.values());
        if (finalSet.size() == 0)
            return null;
        return finalSet;
    }

    HashMap<String, ArrayList<ArrayList<Coordinates>>> getPossibleMovesList(Coordinates[] coordinatesList){
        if (coordinatesList.length < 2){
            return null;
        }
        HashSet<Integer> rList = new HashSet<>();
        HashSet<Integer> cList = new HashSet<>();
        for (var coords:coordinatesList){
            rList.add(coords.row);
            cList.add(coords.column);
        }
        ArrayList<ArrayList<Coordinates>> allMatchingRows = new ArrayList<>();
        for (var r:rList){
            //Use this to find any matches within a column
            ArrayList<Coordinates> rowCoords = new ArrayList<>();
            for (var coords:coordinatesList){
                if (coords.row == r)
                    rowCoords.add(coords);
            }
            if (rowCoords.size() >= 2){
                Collections.sort(rowCoords, new SortByColumn());
                if (validateList(rowCoords, 0, 2))
                    allMatchingRows.add(rowCoords);
            }
        }
        //For every row check if it's matching in sequence otherwise remove any

        ArrayList<ArrayList<Coordinates>> allMatchingColumns = new ArrayList<>();
        for (var c:cList){
            //Use this to find any matches within a row
            ArrayList<Coordinates> colCoords = new ArrayList<>();
            for (var coords:coordinatesList){
                if (coords.column == c)
                    colCoords.add(coords);
            }
            if (colCoords.size() >= 2){
                Collections.sort(colCoords, new SortByRow());
                if (validateList(colCoords, 1, 2))
                    allMatchingColumns.add(colCoords);
            }
        }
        HashMap<String, ArrayList<ArrayList<Coordinates>>> finalMap = new HashMap<>();
        if (allMatchingRows.size() > 0)
            finalMap.put("row", allMatchingRows);
        if (allMatchingColumns.size() > 0)
            finalMap.put("column", allMatchingColumns);
        return finalMap;
    }

    boolean isInBound(Coordinates coord){
        if (coord.row < 0 || coord.column < 0)
            return false;
        if (coord.row >= this.row || coord.column >= this.column){
            return false;
        }
        return true;
    }

    HashSet<Coordinates> countGem(Coordinates coords,Gem.GemType type){
        HashSet<Coordinates> coordsSet = new HashSet<>();
        HashSet<Coordinates> visited = new HashSet<>();
        visited.add(coords);
        coordsSet.add(coords);
        coordsSet.addAll(countUpGem(coords, type, visited));
        coordsSet.addAll(countDownGem(coords, type, visited));
        coordsSet.addAll(countLeftGem(coords, type, visited));
        coordsSet.addAll(countRightGem(coords, type, visited));

        return coordsSet;
    }

    boolean hasMatchingGemAtCoordinates(Coordinates coords, Gem.GemType type){
        if (!isInBound(coords))
            return false;
        Gem gem = board[coords.row][coords.column];
        if (gem == null)
            return false;
        return gem.type == type;
    }

    boolean findOneInDirections(Coordinates coords,Gem.GemType type, Coordinates startCoords){
        Coordinates upCoords = coords.getUpCoords();
        Coordinates downCoords = coords.getDownCoords();
        Coordinates leftCoords = coords.getLeftCoords();
        Coordinates rightCoords = coords.getRightCoords();
        ArrayList<Coordinates> checkList = new ArrayList<>();
        checkList.add(upCoords);
        checkList.add(downCoords);
        checkList.add(leftCoords);
        checkList.add(rightCoords);
        for (var c:checkList){
            if (!c.equals(startCoords)){
                if (hasMatchingGemAtCoordinates(c, type))
                    return true;
            }
        }
        return false;
    }

    HashSet<Coordinates> countLeftGem(Coordinates coords, Gem.GemType type, HashSet<Coordinates> visited){
        var leftCoords = coords.getLeftCoords();
        HashSet<Coordinates> cSet = new HashSet<>();
        if (!isInBound(leftCoords))
            return cSet;
        if (visited.contains(leftCoords))
            return cSet;
        visited.add(leftCoords);
        Gem gem = board[leftCoords.row][leftCoords.column];
        if (gem == null)
            return cSet;
        if (gem.type == type){
            cSet.add(leftCoords);
            cSet.addAll(countLeftGem(leftCoords, type, visited));
            cSet.addAll(countUpGem(leftCoords, type, visited));
            cSet.addAll(countDownGem(leftCoords, type, visited));
        }
        return cSet;
    }

    HashSet<Coordinates> countRightGem(Coordinates coords, Gem.GemType type, HashSet<Coordinates> visited){
        HashSet<Coordinates> cSet = new HashSet<>();
        var rightCoords = coords.getRightCoords();
        if (!isInBound(rightCoords))
            return cSet;
        if (visited.contains(rightCoords))
            return cSet;
        visited.add(rightCoords);
        Gem gem = board[rightCoords.row][rightCoords.column];
        if (gem == null)
            return cSet;
        if (gem.type == type){
            cSet.add(rightCoords);
            cSet.addAll(countRightGem(rightCoords, type, visited));
            cSet.addAll(countDownGem(rightCoords, type, visited));
            cSet.addAll(countUpGem(rightCoords, type, visited));
        }
        return cSet;
    }

    HashSet<Coordinates> countUpGem(Coordinates coords, Gem.GemType type, HashSet<Coordinates> visited){
        HashSet<Coordinates> cSet = new HashSet<>();
        var upCoords = coords.getUpCoords();
        if (!isInBound(upCoords))
            return cSet;
        if (visited.contains(upCoords))
            return cSet;
        visited.add(upCoords);
        Gem gem = board[upCoords.row][upCoords.column];
        if (gem == null)
            return cSet;
        if (gem.type == type){
            cSet.add(upCoords);
            cSet.addAll(countUpGem(upCoords, type, visited));
            cSet.addAll(countRightGem(upCoords, type, visited));
            cSet.addAll(countLeftGem(upCoords, type, visited));
        }
        return cSet;
    }

    HashSet<Coordinates> countDownGem(Coordinates coords, Gem.GemType type, HashSet<Coordinates> visited){
        HashSet<Coordinates> cSet = new HashSet<>();
        var downCoords = coords.getDownCoords();
        if (!isInBound(downCoords))
            return cSet;
        if (visited.contains(downCoords))
            return cSet;
        visited.add(downCoords);
        Gem gem = board[downCoords.row][downCoords.column];
        if (gem == null)
            return cSet;
        if (gem.type == type){
            cSet.add(downCoords);
            cSet.addAll(countDownGem(downCoords, type, visited));
            cSet.addAll(countRightGem(downCoords, type, visited));
            cSet.addAll(countLeftGem(downCoords, type, visited));
        }
        return cSet;
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
            for (var g:board[x]){
                if (g.type == Gem.GemType.Red)
                    System.out.print("\t"+g.getName() + "\t\t|");
                else
                    System.out.print("\t"+g.getName() + "\t|");
            }
            System.out.println(x);
        }
        System.out.println();
    }

    public void printBoardWithClear(){
        System.out.print("|");
        for (int x = 0; x < row; x++){
            System.out.printf("\t  %d\t\t|", x);
        }
        System.out.println();
        for (int x = 0; x < row; x++){
            System.out.print("|");
            for (var g:board[x]){
                if (g == null){
                    System.out.print("\tcleared\t|");
                    continue;
                }
                if (g.type == Gem.GemType.Red)
                    System.out.print("\t"+g.getName() + "\t\t|");
                else
                    System.out.print("\t"+g.getName() + "\t|");
            }
            System.out.println(x);
        }
        System.out.println();
    }

    public void printBoardForCoordinates(Coordinates[] coordinates){
        HashSet<Coordinates> cSet = new HashSet<>(Arrays.asList(coordinates));
        System.out.print("|");
        for (int x = 0; x < row; x++){
            System.out.printf("\t  %d\t\t|", x);
        }
        System.out.println();
        for (int x = 0; x < row; x++){
            System.out.print("|");
            for (int y = 0; y < column; y++){
                var coords = new Coordinates(x, y);
                if (cSet.contains(coords)){
                    var gem = board[x][y];
                    if (gem.type == Gem.GemType.Red)
                        System.out.print("\t"+gem.getName() + "\t\t|");
                    else
                        System.out.print("\t"+gem.getName() + "\t|");
                } else{
                    System.out.print("\t\t\t|");
                }
            }
            System.out.println(x);
        }
        System.out.println();
    }
}
