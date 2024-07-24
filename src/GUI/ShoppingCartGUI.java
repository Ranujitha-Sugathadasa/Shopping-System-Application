package GUI;

import Console.Product;
import Console.ShoppingCart;
import Console.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShoppingCartGUI extends JFrame {

    private final JTable tableOfSelectedItems;
    private final JLabel priceLabel;
    private final JLabel sameTypeDiscountLabel;
    private final JLabel firstBuyDiscountLabel;
    private final JLabel finalPriceLabel;
    public ShoppingCartGUI(ShoppingCart cart, User user) {

        //Initializing table
        tableOfSelectedItems = new JTable();
        updateTable(cart);
        JScrollPane sp = new JScrollPane(tableOfSelectedItems);
        sp.setBounds(50, 50, 700, 200);

        //Setting up labels
        priceLabel = new JLabel();
        priceLabel.setBounds(250, 250, 300, 100);
        firstBuyDiscountLabel = new JLabel();
        firstBuyDiscountLabel.setBounds(250, 280, 300, 100);
        sameTypeDiscountLabel = new JLabel();
        sameTypeDiscountLabel.setBounds(250, 310, 500, 100);
        finalPriceLabel = new JLabel();
        finalPriceLabel.setBounds(250, 340, 300, 100);

        //Setting up button
        JButton buyProducts = new JButton("Buy Products");
        buyProducts.setBounds(260,430,150,25);
        buyProducts.addActionListener(e -> {
            int purchaseCount = user.getPurchaseCount() + 1;
            user.setPurchaseCount(purchaseCount);
            System.out.println(purchaseCount);
            System.out.println("user: " + user.getPurchaseCount());
            User.writeToFile();
        });

        //Setting up frame
        setTitle("Shopping Cart");
        setLayout(null);
        add(sp);
        add(priceLabel);
        add(firstBuyDiscountLabel);
        add(sameTypeDiscountLabel);
        add(finalPriceLabel);
        add(buyProducts);
        setSize(800,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    //Update the table from the cart
    public void updateTable(ShoppingCart cart) {
        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        for (Product item: cart.getProductList()) {
            tableModel.addRow(new Object[]{cart.getProductDetails(item), cart.getQuantity(item), cart.getPricing(item)});
        }
        tableOfSelectedItems.setModel(tableModel);
    }
    //Display the Discount and final price
    public void updateLabel(ShoppingCart cart, User user) {
        priceLabel.setText("Total Price - " + cart.getRawTotalPrice());
        firstBuyDiscountLabel.setText("First purchase discount(10%) - " + cart.calculateFirstBuyDiscount(user));
        sameTypeDiscountLabel.setText("Three items in same category discount(20%) - " +
                String.format("%.2f",cart.calculateSameTypeDiscountOne()));
        finalPriceLabel.setText("Final Price - " + (cart.calculateTotal() - (cart.calculateSameTypeDiscountOne() +
                cart.calculateFirstBuyDiscount(user))));
    }
}