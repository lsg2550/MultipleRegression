package multipleregression.tempname;

import utils.benchmarking.Logging;
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
        System.out.println(System.lineSeparator()
                + "Statistics:"
                + System.lineSeparator()
                + "Data Processing Time: " + dataProcessingTime + "ms");
    }

}
