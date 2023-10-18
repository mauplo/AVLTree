
/**
 * @author Mau PLO
 * An AVL Tree is a self-balancing BST, 
 * where the height difference of any two child subtrees is at most 1.
 * AVL trees have O(nlogn) time for search, insertion, and deletion 
 */

 public class AVLTree<T extends Comparable<T>>{
    //the root is the top node of an AVL Tree
    private AVLNode<T> root;

    //constructor
    public AVLTree (T elem){
        this.root = new AVLNode<T>(elem);
    }

    //HEIGHT
    //method to calculate tree height = max(height(left), height(right)) + 1 
    public int treeHeight(AVLNode<T> current){
        if (current == null){
            return 0;
        }
        //recursive calls to count the descendant nodes of the left and right subtrees
        int bF1 = treeHeight(current.getLeft()) + 1;
        int bF2 = treeHeight(current.getRight()) + 1;
        return Math.max(bF1, bF2);
    }

    //BALANCE FACTOR
    //method to calculate subTree BF = height right subtree - height left subtree
    public int calcBF(AVLNode<T> current){
        AVLNode<T> subRight = current.getRight();
        AVLNode<T> subLeft = current.getLeft();
        return treeHeight(subRight) - treeHeight(subLeft);
    }

    //SEARCH
    public AVLNode<T> search(T elem){
        //we start looking at the root
        AVLNode<T> current = this.root; 
        boolean flag = false;
        while (current != null && flag ==false){
            //we make comparisons to find the element
            if(current.getElem().compareTo(elem) == 0){
                flag = true;
            }else
            if(elem.compareTo(current.getElem()) <= 0){
                current = current.getLeft();
            }else {
                current = current.getRight();
            }
        }
        return current;
    }

    //INSERTION
    //the insertion method uses auxilliary rotation methods later in the code
    public void add(T elem){
        //if it's empty
        if (root == null){
            root = new AVLNode<T>(elem);
            return;
        }
        //insertion as BST
        AVLNode<T> current = root;
        AVLNode<T> parent = root;
        AVLNode<T> newN = new AVLNode(elem);
        while (current != null) {
            parent = current;
            if (elem.compareTo(current.getElem()) <= 0) 
                current = current.getLeft();
            else 
                current = current.getRight(); 
        }
        parent.hang(newN);
        //we need to update the bF of the branch our node was added to, and rotate if necesarry
        current = newN;
        boolean flag = false;
        while (!flag && parent != null){
            parent.setbF(calcBF(parent)); //new balance factor
            //if bF is 0, the branch is balanced
            if (parent.getbF() == 0) 
                flag = true;
            //if not, the branch requires 1 of 4 rotations
            if (parent.getbF() == 2)
                if (current.getbF() >= 0)
                    parent = rotateRR(parent);
                else
                    parent = rotateRL(parent);
            
            if (parent.getbF() == -2)
                if (current.getbF() <= 0)
                    parent = rotateLL(parent);
                else
                    parent = rotateLR(parent);
            
            current = parent;
            parent = parent.getParent();
        }
    }

    //DELETION
    public T delete (T elem){
        //deletion like BST

        //we search for the node
        AVLNode<T> current = search(elem);

        //exception: node is not in the tree
        if (current == null) 
            throw new RuntimeException();


        AVLNode<T> parent = current.getParent();
        //node that we need to balance
        AVLNode<T> temp;
        T res = current.getElem();

        //There are 3 cases that should be adressed during deletion
        //CASE 1: the node to be deleted doesn´t have children
        if (current.getLeft() == null && current.getRight() == null) {
            //sub cases: the node the root, the node is a right child, the node is a left child
            if (current == root) 
                root = null;
            if ((res.compareTo(current.getParent().getElem())) > 0) 
                parent.setRight(null);
            else 
                parent.setLeft(null);
            current.setParent(null);
            temp = parent; 
        }
      
        //CASE 2: the node to be deleted has 1 child
        if (current.getLeft() == null || current.getRight() == null) {
            AVLNode<T> child;
            if (current.getLeft() == null) 
                child = current.getRight();
            else 
                child = current.getLeft();
            
            if (current.equals(root)) 
                root = child;
            else 
                parent.hang(child);
            current.setParent(null);
            temp = parent; 
        }

        //CASE 3: the node to delete has 2 children
        else {
            AVLNode<T> aux = current.getRight();
            while (aux.getLeft() != null) 
                aux = aux.getLeft(); 
            current.setElem(aux.getElem());
            //sub case: aux is not right child of current
            if (aux != current.getRight()){ 
                temp = aux.getParent();
                //subsubcase: aux has no children
                if (aux.getRight() == null) {
                    aux.getParent().setLeft(null);
                    aux.setParent(null);
                }
                else{ 
                    //subsubcase: aux has a right child 
                    aux.getParent().hang(aux.getRight()); 
                    aux.setParent(null);
                }
            }
            //subcase: aux is right child of current
            else{ 
                temp = current;
                //subsubcase: aux has no children
                if (aux.getRight() == null){ 
                    current.setRight(null);
                    aux.setParent(null);
                }
                else{
                    //subsubcase: aux has a right child 
                    aux.getRight().setParent(current.getParent());
                    aux.getParent().setRight(aux.getRight());
                    aux.setRight(null);
                    aux.setParent(null);
                }
            }
        }

        //deletion for AVL trees
        boolean flag = false;
        AVLNode<T> papaTemp = temp;
        while (!flag && papaTemp != null){
            papaTemp.setbF(calcBF(papaTemp));
            if (papaTemp.getbF() == 1 || papaTemp.getbF() == -1)
                flag = true;
            if (papaTemp.getbF() == 2)
                if (current.getbF() >= 0)
                    papaTemp = rotateRR(papaTemp);
                else
                    papaTemp = rotateRL(papaTemp);
            
            if (papaTemp.getbF() == -2)
                if (current.getbF() <= 0)
                    papaTemp = rotateLL(papaTemp);
                else
                    papaTemp = rotateLR(papaTemp);
            
            temp = papaTemp;
            papaTemp = papaTemp.getParent();
        }
        return res;
    }
    //ROTATIONS
    //there are four types of rotations that help us keep the tree balanced 
    //we will use alpha, beta, gamma, A, B, C, D as auxiliary variables to rotate

    //Left-Left (LL)
    /** bF(current) = -2, bF(left) = -1 or 0
    * 
    *               alpha                               beta
    *              /    \                            /       \ 
    *            beta     D         LL ->        gamma        alpha
    *           /    \                          /     \      /     \ 
    *       gamma     C                        A       B    C       D
    *       /    \ 
    *      A      B
    * 
    */
    private AVLNode<T> rotateLL(AVLNode<T> current){
        AVLNode<T> alpha = current;
        AVLNode<T> beta = alpha.getLeft();
        AVLNode<T> C = beta.getRight();
        
        if (alpha == this.root)
            this.root = beta;
        
        beta.setParent(alpha.getParent());
        alpha.setParent(beta);
        alpha.setLeft(C);
        
        if (C != null)
            C.setParent(alpha);;
      
        beta.setRight(alpha);
        
        if (beta.getParent() != null) 
            if (beta.getParent().getLeft() == alpha) 
                beta.getParent().setLeft(beta);
            else 
                beta.getParent().setRight(beta);
        
        alpha.setbF(calcBF(alpha));
        beta.setbF(calcBF(beta));
        
        return beta;
    }
    //Left-Right (LR)
    /** bF(cuurent) = -2, bF(left) = 1
    * 
    *               alpha                              gamma
    *              /    \                            /       \ 
    *            beta    D          LR ->        beta        alpha
    *           /    \                          /    \      /     \ 
    *          A   gamma                       A      B    C       D
    *              /    \ 
    *             B      C
    * 
    */
    private AVLNode<T> rotateLR(AVLNode<T> current){
        AVLNode<T> alpha = current;
        AVLNode<T> beta = alpha.getLeft();
        AVLNode<T> gamma = beta.getRight();
        AVLNode<T> B = gamma.getLeft();
        AVLNode<T> C = gamma.getRight();
        
        if (alpha == this.root)
            this.root = gamma;
        
        gamma.setParent(alpha.getParent());
        
        if (B != null)
            B.setParent(beta);
        
        beta.setRight(B);
        
        if (C != null)
            C.setParent(alpha);
        
        alpha.setLeft(C);
        alpha.setParent(gamma);
        beta.setParent(gamma);
        gamma.setLeft(beta);
        gamma.setRight(alpha);
        
        if (gamma.getParent() != null) 
            if (gamma.getParent().getLeft() == alpha) 
                gamma.getParent().setLeft(gamma);
            else 
                gamma.getParent().setRight(gamma);
            
        alpha.setbF(calcBF(alpha));
        beta.setbF(calcBF(beta));
        gamma.setbF(calcBF(gamma));
        
        return gamma; 
    }

    //Right-Right (RR)
    /** bF(current) = 2, bF(right) = 1 or 0
    * 
    *               alpha                               beta
    *              /    \                            /        \ 
    *             A     beta         RR ->        alpha        gamma
    *                  /    \                    /     \      /     \ 
    *                 B    gamma                A       B    C       D
    *                      /    \ 
    *                     C      D
    * 
    */
    private AVLNode<T> rotateRR(AVLNode<T> current){
        AVLNode<T> alpha = current;
        AVLNode<T> beta = alpha.getRight();
        AVLNode<T> B = beta.getLeft();
        
        if (alpha == this.root)
            this.root = beta;
        
        beta.setParent(alpha.getParent());
        alpha.setParent(beta);
        
        if (B != null)
            B.setParent(alpha);
        
        alpha.setRight(B);
        beta.setLeft(alpha);
        
        if (beta.getParent() != null) 
            if (beta.getParent().getLeft() == alpha) 
                beta.getParent().setLeft(beta);
            else 
                beta.getParent().setRight(beta);

        alpha.setbF(calcBF(alpha));
        beta.setbF(calcBF(beta));
        
        return beta;
    }
    //Right-Left (RL)
    /** bF(current) = 2, bF(right) = -1
    * 
    *               alpha                               gamma
    *              /    \                            /        \ 
    *             A     beta         RL ->        alpha        beta
    *                  /    \                    /     \      /     \ 
    *               gamma    D                  A       B    C       D
    *               /    \ 
    *              B      C
    * 
    */
    private AVLNode<T> rotateRL(AVLNode<T> current){
        AVLNode<T> alpha = current;
        AVLNode<T> beta = alpha.getRight();
        AVLNode<T> gamma = beta.getLeft();
        AVLNode<T> B = gamma.getLeft();
        AVLNode<T> C = gamma.getRight();

        if (alpha == this.root)
            this.root = gamma;
        
        gamma.setParent(alpha.getParent());
        
        if (B != null)
            B.setParent(alpha);
        
        alpha.setRight(B);
        
        if (C != null)
            C.setParent(beta);
        
        beta.setLeft(C);
        gamma.setRight(beta);
        gamma.setLeft(alpha);
        alpha.setParent(gamma);
        beta.setParent(gamma);
        
        if (gamma.getParent() != null)
            if (gamma.getParent().getLeft() == alpha) 
                gamma.getParent().setLeft(gamma);
            else 
                gamma.getParent().setRight(gamma);

        alpha.setbF(calcBF(alpha));
        beta.setbF(calcBF(beta));
        gamma.setbF(calcBF(gamma));
        
        return gamma;
    }
    //getters
    public AVLNode<T> getRoot() {
        return root;
    }

	
}

