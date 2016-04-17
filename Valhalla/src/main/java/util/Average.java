package util;

/**
 * Average.
 */
public class Average {
    private long sum;
    private long count;

    /**
     * Constructor, initializes object.
     */
    public Average() {
        this.sum = 0;
        this.count = 0;
    }

    /**
     * Adds instance.
     * @param value value of the instance to add
     */
    public void addInstance(long value) {
        sum += value;
        count++;
    }

    /**
     * Get average of instances.
     * @return average
     */
    public double getAverage() {
        if (count == 0) {
            return 0;
        }
        return 1.0 * sum / count;
    }

}
