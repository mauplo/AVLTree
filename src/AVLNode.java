/**
 * @author Mau PLO
 * AVLNode is the node used for AVL trees. 
 */

public class AVLNode<T extends Comparable<T>>{
    //element, also known as key
    private T elem; 
    //AVL nodes can have up to 3 connected nodes (at most two children and one parent)
    private AVLNode<T> left, right, parent;
    //balance factor = height right sub tree - height of left subtree. Between 0 and |2|
    private int bF; 

    //constructor
    public AVLNode (T elem){
        this.elem = elem;
        this.right = null;
        this.left = null;
        this.parent = null;
        this.bF = 0;
    }
    //auxilliary method to attach a descendant node to a new parent
    public void hang(AVLNode<T> child){
        if (child == null){
            return;
        }
        //smaller elements hang to the left
        if (child.getElem().compareTo(elem)<=0){ 
            this.left = child;
        }
        //bigger elements hang to the right
        else{
            this.right = child;
        }
        //the child node must be attatched to its parent
        child.setParent(this); 
    }

    //to print a node we just want the element
    public String toString(){
        return this.elem.toString();
    }

    //getters
    public T getElem() {
        return elem;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public AVLNode<T> getParent() {
        return parent;
    }

    public int getbF() {
        return bF;
    }
    
    //setters
    public void setElem(T elem) {
        this.elem = elem;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public void setParent(AVLNode<T> parent) {
        this.parent = parent;
    }

    public void setbF(int bF) {
        this.bF = bF;
    }

    
}