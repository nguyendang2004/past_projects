#include <stdio.h>
#include <stdlib.h>

char * intToRoman(int num){
    char roman [7]= {'M','D','C','L','X','V','I'};
    char result [13];
    int index=0;
    int divide = 1000;
    int divide_index = 0;
    int denom = num; 
    int iterate = 0;
    while (denom != 0){
        printf("%s\n", result);
        iterate = denom/divide;
        denom = denom%divide;
        if (iterate == 4){
            result[index] = roman[divide_index];
            index = index+1;
            result[index] = roman[divide_index-1];
            index = index+1;
        }
        else if (iterate == 9){
            result[index] = roman[divide_index];
            index = index+1;
            result[index] = roman[divide_index-2];
            index =index+1;
        }
        else if (iterate !=0){
            if (iterate >=5){
                result[index]=roman[divide_index-1];
                index=index+1;
            iterate = iterate-5;
            }
            for (int i = 0; i< iterate;i=i+1 ){
                result[index]=roman[divide_index];
                index=index+1;
            }
        }
        divide_index = divide_index+2;
        divide=divide/10;
    }
    result[index] = '\0';
    return result;
}
int main(int argc, char const *argv[])
{
    int num =3;
    char * ch  = intToRoman(num);
    return 0;
}
