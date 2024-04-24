class Solution {
    public int maxVowels(String s, int k) {
        int max =0;
        int count =0;
        char ch;
        for (int i =0; i< s.length(); i=i+1){
            if (i<k){
                ch = s.charAt(i);
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
                    count = count+1;
                    max =count;
                }
            }
            else{
                ch = s.charAt(i);
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
                    count = count+1;
                }
                ch = s.charAt(i-k);
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'){
                    count = count-1;
                }
                max = Math.max(max, count);
            }
        }
        return max;
    }
}