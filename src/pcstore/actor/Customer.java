package pcstore.actor;

import java.io.Serializable;
import java.util.Scanner;
import static pcstore.MainClass._STORE_DB;
import pcstore.instance.Order;

/**
 *
 * @author Panos
 */
public class Customer implements Serializable {

    //2 references for easy access
    //List<Customer> sampleOrderList = pcstore.MainClass.sampleOrders;
    //List<Product> sampleProductList = pcstore.MainClass.sampleProducts;

    /* the current order for a customer to be filled */
    private Order _order = null;

    /* first name */
    private String name;
    /* last name */
    private String lname;
    /* email address */
    private String email;

    public Customer() {
    }

    /**
     *
     * Constructor for this class
     *
     * @param name
     * @param lname
     * @param email
     */
    public Customer(String name, String lname, String email) {
        this.name = name;
        this.lname = lname;
        this.email = email;
    }

    /**
     *
     * Inserts the final order into the static order list where all orders are
     * held.
     */
    public void submitOrder() {

        Scanner input = new Scanner(System.in);
        System.out.println("===== Submit order =====");
        System.out.println("First name : ");
        String name1 = input.next();
        System.out.println("Last name : ");
        String lname1 = input.next();
        System.out.println("Email : ");
        String email1 = input.next();

        setName(name1);
        setLname(lname1);
        setEmail(email1);

        _STORE_DB.addOrderToList(this);
        /* subtract ordered amount from product stock */
        getOrder().getProductsOrdered().keySet().stream().forEach((p) -> {
            p.setStock(p.getStock() - getOrder().getProductsOrdered().get(p));
        });

        System.out.println("==================================");
        System.out.println("- Your order has been submitted. -");
        System.out.println("==================================");

    }

    /*
     Creates a new order object with the products selected
     */
    public void createOrder() {

        int totalProductCounter = _STORE_DB.getProducts().size();

        Scanner input = new Scanner(System.in);
        int product_number;
        char oneMore = 'y';
        if (getOrder() == null)
            this._order = new Order();

        while (oneMore == 'y') {
            System.out.println("Enter the product you want to add to your order (0-" + totalProductCounter + "): ");
            product_number = input.nextInt();
            if (product_number > 0 && product_number <= totalProductCounter) {
                int amount_request = 0;
                while (amount_request <= 0) {
                    System.out.println("Enter quantity :");
                    amount_request = input.nextInt();
                }
                this._order.addProduct(_STORE_DB.getProducts().get(product_number - 1), amount_request);
            } else {
                System.out.println("[Error] : Product doesn't exists.");
                oneMore = 'y';
                continue;
            }
            System.out.println("Add another product? [y/n]");
            oneMore = input.next().charAt(0);
        }
    }

    /**
     * Prints all the available products
     */
    public void browseProductList() {
        _STORE_DB.getProducts().stream().forEach((p) -> {
            System.out.println("On sale : " + p.getName() + " - " + p.getPrice() + " , Stock : " + p.getStock());
        });
    }

    @Override
    public String toString() {
        return "Customer{" + "name=" + name + ", lname=" + lname + ", email=" + email + '}';
    }

    /* ----- all getters and setters below this ----- */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Order getOrder() {
        return _order;
    }

    public void setOrder(Order _order) {
        this._order = _order;
    }
}
