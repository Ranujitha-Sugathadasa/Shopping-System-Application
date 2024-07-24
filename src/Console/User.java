package Console;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable{
    private String username;
    private String password;
    private int purchaseCount = 0;
    private static ArrayList<User> userList = new ArrayList<>();
    private static final Map<String,String> userDetails = new HashMap<>();



    //Parameterized constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //Getters
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public int getPurchaseCount() {return purchaseCount;}
    public static ArrayList<User> getUserList() {
        return userList;
    }
    public static Map<String, String> getUserDetails() {
        return userDetails;
    }

    //Setters
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setPurchaseCount(int purchaseCount){this.purchaseCount = purchaseCount;}


    //foe the gui description
    public static void writeToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("users.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(userList);
            System.out.println("Successfully saved to file");

            fileOut.close();
            objectOut.close();
        }
        catch (Exception e) {
            System.out.println("Saving to file was unsuccessful");
        }
    }

    public static void loadFromFile() {
        try {
            File file = new File("users.ser");
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            userList = (ArrayList<User>) objIn.readObject();
            fileIn.close();
            objIn.close();

            for (User u: userList) {
                userDetails.put(u.username, u.password);
            }
        }
        catch (Exception e) {
            System.out.println("Unable to load from file");
        }
    }


}
