package multipleregression.usingpivot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * The MultipleRegression class applies the main algorithm
 *
 * @author Anton Kovalyov
 */
public class MultipleRegression {

    private final double[][] arrayOfAllData;
    private final List<DataVariable> dataVariables;
    private final double[][] correlationTable;
    private final boolean[][] correlatedBooleanTable;
    private final boolean[][] noncorrelatedBooleanTable;
    private final double threshold1;
    private final double threshold2;

    /**
     *
     * @param arrayOfAllData -
     * @param threshold1 - threshold for correlated variables
     * @param threshold2 - threshold for non correlated variables
     */
    public MultipleRegression(double[][] arrayOfAllData, double threshold1, double threshold2) {
        this.arrayOfAllData = arrayOfAllData;
        this.threshold1 = threshold1;
        this.threshold2 = threshold2;
        this.dataVariables = computeDataVariables(arrayOfAllData);
        this.correlationTable = new PearsonsCorrelation(arrayOfAllData).getCorrelationMatrix().getData();
        this.correlatedBooleanTable = correlatedBooleanTable(this.correlationTable, this.threshold1);
        this.noncorrelatedBooleanTable = noncorrelatedBooleanTable(this.correlationTable, this.threshold2);
    }

    /**
     * Compute all the data variables and store them in a list in order
     * according to their id.
     *
     * @param arrayOfAllData
     * @return
     */
    private List<DataVariable> computeDataVariables(double[][] arrayOfAllData) {
        List<DataVariable> dataVars = new ArrayList<>();

        for (int i = 0; i < arrayOfAllData[0].length; i++) {
            List<Double> data = new ArrayList<>();

            for (int j = 0; j < arrayOfAllData.length; j++) {
                data.add(arrayOfAllData[j][i]);
            }

            dataVars.add(new DataVariable(i, data));
        }

        return Collections.unmodifiableList(dataVars);
    }

    /**
     * Creates a boolean table of correlations among variables, where T stands
     * for correlated, and F stands for non-correlated.
     *
     * @param correlationTable
     * @param threshold
     * @return
     */
    private boolean[][] correlatedBooleanTable(double[][] correlationTable, double threshold) {
        boolean[][] booleanTable = new boolean[correlationTable.length][correlationTable[0].length];

        for (int i = 0; i < booleanTable.length; i++) {
            for (int j = 0; j < booleanTable[0].length; j++) {
                booleanTable[i][j] = Math.abs(correlationTable[i][j]) > threshold;
            }
        }

        return booleanTable;
    }

    /**
     * Creates a boolean table of correlations among variables, where T stands
     * for non-correlated and F stands for correlated
     *
     * @param correlationTable
     * @param threshold
     * @return
     */
    private boolean[][] noncorrelatedBooleanTable(double[][] correlationTable, double threshold) {
        boolean[][] booleanTable = new boolean[correlationTable.length][correlationTable[0].length];

        for (int i = 0; i < booleanTable.length; i++) {
            for (int j = 0; j < booleanTable[0].length; j++) {
                booleanTable[i][j] = Math.abs(correlationTable[i][j]) <= threshold;
            }
        }

        return booleanTable;
    }

    /**
     * Optimized computation of correlated subsets with pivot selection
     *
     * @return all possible correlated subsets
     */
    public Set<Set<DataVariable>> computeCorrelatedSubsets() {
        Set<Set<DataVariable>> set = new HashSet<>();
        Set<DataVariable> subset = new HashSet<>();

        computeSubsets(this.dataVariables, this.correlatedBooleanTable, subset, set);

        return set;
    }

    /**
     * Optimized computation of correlated subsets (Random pivot selection)
     *
     * @return all possible correlated subsets
     */
    public Set<Set<DataVariable>> computeCorrelatedSubsetsRandomly() {
        Set<Set<DataVariable>> set = new HashSet<>();
        Set<DataVariable> subset = new HashSet<>();

        computeSubsetsRandomly(this.dataVariables, this.correlatedBooleanTable, subset, set);

        return set;
    }

