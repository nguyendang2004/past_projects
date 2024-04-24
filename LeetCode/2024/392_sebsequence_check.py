class Solution {
    public boolean isSubsequence(String s, String t) {
        int start = 0;
        for (int i =0; i< s.length();i=i+1){
            boolean found =false;
            for (int b = start; b < t.length();b=b+1){
                if (s.charAt(i)== t.charAt(b)){
                    start = b+1;
                    found =true;
                    break;
                }
            }
            if (found == false){
                return false;
            }
        }
        return true;
    }
}