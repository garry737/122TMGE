package GridControl;

import java.util.ArrayList;
import java.util.Arrays;

public class Coordinates{
    public int row;
    public int column;
    public Coordinates(int row, int column){
        this.row = row;
        this.column = column;
    }

    public Coordinates(String str){
        str = str.replaceAll("[^0-9]+", " ");
        str = str.trim();
        var nums = Arrays.asList(str.trim().split(" "));
        if (nums.size() != 2)
            throw new Error("Bad input");
        row = Integer.parseInt(nums.get(0));
        column = Integer.parseInt(nums.get(1));
    }

    public Coordinates getUpCoords(){
        return new Coordinates(row -1, column);
    }

    public Coordinates getDownCoords(){
        return new Coordinates(row +1, column);
    }

    public Coordinates getRightCoords(){
        return new Coordinates(row, column +1);
    }

    public Coordinates getLeftCoords(){
        return new Coordinates(row, column -1);
    }

    public ArrayList<Coordinates> getDirections(){
        ArrayList<Coordinates> directions = new ArrayList<>();
        directions.add(this.getDownCoords());
        directions.add(this.getLeftCoords());
        directions.add(this.getUpCoords());
        directions.add(this.getRightCoords());
        return directions;
    }
    public boolean contains(Coordinates coordinates){
        ArrayList<Coordinates> directions = getDirections();
        if (directions.contains(coordinates))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", row, column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordinates)) return false;
        Coordinates coords = (Coordinates) obj;
        return row == coords.row && column == coords.column;
    }

    @Override
    public int hashCode() {
        return (row + column) * (row + column + 1) / 2 + row;
    }

    public static ArrayList<Coordinates> getFlippedCoordinates(ArrayList<Coordinates> coordinates) {
        var result = new ArrayList<Coordinates>();
        for(var coords:coordinates){
            Coordinates nc = new Coordinates(coords.column, coords.row);
            result.add(nc);
        }
        return result;

    }

}
