package TMGE.Game.GamePieces;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import TMGE.GridControl.Coordinates;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Piece extends Region {

    public Group grp;
    public int TILE_SIZE = 50;
    public int score;
    public Piece()
    {
        grp = getGroup();
        this.getChildren().add(grp);
    }

    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){ return null;}
    public void highlightPiece()
    {
        if (grp != null) {
            for (Node n : grp.getChildren()) {
                Rectangle r = (Rectangle) n;
                r.setFill(Color.LIGHTGREEN);
            }
        }
    }
    public void unhighlightPiece()
    {
        if (grp != null) {
            for (Node n : grp.getChildren()) {
                Rectangle r = (Rectangle) n;
                r.setFill(Color.MEDIUMAQUAMARINE);
            }
        }
    }
    public String getString(){ return  "";}
    // default
    public Group getGroup()
    {
        Group grp = new Group();
        Rectangle r1 = new Rectangle(50,50, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);
        grp.getChildren().add(r1);
        return grp;
    }


    static Piece1 GetPiece1() { return  new Piece1();}
    static Piece2 GetPiece2() { return  new Piece2();}
    static Piece3 GetPiece3() { return  new Piece3();}
    static Piece4 GetPiece4() { return  new Piece4();}
    static Piece5 GetPiece5() { return  new Piece5();}
    static Piece6 GetPiece6() { return  new Piece6();}
    static Piece7 GetPiece7() { return  new Piece7();}
    static Piece8 GetPiece8() { return  new Piece8();}
    static Piece9 GetPiece9() { return  new Piece9();}
    static Piece10 GetPiece10() { return  new Piece10();}
    static Piece11 GetPiece11() { return  new Piece11();}
    static Piece12 GetPiece12() { return  new Piece12();}
    static Piece13 GetPiece13() { return  new Piece13();}
    static Piece14 GetPiece14() { return  new Piece14();}
    static Piece15 GetPiece15() { return  new Piece15();}
    static Piece16 GetPiece16() { return  new Piece16();}
    static Piece17 GetPiece17() { return  new Piece17();}
    static Piece18 GetPiece18() { return  new Piece18();}
    static Piece19 GetPiece19() { return  new Piece19();}

    public static Piece RandomPiece(){
        Random random = new Random();
        int ranNum = random.nextInt(19);
        return pieceForNum(ranNum);

    }

    public static Piece pieceForNum(int num){
        if (num < 0)
            num = 0;
        if (num > 18)
            num = 6;
        switch (num){
            case 0: return GetPiece1();
            case 1: return GetPiece2();
            case 2: return GetPiece3();
            case 3: return GetPiece4();
            case 4: return GetPiece5();
            case 5: return GetPiece6();
            case 6: return GetPiece7();
            case 7: return GetPiece8();
            case 8: return GetPiece9();
            case 9: return GetPiece10();
            case 10: return GetPiece11();
            case 11: return GetPiece12();
            case 12: return GetPiece13();
            case 13: return GetPiece14();
            case 14: return GetPiece15();
            case 15: return GetPiece16();
            case 16: return GetPiece17();
            case 17: return GetPiece18();
            case 18: return GetPiece19();

        }
        return new Piece();
    }




}



class Piece1 extends Piece{

    // □
    public int score = 1;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        return result;
    }

    @Override
    public String getString() {
        return "□";
    }

    @Override
    public Group getGroup()
    {
        Group grp = new Group();
        Rectangle r1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        grp.getChildren().add(r1);
        return grp;
    }

}
class Piece2 extends Piece{
    // □ □
    public int score = 2;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        result.add(coordinates.getDownCoords());
        return result;
    }
    @Override
    public String getString() {
        return "□ □";
    }

    @Override
    public Group getGroup()
    {
        Group grp = new Group();
        Rectangle r1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        Rectangle r2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setX(0+TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        return grp;
    }
}
class Piece3 extends Piece{
    // □
    // □
    public int score = 2;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        result.add(coordinates.getRightCoords());
        return result;
    }
    @Override
    public String getString() {
        return "□\n□";
    }


    @Override
    public Group getGroup()
    {
        Group grp = new Group();
        Rectangle r1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setY(0);

        Rectangle r2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setY(0+TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        return grp;
    }
}

class Piece4 extends Piece{
    // □ □ □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoords = coordinates;
        for (int i = 0; i<2;i++){
            lastCoords = lastCoords.getDownCoords();
            result.add(lastCoords);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □";
    }

