#include <stdio.h>
#include <stdlib.h>
bool canJump(int* nums, int numsSize){
    int found = 0;
    int next_index =-1;

    if (numsSize ==1){
        return true;
    }
    if(nums[0]==0){
        return false;
    }
    int index0 = numsSize-1;
    int done = 1;
    while (done ==1){
        found =0;
        if (nums[index0] !=0){
            index0--;
        }
        else{
            for (int i = 1; i<= index0; i++){
                if (nums[index0-i]==0 && next_index == -1){
                    next_index = index0-i;
                }
                if (i <= nums[index0-i] && index0 == numsSize-1){
                    found=1;
                    //break;
                }
                if (i < nums[index0-i] && index0 != numsSize-1){
                    found=1;
                    //break;
                }
            }

            if (found ==1){
                if (next_index == -1){
                    return true;
                }
                index0 = next_index;
                next_index =-1;
            }
            else{
                return false;
            }
        }
        if (index0==0){
            done = 0;
        }
    }
    return true;
}
int main(int argc, char const *argv[])
{
    int nums[4]={1,0,1,0};
    printf("moss2\n");
    int h = canJump(nums, 4);
    printf("%d\n",h);
    return 0;
}