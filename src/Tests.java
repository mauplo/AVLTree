public class Tests {
    public static void printDivider(){
        System.out.println();
        for(int i=0; i<59; i++){
            System.out.print("-");
            System.out.print(".");
        }
        System.out.println();
    }
    public static void main(String[] args) throws Exception {
        AVLTree<Integer> a1 = new AVLTree(0);
        printAVL treePrinter = new printAVL<>();

        System.out.println();
        System.out.println("INSERTION (0,2,4,5)");
        a1.add(2);
        a1.add(4);
        a1.add(5);
        treePrinter.print2D(a1.getRoot());
        printDivider();
        System.out.println("DELETION (2)");
        a1.delete(2);
        treePrinter.print2D(a1.getRoot());
        printDivider();
        System.out.println("SEARCH (0,7)");
        System.out.println();
        System.out.print("node: " + a1.search(0));
        if (a1.search(0) != null){
            System.out.print(", parent: " +a1.search(0).getParent());
        }else{
            System.out.print(", parent: " + null);
        }
        System.out.println();
        System.out.print("node: " + a1.search(7));
        if (a1.search(7) != null){
            System.out.print(", parent: " +a1.search(7).getParent());
        }else{
            System.out.print(", parent: " + null);
        }
        System.out.println();
        printDivider();
    }
}
