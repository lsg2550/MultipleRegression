package multipleregression;

import datasets.Datasets;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Launcher {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean progCont = true;

        while (progCont) {
            System.out.println("Please Select An Option:"
                    + "\np - Multiple Regression using <temp>"
                    + "\nt - Multiple Regression using pivot"
                    + "\n0 (zero) - stop program");
            char userChoice = input.next().toLowerCase().charAt(0);
            System.out.print(System.lineSeparator());

            switch (userChoice) {
                case 'p':
                    multipleregression.tempname.Main.main(Datasets.DATASET_3);
                    break;
                case 't':
                    multipleregression.usingpivot.Main.main(Datasets.DATASET_3);
                    break;
                case '0':
                    progCont = false;
                    break;
                default:
                    System.out.println("Wrong Input, please try again.");
                    break;
            }

            System.out.print(System.lineSeparator());
        }
    }
}
