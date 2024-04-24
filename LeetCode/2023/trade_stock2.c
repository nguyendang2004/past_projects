int maxProfit(int* prices, int pricesSize){
    int lsf = prices[0];
    int op = 0;
    int pist = 0; 
    int last_price = -1;
    int next =0;
    
    for(int i = 0; i < pricesSize; i++){

        if (last_price >= prices[i]){
            pist =last_price-lsf;
            op = pist+op;
            next =1;
        }
        else if (i+1==pricesSize && prices[i]>lsf){
            pist =prices[i]-lsf;
            op = pist+op;
            next =1;
        }
        last_price = prices[i];
        if(prices[i] < lsf|| next ==1){
            lsf = prices[i];
            next = 0;
            last_price =-1;
        }


    }
    return op;
}

