int jump(int* nums, int numsSize){
    int jump = nums[0];
    int max = -1;
    int turns =0;
    int max_index = 0;
    if(numsSize==1){
        return 0;
    }
    while(jump!=0){
        jump = nums[max_index];
        turns ++;
        max=-1;
        int i = max_index;
        for (int h =1; h<= jump;h++){
            if (i+h< numsSize){
                if( nums[i+h]+i+h>= max){
                    max = nums[i+h]+i+h;
                    max_index = i+h;
                }
            }
            if (i+h== numsSize-1){
                return turns;
            }
        }
    }
    return turns;
}