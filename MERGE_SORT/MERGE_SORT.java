package IMPORTANT;
import java.util.*;
public class MERGE_SORT {
	public static void main(String args[]) {
		int[]a= {8,1,6,4,7,9,3};
		System.out.println(sort(a, 0, a.length-1));
	}

	private static int [] sort(int[] a, int si,int ei) {
		if(si==ei) {
			int[]b=new int[1];
			b[0]=a[si];
			return b;
		}
		int mid=si+(ei-si)/2;
		int[] fs= sort(a,si,mid);
		int[] ss= sort(a,mid+1,ei);
		return merge(fs,ss);
	}
	public static int[] merge(int[] a,int[] b) {
		int i=0;int j=0;int k=0;
		int[] c=new int[a.length+b.length];
		
		while(i<a.length&&j<b.length) {
			if(a[i]>=b[j]) {
				c[k]=b[j];
				j++;k++;
			}
			else {
				c[k]=a[i];
				i++;
				k++;
			}
		}
		for(int d=i;d<a.length;d++) {
			c[k]=a[d];
			k++;
		}
		for(int d=j;d<b.length;d++) {
			c[k]=b[d];
			k++;
		}
		return c;
	}

}
