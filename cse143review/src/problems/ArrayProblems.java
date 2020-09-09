package problems;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        StringBuilder ret = (new StringBuilder()).append("[");
        if (array.length > 0) {
            ret.append(array[0]);
            String sep = ", ";
            for (int i = 1; i < array.length; i++) {
                ret.append(sep).append(array[i]);
            }
        }
        ret.append("]");
        return ret.toString();
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    public static int[] reverse(int[] array) {
        int length = array.length;
        int[] ret = new int[length];
        for (int i = length; i > 0; i--) {
            ret[length - i] = array[i - 1];
        }
        return ret;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        int length = array.length;
        if (length > 1) {
            int temp = array[length - 1];
            for (int i = length - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            // System.arraycopy(array, 0, array, 1, length - 2);
            array[0] = temp;
        }
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
