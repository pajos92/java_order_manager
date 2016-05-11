package pcstore.instance;

import java.io.Serializable;

/**
 * Invoice gets attached to an order object and gets filled with payment
 * information by the cash manager
 *
 * @author Panos
 */
public class Invoice implements Serializable{

    /* enum that holds 2 payment methods */
    public enum PaymentMethod {

        CASH, CREDIT_CARD
        
    };

    /* the payment method for this invoice */
    private PaymentMethod paymentMethod;
    /* the credit card number that will be charged */
    private String creditCardNumber;
    /* tax id for invoicing if needed */
    private long taxId;

    /**
     * Constructor for this class - empty for now
     */
    public Invoice() {

    }

    /**
     *
     * @return true if this credit card is valid
     */
    public boolean checkCreditCard() {
        try {
            double d = Double.parseDouble(getCreditCardNumber());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(this == null)
            return "Invoice : N/A";
        
        return "Payment Method: " + getPaymentMethod() + "\nCredit Card Number: " + getCreditCardNumber() + "\nTax ID: " +getTaxId();
    }

    
    
    /* ----- all getters and setters below this ----- */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public long getTaxId() {
        return taxId;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
    }

}
