/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
       Diapair d=diameter(root);
       return d.dia;
    }
    class Diapair{
        int dia=0;
        int h=-1;
    }
    public Diapair diameter (TreeNode root){
        if(root==null){
            return new Diapair();
        }
        Diapair ld=diameter(root.left);
        Diapair rd=diameter(root.right);
        Diapair sdp=new Diapair();
        int sd=ld.h+rd.h+2;
        sdp.dia=Math.max(sd,Math.max(ld.dia,rd.dia));
        sdp.h=Math.max(ld.h,rd.h)+1;
        return sdp;}

    }
