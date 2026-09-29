 private boolean mirror(node root2,node root3) {
		 if(root2==null&&root3==null) {
				return true;
			}
		 if(root2==null||root3==null) {
			 return false;
		 }
		if(root2.data!=root3.data) {
			return false;
		}
		boolean b=mirror(root2.left,root3.right);
		boolean c=mirror(root2.right,root3.left);
		return b&c;
		
	 }