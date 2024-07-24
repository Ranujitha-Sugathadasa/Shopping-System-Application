package Console;

import java.io.Serializable;

public abstract class Product implements Comparable<Product>, Serializable {
    private String product_id;
    private String product_name;
    private int number_of_available_items;
    public double price;
    private String category;


    //Default Constructor
    public Product(){
        product_id = "Not Found";
        product_name =  "Undefined";
        number_of_available_items = -1;
        price = -1;
    }

    //Parameterised Constructor
    public Product(String product_id, String product_name, int number_of_available_items, double price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.number_of_available_items = number_of_available_items;
        this.price = price;
    }

    //Getters
    public String getProduct_id() {return product_id;}
    public String getProduct_name() {return product_name;}
    public int getNumber_of_available_items() {return number_of_available_items;}
    public double getPrice() {return price;}
    public String getCategory(){return category;}

    //Setters
    public void setProduct_id(String product_id) {this.product_id = product_id;}
    public void setProduct_name(String product_name) {this.product_name = product_name;}
    public void setNumber_of_available_items(int number_of_available_items) {this.number_of_available_items = number_of_available_items;}
    public void setPrice(double price) {this.price = price;}
    public void setCategory(String category){this.category = category;}

    //Product details
    public String toString(){
        return "\n" + "Product Name: " + product_name +
                "\n" + "Product ID: " + product_id +
                "\n" + "Category: " + category +
                "\n" + "Number of items left: " +number_of_available_items +
                "\n" + "Price: " + price ;

    }

    public abstract  String getInfo();

    // Add compareTo method for sorting by product ID
    public int compareTo(Product otherProduct) {
        return this.product_id.compareTo(otherProduct.product_id);
    }

}
