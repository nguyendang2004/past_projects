class Solution {
    public int pivotIndex(int[] nums) {
        int leftSum = 0;
        int rightSum =0;
        int max = nums.length;
        for (int i= 0; i < max;i=i+1) {
            rightSum += nums[i];
        }

        for (int i= 0; i < max;i=i+1){
            rightSum = rightSum- nums[i];
           
            if (leftSum==rightSum){
                return i;
            }
            leftSum =leftSum+nums[i];
        }
        return -1;
    }
}