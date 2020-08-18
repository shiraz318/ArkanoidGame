package components;
/**
 * The type Counter.
 */
public class Counter {
    //fields
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
        this.count += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Get current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.count;
    }
}