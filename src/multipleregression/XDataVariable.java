package multipleregression;

import java.util.List;

/**
 * Predictor variable with its parameter
 * @author Anton Kovalyov
 */
public class XDataVariable extends DataVariable {

    private final double parameter;
    
    public XDataVariable(int id, List<Double> data, double parameter) {
        super(id, data);
        this.parameter = parameter;
    }
    
    public double getParameter() {
        return this.parameter;
    }
}
