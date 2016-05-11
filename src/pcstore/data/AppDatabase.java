package pcstore.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pcstore.actor.Customer;
import pcstore.actor.Manager;
import pcstore.instance.Product;

/**
 * This class is used only for storing purposes. (Java-style) Carries all the
 * products, orders & managers accounts.
 *
 *
 * @author Panos
 */
public final class AppDatabase implements Serializable {

    private List<Product> products;
    private List<Customer> orders;
    private List<Manager> managers;

    public AppDatabase() {
        loadFromDisk();
    }

    public AppDatabase(List<Product> products, List<Customer> orders, List<Manager> managers) {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.managers = new ArrayList<>();
        if (products != null) {
            this.products = products;
        }
        if (orders != null) {
            this.orders = orders;
        }
        if (managers != null) {
            this.managers = managers;
        }
    }
    
    public AppDatabase(Sample sample){
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public void addProductToList(Product p) {
        getProducts().add(p);
    }

    public void addOrderToList(Customer c) {
        getOrders().add(c);
    }

    public void addManagerToList(Manager m) {
        getManagers().add(m);
    }

    /**
     * Saves all the data handled by the application to one ApplicationData
     * object, to disk. (data.txt)
     */
    public void saveToDisk() {
        try {
            File file = new File("data.txt");
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Saving data...");

        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("data.txt"));
            out.writeObject(this);
            out.close();
            System.out.println("All data saved.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Loads the data.txt file from disk to obtain all the saved data.
     */
    public void loadFromDisk() {

        ObjectInputStream inputStream = null;
        System.out.println("Loading data from disk..");

        try {
            inputStream = new ObjectInputStream(new FileInputStream("data.txt"));
            AppDatabase app = (AppDatabase) inputStream.readObject();
            this.orders = app.getOrders();
            this.products = app.getProducts();
            this.managers = app.getManagers();
            System.out.println("All data were loaded.");
            inputStream.close();

        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Customer> getOrders() {
        return orders;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setOrders(List<Customer> orders) {
        this.orders = orders;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }

}
