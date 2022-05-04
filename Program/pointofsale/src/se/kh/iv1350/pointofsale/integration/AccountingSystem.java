package se.kh.iv1350.pointofsale.integration;
import se.kh.iv1350.pointofsale.model.Sale;

/**
 * Class that makes call to an external accounting system,
 * for this application subsequent information will be hardcoded.
 */
public class AccountingSystem {
    private int amountOfMoneyInSystem;

    /**
     * Gets amount of money in the system
     * @return amount of money
     */

    public int getAmountOfMoneyInSystem() {
        return amountOfMoneyInSystem;
    }
    /**
     * Updates our made-up accounting system
     * @param sale the current sale
     */
    public void updateAccountingSystem(Sale sale, int paymentAmount){
        if (paymentAmount >= sale.getTotalPriceAndTaxForEntirePurchase())
            amountOfMoneyInSystem += sale.getTotalPrice() + sale.getTaxForEntirePurchase();
    }
}
