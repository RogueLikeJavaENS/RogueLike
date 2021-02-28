package utils;

public class Check {

    public static int checkPositivity(int i) {
        if (i < 0){
            throw new IllegalArgumentException("The number is negative and should be positive");
        }
        else {
            return i;
        }
    }

}
