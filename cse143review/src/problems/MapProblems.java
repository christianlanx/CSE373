package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String str : list) {
            map.put(str, (map.containsKey(str) ? map.get(str) + 1 : 1));
        }
        return map.containsValue(3);
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> intersect = new HashMap<>();
        for (String key : m1.keySet()) {
            if (m2.containsKey(key)) {
                Integer i = m1.get(key);
                if (m2.get(key).equals(i)) {
                    intersect.put(key, i);
                }
            }
        }
        return intersect;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
