package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode temp = list.front.next.next;
        temp.next = list.front.next;
        temp.next.next = list.front;
        list.front.next = null;
        list.front = temp;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) {
            ListNode temp = list.front;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = list.front;
            list.front = temp.next.next;
            temp.next.next = null;
        }
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        if (a.front == null) {
            return b;
        }
        LinkedIntList ret = new LinkedIntList(a.front.data);
        ListNode temp = a.front;
        ListNode retTemp = ret.front;
        while (temp.next != null) {
            retTemp.next = new ListNode(temp.next.data);
            retTemp = retTemp.next;
            temp = temp.next;
        }
        if (b.front != null) {
            temp = b.front;
            retTemp.next = new ListNode(temp.data);
            retTemp = retTemp.next;
            while (temp.next != null) {
                retTemp.next = new ListNode(temp.next.data);
                retTemp = retTemp.next;
                temp = temp.next;
            }
        }
        return ret;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
}
