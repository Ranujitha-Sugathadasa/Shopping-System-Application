package Console;

import Console.Product;

public class Clothing extends Product {
    public String size;
    private String colour;


    //Default Constructor
    public Clothing(){
        size ="Undefined";
        colour = "Undefined";
        setCategory("Clothing");
    }

    //Parameterized Constructor
    public Clothing(String product_id, String product_name, int number_of_available_items, double price, String size, String colour) {
        super(product_id, product_name, number_of_available_items, price);
        this.size = size;
        this.colour = colour;
        setCategory("Clothing");
    }

    //Getters
    public String getSize() {
        return size;
    }
    public String getColour() {
        return colour;
    }


    //Setters
    public void setSize(String size) {
        this.size = size;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }


    public String getInfo(){return size + ", " + colour;}

    //Display details of product
    public String toString(){
            return super.toString()+
                    "\n" + "Size: " + size +
                    "\n" + "Colour: " + colour +
                    "\n ";
    }
}

