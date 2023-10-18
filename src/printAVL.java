public class printAVL<T extends Comparable<T>>{
    /**
     * Print Binary Tree in 2-Dimensions
     * @author Arnab Kundu
     * source: https://www.geeksforgeeks.org/print-binary-tree-2-dimensions/
     * 17 Oct, 2023
     */
    
    //BY LEVEL print methods
    static final int COUNT = 10;
    AVLTree auxTree = new AVLTree(null);

    // Wrapper over print2DUtil()
	public void print2D(AVLNode<T>  root)
	{
		// Pass initial space count as 0
		print2DUtil(root, 0);
	}

	// Function to print binary tree in 2D
	// It does reverse inorder traversal
	public void print2DUtil(AVLNode<T> root, int space)
	{
		// Base case
		if (root == null)
			return;

		// Increase distance between levels
		space += COUNT;

		// Process right child first
		print2DUtil(root.getRight(), space);

		// Print current node after space
		// count
		System.out.print("\n");
		for (int i = COUNT; i < space; i++)
			System.out.print(" ");
		System.out.print(root.getElem().toString() + "(" + auxTree.calcBF(root) + ")" + "\n");

		// Process left child
		print2DUtil(root.getLeft(), space);
	}
}
