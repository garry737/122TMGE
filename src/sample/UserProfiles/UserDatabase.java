package sample.UserProfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserDatabase {

    public static String CURRENT_USER = "";
    private ArrayList<String> usernames;
    private HashMap<String, String> passwords;
//    private HashMap<String, String> profilePictures;
    private String filePath;

    public UserDatabase(String filePath){
        this.filePath = filePath;
        this.usernames = new ArrayList<>();
        this.passwords = new HashMap<>();
//        this.profilePictures = new HashMap<>();

        try {
            File myObj = new File(this.filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] userData = data.split(" ");

                if (userData.length == 2){
                    String uname = userData[0].toLowerCase();
                    this.usernames.add(uname);
                    this.passwords.put(uname, userData[1]);
//                    this.profilePictures.put(uname,userData[2]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public boolean addUser(String username, String password){
        try {

            // Open given file in append mode.
            if (this.usernames.contains(username.toLowerCase())){
                System.out.println("USER ALREADY EXIST!");
                return false;
            }
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(this.filePath, true));
            String uname = username.toLowerCase();
            out.write(uname + " " + password + "\n");

            this.usernames.add(uname);
            this.passwords.put(uname, password);
//            this.profilePictures.put(uname,profilePicture);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
        return true;
    }

    public boolean login(String username){
        if (this.usernames.contains(username.toLowerCase())){
            System.out.println("User Exists!");
            return true;
        }
        System.out.println("User does not exist!");
        return false;
    }

    public boolean checkPassword(String username, String password){
        System.out.println("okay");
        if (this.passwords.get(username).equals(password)){
            System.out.println("wat is happening");
            System.out.println(this.passwords.get(username));
            System.out.println("okay 2");
            return true;
        }
        return false;
    }

    public boolean checkValidUsernamePassword(String username, String password){
        if (username.contains(" ") || password.contains(" ")){
            return false;
        }
        return true;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public HashMap<String, String> getPasswords() {
        return passwords;
    }

    public void setPasswords(HashMap<String, String> passwords) {
        this.passwords = passwords;
    }

//    public HashMap<String, String> getProfilePictures() {
//        return profilePictures;
//    }
//
//    public void setProfilePictures(HashMap<String, String> profilePictures) {
//        this.profilePictures = profilePictures;
//    }
}
