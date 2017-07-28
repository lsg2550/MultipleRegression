package multipleregressionxp;

import datasets.Datasets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.benchmarking.Logging;
import utils.benchmarking.MemoryUsage;
import utils.io.Read;
import utils.operations.MultipleRegression;
import utils.operators.Data;
import utils.operators.Function;
import utils.operators.OLS;
import utils.stats.Graphing;

/**
 *
 * @author Luis
 */
class XP {

    private static List<OLS> olsFunctions;

    static void run() {
        //Start Recording Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        System.out.println("Reading Data...");
        Logging.setStartTime();
        double[][] dataset = Read.computeArrayOfAllData(Datasets.DATASET_1);
        Data data = new Data(dataset, 0.7);
        Logging.setEndTime();
        long dataProcessingTime = Logging.benchmarkTime();

        //Algorithm
        System.out.println("Running Algorithm...");
        Logging.setStartTime();
        Set<Function> setOfFunctions = MultipleRegression.runMultipleRegression(data.getCorrelatedData(), data.getListOfDataVariables());
        Logging.setEndTime();
        long algorithmProcessingTime = Logging.benchmarkTime();

        //Debug Output
        olsFunctions = new ArrayList<>(setOfFunctions.size());
        StringBuilder debugOutput = new StringBuilder();

        debugOutput.append(data.CorrelationTableToString(dataset))
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        setOfFunctions.forEach((setOfFunction) -> {
            OLS ols = new OLS(setOfFunction);
            olsFunctions.add(ols);

            debugOutput.append("{ Function: ")
                    .append(ols.toString())
                    .append(" }")
                    .append(System.lineSeparator());
        });

        debugOutput.append(System.lineSeparator()).append(System.lineSeparator())
                .append("XP Statistics:")
                .append(System.lineSeparator())
                .append("Data Processing Time: ").append(dataProcessingTime).append("ms")
                .append(System.lineSeparator())
                .append("Algorithm Processing Time: ").append(algorithmProcessingTime).append("ms")
                .append(System.lineSeparator())
                .append("Total Memory Used: ").append(MemoryUsage.memoryUsageInMBytes()).append("MB")
                .append(System.lineSeparator())
                .append("Total Running Time: ").append(System.currentTimeMillis() - progStartTime).append("ms");

        System.out.println(debugOutput.toString());
    }

    static String getHistogramForConsole() {
        return Graphing.histogramString(olsFunctions);
    }

    static Map<String, Integer> getHistogramForGraph() {
        return Graphing.histogramDouble(olsFunctions);
    }
}
