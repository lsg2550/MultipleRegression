package utils.math;

import java.util.Arrays;

/**
 *
 * @author Luis
 */
public class Correlation {

    /**
     * Creates a boolean table of correlations among variables, where T stands
     * for correlated, and F stands for non-correlated.
     *
     * @param correlationTable
     * @param threshold
     * @return
     */
    public static boolean[][] findCorrelatedValues(double[][] correlationTable, double threshold) {
        boolean[][] correlatedBooleanTable = new boolean[correlationTable.length][correlationTable[0].length];

        for (int i = 0; i < correlatedBooleanTable.length; i++) {
            for (int j = 0; j < correlatedBooleanTable[0].length; j++) {
                correlatedBooleanTable[i][j] = Math.abs(correlationTable[i][j]) > threshold;
            }
        }

        //displayTable(correlatedBooleanTable);
        return correlatedBooleanTable;
    }

    /**
     * Creates a boolean table of non-correlated variables, where T stands for
     * non-correlated, and F stands for correlated.
     *
     * @param correlationTable
     * @param threshold
     * @return
     */
    public static boolean[][] findNonCorrelatedValues(double[][] correlationTable, double threshold) {
        boolean[][] nonCorrelatedBooleanTable = new boolean[correlationTable.length][correlationTable[0].length];

        for (int i = 0; i < nonCorrelatedBooleanTable.length; i++) {
            for (int j = 0; j < nonCorrelatedBooleanTable[0].length; j++) {
                nonCorrelatedBooleanTable[i][j] = Math.abs(correlationTable[i][j]) <= threshold;
            }
        }

        //displayTable(nonCorrelatedBooleanTable);
        return nonCorrelatedBooleanTable;
    }

    /**
     * Takes Correlated Boolean Table and returns a negated table.
     *
     * @param correlationBooleanTable
     * @return
     */
    public static boolean[][] negateCorrelationBooleanTables(boolean[][] correlationBooleanTable) {
        boolean[][] negatedCorrelationBooleanTable = new boolean[correlationBooleanTable.length][correlationBooleanTable[0].length];

        for (int i = 0; i < correlationBooleanTable.length; i++) {
            for (int j = 0; j < correlationBooleanTable[0].length; j++) {
                negatedCorrelationBooleanTable[i][j] = !correlationBooleanTable[i][j];
            }
        }

        //displayTable(negatedCorrelationBooleanTable);
        return negatedCorrelationBooleanTable;
    }

    //Debug
    private static void displayTable(boolean[][] table) {
        System.out.println("BOOLEAN TABLE:");

        for (int i = 0; i < table[0].length; i++) {
            System.out.println("Row " + i + ": " + Arrays.toString(table[i]));
        }
    }
}
