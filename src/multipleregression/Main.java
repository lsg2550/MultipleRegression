package multipleregression;

import datasets.Datasets;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean choice = true;

        while (choice) {
            System.out.println("Run Multiple Regression w/ GUI - 1");
            System.out.println("Run Multiple Regression w/o GUI - 2");
            System.out.println("Stop Program - 5");

            switch (input.next().charAt(0)) {
                case '1':
                    System.out.println("Loading GUI...");
                    MainFX.main(args);
                    break;
                case '2':
                    XP.run(Datasets.DATASETS[0]);
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
}
