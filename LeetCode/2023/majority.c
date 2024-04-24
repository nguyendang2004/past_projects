#include <stdio.h>
#include <stdlib.h>


int majorityElement(int* nums, int numsSize){
	if (numsSize==1){
		return nums[0];
	}
	if (numsSize%2!=0){
		int majority = numsSize/2;
		int numb = nums[numsSize-1];
		int appearance =0;
		for ( int i =0; i< numsSize;i++){
			if (nums[i] == numb){
				appearance = appearance+1;
			}
		}
		if (appearance > majority){
			return numb;
		}
		numsSize = numsSize-1;
	}
	int index = 0;
	int new_numsSize =0;
	for (int i =0; i< numsSize;i=i+2){
		if (nums[i]==nums[i+1]){
			nums[index] = nums[i];
			new_numsSize++;
			index++;
		}
	}
	return majorityElement(nums, new_numsSize);


}