    /**
     * Optimized computation of non-correlated subsets with pivot selection
     *
     * @return all possible non-correlated subsets
     */
    public Set<Set<DataVariable>> computeNoncorrelatedSubsets() {
        Set<Set<DataVariable>> set = new HashSet<>();
        Set<DataVariable> subset = new HashSet<>();

        computeSubsets(this.dataVariables, this.noncorrelatedBooleanTable, subset, set);

        return set;
    }

    /**
     * Optimized computation of non-correlated subsets (random pivot selection)
     *
     * @return all possible non-correlated subsets
     */
    public Set<Set<DataVariable>> computeNoncorrelatedSubsetsRandomly() {
        Set<Set<DataVariable>> set = new HashSet<>();
        Set<DataVariable> subset = new HashSet<>();

        computeSubsetsRandomly(this.dataVariables, this.noncorrelatedBooleanTable, subset, set);

        return set;
    }

    /**
     * Computes recursively all possible correlated or non-correlated subsets,
     * depending the correlation table passed, using an optimized approach.
     *
     * @param list of variables
     * @param correlationTable (can be correlated or non-correlated)
     * @param subset (current subset being updated)
     * @param set of computed correlation subsets so far
     */
    private void computeSubsets(List<DataVariable> list, boolean[][] correlationTable, Set<DataVariable> subset, Set<Set<DataVariable>> set) {
        List<DataVariable> rows = new ArrayList<>(list);
        List<DataVariable> columns = new ArrayList<>(list);

        for (int i = 0; i < rows.size(); i++) {
            List<DataVariable> vars = new ArrayList<>();
            DataVariable current = rows.get(i);

            if (current.getId() == -1) {
                continue;
            }

            for (int j = 0; j < columns.size(); j++) {
                if (current != columns.get(j) && correlationTable[current.getId()][columns.get(j).getId()]) {
                    vars.add(columns.get(j));
                    if (i == 0) {
                        rows.set(j, new DataVariable(-1));
                    }
                }
            }

            rows.set(i, new DataVariable(-1));
            Set<DataVariable> updatedSubset = new HashSet<>(subset);
            updatedSubset.add(current);

            if (vars.isEmpty()) {
                set.add(updatedSubset);
            } else {
                computeSubsets(vars, correlationTable, updatedSubset, set);
            }
        }
    }

    /**
     * Computes recursively all possible correlated or non-correlated subsets,
     * depending the correlation table passed, using an optimized approach and
     * random pivot selection.
     *
     * @param list of variables
     * @param correlationTable (can be correlated or non-correlated)
     * @param subset (current subset being updated)
     * @param set of computed correlation subsets so far
     */
    private void computeSubsetsRandomly(List<DataVariable> list, boolean[][] correlationTable, Set<DataVariable> subset, Set<Set<DataVariable>> set) {
        int pivot = (int) (Math.random() * list.size());

        List<DataVariable> rows = new ArrayList<>(list);
        List<DataVariable> columns = new ArrayList<>(list);
        List<DataVariable> corrVars = new ArrayList<>();

        DataVariable pivotVar = rows.get(pivot);
        rows.set(pivot, new DataVariable(-1));

        for (int i = 0; i < rows.size(); i++) {
            if (correlationTable[pivotVar.getId()][columns.get(i).getId()]) {
                corrVars.add(columns.get(i));
                rows.set(i, new DataVariable(-1));
            }
        }

        Set<DataVariable> updatedPivotSubset = new HashSet<>(subset);
        updatedPivotSubset.add(pivotVar);

        if (corrVars.isEmpty()) {
            set.add(updatedPivotSubset);
        } else {
            computeSubsetsRandomly(corrVars, correlationTable, updatedPivotSubset, set);
        }

        for (int i = 0; i < rows.size(); i++) {
            List<DataVariable> vars = new ArrayList<>();
            DataVariable current = rows.get(i);

            if (current.getId() == -1) {
                continue;
            }

            for (int j = 0; j < columns.size(); j++) {
                if (current != columns.get(j) && correlationTable[current.getId()][columns.get(j).getId()]) {
                    vars.add(columns.get(j));
                }
            }

            Set<DataVariable> updatedSubset = new HashSet<>(subset);
            updatedSubset.add(current);

            if (vars.isEmpty()) {
                set.add(updatedSubset);
            } else {
                computeSubsetsRandomly(vars, correlationTable, updatedSubset, set);
            }
        }
    }

