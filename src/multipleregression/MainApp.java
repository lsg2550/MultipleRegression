package multipleregression;

import java.io.IOException;
import java.util.Set;
import utils.benchmarking.Logging;

/**
 * The Main class. Output the results using a file with 99 variables and where
 * T1 = T2 = 0.7
 *
 * @author Anton Kovalyov
 */
public class MainApp {

    public static void main(String[] args) throws IOException {
        //Start Recording Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        Logging.setStartTime();
        double[][] data = MultipleRegressionData.computeArrayOfAllData("thads2013n.txt");
        MultipleRegression mr = new MultipleRegression(data, 0.7, 0.7);
        Logging.setEndTime();
        long dataProcessingRunTime = Logging.benchmarkTime();

        //Algorithm
        Logging.setStartTime();
        Set<Function> f = (Set<Function>) mr.computeFunctionsRandomly();
        Logging.setEndTime();
        long algorithmRunTime = Logging.benchmarkTime();

        //Debug Output
        System.out.println(mr.CorrelationTableToString(mr.getCorrelationTable())
                + System.lineSeparator() + System.lineSeparator()
                + Functions.toString(f, FunctionComparator.SSR_SORT)
                + System.lineSeparator() + System.lineSeparator()
                + "Data processing running time: " + dataProcessingRunTime + " milliseconds."
                + System.lineSeparator()
                + "Algorithm running time: " + algorithmRunTime + " milliseconds."
                + System.lineSeparator()
                + "Parameters computation time: " + (System.currentTimeMillis()
                - (dataProcessingRunTime + algorithmRunTime + progStartTime)
                + " milliseconds."));
    }
}
