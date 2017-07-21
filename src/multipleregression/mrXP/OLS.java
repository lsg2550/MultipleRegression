package multipleregression.mrXP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * Ordinary Least Squares
 *
 * @author Luis
 */
class OLS {

    private final Function function;
    private final OLSMultipleLinearRegression olsMLR;
    private final double[] parameters;
    private final double SSR;

    OLS(Function function) {
        this.function = function;
        this.olsMLR = computeRegression();
        this.parameters = this.olsMLR.estimateRegressionParameters();
        this.SSR = this.olsMLR.calculateResidualSumOfSquares();
    }

    /**
     *
     *
     * @return
     */
    private OLSMultipleLinearRegression computeRegression() {
        List<DataVariable> dataVariables = new ArrayList<>(this.function.getOrderedSetOfDependentDataVariables());
        int columns = dataVariables.size();
        int rows = dataVariables.get(0).getArray().length;

        double[][] xData = new double[rows][columns];
        double[] yData = this.function.getDependentVariable().getArray();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                xData[i][j] = dataVariables.get(j).getArray()[i];
            }
        }

        OLSMultipleLinearRegression tempOLSMLR = new OLSMultipleLinearRegression();
        tempOLSMLR.setNoIntercept(false);
        tempOLSMLR.newSampleData(yData, xData);
        return tempOLSMLR;
    }

    @Override
    public String toString() {
        Set<DataVariable> orderedSet = this.function.getOrderedSetOfDependentDataVariables();

        StringBuilder olsFunctionBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        olsFunctionBuilder.append(this.function.getDependentVariable().toString())
                .append(" = ").append(decimalFormat.format(this.parameters[0]));

        int index = 1;
        for (final DataVariable independentVariables : orderedSet) {
            independentVariables.setParameter(this.parameters[index]);

            olsFunctionBuilder.append(" + ")
                    .append(decimalFormat.format(this.parameters[index]))
                    .append(" ")
                    .append(independentVariables.toString());

            index++;
        }

        decimalFormat = new DecimalFormat("0.00E00");
        olsFunctionBuilder.append(", SSR = ").append(decimalFormat.format(this.SSR));
        return olsFunctionBuilder.toString();
    }

    /**
     * @return the SSR, residualSumOfSquares
     */
    public double getSSR() {
        return SSR;
    }

    /**
     * @return function to use its accessors methods
     */
    public Function getFunction() {
        return function;
    }

}
