int lengthOfLastWord(char * s){
    int index =0;
    int count =0;
    int next = 0;

    while (s[index]!='\0'){
        if ( s[index] !=' '){
            if (next == 1){
                next = 0;
                count =0;
            }
            count= count+1;
        }
        else if (s[index] ==' '){
            next = 1;
        }
        index = index+1;

    }
    return count;
}