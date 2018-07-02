package multipleregression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.benchmarking.Logging;
import utils.benchmarking.MemoryUsage;
import utils.io.Read;
import utils.io.Write;
import utils.math.Correlation;
import utils.math.Normalize;
import utils.operations.FunctionComparator;
import utils.operations.MultipleRegression;
import utils.operations.Data;
import utils.operations.Function;
import utils.operations.OLS;

/**
 *
 * @author Anton Kovalyov & Luis Garay
 */
class MLR {

    private static List<OLS> olsFunctions;

    static void run(String args) {
        //Start Recording Program Time
        long progStartTime = System.currentTimeMillis();

        //Data Processing
        System.out.println("Processing Data...");
        Logging.setStartTime();
        double[][] dataset = Read.computeArrayOfAllData(args);
        Data data = new Data(dataset, 0.7, 0.7);
        Logging.setEndTime();
        long dataProcessingTime = Logging.benchmarkTime();

        //Run Multiple Linear Regression
        System.out.println("Running Multiple Linear Regression...");
        Logging.setStartTime();
        Set<Function> setOfFunctions = MultipleRegression.computeFunctionsRandomly(data.getListOfDataVariables(), data.getCorrelatedData());
        Logging.setEndTime();
        long algorithmProcessingTime = Logging.benchmarkTime();

        //Create OLS Functions
        System.out.println("Creating OLS Functions...");
        olsFunctions = new ArrayList<>(setOfFunctions.size());
        for (Function setOfFunction : setOfFunctions) {
            OLS ols = new OLS(setOfFunction);
            olsFunctions.add(ols);
        }
        Collections.sort(olsFunctions, FunctionComparator.SSR_SORT);

        //Debug Output
        System.out.println("Building Debug Output...");
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
                .append("Data Processing Time: ").append(dataProcessingTime).append(" ms")
                .append(System.lineSeparator())
                .append("Multiple Linear Regression Processing Time: ").append(algorithmProcessingTime).append(" ms")
                .append(System.lineSeparator())
                .append("Total Memory Used: ").append(MemoryUsage.memoryUsageInMBytes()).append(" MB")
                .append(System.lineSeparator())
                .append("Total Running Time: ").append(System.currentTimeMillis() - progStartTime).append(" ms");

        System.out.println(debugOutput.toString());
        Write.output(debugOutput.toString());
        
        //Garbage Collection
        debugOutput.setLength(0);
        debugOutput = null;
        setOfFunctions = null;
        dataset = null;
        data = null;
        System.gc();
    }

