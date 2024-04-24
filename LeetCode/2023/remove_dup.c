#include <stdio.h>
#include <stdlib.h>


int removeDuplicates(int* nums, int numsSize){
    if (numsSize!=1){
        int index = 1;
        int k=1;
        int numb = nums[0];
        for (int i =1; i<numsSize;i++){
            if (nums[i]!=numb){
                nums[index] = nums[i];
                numb = nums[index];
                index ++;
                k++;
            }
        }
        return k;
    }
    else{
        return 1;
    }

}