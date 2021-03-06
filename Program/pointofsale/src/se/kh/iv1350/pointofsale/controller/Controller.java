package se.kh.iv1350.pointofsale.controller;

import se.kh.iv1350.pointofsale.dto.ItemDTO;
import se.kh.iv1350.pointofsale.integration.AccountingSystem;
import se.kh.iv1350.pointofsale.integration.InventorySystem;
import se.kh.iv1350.pointofsale.integration.Printer;
import se.kh.iv1350.pointofsale.model.Sale;

/**
 * Calls to the model pass through this class.
 */
public class Controller {

    private Printer printer;
    private AccountingSystem accountingSystem;
    private InventorySystem inventorySystem;
    private Sale sale;

    /**
     * @param printer Controller takes and object of Printer as a parameter.
     */
    public Controller(Printer printer) {

        this.printer = printer;
        accountingSystem = new AccountingSystem();
        inventorySystem = new InventorySystem();

    }

    /**
     * Method to start a sale.
     */
    public void startSale()
    {
        sale = new Sale();
    }

    /**
     * Retrieves information from inventory system and adds that item to the sale.
     * @param scannedItemId barcode from scanned item, for this application it will be a pre-set integer.
     */
    public void addItemToSale(int scannedItemId)
    {
        ItemDTO itemDTO = inventorySystem.retrieveItemInformation(scannedItemId);
        sale.addItemToSale(itemDTO);
    }

    /**
     * Ends the current sale
     * @return the total amount to pay including tax
     */

    public int endSale()
    {
        return sale.endSale();
    }

    /**
     * Receives amount payed and calculates change.
     * If payment amount is enough, update external systems and print receipt
     * @param paymentAmount the amount given by customer
     * @return the change amount
     */
    public int recievesPayment(int paymentAmount)
    {
        int amountOfChange = sale.calculateChange(paymentAmount);
        updateExternalSystems(paymentAmount);
        printReceipt(paymentAmount);

        return amountOfChange;
    }

    /**
     * Updates the external systems with our current sale
     */

    public void updateExternalSystems(int paymentAmount)
    {
        inventorySystem.updateInventorySystem(sale, paymentAmount);
        accountingSystem.updateAccountingSystem(sale, paymentAmount);
    }

    /**
     * Prints the current receipt
     */

    public void printReceipt(int paymentAmount)
    {
        sale.printReceipt(printer, paymentAmount);
    }
}
