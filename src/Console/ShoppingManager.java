package Console;

import java.util.ArrayList;

public interface  ShoppingManager {
    //Adds a new Console.Product to the System----------------------------------------------------------------------------------
    void addProduct();

    //Removes existing Products from the System-------------------------------------------------------------------------
    void removeProduct();

    //Displays a list of existing products of the System----------------------------------------------------------------
    void showProducts();

    //Saves a list of existing products of the System onto a text file--------------------------------------------------
    void saveToFile();

    //Reads the list of existing products of the System from the text file----------------------------------------------
    void readFromFile();

}
