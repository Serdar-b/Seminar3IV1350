package se.kh.iv1350.pointofsale.startup;

import se.kh.iv1350.pointofsale.controller.Controller;
import se.kh.iv1350.pointofsale.integration.Printer;
import se.kh.iv1350.pointofsale.model.Receipt;
import se.kh.iv1350.pointofsale.view.View;

import java.time.LocalTime;

/**
 * Contains main method that starts entire application.
 */
public class Main {
    /**
     * The main method that starts entire application.
     *
     * @param args The application does not take any command-line parameters.
     * */
    public static void main(String[] args) {

        Printer printer = new Printer();
        Controller contr = new Controller(printer);
        View view = new View(contr);

    }
}
