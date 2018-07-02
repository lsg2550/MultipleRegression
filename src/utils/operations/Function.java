package utils.operations;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author Anton Kovalyov & Luis Garay
 */
public class Function {

    private final DataVariable dependentVariable;
    private final HashSet<DataVariable> independentVariables;

    public Function(final DataVariable dependentVariable) {
        this.dependentVariable = dependentVariable;
        this.independentVariables = new HashSet<>();
    }

    public Function(final DataVariable dependentVariable, final HashSet<DataVariable> independentDataVariables) {
        this.dependentVariable = dependentVariable;
        this.independentVariables = independentDataVariables;
    }

    public DataVariable getDependentVariable() {
        return this.dependentVariable;
    }

    public HashSet<DataVariable> getIndependentVariables() {
        return new HashSet<>(this.independentVariables);
    }

    public void addIndependentVariable(DataVariable x) {
        this.independentVariables.add(x);
    }

    public TreeSet<DataVariable> getOrderedSetOfDependentDataVariables() {
        return new TreeSet<>(getIndependentVariables());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Function) {
            Function f = (Function) o;

            if (f.getDependentVariable().getId() == this.getDependentVariable().getId()
                    && f.getIndependentVariables().equals(this.getIndependentVariables())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 41 * hash + Objects.hashCode(this.dependentVariable);
        hash = 41 * hash + Objects.hashCode(this.independentVariables);

        return hash;
    }
}
