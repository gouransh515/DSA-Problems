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
    int visited =-1;
    ArrayList<Integer> ll=new ArrayList<>();
    public List<Integer> rightSideView(TreeNode root) {
       int level=0;
     view(root,level);
     return ll;
    }
    public  void view(TreeNode root,int level){
 
        if(root==null){
            return ;
        }
        if(visited<level){
            ll.add(root.val);
            visited++;
        }
        view( root.right,level+1);
        view( root.left,level+1);
        
    }}
