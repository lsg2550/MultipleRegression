package utils.math;

import java.util.Arrays;

/**
 *
 * @author Anton Kovalyov & Luis Garay
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

    /**
     * Store the correlation table in a string for display purposes.
     *
     * @param correlationTable
     * @return the String showing a matrix of correlations
     */
    public static String CorrelationTableToString(double[][] correlationTable) {
        //StringBuilder, since we will constantly be altering the string this will be more efficient
        StringBuilder correlationTableString = new StringBuilder();
        correlationTableString.append("Correlation Matrix:")
                .append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("        ");

        /*Top Row*/
        int size = correlationTable.length;
        for (int i = 0; i < size; i++) {
            correlationTableString.append("x")
                    .append(i)
                    .append(" ")
                    .append(computeOffset(i));
        }

        /*Left Column*/
        for (int i = 0; i < size; i++) {
            correlationTableString.append(System.lineSeparator())
                    .append("x")
                    .append(i)
                    .append(":")
                    .append(computeOffset(i));

            for (int j = 0; j < correlationTable[0].length; j++) {
                if (Math.round(correlationTable[i][j] * 100.0) / 100.0 >= 0) {
                    correlationTableString.append(" ")
                            .append(String.format("%.2f", Math.round(correlationTable[i][j] * 100.0) / 100.0))
                            .append(" ");
                } else {
                    correlationTableString.append(String.format("%.2f", Math.round(correlationTable[i][j] * 100.0) / 100.0))
                            .append(" ");
                }
            }
        }

        return correlationTableString.toString();
    }

    /**
     * Helper function for the CorrelationTableToString method.
     *
     * @param i
     * @return number of spaces before displaying the next item in the String.
     */
    private static String computeOffset(int i) {
        StringBuilder offsetString = new StringBuilder();

        if (i < 10) {
            offsetString.append("   ");
        } else if (i < 100) {
            offsetString.append("  ");
        } else {
            offsetString.append(" ");
        }

        return offsetString.toString();
    }

    //Debug
    private static void displayTable(boolean[][] table) {
        System.out.println("BOOLEAN TABLE:");

        for (int i = 0; i < table[0].length; i++) {
            System.out.println("Row " + i + ": " + Arrays.toString(table[i]));
        }
    }
}
