package pcstore.data;

import static pcstore.MainClass._STORE_DB;
import pcstore.actor.CashManager;
import pcstore.actor.StorageManager;
import pcstore.instance.Product;

/**
 *
 * @author Panos
 */
public final class Sample {
    
    public Sample(){
        populateProductList();
        populateManagers();
    }

    /**
     *
     * Populates the product list with some sample products
     */
    public void populateProductList() {
        _STORE_DB.addProductToList(new Product("LAPTOP TOSHIBA SATELLITE L50", Product.ProductCategory.COMPUTER, 899.9, 7));
        _STORE_DB.addProductToList(new Product("PRINTER HP DESKJET 1010 CX015B", Product.ProductCategory.PRINTER, 49.9, 12));
        _STORE_DB.addProductToList(new Product("EPSON INK - BLACK", Product.ProductCategory.PRINTER_PART, 19.9, 20));
        _STORE_DB.addProductToList(new Product("EPSON INK - YELLOW", Product.ProductCategory.PRINTER_PART, 19.9, 21));
        _STORE_DB.addProductToList(new Product("EPSON INK - MAGENTA", Product.ProductCategory.PRINTER_PART, 19.9, 22));
        _STORE_DB.addProductToList(new Product("EPSON INK - CYAN", Product.ProductCategory.PRINTER_PART, 19.9, 19));
        _STORE_DB.addProductToList(new Product("RAM CORSAIR VENGEANCE PRO 8GB DDR3", Product.ProductCategory.COMPUTER_PART, 129.9, 4));
        _STORE_DB.addProductToList(new Product("CPU INTEL CORE I7-4820K 3.70GHZ", Product.ProductCategory.COMPUTER_PART, 354.2, 11));
        _STORE_DB.addProductToList(new Product("LAPTOP HP PAVILION 15-P205NV", Product.ProductCategory.COMPUTER, 888.0, 4));
        _STORE_DB.addProductToList(new Product("PRINTER LEXMARK CS310N", Product.ProductCategory.PRINTER, 147.6, 5));
    }

    public void populateManagers() {
        _STORE_DB.addManagerToList(new CashManager("cash", "12345"));
        _STORE_DB.addManagerToList(new StorageManager("storage", "12345"));
    }

}
