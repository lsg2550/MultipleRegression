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
                    + System.lineSeparator()
                    + "m - Multiple Regression using MultipleRegression"
                    + System.lineSeparator()
                    + "x - Multiple Regression using MultipleRegressionXP"
                    + System.lineSeparator()
                    + "0 (zero) - stop program");
            char userChoice = input.next().toLowerCase().charAt(0);
            System.out.print(System.lineSeparator());

            switch (userChoice) {
                case 'm':
                    multipleregression.mr.Main.main(Datasets.DATASET_4);
                    break;
                case 'x':
                    multipleregression.mrXP.Main.main(Datasets.DATASET_4);
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
