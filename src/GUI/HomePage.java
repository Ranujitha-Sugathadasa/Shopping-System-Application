package GUI;
import Console.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private final ShoppingCart shoppingCart;
    private final JTable productTable;
    private final JComboBox<String> comboBox;
    private final JTextArea textArea;
    private final ShoppingCartGUI cartGUI;

    /**
     *Initializes the main page of the UI
     */
    public HomePage(User user) {

        //Creating shopping cart
        shoppingCart = new ShoppingCart();

        //Creating window for displaying user shopping cart
        cartGUI = new ShoppingCartGUI(shoppingCart,user);
        cartGUI.setVisible(false);

        //Working panels in the system
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        //Setting up table containing product details
        productTable = new JTable() {
            public Component prepareRenderer(
                    TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                //  add custom rendering here
                String id = (String)productTable.getValueAt(row, 0);
                for (Product i: WestminsterShoppingManager.getStoreItems()) {
                    if (i.getProduct_id().equals(id)) {
                        if (i.getNumber_of_available_items() < 3)
                            c.setForeground(Color.RED);
                        else c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        };
        fillTable("All");
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selection = productTable.getSelectedRow();
                if (selection != -1) {
                    displayProductDetails(WestminsterShoppingManager.getStoreItems().get(selection));
                }
            }
        });
        JScrollPane sp = new JScrollPane(productTable);

        //Setting up buttons
        JButton addToCart = new JButton("Add to Shopping Cart");
        addToCart.setPreferredSize(new Dimension(100,25));
        addToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selection = productTable.getSelectedRow();
                    String id = (String) productTable.getValueAt(selection,0);

                    for (Product item: WestminsterShoppingManager.getStoreItems()) {
                        if (item.getProduct_id().equals(id) && item.getNumber_of_available_items() > 0) {
                            shoppingCart.add(item);
                            item.setNumber_of_available_items(item.getNumber_of_available_items()-1);
                            productTable.getSelectionModel().clearSelection();
                            textArea.setText("");
                            shoppingCart.calculateTotal();
                            cartGUI.updateTable(shoppingCart);
                            cartGUI.updateLabel(shoppingCart,user);
                        }
                    }
                }
                catch(Exception ex) {
                    System.out.println();
                }
            }
        });

        JButton goToCart = new JButton("Shopping Cart");
        goToCart.setBounds(820, 20, 150, 30);
        goToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shoppingCart.getProductList().size() == 0) {
                    infoBox("Cart is empty","");
                }
                else
                    cartGUI.setVisible(true);
            }
        });

        //Dropdown list for product type selection
        String[] selectionOptions = {"All", "Electronics", "Clothing"};
        comboBox = new JComboBox<>(selectionOptions);
        comboBox.setBounds(350,20,300,30);
        comboBox.addActionListener(e -> {
            String choice = (String)comboBox.getSelectedItem();
            fillTable(choice);
        });

        //Setting text area to display product details
        textArea = new JTextArea();
        textArea.setMaximumSize(new Dimension(600, 200));
        textArea.setEditable(false);

        //Setting layout managers
        panel1.setLayout(null);
        panel2.setLayout(new BorderLayout());
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));

        //Adding components to panels
        JLabel label1 = new JLabel("Select Product Category");
        label1.setBounds(100, 20, 200, 30);
        panel1.add(label1);
        panel1.add(comboBox);
        panel1.add(goToCart);
        panel2.add(sp, BorderLayout.CENTER);
        panel2.add(new JPanel(), BorderLayout.EAST);
        panel2.add(new JPanel(), BorderLayout.WEST);
        panel2.add(new JPanel(), BorderLayout.SOUTH);
        panel3.add(textArea);
        Component rigidArea = Box.createRigidArea(new Dimension(0, 10)); // Width, height
        panel3.add(rigidArea); // Add it between the textarea and button
        panel3.add(addToCart);

        //Setting up the frame
        add(panel1);
        add(panel2);
        add(panel3);
        setTitle("Westminster Shopping Centre");
        setLayout(new GridLayout(3,1));
        setSize(1000,600);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void fillTable(String selection) {
        String[] columnNames = {"Product ID", "Name", "Category", "Price", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Product item: WestminsterShoppingManager.getStoreItems()) {
            if (selection.equals("All"))
                tableModel.addRow(new Object[]{item.getProduct_id(), item.getProduct_name(), item.getCategory(),
                        item.getPrice(), item.getInfo()});
            else if (selection.equals("Electronics") && item instanceof Electronics)
                tableModel.addRow(new Object[]{item.getProduct_id(), item.getProduct_name(), item.getCategory(),
                        item.getPrice(), item.getInfo()});
            else if (selection.equals("Clothing") && item instanceof Clothing)
                tableModel.addRow(new Object[]{item.getProduct_id(), item.getProduct_name(), item.getCategory(),
                        item.getPrice(), item.getInfo()});
        }
        productTable.setModel(tableModel);
    }

    private void displayProductDetails(Product item) {
        String details = "Selected Product - Details" + "\nProduct ID: " + item.getProduct_id() +
                "\nCategory: " + item.getCategory() + "\nName: " + item.getProduct_name();
        if (item instanceof Electronics)
            details += "\nBrand: " + ((Electronics) item).getBrand() + "\nWarranty period: " +
                    ((Electronics) item).getWarranty_period();
        else if (item instanceof Clothing)
            details += "\nSize: " + ((Clothing) item).getSize() + "\nColor: " + ((Clothing) item).getColour();
        details += "\nNumber of available items: " + item.getNumber_of_available_items();
        textArea.setText(details);
    }

    public void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
