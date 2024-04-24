class Solution {
    public int compress(char[] chars) {
        int current=0;
        int max=chars.length;
        int index=0;
        if(max==1)
            return 1;
        while(current<max){
            int count=1;
            char charac=chars[current];
            while(current+1<max && chars[current]==chars[current+1]){
                count++;
                current++;
            }
            if(count==1){
                chars[index++]=charac;
            }
            else{
                if(count>1){
                    chars[index++]=charac;
                    String counterstring=count+"";
                    for(int r=0;r<counterstring.length();r++){
                        chars[index++]=counterstring.charAt(r);
                    }
                }
            }
            current++;
        }
        return index;
    }
}