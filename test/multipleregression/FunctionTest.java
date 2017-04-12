/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author srv_veralab
 */
public class FunctionTest {
    
    @Test
    public void equalsMethod() {
        Function f1 = new Function(new DataVariable(1));
        Function f2 = new Function(new DataVariable(2));
        assertFalse(f1.equals(f2));
        Function f3 = new Function(new DataVariable(2));
        assertTrue(f2.equals(f3));
        f2.add(new DataVariable(4));
        assertFalse(f2.equals(f3));
        f3.add(new DataVariable(4));
        assertTrue(f2.equals(f3));
        f1.add(new DataVariable(4));
        assertFalse(f1.equals(f3));
        f3.add(new DataVariable(4));
        assertTrue(f2.equals(f3));
        f3.add(new DataVariable(5));
        assertFalse(f3.equals(f2));
        Function f4 = new Function(f3.getDependentDataVariable(), f3.getIndependentDataVariables());
        assertTrue(f4.equals(f3));
        f4.add(new DataVariable(3));
        assertFalse(f4.equals(f3));
    }
}
