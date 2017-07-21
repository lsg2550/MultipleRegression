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

        //Debug Output
        StringBuilder debugOutput = new StringBuilder();
        debugOutput.append(data.CorrelationTableToString(dataset))
                .append(System.lineSeparator()).append(System.lineSeparator());

        setOfFunctions.forEach((setOfFunction) -> {
            debugOutput.append(setOfFunction.toString());
        });

        debugOutput.append(System.lineSeparator()).append(System.lineSeparator())
                .append("XP Statistics:")
                .append(System.lineSeparator())
                .append("Data Processing Time: ").append(dataProcessingTime).append("ms")
                .append(System.lineSeparator())
                .append("Algorithm Processing Time: ").append(algorithmProcessingTime).append("ms")
                .append(System.lineSeparator())
                .append("Total Running Time: ").append(System.currentTimeMillis() - progStartTime).append("ms")
                .append(System.lineSeparator())
                .append("Total Memory Used: ").append(MemoryUsage.memoryUsageInMBytes()).append("MB");

        System.out.println(debugOutput.toString());
    }
}
