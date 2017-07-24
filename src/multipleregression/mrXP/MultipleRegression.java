package multipleregression.mrXP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Luis
 */
class MultipleRegression {

    static Set<Function> runMultipleRegression(boolean[][] correlatedValues, List<DataVariable> listOfDataVariables) {
        Set<Function> setOfFunctions = new HashSet<>();
        initialRun(setOfFunctions, listOfDataVariables, correlatedValues);
        return setOfFunctions;
    }

    /**
     *
     *
     */
    private static void initialRun(Set<Function> setOfFunctions, List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        for (int i = 0; i < listOfDataVariables.size(); i++) {
            List<DataVariable> correlatedList = new LinkedList<>(); //LinkedList is faster with Insertion/Deletion, but at a cost in memory usage
            DataVariable currentDataVariable = listOfDataVariables.get(i);

            for (int j = 0; j < listOfDataVariables.size(); j++) {
                //If the currentDataVariable is not comparing itself and is correlated to the dataVariable at j. Then add it to the correlatedList.
                if (currentDataVariable.getId() != listOfDataVariables.get(j).getId() && correlatedValues[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
                    correlatedList.add(listOfDataVariables.get(j));
                }
            }

            /**
             * If the current DataVariable DOES NOT have any correlation with
             * the other variables, continue to the next iteration.
             *
             * If the current DataVariable DOES have any correlation with the
             * other variables, set it as a dependentVariable in a new function
             * and send it to the next method to find its independent variables
             * (if it has any)
             */
            if (!correlatedList.isEmpty()) {
                secondaryComputation(setOfFunctions, new Function(currentDataVariable), correlatedList, correlatedValues);
            }
        }
    }

    /**
     * This method's purpose is to now weed out the non-correlated variables in
     * the list of correlated variables. It is confusing, but while the
     * variables in the correlatedList are correlated to the Dependent
     * DataVariable we used to compare each Independent DataVariable to, that
     * does not mean that all the Independent DataVariables in the
     * correlatedList are correlated to each other.
     */
    private static void secondaryComputation(Set<Function> setOfFunctions, Function function, List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        List<DataVariable> listOfDataVariablesCopy = new ArrayList<>(listOfDataVariables);

        for (int i = 0; i < listOfDataVariables.size(); i++) {
            List<DataVariable> nonCorrelatedList = new LinkedList<>();
            DataVariable currentDataVariable = listOfDataVariablesCopy.get(i);

            if (currentDataVariable.getId() == -1) {
                continue;
            }

            for (int j = 0; j < listOfDataVariables.size(); j++) {
                if (!correlatedValues[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
                    nonCorrelatedList.add(listOfDataVariables.get(j));

                    if (i == 0) {
                        listOfDataVariablesCopy.set(j, new DataVariable(-1));
                    }
                }
            }

            Function nFunction = new Function(function.getDependentVariable(), function.getIndependentVariables());
            nFunction.addIndependentVariable(currentDataVariable);

            if (nonCorrelatedList.isEmpty()) {
                setOfFunctions.add(nFunction);
            } else {
                secondaryComputation(setOfFunctions, nFunction, nonCorrelatedList, correlatedValues);
            }
        }
    }
}
