package multipleregression;

import utils.datasets.Datasets;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Multiple Regression Pre-Processing w/ Backward Elimination"
                + System.lineSeparator()
                + "Researchers: Anton Kovalyov, Luis Garay"
                + System.lineSeparator()
                + "Supervisor: Dr. Hansheng Lei"
                + System.lineSeparator());

        Scanner input = new Scanner(System.in);
        boolean choice = true;
        while (choice) {
            System.out.println("Run Multiple Regression w/ GUI - 1"
                    + System.lineSeparator()
                    + "Run Multiple Regression w/o GUI - 2"
                    + System.lineSeparator()
                    + "Stop Program - 5");

            switch (input.next().charAt(0)) {
                case '1':
                    System.out.println("Loading GUI...");
                    MainFX.main(args);
                    break;
                case '2':
                    //XP.run(Datasets.DATASETS[0]);
                    //XP.run(Datasets.DATASETS[1]);
                    XP.run(Datasets.DATASETS[2]);
                    //XP.run(Datasets.DATASETS[3]);
                    choice = false;
                    break;
                case '5':
                    choice = false;
                    break;
                default:
                    System.out.println("Bad Input! Please try again!");
                    break;
            }
        }
    }

    private static int determineAttributeLength(String columnHeader) {
        return StringUtils.split(columnHeader, ",").length;
    }
}