    @Override
    public Group getGroup()
    {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        Rectangle r2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setX(0+TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setX(0+TILE_SIZE+TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }
}

class Piece5 extends Piece{
    // □
    // □
    // □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□\n□\n□";
    }
    @Override
    public Group getGroup() {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setY(0);

        Rectangle r2 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setY(0 + TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setY(0 + TILE_SIZE + TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }
}

class Piece6 extends Piece{
    // □ □
    // □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        result.add(coordinates.getDownCoords());
        result.add(coordinates.getRightCoords());
        return result;
    }

    @Override
    public String getString() {
        return "□ □\n□";
    }
    public Group getGroup()
    {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        Rectangle r2 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setX(0 + TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setY(0 + TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }

}

class Piece7 extends Piece{
    // □
    // □ □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var bot = coordinates.getRightCoords();
        result.add(bot);
        result.add(bot.getDownCoords());
        return result;
    }
    @Override
    public String getString() {
        return "□\n□ □";
    }

    public Group getGroup()
    {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        Rectangle r2 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setY(0 + TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setX(0 + TILE_SIZE);
        r3.setY(0 + TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }
}

class Piece8 extends Piece{
    //   □
    // □ □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var bot = coordinates.getRightCoords();
        result.add(bot);
        result.add(bot.getUpCoords());
        return result;
    }
    @Override
    public String getString() {
        return "  □\n□ □";
    }
    public Group getGroup()
    {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0 + TILE_SIZE);

        Rectangle r2 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setY(0 + TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setX(0 + TILE_SIZE);
        r3.setY(0 + TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }

}

class Piece9 extends Piece{
    // □ □
    //   □
    public int score = 3;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var down = coordinates.getDownCoords();
        result.add(down);
        result.add(down.getRightCoords());
        return result;
    }
    @Override
    public String getString() {
        return "□ □\n  □";
    }
    public Group getGroup()
    {
        Group grp = new Group();

        Rectangle r1 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r1.setArcHeight(20);
        r1.setArcWidth(20);
        r1.setX(0);

        Rectangle r2 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r2.setArcHeight(20);
        r2.setArcWidth(20);
        r2.setX(0 + TILE_SIZE);

        Rectangle r3 = new Rectangle(TILE_SIZE, TILE_SIZE, Color.MEDIUMAQUAMARINE);
        r3.setArcHeight(20);
        r3.setArcWidth(20);
        r3.setX(0 + TILE_SIZE);
        r3.setY(0 + TILE_SIZE);

        grp.getChildren().add(r1);
        grp.getChildren().add(r2);
        grp.getChildren().add(r3);
        return grp;
    }
}

class Piece10 extends Piece{
    // □ □ □ □
    public int score = 4;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoords = coordinates;
        for (int i = 0; i<3;i++){
            lastCoords = lastCoords.getDownCoords();
            result.add(lastCoords);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □ □";
    }
    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 4; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setY(0);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }
}

class Piece11 extends Piece{
    // □ □ □
    // □ □ □
    // □ □ □
    public int score = 9;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoords = coordinates;
        var downCoords = lastCoords.getRightCoords();
        result.add(downCoords);
        result.add(downCoords.getRightCoords());
        for (int i = 0; i<2;i++){
            lastCoords = lastCoords.getDownCoords();
            result.add(lastCoords);
            downCoords = lastCoords.getRightCoords();
            result.add(downCoords);
            result.add(downCoords.getRightCoords());

        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □\n□ □ □\n□ □ □";
    }
    @Override
    public Group getGroup(){

        Group grp = new Group();
        for (int i = 0; i < 3;i++)
        {
            for(int j = 0; j< 3; j+=1){
                Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
                rec.setX(TILE_SIZE*i);
                rec.setY(TILE_SIZE*j);
                rec.setArcWidth(20);
                rec.setArcHeight(20);
                grp.getChildren().add(rec);
            }

        }
        return grp;
    }
}

class Piece12 extends Piece{
    // □ □ □ □ □
    public int score =5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoords = coordinates;
        for (int i = 0; i<4;i++){
            lastCoords = lastCoords.getDownCoords();
            result.add(lastCoords);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □ □ □";
    }

    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 5; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }
}

class Piece13 extends Piece{
    //     □
    //     □
    // □ □ □
    public int score = 5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getUpCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "    □\n" + "    □\n□ □ □";
    }

    @Override
    public Group getGroup(){
        Group grp = new Group();
        Rectangle rec1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec1.setX(TILE_SIZE*2);
        rec1.setArcWidth(20);
        rec1.setArcHeight(20);
        grp.getChildren().add(rec1);

        Rectangle rec2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec2.setX(TILE_SIZE*2);
        rec2.setY(TILE_SIZE);
        rec2.setArcWidth(20);
        rec2.setArcHeight(20);
        grp.getChildren().add(rec2);

        for(int x = 0; x< 3; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setY(TILE_SIZE*2);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }
}

class Piece14 extends Piece{
    // □ □ □
    //     □
    //     □
    public int score = 5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getDownCoords();
            result.add(lastCoord);
        }
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □\n    □\n    □";
    }
    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 3; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        Rectangle rec1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec1.setX(TILE_SIZE*2);
        rec1.setY(TILE_SIZE);
        rec1.setArcWidth(20);
        rec1.setArcHeight(20);
        grp.getChildren().add(rec1);

