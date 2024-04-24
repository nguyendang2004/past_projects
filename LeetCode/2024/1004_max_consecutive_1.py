class Solution {
    public int longestOnes(int[] nums, int k) {
        int max =0;
        int kCount=0;
        int count =0;
        int lastKIndex=0;
        for (int i =0; i< nums.length; i=i+1){
            
            if (nums[i]==1){
                count=count+1;
            }
            else{
                if (kCount ==k){
                    max = Math.max(max, count);
                    int b =lastKIndex;
                    while (nums[b]==1){
                        b++;
                    }


                    count =count - (b-lastKIndex)-1;
                    //System.out.println(count);
                                        lastKIndex=b+1;
                    kCount--;
                }
                kCount++;
                count=count+1;
            }
        }
        max = Math.max(max, count);
        return max;
    }
}

0011001101, k=1
i=0,count=1,lastIndex=0, kCount=1
i=1, unflip i=0, loop from lastIndex to count number of zero
    count =count - (number of 1)-1 -> minus the 1s and 1 flipped 0 from count
    set new lastIndex to be 1
    flip i=1, count =1,

i=2, count =2
i=3, count=3
i=4, unflip i=1, loop from lastIndex to count number of zero
    count =count - (number of 1)-1 -> minus the 1s and 1 flipped 0 from count
    set new lastIndex to be 2
    flip i=4, count =3,

repeat

