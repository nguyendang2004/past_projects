class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int max =0;
        int sum =0;
        for (int i =0; i< nums.length; i=i+1){
            if (i<k){
                sum=sum+nums[i];
                max =sum;
            }
            else{
                sum = sum- nums[i-k]+nums[i];
                max = Math.max(max, sum);
            }
        }
        return (double)max/k;
    }
}