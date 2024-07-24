package Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final ArrayList<Product> productList;
    // maintains the number of items of each product type
    private final Map <Product,Integer> frequencies;
    //maintain the total price
    private double rawTotalPrice;
    //Constructor
    public ShoppingCart() {
        this.productList = new ArrayList<>();
        this.frequencies = new HashMap<>();
    }

    //Getters
    public ArrayList<Product> getProductList() {
        return productList;
    }
    public double getRawTotalPrice() {
        return rawTotalPrice;
    }
    //setter
    public void setRawTotalPrice(double rawTotalPrice) {
        this.rawTotalPrice = rawTotalPrice;
    }

    //Counting the items
    public void add(Product item) {
        Integer count = 0;
        if (productList.contains(item)) {
            count = frequencies.get(item);
            frequencies.put(item, count + 1);
        }
        else {
            productList.add(item);
            frequencies.put(item, count + 1);
        }
    }
    //Calculate the items
    public double calculateTotal() {
        rawTotalPrice = 0;
        for (Product i: productList)
            rawTotalPrice += (frequencies.get(i) * i.getPrice());
        return rawTotalPrice;
    }

    //Calculate the discount
    public double calculateSameTypeDiscountOne() {
        double discountValue = 0;
        for (Product i: productList) {
            if (frequencies.get(i) >= 3)
                discountValue = rawTotalPrice * 0.20;
        }
        return discountValue;
    }
    //Calculate the first buying the discount
    public double calculateFirstBuyDiscount(User user) {
        double discountValue = 0;
        if (user.getPurchaseCount() == 0)
            discountValue = rawTotalPrice * 0.1;
        return discountValue;
    }
    //getters
    public double getPricing(Product item) {
        return frequencies.get(item) * item.getPrice();
    }
    public int getQuantity(Product item) {
        return frequencies.get(item);
    }

    //display the details of the product
    public String getProductDetails(Product item) {
        StringBuilder str = new StringBuilder();
        if (productList.contains(item)) {
            str.append(item.getProduct_id()).append(", ").append(item.getProduct_name()).append(", ");

            if (item instanceof Electronics)
                str.append(((Electronics) item).getBrand()).append(", ")
                        .append(((Electronics) item).getWarranty_period()).append(" weeks warranty");
            else if (item instanceof Clothing)
                str.append(((Clothing) item).getSize()).append(", ").append(((Clothing) item).getSize());
        }
        return str.toString();
    }
}




