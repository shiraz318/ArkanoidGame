package components;
/**
 * The type Counter.
 */
public class Counter {
    private int count;

    /**
     * Construct a new Counter.
     *
     * @param number the number
     */
    public Counter(int number) {
        this.count = number;
    }

    /**
     * Add number to current count.
     *
     * @param number the number
     */
    public void increase(int number) {
        count += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        count -= number;
    }

    /**
     * Get current count.
     *
     * @return the value
     */
    public int getValue() {
        return count;
    }
}