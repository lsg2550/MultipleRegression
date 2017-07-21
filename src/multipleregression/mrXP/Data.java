package multipleregression.mrXP;

import utils.math.Correlation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 *
 * @author Luis
 */
class Data {

    //Data
    private final boolean[][] correlatedData;
    private final List<DataVariable> listOfDataVariables; //List Consisting of a Standalone Version of Each Row

    /**
     * @param fullData - Data Read From Datasets
     * @param threshold
     */
    Data(double[][] fullData, double threshold) {
        this.correlatedData = Correlation.findCorrelatedValues(
                new PearsonsCorrelation(fullData).getCorrelationMatrix().getData(), threshold);
        this.listOfDataVariables = buildListOfDataVariables(fullData);
    }

    /**
     * Creates a Standalone Object for Each Row and Its Data
     *
     * @param fullData - Data Read From Datasets
     * @return list of standalone versions of each row from fullData
     */
    private List<DataVariable> buildListOfDataVariables(double[][] fullData) {
        List<DataVariable> dataVariablesList = new ArrayList<>(fullData[0].length);

        for (int i = 0; i < fullData[0].length; i++) {
            double[] data = new double[fullData.length];

            for (int j = 0; j < fullData.length; j++) {
                data[j] = fullData[j][i];
            }

            dataVariablesList.add(new DataVariable(i, data));
        }

        return dataVariablesList;
    }

    boolean[][] getCorrelatedData() {
        return correlatedData;
    }

    List<DataVariable> getListOfDataVariables() {
        return listOfDataVariables;
    }

    /**
     * Store the correlation table in a string for display purposes.
     *
     * @param correlationTable
     * @return the String showing a matrix of correlations
     */
    String CorrelationTableToString(double[][] correlationTable) {
        //Get CorrelationTable
        correlationTable = new PearsonsCorrelation(correlationTable).getCorrelationMatrix().getData();

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
    private String computeOffset(int i) {
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
}
