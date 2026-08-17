package BackTracking;

public class queens_and_boxes_variation {

	public static void main(String[] args) {
		int b=4;
		boolean [] box=new boolean[b];
		int n=2;
		print (box,n,0,"",0);
	}

	private static void print(boolean[] box, int tq, int qpsf, String ans,int idx) {
		if(tq==qpsf) {
			System.out.println(ans);
			return;
		}
		for(int i=idx;i<box.length;i++) {
			//if(box[i]==false) {
				//box[i]=true;
			
			print(box,tq,qpsf+1,ans+"b"+i+"q"+qpsf,i +1);
			//box[i]=false;
		}
	}
}
