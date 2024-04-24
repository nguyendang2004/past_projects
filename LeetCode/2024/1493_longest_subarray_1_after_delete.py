class Solution {
    public int longestSubarray(int[] nums) {
        int max=-1;
        int count =0;
        int lastZ=-1;
        //boolean delete =false;
        boolean offset=true;
        for(int i=0; i< nums.length;i=i+1){
            if (nums[i]==1){
                count=count+1;
            }
            else{
                if (max==-1){
                    max = Math.max (count, max);
                }
                else{
                    max = Math.max (count, max);
                    int b = lastZ;
                    for ( b=lastZ; b <nums.length; b++){
                        if (b==-1){
                            if(offset){
                                offset=false;
                            }
                        }
                        else if (nums[b]==0){
                            if(offset){
                                offset=false;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    count=count-((b-1)-lastZ);
                    lastZ = b;
                    offset=true;
                }
            }
        }
        if (max==-1){
            count =count-1;
        }
        max = Math.max (count, max);
        return max;
    }
}
//0,1,1,1,0,1,1,0,1