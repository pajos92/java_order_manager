package pcstore;

import pcstore.data.Sample;
import pcstore.actor.Customer;
import pcstore.data.AppDatabase;
import pcstore.ui.StoreUI;
import java.util.Scanner;

/**
 *
 * @author Panos
 */
public class MainClass {

    /* the ApplicationData object that carries all data saved to disk */
    public static AppDatabase _STORE_DB = null;

    public static void main(String[] args) {

        /* uncomment 2 lines below to use sample data and not the file */
        //_STORE_DB = new AppDatabase(null, null, null);
        //Sample sample = new Sample();

        /* uncomment below to load data from file(NORMAL USE) */
        _STORE_DB = new AppDatabase();

        Scanner input = new Scanner(System.in);
        int choice;

        /* create one store UI to start the application */
        StoreUI ui = new StoreUI();
        Customer c = null;
        boolean submitted = true;

        while (true) {
            /* in case of no actor(manager) show the basic(customer) menu */
            if (ui.getActor() == null) {
                /* create new customer object in case some1 submits an order */
                if (submitted) {
                    c = new Customer();
                }
                ui.showBasicMenu();
                /*wait for an input */
                choice = input.nextInt();
                /* handle the feedback */
                submitted = ui.onBasicInputListener(c, choice);

            } else /* else show the actor's menu */ {
                ui.getActor().showMenu();
                choice = input.nextInt();
                /* handle this feedback */
                ui.onManagerInputListener(choice);
            }
        }
    }

}
