package pcstore.actor;

import java.io.Serializable;
import java.util.Scanner;
import static pcstore.MainClass._STORE_DB;
import pcstore.instance.Invoice;
import pcstore.instance.Invoice.PaymentMethod;
import pcstore.instance.Order;
import pcstore.instance.Order.OrderState;

/**
 * Cash manager picks an order from the top of the order list and gets it
 * attached with an invoice he creates for this particular order, filling it
 * with payment information Also he confirms or cancels an order
 *
 * @author Panos
 */
public class CashManager extends Manager implements Serializable {

    /* the current order this cash manager is working on */
    private Order order;
    /* the invoice to be attached to the above order */
    private Invoice invoice;

    /**
     * Constructor for this class. Calls Manager superclass for username &
     * password.
     *
     * @param username
     * @param password
     */
    public CashManager(String username, String password) {
        super(username, password);
        this.order = null;
        this.invoice = null;
    }

    /**
     * Extract the top order on the list to process, if state=STARTED.
     */
    public void readOrder() {

        boolean orderFound = false;
        Customer customer = null;
        /* if not already working on an order */
        if (this.getOrder() == null) {

            for (Customer c : _STORE_DB.getOrders()) {
                if (c.getOrder().getState() == OrderState.STARTED) {
                    this.order = c.getOrder();
                    customer = c;
                    orderFound = true;
                    break;
                }
            }
            if (!orderFound) {
                System.out.println("There are no orders to proccess in the system.");
            } else {
                /* change its state to IN_PROGRESS since we're currently proccessing it */
                this.order.setState(Order.OrderState.IN_PROGRESS);
                System.out.println(getOrder().toString() + "\n" + customer.toString());
            }

        } else {
            System.out.println("You have already picked an order to proccess. Either submit or cancel in order to proceed.");
        }
    }

    /**
     * Creates a new invoice to be attached to the order this class is currently
     * processing.
     */
    public void createInvoice() {
        if (getInvoice() == null && getOrder() != null) {
            Invoice inv = new Invoice();
            setInvoice(inv);
            configurePaymentMethod(getInvoice());
            configureTaxId(getInvoice());
            configureCreditCardNumber(getInvoice());
            System.out.println("Invoice has been successfully created.");
            System.out.println(getInvoice().toString());
        } else {
            System.out.println("Either invoice already created or the order you are trying to attach an invoice on is null.");
        }
    }

    /**
     * Edits the current properties assigned to this invoice.
     */
    public void editInvoice() {
        if (getInvoice() != null) {

            Scanner input = new Scanner(System.in);
            int choice = 0;
            while (choice < 1 || choice > 3) {
                System.out.println("Edit invoice options :");
                System.out.println("1.Change payment method.");
                System.out.println("2.Change credit card number.");
                System.out.println("3.Change Tax ID.");

                choice = input.nextInt();
            }

            if (choice == 1) {
                configurePaymentMethod(getInvoice());
            } else if (choice == 2) {
                configureCreditCardNumber(getInvoice());
            } else {
                configureTaxId(getInvoice());
            }
        } else {
            System.out.println("No invoice found to edit.");
        }
    }

    /**
     * Sets the state if this order we are working on as FINISHED.
     */
    public void confirmPayment() {
        if (getOrder() != null) {
            /* since payment has been confirmed update the stock of products */
            System.out.println(getOrder().getProductsOrdered().toString());

            System.out.println("Order " + getOrder() + " has been confirmed.");
            System.out.println(getInvoice().toString());
            getOrder().setState(OrderState.FINISHED);
            /* null the properties to be able to obtain next order */
            this.order = null;
            this.invoice = null;
        } else {
            System.out.println("You are not currently working on an order.");
        }
    }

    /**
     * Cancels the current order.
     */
    public void cancelOrder() {
        if (getOrder() != null) {
            System.out.println("The order " + getOrder() + " has been canceled.");
            getOrder().setState(OrderState.CANCELED);
            /* null the properties to be able to obtain next order */
            this.order = null;
            this.invoice = null;
        } else {
            System.out.println("You are not currently working on an order.");
        }
    }

    /**
     * Sets the payment method of a particular invoice.
     *
     * @param inv the invoice to configure
     */
    private void configurePaymentMethod(Invoice inv) {

        Scanner input = new Scanner(System.in);
        int pmethod = 0;
        while (pmethod < 1 || pmethod > 2) {
            System.out.println("Select a payment method: ");
            System.out.println("1.Cash");
            System.out.println("2.Credit Card");

            pmethod = input.nextInt();
        }

        if (pmethod == 1) {
            inv.setPaymentMethod(PaymentMethod.CASH);
        } else {
            inv.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        }

    }

    /**
     * Sets the tax ID of a particular invoice.
     *
     * @param inv the invoice to configure
     */
    private void configureTaxId(Invoice inv) {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert the customer's tax id: ");
        long taxid = input.nextLong();
        inv.setTaxId(taxid);
    }

    /**
     * Sets the credit card of a particular invoice.
     *
     * @param inv the invoice to configure
     */
    private void configureCreditCardNumber(Invoice inv) {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert the customer's credit card number : ");
        String ccn = input.next();
        inv.setCreditCardNumber(ccn);
        while (!inv.checkCreditCard()) {
            System.out.println("[Error] Invalid creadit card number!");
            ccn = input.next();
            inv.setCreditCardNumber(ccn);
        };
    }

    @Override
    public void showMenu() {
        System.out.println("======= Cash Manager Menu =======");
        System.out.println("1. Read order in queue.");
        System.out.println("2. Create invoice.");
        System.out.println("3. Edit current invoice.");
        System.out.println("4. Confirm payment.");
        System.out.println("5. Cancel current order.");
        System.out.println("6. Logout.");
    }

    @Override
    public String toString() {
        return "Cash Manager";
    }

    /* ----- all getters and setters below this ----- */
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

}
