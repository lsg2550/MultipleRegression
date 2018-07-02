package multipleregression;

import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import utils.io.Read;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Multiple Linear Regression Pre-Processing w/ Backward Elimination"
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
                    GUI.main(args);
                    break;
                case '2':
                    System.out.println("Input dataset location: ");
                    String datasetLocation = input.next();

                    if (Read.isPathValid(datasetLocation)) {
                        MLR.run(datasetLocation);
                    } else {
                        System.out.println("Path is incorrect. Try again!");
                    }
                    
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
