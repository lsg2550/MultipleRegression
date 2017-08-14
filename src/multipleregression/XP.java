package multipleregression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.benchmarking.Logging;
import utils.benchmarking.MemoryUsage;
import utils.io.Read;
import utils.io.Write;
import utils.math.Correlation;
import utils.operations.FunctionComparator;
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

    static void run(String args) {
        //Start Recording Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        System.out.println("Reading Data...");
        Logging.setStartTime();
        double[][] dataset = Read.computeArrayOfAllData(args);
        Data data = new Data(dataset, 0.7);
        Logging.setEndTime();
        long dataProcessingTime = Logging.benchmarkTime();

        //Algorithm
        System.out.println("Running Algorithm...");
        Logging.setStartTime();
        Set<Function> setOfFunctions = MultipleRegression.computeFunctionsRandomly(data.getListOfDataVariables(), data.getCorrelatedData());
        Logging.setEndTime();
        long algorithmProcessingTime = Logging.benchmarkTime();

        //Create OLS Functions
        olsFunctions = new ArrayList<>(setOfFunctions.size());

        for (Function setOfFunction : setOfFunctions) {
            OLS ols = new OLS(setOfFunction);
            olsFunctions.add(ols);
        }

        Collections.sort(olsFunctions, FunctionComparator.SSR_SORT);

        //Debug Output
        System.out.println("Building Output...");
        StringBuilder debugOutput = new StringBuilder();

        debugOutput.append("Output for File: ")
                .append(args).append(System.lineSeparator())
                .append(Correlation.CorrelationTableToString(data.getCorrelationMatrix()))
                .append(System.lineSeparator()).append(System.lineSeparator());

        for (int i = 0; i < olsFunctions.size(); i++) {
            debugOutput.append("{ Function ")
                    .append(i)
                    .append(": ")
                    .append(olsFunctions.get(i).toString())
                    .append(" }")
                    .append(System.lineSeparator());
        }

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
        Write.output(debugOutput.toString());

        //Garbage Collection
        debugOutput.setLength(0);
        setOfFunctions = null;
        dataset = null;
        data = null;
        System.gc();
    }

    static String getHistogramForConsole() {
        return Graphing.histogramForConsole(olsFunctions);
    }

    static Map<String, Integer> getHistogramForGraph() {
        return Graphing.histogramForGUI(olsFunctions);
    }

    static Map<OLS, Double> getLinegraphForGraph() {
        return Graphing.linegraphForGUI(olsFunctions);
    }
}