    static String histogramForConsole() {
        //Sort List by SSR
        Collections.sort(olsFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = olsFunctions.get(0).getSSR();
        double max = olsFunctions.get(olsFunctions.size() - 1).getSSR();
        //System.out.println("Min: " + min + System.lineSeparator() + "Max: " + max);

        //Create ArrayList for the normalized SSR
        List<Double> nSSRList = new ArrayList<>(olsFunctions.size());
        olsFunctions.forEach((listOfOLSFunction) -> {
            nSSRList.add(Normalize.normalize(listOfOLSFunction.getSSR(), min, max)); //nSSR = Normalized SSR
        });

        //Generate Histogram
        int[] histogram = new int[11];
        Collections.sort(nSSRList);
        nSSRList.forEach((nSSR) -> {
            if (nSSR < 0.1) {
                histogram[0] += 1;
            } else if (nSSR >= 0.1 && nSSR < 0.2) {
                histogram[1] += 1;
            } else if (nSSR >= 0.2 && nSSR < 0.3) {
                histogram[2] += 1;
            } else if (nSSR >= 0.3 && nSSR < 0.4) {
                histogram[3] += 1;
            } else if (nSSR >= 0.4 && nSSR < 0.5) {
                histogram[4] += 1;
            } else if (nSSR >= 0.5 && nSSR < 0.6) {
                histogram[5] += 1;
            } else if (nSSR >= 0.6 && nSSR < 0.7) {
                histogram[6] += 1;
            } else if (nSSR >= 0.7 && nSSR < 0.8) {
                histogram[7] += 1;
            } else if (nSSR >= 0.8 && nSSR < 0.9) {
                histogram[8] += 1;
            } else if (nSSR >= 0.9 && nSSR < 1.0) {
                histogram[9] += 1;
            } else if (nSSR == 1.0) {
                histogram[10] += 1;
            }
        });

        //Create StringBuilder for Output
        StringBuilder histogramStringBuilder = new StringBuilder();

        //Debug Output
        for (int i = 0; i < histogram.length; i++) {
            //Formatting
            if (Integer.toString(i).length() == 1) {
                histogramStringBuilder.append("Histogram 0").append(i).append("| ");
            } else {
                histogramStringBuilder.append("Histogram ").append(i).append("| ");
            }

            //Print Frequency
            for (int j = 0; j < histogram[i]; j++) {
                histogramStringBuilder.append("X ");
            }

            //Create New Line
            histogramStringBuilder.append(System.lineSeparator());
        }

        //Return Histogram
        return histogramStringBuilder.toString();
    }

    static Map<String, Double> histogramForGUI() {
        //Map
        Map<String, Double> map = new LinkedHashMap<>();
        map.put("< 0.1", null);
        map.put("0.1-0.2", null);
        map.put("0.2-0.3", null);
        map.put("0.3-0.4", null);
        map.put("0.4-0.5", null);
        map.put("0.5-0.6", null);
        map.put("0.6-0.7", null);
        map.put("0.7-0.8", null);
        map.put("0.8-0.9", null);
        map.put("0.9-1.0", null);
        map.put("== 1.0", null);

        //Sort List by SSR
        Collections.sort(olsFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = olsFunctions.get(0).getSSR();
        double max = olsFunctions.get(olsFunctions.size() - 1).getSSR();

        //Create ArrayList for the normalized SSR
        List<Double> nSSRList = new ArrayList<>(olsFunctions.size());
        olsFunctions.forEach((listOfOLSFunction) -> {
            nSSRList.add(Normalize.normalize(listOfOLSFunction.getSSR(), min, max)); //nSSR = Normalized SSR
        });

        //Generate Histogram
        double[] histogram = new double[11];
        Collections.sort(nSSRList);
        nSSRList.forEach((nSSR) -> {
            if (nSSR < 0.1) {
                histogram[0] += 1;
                map.put("< 0.1", histogram[0]);
            } else if (nSSR >= 0.1 && nSSR < 0.2) {
                histogram[1] += 1;
                map.put("0.1-0.2", histogram[1]);
            } else if (nSSR >= 0.2 && nSSR < 0.3) {
                histogram[2] += 1;
                map.put("0.2-0.3", histogram[2]);
            } else if (nSSR >= 0.3 && nSSR < 0.4) {
                histogram[3] += 1;
                map.put("0.3-0.4", histogram[3]);
            } else if (nSSR >= 0.4 && nSSR < 0.5) {
                histogram[4] += 1;
                map.put("0.4-0.5", histogram[4]);
            } else if (nSSR >= 0.5 && nSSR < 0.6) {
                histogram[5] += 1;
                map.put("0.5-0.6", histogram[5]);
            } else if (nSSR >= 0.6 && nSSR < 0.7) {
                histogram[6] += 1;
                map.put("0.6-0.7", histogram[6]);
            } else if (nSSR >= 0.7 && nSSR < 0.8) {
                histogram[7] += 1;
                map.put("0.7-0.8", histogram[7]);
            } else if (nSSR >= 0.8 && nSSR < 0.9) {
                histogram[8] += 1;
                map.put("0.8-0.9", histogram[8]);
            } else if (nSSR >= 0.9 && nSSR < 1.0) {
                histogram[9] += 1;
                map.put("0.9-1.0", histogram[9]);
            } else if (nSSR == 1.0) {
                histogram[10] += 1;
                map.put("== 1.0", histogram[10]);
            }
        });

        //Return Histogram
        return map;
    }

    static Map<OLS, Double> linegraphForGUI() {
        //Y-Axis - Normalized SSR & X-Axis - Function
        Map<OLS, Double> map = new LinkedHashMap<>();

        //Sort List by SSR
        Collections.sort(olsFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = olsFunctions.get(0).getSSR();
        double max = olsFunctions.get(olsFunctions.size() - 1).getSSR();

        //Put OLS and its SSR into the map
        olsFunctions.forEach((listOfOLSFunction) -> {
            map.put(listOfOLSFunction, Normalize.normalize(listOfOLSFunction.getSSR(), min, max));
        });

        return map;
    }
}
