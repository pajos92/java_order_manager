package pcstore.actor;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import static pcstore.MainClass._STORE_DB;
import pcstore.instance.Product;
import pcstore.instance.Product.ProductCategory;

/**
 *
 * @author Panos
 */
public class StorageManager extends Manager implements Serializable {

    /**
     * Constructor for this class. Calls superclass Manager for username &
     * password.
     *
     * @param username
     * @param password
     */
    public StorageManager(String username, String password) {
        super(username, password);
    }

    /**
     * Checks for product stock sufficiency.
     */
    public void checkProductStock() {
        for (Product p : _STORE_DB.getProducts()) {
            if (p.isStockSufficient()) {
                System.out.println("(" + p.getId() + ") Product " + p.getName() + " is stock sufficient: " + p.getStock() + " left in stock.");
            } else {
                System.out.println("(" + p.getId() + ") Product " + p.getName() + " is running low: " + p.getStock() + " left in stock.");
            }
        }
    }

    /**
     * Adds ordered amount to a product, to refresh its stock.
     *
     */
    public void refreshStock() {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the id of the product to refresh its stock:");
        int id = input.nextInt();
        System.out.println("Enter the amount to add in stock:");
        int refreshAmount = input.nextInt();

        Product productToRefresh = null;
        for (Product p : _STORE_DB.getProducts()) {
            if (id == p.getId()) {
                productToRefresh = p;
            }
        }
        if (refreshAmount < 0) {
            System.out.println("Wrong refresh amount as input.");
            return;
        }
        if (productToRefresh == null) {
            System.out.println("This product doesn't exist.");
        } else {
            /* add the refresh amount to current stock amount */
            productToRefresh.setStock(productToRefresh.getStock() + refreshAmount);
            System.out.println("Product (" + productToRefresh.getId() + ")" + productToRefresh.getName() + " has been successfully refreshed.");
        }

    }

    /**
     * Inserts a new product in the product list/database.
     */
    public void submitNewProduct() {

        String name = "";
        int stock, category = 0;
        double price;

        Scanner input = new Scanner(System.in);
        System.out.println("..:: Submit a new product ::.. ");

        /* input a product name*/
        System.out.print("Enter the product name: ");
        name = input.next();

        /* input a category for the product */
        ProductCategory categoryType = null;
        while (category < 1 || category > 4) {
            System.out.println("Enter the product's category: \n1.Computer\n2.Printer\n3.Computer Part\n4.Printer Part");
            category = input.nextInt();
            categoryType = Product.ProductCategory.getProductCategoryByValue(category);
        }

        /* input a product price */
        System.out.println("Enter the product's price: ");
        price = input.nextDouble();

        /* input a product stock amount in storage */
        System.out.println("Enter the product's stock amount: ");
        stock = input.nextInt();

        /* creates the new product */
        Product newProduct = new Product(name, categoryType, price, stock);
        /* insert it in the product list */
        _STORE_DB.addProductToList(newProduct);
    }

    @Override
    public void showMenu() {
        System.out.println("======= Storage Manager Menu =======");
        System.out.println("1. Check product stock.");
        System.out.println("2. Refresh existing stock.");
        System.out.println("3. Submit new product in storage.");
        System.out.println("4. Logout.");
    }

    @Override
    public String toString() {
        return "Storage Manager";
    }

}
