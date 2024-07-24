package Console;

import Console.Product;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidators {
    //Double input validation
    public static double getValidDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Integer input validation
    public static int getValidIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Float input validation
    public static float getValidFloatInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextFloat();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
            }
        }
    }
    //Console.Product id validation
    public static String getValidProductId(Scanner scanner, ArrayList <Product> arrayList, String prompt) {
        while (true) {
            System.out.print(prompt);
            String potentialId = scanner.next().toUpperCase();
            if (duplicateProductId(potentialId, arrayList)) {
                System.out.println("""
                        There's already a product with the Product ID you entered.
                        Please enter a different ID.
                        """);
            } else {
                return potentialId;
            }
        }
    }
    //Duplicate product id validation
    public static boolean duplicateProductId(String productId, ArrayList <Product> arrayList) {
        for (Product product : arrayList) {
            if (product.getProduct_id().equals(productId)) {
                return true;
            }
        }
        return false;
    }


    //Integer input mismatch exceptions
    public static int noInputMismatchInteger(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid integer.\nType here: ");
                scanner.next(); // Consume the invalid input
            }
        }
    }
}
