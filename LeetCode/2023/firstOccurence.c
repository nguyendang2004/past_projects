
int strStr(char * haystack, char * needle){
    int stack_index=0;
    int need_index = 0;
    int holder = 0;
    int found=1;
    while (haystack[stack_index]!='\0'){
        holder = stack_index;
        found = 1;
        need_index=0;
        while (needle[need_index]!='\0'){
            if(haystack[stack_index]!=needle[need_index]){
                found =0;
                break;

            }
            stack_index++;
            need_index++;
        }
        if (found ==1){
            return holder;
        }
        stack_index = holder+1;
    }
    return -1;
}