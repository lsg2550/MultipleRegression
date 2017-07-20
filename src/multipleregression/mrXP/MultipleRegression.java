package multipleregression.mrXP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import utils.math.Correlation;

/**
 *
 * @author Luis
 */
class MultipleRegression {

    static Set<Function> runMultipleRegression(boolean[][] correlatedValues, List<DataVariable> listOfDataVariables) {
        Set<Function> setOfFunctions = new HashSet<>();
        initialRun(setOfFunctions, correlatedValues, listOfDataVariables);
        return setOfFunctions;
    }

    /**
     *
     *
     */
    private static void initialRun(Set<Function> setOfFunctions, boolean[][] correlatedValues, List<DataVariable> listOfDataVariables) {
        int listSize = listOfDataVariables.size();

        for (int i = 0; i < listSize; i++) {
            List<DataVariable> correlatedList = new LinkedList<>(); //LinkedList is faster with Insertion/Deletion and is easier on memory usage
            DataVariable currentDataVariable = listOfDataVariables.get(i);

            for (int j = 0; j < listSize; j++) {
                //If the currentDataVariable is not comparing itself and is correlated to the dataVariable at j. Then add it to the correlatedList.
                if (currentDataVariable.getId() != listOfDataVariables.get(j).getId() && correlatedValues[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
                    correlatedList.add(listOfDataVariables.get(j));
                }
            }

            /**
             * If the current Data Variable DOES NOT have any correlation with
             * the other variables, continue to the next iteration.
             *
             * If the current Data Variable DOES have any correlation with the
             * other variables, set it as a dependentVariable in a new function
             * and send it to the next method to find its independent variables
             * (if it has any)
             */
            if (!correlatedList.isEmpty()) {
                secondaryComputation(setOfFunctions, new Function(currentDataVariable), Correlation.negateCorrelationBooleanTables(correlatedValues), correlatedList);
            }
        }
    }

    /**
     * This method's purpose is to now weed out the non-correlated variables in
     * this list of correlated variables. It is confusing, but while the
     * variables in the correlatedList are correlated to the one DataVariable we
     * used to compare to each DataVariable that does not mean that all the
     * variables in the correlatedList are correlated to each other.
     */
    private static void secondaryComputation(Set<Function> setOfFunctions, Function function, boolean[][] nonCorrelatedValues, List<DataVariable> listOfDataVariables) {
        List<DataVariable> listOfDataVariablesCopy = new ArrayList<>(listOfDataVariables);

        for (int i = 0; i < listOfDataVariablesCopy.size(); i++) {
            List<DataVariable> nonCorrelatedList = new LinkedList<>();
            DataVariable currentDataVariable = listOfDataVariablesCopy.get(i);

            if (currentDataVariable.getId() == -1) {
                continue;
            }

            for (int j = 0; j < listOfDataVariables.size(); j++) {
                if (nonCorrelatedValues[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
                    nonCorrelatedList.add(listOfDataVariables.get(j));

                    if (i == 0) {
                        listOfDataVariablesCopy.set(j, new DataVariable(-1));
                    }
                }
            }

            function = new Function(function.getDependentVariable(), function.getIndependentVariables());
            function.addIndependentVariable(currentDataVariable);

            if (nonCorrelatedList.isEmpty()) {
                setOfFunctions.add(function);
            } else {
                secondaryComputation(setOfFunctions, function, nonCorrelatedValues, nonCorrelatedList);
            }
        }

    }

}
