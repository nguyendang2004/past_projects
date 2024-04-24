int removeDuplicates(int* nums, int numsSize){
    if (numsSize!=1){
        int index = 1;
        int k=1;
        int impossible = nums[0]-1;
        int numb = nums[0];
        int twice = 0;
        for (int i =1; i<numsSize;i++){
           
            if (nums[i]!=numb){
                nums[index] = nums[i];
                numb = nums[index];
                /*if (index!= i){
                    nums[i] =impossible;
                }*/
                index ++;
                k++;
                twice =0;

            }
            else if (nums[i]==numb && twice ==0 ){
                nums[index] = nums[i];
                twice =1;
                index++;
                k++;
            }
        }
        return k;
    }
    else{
        return 1;
    }
}