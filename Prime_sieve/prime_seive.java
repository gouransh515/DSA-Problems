package imp;

public class prime_seive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=100;
		boolean[] a=new boolean[n+1];
		for(int i=2;i<a.length;i++) {
			a[i]=true;
		}
		for(int i=2;i*i<a.length;i++) {
			if(a[i]==true) {
			for(int j=i*i;j<=a.length;j+=i) {
				a[j]=false;
			}}
		}
		for(int i=2;i<a.length;i++) {
			if(a[i]==true) {
			System.out.println(i);}
		}

	}

}
