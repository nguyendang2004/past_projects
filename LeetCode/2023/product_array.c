int* productExceptSelf(int* nums, int numsSize, int* returnSize){
    *returnSize = numsSize;
    int *res=malloc(numsSize*sizeof(int));
    int prefix=1; //first store prefix in the res array
    for(int i=0;i<numsSize;i++)
    {
        res[i]=prefix;
        prefix = prefix*nums[i];
    }
    int postfix=1; //traverse from the end of the array and store postfix
    for(int i=numsSize-1;i>=0;i--)
    {
        res[i]=res[i]*postfix;
        postfix = nums[i]*postfix;
    }
    return res;
}