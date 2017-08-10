package utils.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class that processes a data file and stores it in a 2D array of double
 * values
 *
 * @author Anton Kovalyov
 */
public class Read {

    //Returns Numerical Data
    public static double[][] computeArrayOfAllData(String fileLocation) {
        return stringToNumericalData(fileToStringData(fileLocation));
    }

    //Read File, Split Rows of Data into Array
    private static String[][] fileToStringData(String fileLocation) {
        //StringBuilder, faster when making multiple string concatination 
        StringBuilder data = new StringBuilder();

        //Reads Data
        System.out.println("Reading...");
        try {
            data.append(FileUtils.readFileToString(new File(fileLocation), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            return null;
        }

        // Split each line of data and store into array
        System.out.println("Spliting...");
        String[] tokens = data.toString().split("\n");

        //Clean Up StringBuilder, Some Datasets are very large and will create a large stringbuilder object, this will hopefully cut it down.
        data.setLength(0);
        data = null;

        // Store each element into a 2d array
        String[][] elementsAsString = new String[tokens.length][];

        //Removes ',' from all entries to leave only the data in the matrix
        System.out.println("Gathering Elements...");
        for (int i = 0; i < elementsAsString.length; i++) {
            elementsAsString[i] = StringUtils.split(tokens[i], ",");
        }

        return elementsAsString;
    }

    //Read Through String Data Converting to Numerical Data
    private static double[][] stringToNumericalData(String[][] data) {
        System.out.println("Converting Elements to Data...");

        double[][] numericData = new double[data.length][data[0].length];

        for (int i = 0; i < data[0].length; i++) {
            Map<String, Double> map = new HashMap<>();
            double key = 0.0;

            // If there are non-number values convert them to numbers
            for (int j = 0; j < data.length; j++) {
                String current = data[j][i];

                if (!NumberUtils.isParsable(current)) {
                    if (!map.containsKey(current)) {
                        map.put(current, key);
                        numericData[j][i] = key;
                        key++;
                    } else {
                        numericData[j][i] = map.get(current);
                    }
                } else {
                    numericData[j][i] = Double.parseDouble(current);
                }
            }
        }

        return numericData;
    }
}
