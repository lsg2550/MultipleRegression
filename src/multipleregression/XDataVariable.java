/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.util.List;

/**
 *
 * @author srv_veralab
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
