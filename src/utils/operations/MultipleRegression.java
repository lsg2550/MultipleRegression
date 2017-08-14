package utils.operations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.operators.DataVariable;
import utils.operators.Function;

/**
 *
 * @author Luis
 */
public class MultipleRegression {

    public static Set<Function> computeFunctions(List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        Set<Function> setOfFunctions = new HashSet<>(1000, 1.0f);
        initialComputation(setOfFunctions, listOfDataVariables, correlatedValues);
        return setOfFunctions;
    }

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @param listOfDataVariables
     * @param correlatedValues
     * @return set of all possible multiple regression functions where y is
     * correlated to all x, and each x is non-correlated to one another.
     */
    public static Set<Function> computeFunctionsRandomly(List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        Set<Function> set = new HashSet<>(1000, 1.0f);
        initialComputationR(set, listOfDataVariables, correlatedValues);
        return set;
    }

    /**
     *
     *
     */
    private static void initialComputation(Set<Function> setOfFunctions, List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        int loopLength = listOfDataVariables.size();
        System.out.println("Initial Loop Length: " + loopLength);

        for (int i = 0; i < loopLength; i++) {
            List<DataVariable> correlatedList = new ArrayList<>(loopLength);
            DataVariable currentDataVariable = listOfDataVariables.get(i);

            for (int j = 0; j < loopLength; j++) {
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
     * This method's purpose is to now find other variables that correlate with
     * the dependent variable but aren't correlated to each other. It is
     * confusing, but while the variables in the correlatedList are correlated
     * to the Dependent DataVariable, that does not mean that all the
     * Independent DataVariables in the correlatedList are correlated to each
     * other.
     */
    private static void secondaryComputation(Set<Function> setOfFunctions, Function function, List<DataVariable> listOfDataVariables, boolean[][] correlatedValues) {
        List<DataVariable> listOfDataVariablesCopy = new ArrayList<>(listOfDataVariables);
        int loopLength = listOfDataVariables.size();
        System.out.println("Secondary Loop Length: " + loopLength);

        for (int i = 0; i < loopLength; i++) {
            List<DataVariable> nonCorrelatedList = new ArrayList<>(loopLength);
            DataVariable currentDataVariable = listOfDataVariablesCopy.get(i);

            if (currentDataVariable.getId() == -1) {
                continue;
            }

            for (int j = 0; j < loopLength; j++) {
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

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @param list of variables
     * @param correlatedTable with threshold T1
     * @param set of Multiple Regression Functions being computed so far
     */
    private static void initialComputationR(Set<Function> set, List<DataVariable> list, boolean[][] correlatedTable) {
        for (int i = 0; i < list.size(); i++) {
            List<DataVariable> correlatedVars = new ArrayList<>();
            DataVariable current = list.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (current.getId() != list.get(j).getId() && correlatedTable[current.getId()][list.get(j).getId()]) {
                    correlatedVars.add(list.get(j));
                }
            }

            if (!correlatedVars.isEmpty()) {
                secondaryComputationR(set, new Function(current), correlatedVars, correlatedTable);
            }
        }
    }

    /**
     * Recursive Multiple Regression Functions computation with a given y using
     * an optimized approach with random pivot selection.
     *
     * @param list of variables being examined
     * @param noncorrelatedTable T2
     * @param f current function to be updated or added to set
     * @param set of multiple regression functions being computed so far
     */
    private static void secondaryComputationR(Set<Function> set, Function f, List<DataVariable> list, boolean[][] noncorrelatedTable) {
        int pivot = (int) (Math.random() * list.size());
        List<DataVariable> rows = new ArrayList<>(list);
        List<DataVariable> columns = new ArrayList<>(list);

        DataVariable pivotVar = rows.get(pivot);

        List<DataVariable> noncorrVars = new ArrayList<>();
        rows.set(pivot, new DataVariable(-1));

        for (int i = 0; i < rows.size(); i++) {
            if (noncorrelatedTable[pivotVar.getId()][columns.get(i).getId()]) {
                noncorrVars.add(columns.get(i));
                rows.set(i, new DataVariable(-1));
            }
        }

        Function updatedPivotFunction = new Function(f.getDependentVariable(), f.getIndependentVariables());
        updatedPivotFunction.addIndependentVariable(pivotVar);

        if (noncorrVars.isEmpty()) {
            set.add(updatedPivotFunction);
        } else {
            secondaryComputation(set, updatedPivotFunction, noncorrVars, noncorrelatedTable);
        }

        for (int i = 0; i < rows.size(); i++) {
            List<DataVariable> vars = new ArrayList<>();
            DataVariable current = rows.get(i);

            if (current.getId() == -1) {
                continue;
            }

            for (int j = 0; j < columns.size(); j++) {
                if (noncorrelatedTable[current.getId()][columns.get(j).getId()]) {
                    vars.add(columns.get(j));
                }
            }

            Function updatedFunction = new Function(f.getDependentVariable(), f.getIndependentVariables());
            updatedFunction.addIndependentVariable(current);

            if (vars.isEmpty()) {
                set.add(updatedFunction);
            } else {
                secondaryComputation(set, updatedFunction, vars, noncorrelatedTable);
            }
        }
    }
}
