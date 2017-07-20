package multipleregression.usingpivot;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 * A Function object that has a dependent DataVariable and a HashSet of
 * independent DataVariables
 *
 * @author Anton Kovalyov
 */
class Function {

    private final DataVariable dependentDataVariable;
    private final HashSet<DataVariable> independentDataVariables;

    public Function(final DataVariable dependentDataVariable) {
        this.dependentDataVariable = dependentDataVariable;
        this.independentDataVariables = new HashSet<>();
    }

    public Function(final DataVariable dependentDataVariable, final HashSet<DataVariable> independentDataVariables) {
        this.dependentDataVariable = dependentDataVariable;
        this.independentDataVariables = independentDataVariables;
    }

    public DataVariable getDependentDataVariable() {
        return this.dependentDataVariable;
    }

    public HashSet<DataVariable> getIndependentDataVariables() {
        return new HashSet<>(this.independentDataVariables);
    }

    public void add(DataVariable x) {
        this.independentDataVariables.add(x);
    }

    public TreeSet<DataVariable> getOrderedSetOfDependentDataVariables() {
        return new TreeSet<>(getIndependentDataVariables());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Function) {
            Function f = (Function) o;

            if (f.getDependentDataVariable().getId() == this.getDependentDataVariable().getId()
                    && f.getIndependentDataVariables().equals(this.getIndependentDataVariables())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 41 * hash + Objects.hashCode(this.dependentDataVariable);
        hash = 41 * hash + Objects.hashCode(this.independentDataVariables);

        return hash;
    }
}
