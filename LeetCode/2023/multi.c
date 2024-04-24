#include <stdio.h>
#include <stdlib.h>
int* productExceptSelf(int* nums, int numsSize){
    int *res= (int*)malloc(numsSize*sizeof(int));
    int prefix=1; //first store prefix in the res array
    printf("pre:\n");
    for(int i=0;i<numsSize;i++)
    {
        res[i]=prefix;
        prefix = prefix*nums[i];
        printf("%d\n", res[i]);
    }
    int postfix=1; //traverse from the end of the array and store postfix
    printf("post:\n");
    for(int i=numsSize-1;i>=0;i--)
    {
        res[i]=res[i]*postfix;
        postfix = nums[i]*postfix;
        printf("%d\n", res[i]);
    }
    return res;
}
int main(int argc, char const *argv[])
{
    int h[4]= {1,2,3,4};
    int *j = productExceptSelf(h,4);
    return 0;
}
