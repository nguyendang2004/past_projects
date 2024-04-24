#include <stdio.h>
#include <stdlib.h>

 int removeElement(vector<int>& nums, int val) {
        int p = nums.size()-1; // Pointer for placing the "val" element at last indices
        int i = 0; 
        int cnt = 0; // count to store the value how many times the "val" element is found
        while(i<p){
            if(nums[i]==val){
                swap(nums[i],nums[p]);
                cnt++;
                p--;
            }else{
                ++i;
            }
        }
        return nums.size()-cnt;
    }