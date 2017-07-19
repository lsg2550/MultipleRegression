package data;

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

        displayTable(correlatedBooleanTable);
        return correlatedBooleanTable;
    }

    /**
     * Takes Correlated Boolean Table and returns a negated table.
     *
     * @param correlatedBooleanTable
     * @return
     */
    public static boolean[][] findNonCorrelatedValuesFromCorrelatedValues(boolean[][] correlatedBooleanTable) {
        boolean[][] nonCorrelatedBooleanTable = new boolean[correlatedBooleanTable.length][correlatedBooleanTable[0].length];

        for (int i = 0; i < correlatedBooleanTable.length; i++) {
            for (int j = 0; j < correlatedBooleanTable[0].length; j++) {
                nonCorrelatedBooleanTable[i][j] = !correlatedBooleanTable[i][j];
            }
        }

        displayTable(nonCorrelatedBooleanTable);
        return nonCorrelatedBooleanTable;
    }

    private static void displayTable(boolean[][] table) {
        System.out.println("BOOLEAN TABLE:");

        for (int i = 0; i < table[0].length; i++) {
            System.out.println("Row " + i + ": " + Arrays.toString(table[i]));
        }
    }
}
