package problems;

import datastructures.IntTree;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.IntTree.IntTreeNode;

/**
 * See the spec on the website for tips and example behavior.
 *
 * Also note: you may want to use private helper methods to help you solve these problems.
 * YOU MUST MAKE THE PRIVATE HELPER METHODS STATIC, or else your code will not compile.
 * This happens for reasons that aren't the focus of this assignment and are mostly skimmed over in
 * 142 and 143. If you want to know more, you can ask on the discussion board or at office hours.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `IntTree` objects
 * - do not construct new `IntTreeNode` objects (though you may have as many `IntTreeNode` variables
 *      as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the tree only by modifying
 *      links between nodes.
 */

public class IntTreeProblems {

    /**
     * Computes and returns the sum of the values multiplied by their depths in the given tree.
     * (The root node is treated as having depth 1.)
     */
    public static int depthSum(IntTree tree) {
        IntTreeNode root = tree.overallRoot;
        return (root == null) ? 0 : root.data + depthSum(root.left, 2) + depthSum(root.right, 2);
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    private static int depthSum(IntTreeNode root, int depth) {
        return (root == null) ? 0 : root.data * depth
            + depthSum(root.left, depth + 1)
            + depthSum(root.right, depth + 1);
    }

    /**
     * Removes all leaf nodes from the given tree.
     */
    public static void removeLeaves(IntTree tree) {
        IntTreeNode root = tree.overallRoot;
        if (root != null) {
            if (root.left == null && root.right == null) {
                tree.overallRoot = null;
            } else {
                root.left = removeLeaves(tree.overallRoot.left);
                root.right = removeLeaves(tree.overallRoot.right);
            }
        }
    }

    private static IntTreeNode removeLeaves(IntTreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                return null;
            }
            root.left = removeLeaves(root.left);
            root.right = removeLeaves(root.right);
        }
        return root;
    }

    /**
     * Removes from the given BST all values less than `min` or greater than `max`.
     * (The resulting tree is still a BST.)
     */
    public static void trim(IntTree tree, int min, int max) {
        if (tree.overallRoot != null) {
            if (tree.overallRoot.data < min) {
                tree.overallRoot = trim(tree.overallRoot.right, min, max);
            } else if (tree.overallRoot.data > max) {
                tree.overallRoot = trim(tree.overallRoot.left, min, max);
            } else {
                tree.overallRoot.right = trim(tree.overallRoot.right, min, max);
                tree.overallRoot.left = trim(tree.overallRoot.left, min, max);
            }
            //throw new UnsupportedOperationException("Not implemented yet.");
        }
    }

    private static IntTreeNode trim(IntTreeNode root, int min, int max) {
        if (root != null) {
            if (root.data < min) {
                return trim(root.right, min, max);
            } else if (root.data > max) {
                return trim(root.left, min, max);
            } else {
                root.right = trim(root.right, min, max);
                root.left = trim(root.left, min, max);
            }
        }
        return root;
    }
}
