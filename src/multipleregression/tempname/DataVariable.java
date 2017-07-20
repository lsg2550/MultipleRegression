package multipleregression.tempname;

/**
 *
 * @author Luis
 */
class DataVariable {

    private final int id;
    private final double[] array;

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

}
