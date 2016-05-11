package pcstore.ui;

import java.util.Scanner;
import static pcstore.MainClass._STORE_DB;
import pcstore.actor.CashManager;
import pcstore.actor.Customer;
import pcstore.actor.Manager;
import pcstore.actor.StorageManager;

/**
 *
 * @author Panos
 */
public class StoreUI {
    
    private Manager actor;

    public StoreUI() {
        this.actor = null;
    }

    /**
     * The basic menu for a customer/employee to submit orders in the system.
     * Also contains choices for login as a manager.
     */
    public void showBasicMenu() {
        System.out.println("\nPC Store - Computer & Printer Stock\n");
        System.out.println("====== Customer Options =======");
        System.out.println("1. Browse product list");
        System.out.println("2. Create new order");
        System.out.println("3. Submit order");
        System.out.println("===============================");
        System.out.println("4. Login as a manager");
        System.out.println("5. Save & exit.");
    }

    /**
     * Basic Input listener responds to the choice given by the
     * customer/employee menu.
     *
     * @param c the customer-default actor
     * @param choice the choice to handle
     * @return true if customer has submitted his order
     */
    public boolean onBasicInputListener(Customer c, int choice) {

        switch (choice) {
            case 1:
                c.browseProductList();
                return false;
            case 2:
                c.createOrder();
                return false;
            case 3:
                c.submitOrder();
                return true;
            case 4:
                requestLogin();
                return false;
            case 5:
                _STORE_DB.saveToDisk();
                System.exit(0);
                return false;
            default:
                System.out.println("[N/A]");
                return false;

        }
    }

    /**
     * Manager Input listener responds to the choice given by the manager menu.
     *
     * @param choice the choice to handle
     */
    public void onManagerInputListener(int choice) {

        if (getActor() instanceof CashManager) {
            switch (choice) {
                case 1:
                    ((CashManager) getActor()).readOrder();
                    break;
                case 2:
                    ((CashManager) getActor()).createInvoice();
                    break;
                case 3:
                    ((CashManager) getActor()).editInvoice();
                    break;
                case 4:
                    ((CashManager) getActor()).confirmPayment();
                    break;
                case 5:
                    ((CashManager) getActor()).cancelOrder();
                    break;
                case 6:
                    setActor(null);
                    break;
                default:
                    System.out.println("[N/A]");
                    break;

            }
        }else if (getActor() instanceof StorageManager){
            switch (choice) {
                case 1:
                    ((StorageManager) getActor()).checkProductStock();
                    break;
                case 2:
                    ((StorageManager) getActor()).refreshStock();
                    break;
                case 3:
                    ((StorageManager) getActor()).submitNewProduct();
                    break;
                case 4:
                     setActor(null);
                    break;
                default:
                    System.out.println("[N/A]");
                    break;

            }
        }

    }

    /**
     * The request login method for a manager. In case of a successful login, it
     * shows the menu of the manager(cash or storage), accordingly.
     */
    public void requestLogin() {

        System.out.println("Complete your username and password below to login.");
        System.out.println("[StorageManager]: Username: storage , Password: 12345");
        System.out.println("[CashManager]: Username: cash , Password: 12345");

        boolean validLogin = false;
        while (!validLogin) {
            System.out.println("Username : ");
            Scanner input = new Scanner(System.in);
            String un = input.next();
            System.out.println("Password : ");
            String pw = input.next();

            for (Manager manager : _STORE_DB.getManagers()) {
                if (manager.getUsername().equals(un) && manager.getPassword().equals(pw)) {
                    System.out.println("You have successfully logged in as " + manager.toString());
                    validLogin = true;
                    setActor(manager);
                    //activeManager = manager;
                }
            }
            if (!validLogin) {
                System.out.println("Wrong username or password.Try again.");
            }
        }
    }
    
    public Manager getActor() {
        return actor;
    }

    public void setActor(Manager actor) {
        this.actor = actor;
    }
    
    

}
