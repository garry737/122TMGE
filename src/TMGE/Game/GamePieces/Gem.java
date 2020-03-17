package TMGE.Game.GamePieces;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Gem {
    static Image redGem = new Image("file:assets/bejeweled/red2.png");
    static Image blueGem = new Image("file:assets/bejeweled/blue2.png");
    static Image greenGem = new Image("file:assets/bejeweled/green2.png");
    static Image yellowGem = new Image("file:assets/bejeweled/yellow2.png");
    static Image orangeGem = new Image("file:assets/bejeweled/orange2.png");
    static Image whiteGem = new Image("file:assets/bejeweled/white2.png");
    static Image purpleGem = new Image("file:assets/bejeweled/purple2.png");
    public enum GemType{
        Red("Red"),
        Blue("Blue"),
        Green("Green"),
        Yellow("Yellow"),
        Orange("Orange"),
        Purple("Purple"),
        White("White");

        private String text;

        GemType(String text){
            this.text = text;
        }

        public static GemType fromString(String text) {
            for (GemType a : GemType.values()) {
                if (a.text.equalsIgnoreCase(text))
                    return a;
            }
            return Red;
        }
    }

    int points;
    Image image;
    public GemType type;

    Gem(int points,Image image, GemType type){
        this.points = points;
        this.image = image;
        this.type = type;
    }

    public Image getImage(){
        return image;
    }

    static Gem gemForNum(int num){
        if (num < 0)
            num = 0;
        if (num > 6)
            num = 6;
        switch (num){
            case 0: return RedGem();
            case 1: return BlueGem();
            case 2: return GreenGem();
            case 3: return YellowGem();
            case 4: return OrangeGem();
            case 5: return PurpleGem();
            case 6: return WhiteGem();
        }
        return null;
    }

    public String getName(){
        return type.toString();
    }

    static public double getScore(int numGems, Gem gem){
        double multiplier = 1.0;
        if (numGems > 3) {
            int extraGem = numGems - 3;
            multiplier = multiplier + 0.2 * extraGem;
        }
        return gem.points * multiplier;
    }

    static public double getScore(int numGems, GemType type){
        int points = 0;
        switch (type) {
            case Red:
                points = 10;
                break;
            case Blue:
                points = 20;
                break;
            case Green:
                points = 30;
                break;
            case Yellow:
                points = 40;
                break;
            case Orange:
                points = 50;
                break;
            case Purple:
                points = 60;
                break;
            case White:
                points = 70;
                break;
        }
        double multiplier = 1.0;
        if (numGems > 3) {
            int extraGem = numGems - 3;
            multiplier = multiplier + 0.2 * extraGem;
        }
        return points * multiplier;
    }

    static Gem RedGem(){ return  new Gem(10, redGem, GemType.Red); }
    static Gem BlueGem(){ return  new Gem(20, blueGem, GemType.Blue); }
    static Gem GreenGem(){ return  new Gem(30, greenGem, GemType.Green); }
    static Gem YellowGem(){ return  new Gem(40, yellowGem, GemType.Yellow); }
    static Gem OrangeGem(){ return  new Gem(50, orangeGem, GemType.Orange); }
    static Gem PurpleGem(){ return  new Gem(60, purpleGem, GemType.Purple); }
    static Gem WhiteGem(){ return  new Gem(70, whiteGem, GemType.White); }
    public static Gem RandomGem(){
        Random random = new Random();
        int ranNum = random.nextInt(7);
        return gemForNum(ranNum);
    }
    static boolean LSB(int num, int K)
    {
        return (num & (1 << (K-1))) != 0;
    }

    public static Gem getAvialableGem(HashSet<GemType> gemSet){
        int bitMap = 0;
        for (var gemType:gemSet){
            switch (gemType){
                case Red:
                    bitMap = bitMap | (1);
                    break;
                case Blue:
                    bitMap = bitMap | (1 << 1);
                    break;
                case Green:
                    bitMap = bitMap | (1 << 2);
                    break;
                case Yellow:
                    bitMap = bitMap | (1 << 3);
                    break;
                case Orange:
                    bitMap = bitMap | (1 << 4);
                    break;
                case Purple:
                    bitMap = bitMap | (1 << 5);
                    break;
                case White:
                    bitMap = bitMap | (1 << 6);
                    break;
            }
        }
        if (bitMap == 127) //bitmap == 1111111 meaning there should be no more random
            return null;
        //Go through bitmap and check which gem hasn't been used
        Random random = new Random();
        int ranNum = random.nextInt(7);
        ArrayList<Integer> randList = new ArrayList<>();
        while (randList.size() != 7){
            if (!randList.contains(ranNum))
                randList.add(ranNum + 1);
            ranNum = random.nextInt(7);
        }
        for (var i:randList){
            boolean isSet = LSB(bitMap, i);
            if (!isSet){
                return gemForNum(i);
            }
        }
        return null;
    }





}
