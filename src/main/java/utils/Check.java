package utils;

public class Check {

    /**
     * Prevents a value from being negative
     * @param i value to check
     * @return the value if it is positive, 0 otherwise
     */
    public static int checkPositivity(int i) {
        return Math.max(i, 0);
    }

}
