package Console;

import GUI.HomePage;
import GUI.Login;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    static ArrayList<Product> storeItems = new ArrayList<>();



    public static ArrayList<Product> getStoreItems(){
        return storeItems;
    }

    public static void main(String[] args) {
        WestminsterShoppingManager newShoppingManager = new WestminsterShoppingManager();
        newShoppingManager.displayMainMenu(storeItems);
        User.loadFromFile();
    }



    //Menu console display
    private void displayMainMenu(ArrayList <Product> arrayList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n-------- Westminster Shopping Manager --------");

        boolean exit = false;
        while (!exit) {
            System.out.print("""
                    
                    Enter an integer from the list to perform an Action
                    
                        1. Add Product
                        2. Delete Product
                        3. View All Products
                        4. Save to File
                        5. Load from File
                        6. Open Graphical User Interface
                        0. Exit
                    
                    Type here:\s""");

            int choice = InputValidators.noInputMismatchInteger(scanner);

            switch (choice) {
                case 1 -> {
                    addProduct();
                }
                case 2 -> {
                    removeProduct();
                }
                case 3 -> {
                    showProducts();
                }
                case 4 -> {
                    saveToFile();
                }
                case 5 -> {
                    readFromFile();
                }
                case 6 ->{
                    new Login();

                }
                case 0 -> {
                    System.out.println("Exiting the Westminster Shopping Manager. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }



    //Add to product
    @Override
    public void addProduct() {

        if (storeItems.size() < 50) {
            while (true) {
                Product product = createProduct();
                if (product != null) {
                    storeItems.add(product);
                    showNewProduct(product);

                    if (stopActionMoreProducts("adding")) {
                        break;
                    }
                } else {
                    break;
                }
            }
        } else {
            System.out.println("""
                    The system can only contain 50 Products at most.
                    Try deleting some products first.
                    """);
        }
    }

    //Create product

    private Product createProduct(){
        Product product;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    
                    
                    (Enter "E" for Electronic, "C" for Clothing and "Q" to go back)
                    Type here:\s""");

            String input = scanner.next().toUpperCase();

            switch (input) {
                case "E" -> product = new Electronics();
                case "C" -> product = new Clothing();
                case "Q" -> {
                    return null;
                }
                default -> {
                    System.out.println("Invalid Input. Please try again.");
                    continue;
                }
            }

            setProductDetails(product, storeItems, scanner);
            break;
        }

        return product;
    }

    //setProduct
    private void setProductDetails(Product product, ArrayList <Product> arrayList, Scanner scanner){
        //Checking for duplicate Product IDs
        product.setProduct_id(InputValidators.getValidProductId(scanner, arrayList, "\nEnter Product ID: "));

        System.out.print("Enter Product Name: ");
        product.setProduct_name(scanner.next().toUpperCase());

        //Validating double input
        product.setPrice(InputValidators.getValidDoubleInput(scanner,"Enter Price: "));

        //Validating integer input
        product.setNumber_of_available_items(InputValidators.getValidIntInput(scanner, "Enter Number of available items: "));

        //finding which subclass the "product" object is an instance of
        if (product instanceof Electronics) {
            setElectronicDetails(product, scanner);
        }
        else {
            setClothingDetails(product, scanner);
        }
    }
    private void setElectronicDetails(Product product, Scanner scanner) {
        System.out.print("Enter Brand Name: ");
        ((Electronics) product).setBrand(scanner.next().toUpperCase());

        ((Electronics) product)//Validating Input
                .setWarranty_period(InputValidators.getValidFloatInput(scanner, "Enter Warranty(in weeks): "));
    }
    private void setClothingDetails(Product product, Scanner scanner) {
        //validating the input in clothing class
        while (true) {
            System.out.print("Enter Size(XS, S, M, L or XL): ");
            String sizeInput = scanner.next().toUpperCase();
            if (sizeInput.equals("XS") ||
                    sizeInput.equals("S") ||
                    sizeInput.equals("M") ||
                    sizeInput.equals("L") ||
                    sizeInput.equals("XL")) {
                ((Clothing) product).setSize(sizeInput);
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        System.out.print("Enter Colour: ");
        ((Clothing) product).setColour(scanner.next());
    }

    //show added product
    private void showNewProduct(Product product){
        System.out.println("You've successfully added the following Product");
    //show which subclass product instance of
        System.out.println(product);
    }

    //Removing the product
    @Override
    public void removeProduct() {

        if (!storeItems.isEmpty()) {
            while (true) {
                System.out.print("""
                        
                        Enter the Product ID to remove the Product from the System.
                        (Enter "Q" to go back)
                        Type here:\s""");
                Scanner scanner = new Scanner(System.in);
                String productId = scanner.next().toUpperCase();

                //Go back-----------------
                if (productId.equals("Q")) {
                    break;
                }

                findAndRemove(productId, storeItems);

                if (stopActionMoreProducts("deleting")) {
                    break;
                }
            }

        }
        else {
            System.out.println("The System does not contain any Products at the moment.");
        }
    }

    //Find the Product by product ID
    private void findAndRemove(String productId, ArrayList <Product> arrayList) {
        Product productToDelete = null;

        for (Product product : arrayList) {
            if (product.getProduct_id().equals(productId.toUpperCase())) {
                productToDelete = product;
                arrayList.remove(product);
                break;
            }
        }
        if (productToDelete == null) {
            System.out.println("\nThere is no product with the product ID " + productId.toUpperCase() + " in the system." +
                    "\nPlease try again.");
        } else {
            System.out.println("You've successfully deleted the following Product\n");
            if (productToDelete instanceof Electronics) {
                System.out.print("Type: Electronic");
            } else if (productToDelete instanceof Clothing) {
                System.out.print("Type: Clothing");
            }
            //Checking which subclass the "productToDelete" object
            System.out.println(productToDelete);
        }
    }

    //User response for continuation
    private boolean stopActionMoreProducts(String Action){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.printf("""
                            Would you like to keep adding/removing more Products ?
                            (Enter "Y" for Yes, "N" for No)
                            Type here:\s""", Action);
            String more = scanner.next().toUpperCase();

            switch (more) {
                case "Y" -> {
                    return false;
                }
                case "N" -> {
                    return true;
                }
                default -> System.out.println("Invalid Input. Please try again.");
            }
        }
    }

    //show all product
    @Override
    public void showProducts() {
        if (storeItems.isEmpty()) {
            System.out.println("""
                    The System does not contain any Products at the moment.
                    Try adding some products first.""");
        } else {
            //Sorting the ArrayList Alphabetically------------------------
            Collections.sort(storeItems);

            for (Product i: storeItems) {
                System.out.println(i);
            }
        }
    }

    //Saves a list of existing products of the System onto a text file--------------------------------------------------
    @Override
    public void saveToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Store_Item_List.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(WestminsterShoppingManager.storeItems);

            fileOut.close();
            objOut.close();
            System.out.println("Saved to file successfully");
        }
        catch (Exception e) {
            System.out.println("Saving to file was unsuccessful");

        }
    }

    @Override
    public void readFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream("Store_Item_List.ser");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            WestminsterShoppingManager.storeItems = (ArrayList<Product>) objIn.readObject();
            fileIn.close();
            objIn.close();
            System.out.println("Loaded from file successfully");
        }
        catch (Exception e) {
            System.out.println("Loading from file unsuccessful");
        }
    }

    //Arranging all Product details in an easy to read format
    private String formatAllDetails(Product product) {
        if (product instanceof Electronics) {
            return "Electronic: " +
                    formatCommonDetails(product) +
                    ((Electronics) product).getBrand() + " " +
                    ((Electronics) product).getWarranty_period();
        } else {
            return "Clothing: " +
                    formatCommonDetails(product) +
                    ((Clothing) product).getSize() + " " +
                    ((Clothing) product).getColour();
        }
    }

    //Arranging common Product details in an easy to read format
    private String formatCommonDetails(Product product) {
        return product.getProduct_id() + " " +
                product.getProduct_name() + " " +
                product.getPrice() + " " +
                product.getNumber_of_available_items() + " ";
    }

    //Asking the user if they want to erase data or not
    private boolean erase() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                    There are no new Products to save.
                    Continuing this action will erase all previously saved Products.
                    """);
        while (true) {
            System.out.print("""
                    Do you want to continue? (Enter "Y" for Yes, "N" for No)
                    Type here:\s""");
            String save = scanner.next().toUpperCase();

            switch (save) {
                case "Y" -> {
                    return true;
                }
                case "N" -> {
                    return false;
                }
                default -> System.out.println("Invalid Input. Please try again.\n");
            }
        }
    }
}
