# AVL TREE 

## Implementation of the binary search tree known as AVL. 

This java project is one of the structures studied at my Advanced Data Structures class. Included in the source package:

* AVLNode
    * AVLNode is the node used for AVL trees.
    * They carry an element, also known as key
    * AVL nodes can have up to 3 connected nodes (at most two children and one parent)
    * They also carry abalance factor = height right sub tree - height of left subtree. Between 0 and |2|
* AVLTree
    * An AVL Tree is a self-balancing BST,
    * where the height difference of any two child subtrees is at most 1.
    * AVL trees have O(nlogn) time for search, insertion, and deletion
* Tests 
    * Class that can be used to print test trees
* printAVL
    * Print Binary Tree in 2-Dimensions by Arnab Kundu
    * Used for tests of the tree implementation
    * Slight modification to indclude the balance factor when printing the tree

Note: this implementation is probably not the standard.
