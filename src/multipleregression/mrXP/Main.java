package multipleregression.mrXP;

import java.util.Set;
import utils.benchmarking.Logging;
import utils.benchmarking.MemoryUsage;
import utils.io.ReadData;

/**
 *
 * @author Luis
 */
public class Main {

    public static void main(String args) {
        //Start Recording Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        Logging.setStartTime();
        double[][] dataset = ReadData.computeArrayOfAllData(args);
        Data data = new Data(dataset, 0.7);
        Logging.setEndTime();
        long dataProcessingTime = Logging.benchmarkTime();

        //Algorithm
        Logging.setStartTime();
        Set<Function> setOfFunctions = MultipleRegression.runMultipleRegression(data.getCorrelatedData(), data.getListOfDataVariables());
        Logging.setEndTime();
        long algorithmProcessingTime = Logging.benchmarkTime();

        //Stop Recording Time
        long progStopTime = System.currentTimeMillis();

        //Debug
        System.out.println(System.lineSeparator()
                + "Statistics:"
                + System.lineSeparator()
                + "Data Processing Time: " + dataProcessingTime + "ms"
                + System.lineSeparator()
                + "Algorithm Processing Time: " + algorithmProcessingTime + "ms"
                + System.lineSeparator()
                + "Total Running Time: " + (progStopTime - progStartTime) + "ms"
                + System.lineSeparator()
                + "Total Memory Used: " + MemoryUsage.memoryUsageInMBytes() + "MB");
    }

}
