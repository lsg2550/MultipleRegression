/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author srv_veralab
 */
public class MultipleRegressionTest {
    
    @Test
    public void computeFunctionsReliability() {
        MultipleRegression mr = new MultipleRegression(MultipleRegressionData.computeArrayOfAllData("thads2013n.txt"), 0.7);
        assertEquals(mr.computeFunctions(), mr.computeFunctionsInefficiently());
        assertTrue(checkResultOfComputeFunctions(mr.computeFunctionsInefficiently(), mr.getCorrelatedBooleanTable()));
    }
    
    public boolean checkResultOfComputeFunctions(Set<Function> f, boolean[][] correlatedTable) {
        for (Function current : f) {
            DataVariable y = current.getDependentDataVariable();
            Set<DataVariable> vars = current.getIndependentDataVariables();
            for (DataVariable x : vars) {
                if (correlatedTable[y.getId()][x.getId()]) {
                    for (DataVariable comp : vars) {
                        if(x.getId() != comp.getId() && correlatedTable[x.getId()][comp.getId()]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
