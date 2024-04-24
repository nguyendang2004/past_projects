#include <stdio.h>
#include <stdlib.h>
int romanToInt(char * s){
    int index=0;
    int sum =0;
    while (s[index]!='\0'){
        if (s[index]=='I'){
            if (s[index+1] == 'V'){
                sum = sum +4;
                index = index+1;
            }
            else if (s[index+1] == 'X'){
                sum = sum +9;
                index = index+1;
            }
            else{
                sum = sum+1;
            }
        }
        else if (s[index]=='V'){
            sum=sum+5;
        }
        else if (s[index]=='X'){
            if (s[index+1] == 'L'){
                sum = sum +40;
                index = index+1;
            }
            else if (s[index+1] == 'C'){
                sum = sum +90;
                index = index+1;
            }
            else{
                sum = sum+10;
            }
        }
        else if (s[index]=='L'){
            sum = sum+50;
            
        }
        else if (s[index]=='C'){
            if (s[index+1] == 'D'){
                sum = sum +400;
                index = index+1;
            }
            else if (s[index+1] == 'M'){
                sum = sum +900;
                index = index+1;
            }
            else{
                sum = sum+100;
            }
            
        }
        else if (s[index]=='D'){
            sum = sum+500;
            
        }
        else if (s[index]=='M'){
            sum = sum+1000;
            
        }
        index=index+1;
    }
    return sum;
}
int main(int argc, char const *argv[])
{
    char * s ="MCMXCIV";
    printf("%d\n", romanToInt(s));
    return 0;
}