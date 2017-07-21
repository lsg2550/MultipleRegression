package multipleregression.mr;

import utils.io.ReadData;
import java.util.Set;
import utils.benchmarking.Logging;
import utils.benchmarking.MemoryUsage;

/**
 *
 * @author Anton Kovalyov
 */
public class Main {

    public static void main(String args) {
        //Start Recording Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        Logging.setStartTime();
        double[][] data = ReadData.computeArrayOfAllData(args);
        MultipleRegression mr = new MultipleRegression(data, 0.7, 0.7);
        Logging.setEndTime();

        long dataProcessingRunTime = Logging.benchmarkTime();

        //Algorithm
        Logging.setStartTime();
        Set<Function> f = (Set<Function>) mr.computeFunctions();
        Logging.setEndTime();

        long algorithmRunTime = Logging.benchmarkTime();

        //Debug Output
//        System.out.println(mr.CorrelationTableToString(mr.getCorrelationTable())
//                + System.lineSeparator() + System.lineSeparator()
//                + Functions.toString(f, FunctionComparator.SSR_SORT)
//                + System.lineSeparator() + System.lineSeparator()
//                + "Data processing running time: " + dataProcessingRunTime + " milliseconds."
//                + System.lineSeparator()
//                + "Algorithm running time: " + algorithmRunTime + " milliseconds."
//                + System.lineSeparator()
//                + "Parameters computation time: "
//                + (System.currentTimeMillis() - (dataProcessingRunTime + algorithmRunTime + progStartTime)
//                + " milliseconds."));

        //Debug
        System.out.println(System.lineSeparator()
                + "MR Statistics:"
                + System.lineSeparator()
                + "Data Processing Time: " + dataProcessingRunTime + "ms"
                + System.lineSeparator()
                + "Algorithm Processing Time: " + algorithmRunTime + "ms"
                + System.lineSeparator()
                + "Total Running Time: " + (System.currentTimeMillis() - progStartTime) + "ms"
                + System.lineSeparator()
                + "Total Memory Used: " + MemoryUsage.memoryUsageInMBytes() + "MB");
    }

}
