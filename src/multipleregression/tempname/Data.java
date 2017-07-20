package multipleregression.tempname;

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

        //dataVariablesList.forEach((dataVar) -> {
        //    System.out.println(Arrays.toString(dataVar.getArray()));
        //});
        return dataVariablesList;
    }

    boolean[][] getCorrelatedData() {
        return correlatedData;
    }

    List<DataVariable> getListOfDataVariables() {
        return listOfDataVariables;
    }
}
