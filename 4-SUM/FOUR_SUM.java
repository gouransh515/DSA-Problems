class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ll=new ArrayList<>();
	        ArrayList<Integer> l1=new ArrayList<>();
	      Arrays.sort(nums);
	      for(int i=0;i<nums.length-3;i++){
             if(i>0&&nums[i]==nums[i-1]){
                continue;
            }
	       for(int j=i+1;j<nums.length-2;j++){
            if(j>i+1&&nums[j]==nums[j-1]){
                continue;
            }
            int a=j+1,b=nums.length-1;
            long d=(long)target-((long)nums[i]+(long)nums[j]);
            while(a<b){
                 if(a>j+1&&nums[a]==nums[a-1]){
                   a= a+1;
                continue;
            }
            if(b<nums.length-1&&nums[b]==nums[b+1]){
                   b= b-1;
                continue;
            }
                if((long)(nums[a]+nums[b])==d){
                    l1.add(nums[i]);
                    l1.add(nums[j]);
                    l1.add(nums[a]);
                    l1.add(nums[b]);
                    ll.add(new ArrayList<>(l1));
                    l1.clear();
                    a++;b--;
                }
                else if(nums[a]+nums[b]>d){
                    b--;
                }
                else{

                    a++;
                }
            }

           }
          }
          return ll;
    }
}