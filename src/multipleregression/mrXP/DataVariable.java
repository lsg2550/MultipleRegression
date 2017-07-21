package multipleregression.mrXP;

/**
 *
 * @author Luis
 */
class DataVariable implements Comparable {

    private final int id;
    private final double[] array;
    private double parameter;

    DataVariable(int id) {
        this.id = id;
        this.array = null;
    }

    DataVariable(int id, double[] array) {
        this.id = id;
        this.array = array;
    }

    DataVariable(int id, double[] array, double parameter) {
        this.id = id;
        this.array = array;
        this.parameter = parameter;
    }

    int getId() {
        return id;
    }

    double[] getArray() {
        return array;
    }

    double getParameter() {
        return parameter;
    }

    void setParameter(double parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof DataVariable) {
            DataVariable other = (DataVariable) o;
            if (this.id == other.getId()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "X" + this.id;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof DataVariable) {
            DataVariable other = (DataVariable) o;

            if (this.getId() == other.getId()) {
                return 0;
            }

            if (this.getId() > other.getId()) {
                return 1;
            }
        }

        return -1;
    }
}
