package BackTracking;

public class Coin_Permutation {
//the question is that a amount is given and a array of coins is given (you can use each coin infinitely)and you have to print all ways that sum up to that amount by picking one coin at a time .
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] coin= {1,2,3,4};
		int amount=6;
		print(coin,amount," ");

	}

public static void print(int[] coin, int amount, String string) {
	// TODO Auto-generated method stub
	if(amount==0) {
		System.out.println(string);
		return;
	}
	for(int i=0;i<coin.length;i++) {
		if(amount>=coin[i]) {
		//with backtracking
		
		//amount=amount-coin[i];
		//print(coin,amount,string+coin[i]);
		//amount+=coin[i];
			//without backtracking
			print(coin,amount-coin[i],string+coin[i]);
	}}
	
}

}
