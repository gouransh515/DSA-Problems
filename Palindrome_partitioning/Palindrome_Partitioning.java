package BackTracking;

public class Palindrome_Partitioning {

	public static void main(String[] args) {
		String s="nitin";
		partitioning("",s);

	}
public static void partitioning (String ans,String ques) {
	if(ques.length()==0) {
		System.out.println(ans);
	}
	for(int i=0;i<ques.length();i++) {
		String s=ques.substring(0,i+1);
		if(palindrome(s)==true) {
		partitioning(ans+s+"|",ques.substring(i+1));}
		
	}}
	private static boolean palindrome(String x) {
		// TODO Auto-generated method stub
		int i=0;int j=x.length()-1;
		while(i<j){
			if(x.charAt(i)!=x.charAt(j)) {
				return false;
			}
			i++;j--;
		}
		return true;
	
}
}
