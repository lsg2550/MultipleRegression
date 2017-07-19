package multipleregression.usingpivot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * A class to display functions with parameters and find the maximum value p
 *
 * @author Anton Kovalyov
 */
public class Functions {

    /**
     * Display functions in order, show parameters and SSR values. Note that
     * computing parameters and SSR can take a while depending on the data used
     *
     * @param functions
     * @param fc
     * @return
     */
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

    /**
     * Get the maximum p value from all functions
     *
     * @param functions
     * @return
     */
    public static int maxP(Set<Function> functions) {
        int maxP = 0;

        for (Function f : functions) {
            int currentP = f.getIndependentDataVariables().size();
            if (maxP < currentP) {
                maxP = currentP;
            }
        }

        return maxP;
    }
}
