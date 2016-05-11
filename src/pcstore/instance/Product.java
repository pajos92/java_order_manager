package pcstore.instance;

import java.io.Serializable;
import static pcstore.MainClass._STORE_DB;

/**
 *
 * @author Panos
 */
public class Product implements Serializable {

    public enum ProductCategory {

        COMPUTER, PRINTER, COMPUTER_PART, PRINTER_PART;

        /**
         * Returns the category type by code value.
         *
         * @param value the value of the category 1-4
         * @return the category of the product by value given
         */
        public static ProductCategory getProductCategoryByValue(int value) {
            switch (value) {
                case 1:
                    return COMPUTER;
                case 2:
                    return PRINTER;
                case 3:
                    return COMPUTER_PART;
                case 4:
                    return PRINTER_PART;
            }

            return null;
        }

    };

    /* final static variable for the sufficient 
     threshold of a product stock */
    public static final int STOCK_THRESHOLD = 5;

    /* the unique id */
    private int id;
    /* the product name */
    private String name;
    /* the product category */
    private ProductCategory category;
    /* the product price */
    private double price;
    /* the ramaining amount in storage */
    private int stock;

    public Product(String name, ProductCategory category, double price, int stock) {
        if (_STORE_DB.getProducts() == null) {
            this.id = 1;
        } else {
            this.id = _STORE_DB.getProducts().size() + 1;
        }

        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    /* ----- all getters and setters below this ----- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return true if there is a sufficient amount in storage
     */
    public boolean isStockSufficient() {
        return getStock() > STOCK_THRESHOLD;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", price=" + price + '}';
    }

}
