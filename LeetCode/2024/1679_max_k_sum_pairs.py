class Solution {
    public int maxOperations(int[] nums, int k) {
        int count =0;
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length-1;
        while (right>0 && left<right && left<nums.length){ 
            int sum = nums[left]+nums[right];

            if (sum==k){
                count++;
                left++;
                right--;
            }
            else if (sum<k ){
                left++;
            }
            else{
                right--;
            }
        }
        return count;
    }
}
