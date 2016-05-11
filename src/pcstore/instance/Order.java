package pcstore.instance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import pcstore.actor.Customer;

/**
 * Order holds all the products list for a particular order, created by a
 * customer. Also has a state of fulfillment, handled by the cash manager
 *
 * @author Panos
 */
public class Order implements Serializable {

    /* all three stages an order passes through */
    public enum OrderState {

        STARTED, IN_PROGRESS, FINISHED, CANCELED
    };

    /* the list of all the products for this current order */
    private final Map<Product, Integer> _productsOrdered;

    /* the unique id */
    private int id;
    /* the customer that created that order */
    private Customer customer;
    /* the order state */
    private OrderState state;
    /* the invoice attached to this order */
    private Invoice invoice;

    /*
     Constructor for this class
     */
    public Order() {
        this.invoice = null;
        this._productsOrdered = new HashMap<>();
        this.state = OrderState.STARTED;
        //just a random id cause it's easy for now.
        Random r = new Random();
        this.id = r.nextInt(99999999) + 1;
    }

    /**
     * Adds a product in the order
     *
     * @param p the product to add in this order
     * @param quantity the requested amount
     */
    public void addProduct(Product p, int quantity) {
        if (p == null) {
            System.out.println("Product doesn't exist.");
            return;
        }
        if (checkProductStock(p, quantity)) {
            System.out.println("Requested amount exceeds current stock amount! Try less.");
            return;
        }
        this._productsOrdered.put(p, quantity);
    }

    /**
     * Checks product availability.
     *
     * @param p the product to check
     * @param demand
     * @return true if product is available in stock
     */
    public boolean checkProductStock(Product p, int demand) {
        if (p.getStock() <= demand) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Order ID: " + getId() + "\n== Product Container ==\n" + _productsOrdered.toString();
    }

    /* ----- all getters and setters below this ----- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<Product, Integer> getProductsOrdered() {
        return _productsOrdered;
    }

}
