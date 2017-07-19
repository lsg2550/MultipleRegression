package multipleregression.tempname;

/**
 *
 * @author Luis
 */
public class DataVariable {

    private final int id;
    private final double[] array;

    public DataVariable(int id, double[] array) {
        this.id = id;
        this.array = array;
    }

    public int getId() {
        return id;
    }

    public double[] getArray() {
        return array;
    }

}
