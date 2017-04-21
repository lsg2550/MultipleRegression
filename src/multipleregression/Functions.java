/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multipleregression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author srv_veralab
 */
public class Functions {

    public static String toString(Set<Function> functions, FunctionComparator fc) {
        String s = "{" + System.lineSeparator();
        List<OLSFunction> list = new ArrayList<>();
        for (Function current : functions) {
            list.add(new OLSFunction(current));
        }
        Collections.sort(list, fc);
        int n = 1;
        for (OLSFunction current : list) {
            s += " Function " + n + ": " + current + System.lineSeparator();
            n++;
        }
        return s += "}";
    }
    
    public static int maxP(Set<Function> functions) {
        int maxP = 0;
        for (Function f : functions) {
            int currentP = f.getIndependentDataVariables().size();
            if (maxP < currentP)
                maxP = currentP;
        }
        return maxP;
    }
}