        Rectangle rec2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec2.setX(TILE_SIZE*2);
        rec2.setY(TILE_SIZE*2);
        rec2.setArcWidth(20);
        rec2.setArcHeight(20);
        grp.getChildren().add(rec2);
        return grp;
    }
}

class Piece15 extends Piece{
    // □ □ □
    // □
    // □
    public int score = 5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getDownCoords();
            result.add(lastCoord);
        }
        lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □ □\n□\n□";
    }

    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 3; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        Rectangle rec1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec1.setY(TILE_SIZE);
        rec1.setArcWidth(20);
        rec1.setArcHeight(20);
        grp.getChildren().add(rec1);

        Rectangle rec2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec2.setY(TILE_SIZE*2);
        rec2.setArcWidth(20);
        rec2.setArcHeight(20);
        grp.getChildren().add(rec2);
        return grp;
    }
}

class Piece16 extends Piece{
    // □ □
    // □ □
    public int score = 4;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoords = coordinates;
        var downCoords = lastCoords.getRightCoords();
        result.add(downCoords);
        for (int i = 0; i<1;i++){
            lastCoords = lastCoords.getDownCoords();
            result.add(lastCoords);
            downCoords = lastCoords.getRightCoords();
            result.add(downCoords);

        }
        return result;
    }
    @Override
    public String getString() {
        return "□ □\n□ □";
    }

    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 2; x++){
            for(int y = 0;y<2;y++){
                Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
                rec.setX(TILE_SIZE*x);
                rec.setY(TILE_SIZE*y);
                rec.setArcWidth(20);
                rec.setArcHeight(20);
                grp.getChildren().add(rec);
            }

        }

        return grp;
    }
}

class Piece17 extends Piece{
    // □
    // □
    // □ □ □
    public int score = 5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        for (int i = 0; i<2;i++){
            lastCoord = lastCoord.getDownCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□\n□\n□ □ □";
    }

    @Override
    public Group getGroup(){
        Group grp = new Group();
        Rectangle rec1 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec1.setArcWidth(20);
        rec1.setArcHeight(20);
        grp.getChildren().add(rec1);

        Rectangle rec2 = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
        rec2.setY(TILE_SIZE);
        rec2.setArcWidth(20);
        rec2.setArcHeight(20);
        grp.getChildren().add(rec2);

        for(int x = 0; x< 3; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setX(TILE_SIZE*x);
            rec.setY(TILE_SIZE*2);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }
}

class Piece18 extends Piece{
    // □
    // □
    // □
    // □
    public int score = 4;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<3;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□\n□\n□\n□";
    }
    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 4; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setY(TILE_SIZE*x);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }

}

class Piece19 extends Piece{
    // □
    // □
    // □
    // □
    // □
    public int score = 5;

    @Override
    public ArrayList<Coordinates> getCoordinates(Coordinates coordinates){
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        result.add(coordinates);
        var lastCoord = coordinates;
        for (int i = 0; i<4;i++){
            lastCoord = lastCoord.getRightCoords();
            result.add(lastCoord);
        }
        return result;
    }
    @Override
    public String getString() {
        return "□\n□\n□\n□\n□";
    }
    @Override
    public Group getGroup(){
        Group grp = new Group();
        for(int x = 0; x< 5; x++){
            Rectangle rec = new Rectangle(TILE_SIZE,TILE_SIZE, Color.MEDIUMAQUAMARINE);
            rec.setY(TILE_SIZE*x);
            rec.setArcWidth(20);
            rec.setArcHeight(20);
            grp.getChildren().add(rec);
        }

        return grp;
    }
}