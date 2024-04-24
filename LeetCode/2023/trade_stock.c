#include <stdio.h>
#include <stdlib.h>
int maxProfit(int* prices, int pricesSize){
        int lsf = prices[0]; // least so far
        int op = 0; // overall profit
        int pist = 0; // profit if sold today
        
        for(int i = 0; i < pricesSize; i++){
            if(prices[i] < lsf){ // if we found new buy value which is more smaller then previous one
                lsf = prices[i]; // update our least so far
            }
            pist = prices[i] - lsf; // calculating profit if sold today by, Buy - sell
            if(op < pist){ // if pist is more then our previous overall profit
                op = pist; // update overall profit
            }
        }
        return op; // return op
}


int main(int argc, char const *argv[])
{
    int prices[6] = {7,1,5,3,6,4};
    int h = maxProfit(prices, 6);
    return 0;
}