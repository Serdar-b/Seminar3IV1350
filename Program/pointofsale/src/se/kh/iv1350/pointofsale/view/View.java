package se.kh.iv1350.pointofsale.view;

import se.kh.iv1350.pointofsale.controller.Controller;

/**
 * Placeholder for view, contains hardcoded execution with calls to controller for testing.
 */
public class View {

    private Controller contr;

    public View(Controller contr) {
        this.contr = contr;

        contr.startSale();
        contr.addItemToSale(1);
        contr.addItemToSale(1);
        contr.addItemToSale(2);
        contr.addItemToSale(2);
        contr.endSale();
        contr.recievesPayment(200);
    }

}
