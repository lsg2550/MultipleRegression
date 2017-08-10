package multipleregression;

import utils.datasets.Datasets;
import java.util.Scanner;

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

        //Determine Attribute Length
//        StringBuilder b = new StringBuilder();
//
//        try (Stream<String> lines = Files.lines(Paths.get("t.txt"), Charset.defaultCharset())) {
//            lines.forEachOrdered(line -> b.append(line));
//        } catch (IOException e) {
//            return;
//        }
//
//        System.out.println(determineAttributeLength(b.toString()));

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
                    XP.run(Datasets.DATASETS[6]);
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
        return columnHeader.split(",").length;
    }
}
