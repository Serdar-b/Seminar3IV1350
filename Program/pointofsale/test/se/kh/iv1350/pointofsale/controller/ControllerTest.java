package se.kh.iv1350.pointofsale.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kh.iv1350.pointofsale.dto.ItemDTO;
import se.kh.iv1350.pointofsale.integration.AccountingSystem;
import se.kh.iv1350.pointofsale.integration.InventorySystem;
import se.kh.iv1350.pointofsale.integration.Printer;
import se.kh.iv1350.pointofsale.model.Sale;

import java.nio.channels.Pipe;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Printer printer;
    private Controller controller;
    private Sale sale;

    @BeforeEach
    void setUp() {
        printer = new Printer();
        controller = new Controller(printer);
        sale = new Sale();
    }

    @AfterEach
    void tearDown() {
        controller = null;
        sale = null;
        printer = null;
    }

    @Test
    void startSaleTest()
    {
        int expectedQuantityOfGoodsBeforeAddingItems = 0;
        assertEquals(expectedQuantityOfGoodsBeforeAddingItems,sale.getTotalQuantityOfGoods());
    }

    @Test
    void addItemToSaleTest()
    {
        InventorySystem inventorySystem = new InventorySystem();
        ItemDTO itemDTO = inventorySystem.retrieveItemInformation(1);
        sale.addItemToSale(itemDTO);
        int expectedQuantityOfGoodsAfterAddingOneItem = 1;
        assertEquals(expectedQuantityOfGoodsAfterAddingOneItem, sale.getTotalQuantityOfGoods());
    }

    @Test
    void endSaleTest ()
    {
        int expectedTotalCostForPurchase = 55;
        addMeatballToSale();
        assertEquals(expectedTotalCostForPurchase, sale.endSale(), "Wrong total price including tax");
    }

    @Test
    void receivesPaymentWhenEnoughTest()
    {
        addMeatballToSale();
        int expectedChangeAmount = 15;
        int amountPaidByCustomer = 70;
        assertEquals(expectedChangeAmount, sale.calculateChange(amountPaidByCustomer),"Wrong calculated change");
    }

    @Test
    void receivesPaymentWhenNotEnoughTest()
    {
        addMeatballToSale();
        int expectedChangeAmount = 0;
        int amountPaidByCustomer = 50;
        assertEquals(expectedChangeAmount, sale.calculateChange(amountPaidByCustomer), "Wrong calculated change");
    }

    @Test
    void receivesPaymentUpdateExternalInventorySystemTest()
    {
        InventorySystem inventorySystem = new InventorySystem();
        addMeatballToSale();
        int amountPaidByCustomer = 70;
        inventorySystem.updateInventorySystem(sale, amountPaidByCustomer);
        int expectedMeatballsLeft = 99;
        assertEquals(expectedMeatballsLeft, inventorySystem.getMeatballsLeft());
    }

    @Test
    void receivesPaymentUpdateExternalAccountingSystemTest()
    {
        AccountingSystem accountingSystem = new AccountingSystem();
        addMeatballToSale();
        int amountPaidByCustomer = 70;
        accountingSystem.updateAccountingSystem(sale, amountPaidByCustomer);
        int expectedAmountInSystemAfterSalePurchase = 55;
        assertEquals(expectedAmountInSystemAfterSalePurchase, accountingSystem.getAmountOfMoneyInSystem());
    }


    void addMeatballToSale()
    {
        String name = "Meatball";
        int id = 1;
        String description = "Food";
        int price = 50;
        int tax = 5;
        ItemDTO itemDTO = new ItemDTO(name,id,description, price, tax);
        sale.addItemToSale(itemDTO);
    }
}