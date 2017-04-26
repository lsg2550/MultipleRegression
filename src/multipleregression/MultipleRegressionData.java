package multipleregression;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class that processes a data file and stores it in a 2D array of double values
 * @author Anton Kovalyov
 */
public class MultipleRegressionData {
    
    public static double[][] computeArrayOfAllData(String fileLocation) {
        return processData(getStringsData(fileLocation));
    } 
    
    public static String[][] getStringsData(String fileLocation) {
        try(FileInputStream inputStream = new FileInputStream(fileLocation)) {     
            String data = IOUtils.toString(inputStream, Charset.defaultCharset());
            // Split each line of data and store into array
            String[] tokens = data.split("\\R");
            // Store each element into a 2d array
            String[][] elementsAsString = new String[tokens.length][];
            for (int i = 0; i < elementsAsString.length; i++) {
                elementsAsString[i] = tokens[i].split(",");
            }
            return elementsAsString;
        }
        catch (Exception ex) {
            return null;
        }
    }
        
    public static double[][] processData(String[][] data) {
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
                    }
                    else {
                        numericData[j][i] = map.get(current);
                    }
                }
                else {
                    numericData[j][i] = Double.parseDouble(current);
                }
            }
        }
        return numericData;
    }
}
