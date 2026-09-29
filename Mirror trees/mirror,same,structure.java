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
	  private boolean same(node root2, node root3) {
		// TODO Auto-generated method stub
		 if(root2==null&&root3==null) {
				return true;
			}
		 if(root2==null||root3==null) {
			 return false;
		 }
		if(root2.data!=root3.data) {
			return false;
		}
		boolean b=same(root2.left,root3.left);
		boolean c=same(root2.right,root3.right);
		return b&c;
	 }
	 private boolean structure(node root2, node root3) {
		 if(root2==null&&root3==null) {
				return true;
			}
		 if(root2==null||root3==null) {
			 return false;
		 }
		
		boolean b=structure(root2.left,root3.left);
		boolean c=structure(root2.right,root3.right);
		return b&c;
	 }