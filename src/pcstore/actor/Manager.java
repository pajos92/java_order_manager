package pcstore.actor;

import java.io.Serializable;


/**
 * Abstract class Manager contains info related 
 * to the login information of a class.
 * Superclass for the classes CashManager and StorageManager
 *
 * @author Panos
 */
public abstract class Manager implements Serializable {
      
    /* the final username of this Manager */
    private final String username;
    /* the final password of this Manager */
    private final String password;
    
    /**
     * displays the menu to the corresponding Manager
     * in this application, accordingly
     */
    public abstract void showMenu();

    /**
     * Constructor of this class
     * @param username
     * @param password 
     */
    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
        
}
