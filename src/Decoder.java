/*======================================================================================
 |         Author: Jacob Knorr
 |         Course: CSc 127B
 |     Assignment: Program #11: Decoding Prefix Codes
 |     Instructor: L. McCann
 |
 |    Description: This program is given a text file with three lines; the first two being 
 |                 preorder and inorder traversals of integers, and the third being a encoded
 |                 sequence of values containing 0's and 1's. Using the preorder and inorder
 |                 traversals, a decoding tree is made, which is used to decode the third line
 |                 of text in the file representing bits. Creating the binary tree is done with 
 |                 recursion. The output includes the postorder traversal and the decoded sequence  
 |                 of values.
 |                 
 |      Operation: This is a java program that uses a TreeNode class to hold the content of 
 |                 the tree. When the program is run, the user will be asked to enter a file 
 |                 whose content will be used to create the tree and display the output. The
 |                 TreeNode class is included at the bottom of Decoder.
 |
 *========================================================================================*/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Decoder {
    
    static int preLocation = 0;
    
    public static void main (String [] args) {
     
     Scanner input, reader = null;
     String fileName;
     File inFile = null;
        
     input = new Scanner(System.in);
     System.out.print("Please enter the name of the file.");
     fileName = input.next();
     
     inFile = new File(fileName);  
     
     try {
         reader = new Scanner(inFile);   
     }
     catch (FileNotFoundException e) {
         e.printStackTrace();
     }
     //removes space at beginning and end of string
     String strPre = reader.nextLine().trim();  
     String strIn = reader.nextLine().trim();      
     String strSeq = reader.nextLine();   
   
     //removes in-between space and puts individual
     //integers into their respective arrays
     String[] arrPre = strPre.split("\\s+");
     String[] arrIn = strIn.split("\\s+");
     
     //String[] to int[]
     int[] preorder = new int[arrPre.length];
     int[] inorder = new int[arrIn.length];
     for (int i = 0; i < arrPre.length; i++) {
         preorder[i] = Integer.parseInt(arrPre[i]);
         inorder[i] = Integer.parseInt(arrIn[i]);
     }
     
     Decoder q = new Decoder(); 
     TreeNode<Integer> node = q.decodingTree(preorder, inorder);
     
     System.out.print("\nPostorder Traversal: ");
     q.postorder(node);
     System.out.print("\nDecoded Sequence: ");
     q.decodingSequence(node, strSeq);
     
    }  //main
    
    
    //"Store-front" method, sends parameters to private decodingTree method
    public  TreeNode<Integer> decodingTree(int[] pre, int[] in) {
        
        return decodingTree(pre, in, 0, in.length-1);
    }
    
    /*
     *  decodingTree: Constructs a binary tree recursively given the inorder and preorder
     *  traversals. The resulting tree will be used to decode the 'bit'
     *  sequence.
     *     
     *  Returns: TreeNode<Integer> root
     */
    private TreeNode<Integer> decodingTree(int[] pre, int[] in, int iFirst, int iLast) {
                                    
        if (iFirst > iLast) {
            return null;
        }
        TreeNode<Integer> root = new TreeNode<Integer>(pre[preLocation]);
        preLocation++;
        
        if (iFirst == iLast) {
            return root;
        }
        int index = inLocation(in, iFirst, iLast, root.data); //root location in int[]in
        
        root.left = decodingTree(pre, in, iFirst, index-1);
        root.right = decodingTree(pre, in, index+1, iLast);
        
        return root;
    }
    
    /*
     *  inLocation -- Used from within the decodingTree method to find the location
     *  of the root within the inorder traversal array.
     *     
     *  Returns: index of root if root is found, -1 otherwise
     */
    public int inLocation(int[] in, int iFirst, int iLast, int value) {
        
        for (int i = iFirst; i <= iLast; i++) {
            if (in[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    
    // postorder -- prints the postorder traversal from the previously constructed binary tree.
    public void postorder(TreeNode<Integer> root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
    }
    

    // decodingSequence -- iteratively decodes and prints the third 
    // line of the text file 
    public void decodingSequence(TreeNode<Integer> node, String seq) {
        
        TreeNode<Integer> root = node;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '0' && root != null) {
                root = root.left;
                if (root.left == null && root.right == null) {
                    System.out.print(root.data);
                    root = node;
                } 
            }
            if (seq.charAt(i) == '1' && root != null) {
                root = root.right;
                if (root.left == null && root.right == null) {
                    System.out.print(root.data);
                    root = node;
                }
            } 
        }
    }

    /*-----------------------------------------------------------------------------
     * TreeNode<E> represents the nodes of the binary tree that will be
     * used in Prog11. Instance variables TreeNode<E> left and TreeNode<E> right
     * represent the left and right children of the tree and E data represents the
     * data each node holds.
     *-----------------------------------------------------------------------------*/
    class TreeNode<E> {
        
        E data;
        TreeNode<E> left;
        TreeNode<E> right;
        
        public TreeNode (E newData) {
            this.data = newData;
            left = right = null;
        }
    }

}  // Decoder



