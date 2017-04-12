/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 *
 * @author srv_veralab
 */
public class OLSFunction {
    
    private final Function f;
    private final OLSMultipleLinearRegression oLSMultipleLinearRegression;
    private final double[] parameters;
    private final double SSR;
    
    public OLSFunction(Function f) {
        this.f = f;
        this.oLSMultipleLinearRegression = computeRegression();
        this.parameters = this.oLSMultipleLinearRegression.estimateRegressionParameters();
        this.SSR = this.oLSMultipleLinearRegression.calculateResidualSumOfSquares();
    }

    private OLSMultipleLinearRegression computeRegression() {
        List<DataVariable> xVars = new ArrayList<>(getOrderedSetOfDependentDataVariables());
        int columns = xVars.size();
        int rows = xVars.get(0).getData().size();
        double[][] xData = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                xData[i][j] = xVars.get(j).getData().get(i);                
            }            
        }
        double[] yData = getDependentDataVariable().getDataArray();
        OLSMultipleLinearRegression r = new OLSMultipleLinearRegression();
        r.setNoIntercept(false);
        r.newSampleData(yData, xData);
        return r;
    }    
    
    @Override
    public String toString() {
        String s = getDependentDataVariable() + " = ";
        Set<DataVariable> orderedSet = getOrderedSetOfDependentDataVariables();
        DecimalFormat df = new DecimalFormat("0.00");
        s += df.format(getParameters()[0]);
        List<XDataVariable> xVars = new ArrayList<>();
        int index = 1;
        for (final DataVariable current : orderedSet) {
            xVars.add(new XDataVariable(current.getId(), current.getData(), getParameters()[index]));
            index++;
        }
        index = 1;
        for (XDataVariable x : xVars) {
            s += " + " + df.format(getParameters()[index]) + " " + x;
            index++;
        }
        DecimalFormat df2 = new DecimalFormat("0.00E00");
        return s + ", SSR = " + df2.format(getSSR());
    }

    /**
     * @return the f
     */
    public Function getF() {
        return f;
    }

    /**
     * @return the oLSMultipleLinearRegression
     */
    public OLSMultipleLinearRegression getoLSMultipleLinearRegression() {
        return oLSMultipleLinearRegression;
    }

    /**
     * @return the parameters
     */
    public double[] getParameters() {
        return parameters;
    }

    /**
     * @return the SSR
     */
    public double getSSR() {
        return SSR;
    }
    
    public DataVariable getDependentDataVariable() {
        return this.f.getDependentDataVariable();
    }
    
    public Set<DataVariable> getIndependentDataVariables() {
        return this.f.getIndependentDataVariables();
    }
    
    public Set<DataVariable> getOrderedSetOfDependentDataVariables() {
        return this.f.getOrderedSetOfDependentDataVariables();
    }
}
