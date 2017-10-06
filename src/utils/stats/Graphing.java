package utils.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.operations.FunctionComparator;
import utils.operators.OLS;
import utils.math.Normalize;

/**
 *
 * @author Luis
 */
public class Graphing {

    public static String histogramForConsole(List<OLS> listOfOLSFunctions) {
        //Sort List by SSR
        Collections.sort(listOfOLSFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = listOfOLSFunctions.get(0).getSSR();
        double max = listOfOLSFunctions.get(listOfOLSFunctions.size() - 1).getSSR();
        //System.out.println("Min: " + min + System.lineSeparator() + "Max: " + max);

        //Create ArrayList for the normalized SSR
        List<Double> nSSRList = new ArrayList<>(listOfOLSFunctions.size());
        listOfOLSFunctions.forEach((listOfOLSFunction) -> {
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

    public static Map<String, Double> histogramForGUI(List<OLS> listOfOLSFunctions) {
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
        Collections.sort(listOfOLSFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = listOfOLSFunctions.get(0).getSSR();
        double max = listOfOLSFunctions.get(listOfOLSFunctions.size() - 1).getSSR();

        //Create ArrayList for the normalized SSR
        List<Double> nSSRList = new ArrayList<>(listOfOLSFunctions.size());
        listOfOLSFunctions.forEach((listOfOLSFunction) -> {
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

    public static Map<OLS, Double> linegraphForGUI(List<OLS> listOfOLSFunctions) {
        //Y-Axis - Normalized SSR & X-Axis - Function
        Map<OLS, Double> map = new LinkedHashMap<>();

        //Sort List by SSR
        Collections.sort(listOfOLSFunctions, FunctionComparator.SSR_SORT);

        //Get Min Value and Max Value for Normalization of Data
        double min = listOfOLSFunctions.get(0).getSSR();
        double max = listOfOLSFunctions.get(listOfOLSFunctions.size() - 1).getSSR();

        //Put OLS and its SSR into the map
        listOfOLSFunctions.forEach((listOfOLSFunction) -> {
            map.put(listOfOLSFunction, Normalize.normalize(listOfOLSFunction.getSSR(), min, max));
        });
        
        return map;
    }
}
