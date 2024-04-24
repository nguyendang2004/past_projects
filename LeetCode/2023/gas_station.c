int canCompleteCircuit(int* gas, int gasSize, int* cost, int costSize){
    int total_cost =0;
    int total_gas = 0;
    for (int i =0; i< costSize;i++){
        total_gas = gas[i]+total_gas;
        total_cost = cost[i]+total_cost;
    }
    if (total_cost > total_gas){
        return -1;
    }
    //minus sum and cost every turn . if it is alright, everything is alright
    int tank =0;
    int start =0;
    for (int i =0; i < costSize; i=i+1){
        tank =tank + gas[i]-cost[i];
        if (tank <0){
            tank =0;
            start = i+1;
        }
    }
    return start;

}