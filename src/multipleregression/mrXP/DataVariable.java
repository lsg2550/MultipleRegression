package multipleregression.mrXP;

/**
 *
 * @author Luis
 */
class DataVariable {

    private final int id;
    private final double[] array;

    DataVariable(int id) {
        this.id = id;
        this.array = null;
    }

    DataVariable(int id, double[] array) {
        this.id = id;
        this.array = array;
    }

    int getId() {
        return id;
    }

    double[] getArray() {
        return array;
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
}
