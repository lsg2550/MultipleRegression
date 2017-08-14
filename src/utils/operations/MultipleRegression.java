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

    /**
     * Multiple Regression Functions computation
     *
     * @param listOfDataVariables
     * @param correlatedTable
     * @return set of all possible multiple regression functions where y is
     * correlated to all x, and each x is non-correlated to one another.
     */
    public static Set<Function> computeFunctions(List<DataVariable> listOfDataVariables, boolean[][] correlatedTable) {
        Set<Function> setOfFunctions = new HashSet<>(1000, 1.0f);
        initialComputation(setOfFunctions, listOfDataVariables, correlatedTable);
        return setOfFunctions;
    }

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @param listOfDataVariables
     * @param correlatedTable
     * @return set of all possible multiple regression functions where y is
     * correlated to all x, and each x is non-correlated to one another.
     */
    public static Set<Function> computeFunctionsRandomly(List<DataVariable> listOfDataVariables, boolean[][] correlatedTable) {
        Set<Function> set = new HashSet<>(1000, 1.0f);
        initialComputationR(set, listOfDataVariables, correlatedTable);
        return set;
    }

    /**
     * Multiple Regression Functions computation
     *
     * @param list of variables
     * @param correlatedTable with threshold T1
     * @param setOfFunctions of Multiple Regression Functions being computed so
     * far
     */
    private static void initialComputation(Set<Function> setOfFunctions, List<DataVariable> listOfDataVariables, boolean[][] correlatedTable) {
        int loopLength = listOfDataVariables.size();

        for (int i = 0; i < loopLength; i++) {
            List<DataVariable> correlatedList = new ArrayList<>(loopLength);
            DataVariable currentDataVariable = listOfDataVariables.get(i);

            for (int j = 0; j < loopLength; j++) {
                //If the currentDataVariable is not comparing itself and is correlated to the dataVariable at j. Then add it to the correlatedList.
                if (currentDataVariable.getId() != listOfDataVariables.get(j).getId() && correlatedTable[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
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
                secondaryComputation(setOfFunctions, new Function(currentDataVariable), correlatedList, correlatedTable);
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
    private static void secondaryComputation(Set<Function> setOfFunctions, Function function, List<DataVariable> listOfDataVariables, boolean[][] correlatedTable) {
        List<DataVariable> listOfDataVariablesCopy = new ArrayList<>(listOfDataVariables);
        int loopLength = listOfDataVariables.size();

        for (int i = 0; i < loopLength; i++) {
            List<DataVariable> nonCorrelatedList = new ArrayList<>(loopLength);
            DataVariable currentDataVariable = listOfDataVariablesCopy.get(i);

            if (currentDataVariable.getId() == -1) {
                continue;
            }

            for (int j = 0; j < loopLength; j++) {
                if (!correlatedTable[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
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
                secondaryComputation(setOfFunctions, nFunction, nonCorrelatedList, correlatedTable);
            }
        }
    }

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @param list of variables
     * @param correlatedTable with threshold T1
     * @param setOfFunctions of Multiple Regression Functions being computed so
     * far
     */
    private static void initialComputationR(Set<Function> setOfFunctions, List<DataVariable> list, boolean[][] correlatedTable) {
        for (int i = 0; i < list.size(); i++) {
            List<DataVariable> correlatedList = new ArrayList<>();
            DataVariable currentDataVariable = list.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (currentDataVariable.getId() != list.get(j).getId() && correlatedTable[currentDataVariable.getId()][list.get(j).getId()]) {
                    correlatedList.add(list.get(j));
                }
            }

            if (!correlatedList.isEmpty()) {
                secondaryComputationR(setOfFunctions, new Function(currentDataVariable), correlatedList, correlatedTable);
            }
        }
    }

    /**
     * Recursive Multiple Regression Functions computation with a given y using
     * an optimized approach with random pivot selection.
     *
     * @param listOfDataVariables of variables being examined
     * @param correlatedTable T2
     * @param function current function to be updated or added to set
     * @param setOfFunctions of multiple regression functions being computed so
     * far
     */
    private static void secondaryComputationR(Set<Function> setOfFunctions, Function function, List<DataVariable> listOfDataVariables, boolean[][] correlatedTable) {
        int currentListSize = listOfDataVariables.size();
        int pivot = (int) (Math.random() * currentListSize);

        List<DataVariable> listOfDataVariablesCopy = new ArrayList<>(listOfDataVariables);
        List<DataVariable> nonCorrelatedList = new ArrayList<>(currentListSize);

        DataVariable pivotDataVariable = listOfDataVariablesCopy.get(pivot);
        listOfDataVariablesCopy.set(pivot, new DataVariable(-1));

        for (int i = 0; i < currentListSize; i++) {
            if (!correlatedTable[pivotDataVariable.getId()][listOfDataVariables.get(i).getId()]) {
                nonCorrelatedList.add(listOfDataVariables.get(i));
                listOfDataVariablesCopy.set(i, new DataVariable(-1));
            }
        }

        Function nPivotFunction = new Function(function.getDependentVariable(), function.getIndependentVariables());
        nPivotFunction.addIndependentVariable(pivotDataVariable);

        if (nonCorrelatedList.isEmpty()) {
            setOfFunctions.add(nPivotFunction);
        } else {
            secondaryComputation(setOfFunctions, nPivotFunction, nonCorrelatedList, correlatedTable);
        }

        for (int i = 0; i < currentListSize; i++) {
            List<DataVariable> nonCorrelatedListS = new ArrayList<>();
            DataVariable currentDataVariable = listOfDataVariablesCopy.get(i);

            if (currentDataVariable.getId() == -1) {
                continue;
            }

            for (int j = 0; j < currentListSize; j++) {
                if (!correlatedTable[currentDataVariable.getId()][listOfDataVariables.get(j).getId()]) {
                    nonCorrelatedListS.add(listOfDataVariables.get(j));
                }
            }

            Function nFunction = new Function(function.getDependentVariable(), function.getIndependentVariables());
            nFunction.addIndependentVariable(currentDataVariable);

            if (nonCorrelatedListS.isEmpty()) {
                setOfFunctions.add(nFunction);
            } else {
                secondaryComputation(setOfFunctions, nFunction, nonCorrelatedListS, correlatedTable);
            }
        }
    }
}
