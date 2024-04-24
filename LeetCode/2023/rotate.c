#include <stdio.h>
#include <stdlib.h>

void rotate(int* nums, int numsSize, int k){
    int new_index =0;
    int new_buffer = nums[0];
    int old_buffer;
    int i = 0;
    int start_loop = 0;
    int first = 1;
    while (i< numsSize){
        if (new_index == start_loop && first == 0){
            new_index = new_index+1;
            new_buffer = nums[new_index];
            start_loop = new_index;
        }
        else{
            first =0;
        }
        new_index = (new_index +k)%numsSize;
        old_buffer = nums[new_index];
        nums[new_index] = new_buffer;
        new_buffer = old_buffer;
        i++;
    }
}

int main(int argc, char const *argv[])
{
	return 0;
}