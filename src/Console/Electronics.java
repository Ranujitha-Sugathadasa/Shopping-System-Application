package Console;

import Console.Product;

public class Electronics extends Product {
    private String Brand;
    private  float warranty_period;

    //Default Constructor
    public Electronics() {
        Brand = "Not Defined";
        this.warranty_period = 0;
        setCategory("Electronics");
    }

    //Parameterized Constructor
    public Electronics(String product_id, String product_name, int number_of_available_items, double price, String brand, float warranty_period) {
        super(product_id, product_name, number_of_available_items, price);
        this.Brand = brand;
        this.warranty_period = warranty_period;
        setCategory("Electronics");
    }

    //Getters
    public String getBrand() {return Brand;}
    public float getWarranty_period() {return warranty_period;}

    //Setters
    public void setBrand(String brand) {Brand = brand;}
    public void setWarranty_period(float warranty_period) {this.warranty_period = warranty_period;}

    public String getInfo(){return Brand + ", " + warranty_period + " weeks warranty";}

    //Console.Product Details
    public String toString(){
        return super.toString() +
                "\n" + "Brand: " + Brand +
                "\n" + "Warranty Period: " + warranty_period + " weeks" +
                "\n";
    }
}

