package utils.operations;

/**
 *
 * @author Anton Kovalyov
 */
public class DataVariable implements Comparable {

    private final int id;
    private final double[] array;
    private double parameter;

    public DataVariable(int id) {
        this.id = id;
        this.array = null;
    }

    public DataVariable(int id, double[] array) {
        this.id = id;
        this.array = array;
    }

    public DataVariable(int id, double[] array, double parameter) {
        this.id = id;
        this.array = array;
        this.parameter = parameter;
    }

    public int getId() {
        return id;
    }

    public double[] getArray() {
        return array;
    }

    public double getParameter() {
        return parameter;
    }

    public void setParameter(double parameter) {
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
