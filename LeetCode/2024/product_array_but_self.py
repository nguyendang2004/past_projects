class Solution {
    public int[] productExceptSelf(int[] nums) {
        int [] leftList = new int [nums.length];
        int [] rightList = new int [nums.length];
        int [] returnList = new int [nums.length];
        int max =nums.length;
        leftList[0] =1;
        rightList[max-1] =1;
        int leftLast =1;
        int rightLast =1;
        for (int i =0; i< nums.length-1; i=i+1){
            leftList[i+1] = nums[i]*leftLast;
            leftLast = nums[i]*leftLast;

        }
        for (int i =max-1; i>0; i=i-1){
            rightList[i-1] = nums[i]*rightLast;
            rightLast = nums[i]*rightLast;

        }

        for (int i =0; i< max; i=i+1){
            returnList[i] = leftList[i]*rightList[i];
        }
        return returnList;
    }
}
/*12345

2345,1345,1245,1235,1234

1234, 123, 12,1,(1)

(1), 1,12,123,1234 -> left
2345,345,45,5,(1) -> right*/