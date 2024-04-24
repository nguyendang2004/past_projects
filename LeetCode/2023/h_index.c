#include <stdio.h>
#include <stdlib.h>
int hIndex(int* citations, int citationsSize){
    int largest =-1;
    int largest_index =0;
    for (int i =0; i<citationsSize;i++){
        if(citations[i]>largest){
            largest = citations[i];
            largest_index =1;
        }
    }
    int wrong =0;
    int wrong_sum = 0;
    int h =0;
    if (largest > citationsSize){
        largest = citationsSize;
    }

    for (int i = largest; i>=0; i=i-1){
        h =0;
        wrong_sum = 0;
        wrong =  citationsSize-i;
        while(wrong_sum <= wrong){
            if(citations[h]<i){
                wrong_sum++;
            }
            if (h == citationsSize-1 && wrong_sum <= wrong){
                return i;
            }
            h++;
        }
    }
    return largest;
}
int main(int argc, char const *argv[])
{
    int cit [3] = {1,3,1};
    printf("final: %d\n", hIndex(cit, 3));
    return 0;
}