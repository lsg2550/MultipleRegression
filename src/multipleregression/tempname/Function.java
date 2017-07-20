package multipleregression.tempname;

import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Luis
 */
class Function {

    private final DataVariable dependentVariable;
    private final HashSet<DataVariable> independentVariables;

    Function(DataVariable dependentVariable) {
        this.dependentVariable = dependentVariable;
        this.independentVariables = new HashSet<>();
    }

    Function(DataVariable dependentVariable, HashSet<DataVariable> independentDataVariables) {
        this.dependentVariable = dependentVariable;
        this.independentVariables = independentDataVariables;
    }

    DataVariable getDependentVariable() {
        return dependentVariable;
    }

    HashSet<DataVariable> getIndependentVariables() {
        return independentVariables;
    }

    void addIndependentVariable(DataVariable x) {
        this.independentVariables.add(x);
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
