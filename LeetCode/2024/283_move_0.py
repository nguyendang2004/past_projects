class Solution {
    public void moveZeroes(int[] nums) {
        int zIndex = -1;
        int found =1;
        int max = nums.length;
        while (found ==1){
            found =0;
            zIndex = -1;
            for (int i =0; i< max; i=i+1){
                if (nums[i]== 0){
                    zIndex = i;
                    found =1;
                }
                else{
                    if (zIndex!=-1){
                        nums[zIndex] = nums[i];
                        nums[i] = 0;
                        zIndex =i;
                    }
                }
            }
            max=max-1;
        }
    }
}