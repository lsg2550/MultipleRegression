package multipleregression;

import java.io.IOException;
import java.util.Set;

public class MainApp {
    
    public static void main(String[] args) throws IOException {
        long progStartTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        double[][] data = MultipleRegressionData.computeArrayOfAllData("thads2013n.txt");
        MultipleRegression mr = new MultipleRegression(data, 0.5, 0.8);
        long endTime = System.currentTimeMillis();
        long dataProcessingRunTime = endTime - startTime;
        startTime = System.currentTimeMillis();
        Set<Function> f = (Set<Function>)mr.computeFunctionsRandomly();
        endTime = System.currentTimeMillis();
        long algorithmRunTime = endTime - startTime;
        System.out.println(mr.CorrelationTableToString(mr.getCorrelationTable()) 
                + System.lineSeparator() + System.lineSeparator() 
                + f.size() + " " + Functions.maxP(f)
                + System.lineSeparator() + System.lineSeparator()
                + "Data processing running time: " + dataProcessingRunTime + " milliseconds."
                + System.lineSeparator()
                + "Algorithm running time: " + algorithmRunTime + " milliseconds."
                + System.lineSeparator()
                + "Parameters computation time: " + (System.currentTimeMillis() - 
                        (dataProcessingRunTime + algorithmRunTime + progStartTime)
                + " milliseconds."));
    }
}
