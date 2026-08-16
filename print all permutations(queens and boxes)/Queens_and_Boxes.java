package BackTracking;

public class Queens_and_Boxes {

	public static void main(String[] args) {
		int b=4;
		boolean [] box=new boolean[b];
		int n=2;
		print (box,n,0,"");
	}

	private static void print(boolean[] box, int tq, int qpsf, String ans) {
		if(tq==qpsf) {
			System.out.println(ans);
			return;
		}
		for(int i=0;i<box.length;i++) {
			if(box[i]==false) {
				box[i]=true;
			
			print(box,tq,qpsf+1,ans+"b"+i+"q"+qpsf);
			box[i]=false;
		}}
		
	}

}
