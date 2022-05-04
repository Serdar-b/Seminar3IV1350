package se.kh.iv1350.pointofsale.integration;

import se.kh.iv1350.pointofsale.dto.ItemDTO;
import se.kh.iv1350.pointofsale.model.Sale;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that makes call to an external inventory system,
 * for this application subsequent information will be hardcoded.
 */
public class InventorySystem {
    private int meatballsLeft = 100;
    private int pringlesLeft = 100;

    public int getMeatballsLeft() {
        return meatballsLeft;
    }

    public int getPringlesLeft() {
        return pringlesLeft;
    }
    /**
     * Database of hardcoded items in the shop, method return an itemDTO.
     * @param scannedItemId Barcode for item, this will be a pre-set integer.
     * @return ItemDTO
     */
    public ItemDTO retrieveItemInformation(int scannedItemId)
    {
        String name;
        int id;
        String description;
        int price;
        int tax;
        ItemDTO itemDTO;

        switch(scannedItemId)
        {
            case 1: name = "Meatball";
            id = 1;
            description = "Food";
            price = 50;
            tax = 5;
            itemDTO = new ItemDTO(name,id,description, price, tax);
            break;


            case 2: name = "Pringles";
            id = 2;
            description = "Snacks";
            price = 20;
            tax = 2;
            itemDTO = new ItemDTO(name, id, description, price, tax);
            break;

            default: itemDTO = new ItemDTO(null,0,null,0,0);
            break;
        }
        return  itemDTO;
    }

    /**
     * Updates the made-up inventory system
     * @param sale the current sale
     */
    public void updateInventorySystem(Sale sale, int paymentAmount)
    {
        if (sale.getTotalPriceAndTaxForEntirePurchase() <= paymentAmount)
        {
            ArrayList <ItemDTO> NamesOfItemsInTheSale = sale.getCopyOfItemDTOList();

            for (int i = 0; i < NamesOfItemsInTheSale.size(); i++)
            {
                if (NamesOfItemsInTheSale.get(i).getName().equals("Meatball"))
                    meatballsLeft--;
                if (NamesOfItemsInTheSale.get(i).getName().equals("Pringles"))
                    pringlesLeft--;
            }
        }

    }
}
