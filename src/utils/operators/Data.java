package utils.operators;

import utils.math.Correlation;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 *
 * @author Luis
 */
public class Data {

    //Data
    private final List<DataVariable> listOfDataVariables; //List Consisting of a Standalone Version of Each Row
    private final double[][] correlationMatrix;
    private final boolean[][] correlatedData;
    private final boolean[][] nonCorrelatedData;

    /**
     * @param fullData - Data Read From Datasets
     * @param thresholdOne
     * @param thresholdTwo
     */
    public Data(double[][] fullData, double thresholdOne, double thresholdTwo) {
        System.out.println("Creating Data...");

        this.listOfDataVariables = buildListOfDataVariables(fullData);
        this.correlationMatrix = new PearsonsCorrelation(fullData).getCorrelationMatrix().getData();
        this.correlatedData = Correlation.findCorrelatedValues(this.correlationMatrix, thresholdOne);
        this.nonCorrelatedData = Correlation.findNonCorrelatedValues(this.correlationMatrix, thresholdTwo);
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

    public boolean[][] getCorrelatedData() {
        return correlatedData;
    }

    public boolean[][] getNonCorrelatedData() {
        return nonCorrelatedData;
    }

    public List<DataVariable> getListOfDataVariables() {
        return listOfDataVariables;
    }

    public double[][] getCorrelationMatrix() {
        return correlationMatrix;
    }

}