    /**
     * Multiple Regression Functions computation using an optimized approach.
     *
     * @return set of all possible multiple regression functions where y is
     * correlated to all x, and each x is non-correlated to one another.
     */
    public Set<Function> computeFunctions() {
        Set<Function> set = new HashSet<>();
        computeFunctions(this.dataVariables, this.correlatedBooleanTable, this.noncorrelatedBooleanTable, set);
        return set;
    }

    /**
     * Multiple Regression Functions computation using an optimized approach.
     *
     * @param list of variables
     * @param correlatedTable with threshold T1
     * @param noncorrelatedTable with threshold T2
     * @param set of Multiple Regression Functions being computed so far
     */
    private void computeFunctions(List<DataVariable> list, boolean[][] correlatedTable, boolean[][] noncorrelatedTable, Set<Function> set) {
        for (int i = 0; i < list.size(); i++) {
            List<DataVariable> correlatedVars = new ArrayList<>();
            DataVariable current = list.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (current.getId() != list.get(j).getId() && correlatedTable[current.getId()][list.get(j).getId()]) {
                    correlatedVars.add(list.get(j));
                }
            }

            if (!correlatedVars.isEmpty()) {
                computeFunctions(correlatedVars, noncorrelatedTable, new Function(current), set);
            }
        }
    }

    /**
     * Recursive Multiple Regression Functions computation with a given y using
     * an optimized approach with pivot selection.
     *
     * @param list of variables being examined
     * @param noncorrelatedTable T2
     * @param f current function to be updated or added to set
     * @param set of multiple regression functions being computed so far
     */
    private void computeFunctions(List<DataVariable> list, boolean[][] noncorrelatedTable, Function f, Set<Function> set) {
        List<DataVariable> rows = new ArrayList<>(list);
        List<DataVariable> columns = new ArrayList<>(list);

        for (int i = 0; i < rows.size(); i++) {
            List<DataVariable> vars = new ArrayList<>();
            DataVariable current = rows.get(i);

            if (current.getId() == -1) {
                continue;
            }

            for (int j = 0; j < columns.size(); j++) {
                if (noncorrelatedTable[current.getId()][columns.get(j).getId()]) {
                    vars.add(columns.get(j));

                    if (i == 0) {
                        rows.set(j, new DataVariable(-1));
                    }
                }
            }

            Function updatedFunction = new Function(f.getDependentDataVariable(), f.getIndependentDataVariables());
            updatedFunction.add(current);

            if (vars.isEmpty()) {
                set.add(updatedFunction);
            } else {
                computeFunctions(vars, noncorrelatedTable, updatedFunction, set);
            }
        }
    }

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @return set of all possible multiple regression functions where y is
     * correlated to all x, and each x is non-correlated to one another.
     */
    public Set<Function> computeFunctionsRandomly() {
        Set<Function> set = new HashSet<>();
        computeFunctionsRandomly(this.dataVariables, this.correlatedBooleanTable, this.noncorrelatedBooleanTable, set);
        return set;
    }

    /**
     * Multiple Regression Functions computation using an optimized approach
     * (random pivot selection).
     *
     * @param list of variables
     * @param correlatedTable with threshold T1
     * @param noncorrelatedTable with threshold T2
     * @param set of Multiple Regression Functions being computed so far
     */
    private void computeFunctionsRandomly(List<DataVariable> list, boolean[][] correlatedTable, boolean[][] noncorrelatedTable, Set<Function> set) {
        for (int i = 0; i < list.size(); i++) {
            List<DataVariable> correlatedVars = new ArrayList<>();
            DataVariable current = list.get(i);

            for (int j = 0; j < list.size(); j++) {
                if (current.getId() != list.get(j).getId() && correlatedTable[current.getId()][list.get(j).getId()]) {
                    correlatedVars.add(list.get(j));
                }
            }

            if (!correlatedVars.isEmpty()) {
                computeFunctionsRandomly(correlatedVars, noncorrelatedTable, new Function(current), set);
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
    private void computeFunctionsRandomly(List<DataVariable> list, boolean[][] noncorrelatedTable, Function f, Set<Function> set) {
        int pivot = (int) (Math.random() * list.size());
        List<DataVariable> rows = new ArrayList<>(list);
        List<DataVariable> columns = new ArrayList<>(list);
        List<DataVariable> noncorrVars = new ArrayList<>();
        DataVariable pivotVar = rows.get(pivot);
        rows.set(pivot, new DataVariable(-1));

        for (int i = 0; i < rows.size(); i++) {
            if (noncorrelatedTable[pivotVar.getId()][columns.get(i).getId()]) {
                noncorrVars.add(columns.get(i));
                rows.set(i, new DataVariable(-1));
            }
        }

        Function updatedPivotFunction = new Function(f.getDependentDataVariable(), f.getIndependentDataVariables());
        updatedPivotFunction.add(pivotVar);

        if (noncorrVars.isEmpty()) {
            set.add(updatedPivotFunction);
        } else {
            computeFunctions(noncorrVars, noncorrelatedTable, updatedPivotFunction, set);
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

            Function updatedFunction = new Function(f.getDependentDataVariable(), f.getIndependentDataVariables());
            updatedFunction.add(current);

            if (vars.isEmpty()) {
                set.add(updatedFunction);
            } else {
                computeFunctions(vars, noncorrelatedTable, updatedFunction, set);
            }
        }
    }

    /**
     * Store the correlation table in a string for display purposes.
     *
     * @param correlationTable
     * @return the String showing a matrix of correlations
     */
    public String CorrelationTableToString(double[][] correlationTable) {
        String s = "Correlation Matrix:" + System.lineSeparator() + System.lineSeparator() + "        ";
        int size = correlationTable.length;

        for (int i = 0; i < size; i++) {
            s += "x" + i + " " + computeOffset(i);
        }

        for (int i = 0; i < size; i++) {
            s += System.lineSeparator() + "x" + i + ":" + computeOffset(i);

            for (int j = 0; j < correlationTable[0].length; j++) {
                if (Math.round(correlationTable[i][j] * 100.0) / 100.0 >= 0) {
                    s += " " + String.format("%.2f", Math.round(correlationTable[i][j] * 100.0) / 100.0) + " ";
                } else {
                    s += String.format("%.2f", Math.round(correlationTable[i][j] * 100.0) / 100.0) + " ";
                }
            }

        }

        return s;
    }

    /**
     * Helper function for the CorrelationTableToString method.
     *
     * @param i
     * @return number of spaces before displaying the next item in the String.
     */
    private String computeOffset(int i) {
        String s = "";

        if (i < 10) {
            s += "   ";
        } else if (i < 100) {
            s += "  ";
        } else {
            s += " ";
        }

        return s;
    }

    /**
     * @return the arrayOfAllData
     */
    public double[][] getArrayOfAllData() {
        return arrayOfAllData;
    }

    /**
     * @return the dataVariables
     */
    public List<DataVariable> getDataVariables() {
        return dataVariables;
    }

    /**
     * @return the threshold T2
     */
    public double getThreshold1() {
        return threshold1;
    }

    /**
     * @return the threshold T2
     */
    public double getThreshold2() {
        return threshold2;
    }

    /**
     * @return the correlationTable
     */
    public double[][] getCorrelationTable() {
        return correlationTable;
    }

    /**
     * @return the correlatedBooleanTable
     */
    public boolean[][] getCorrelatedBooleanTable() {
        return correlatedBooleanTable;
    }

    /**
     * @return the noncorrelatedBooleanTable
     */
    public boolean[][] getNoncorrelatedBooleanTable() {
        return noncorrelatedBooleanTable;
    }
}
