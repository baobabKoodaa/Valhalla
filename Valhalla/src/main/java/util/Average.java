package util;

/**
 * Average.
 */
public class Average {
    private long sum;
    private long count;

    public Average() {
        this.sum = 0;
        this.count = 0;
    }

    public void addInstance(long value) {
        sum += value;
        count++;
    }

    public double getAverage() {
        if (count == 0) {
            return 0;
        }
        return 1.0 * sum / count;
    }

}
