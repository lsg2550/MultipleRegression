package utils.math;

/**
 *
 * @author Luis
 */
public class Normalize {

    public static double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static double deNormalize(double normalizedValue, double min, double max) {
        return (normalizedValue * (max - min)) + min;
    }

}
