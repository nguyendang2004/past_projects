class Solution {
    public String reverseVowels(String s) {
        List<Integer> intList = new ArrayList <Integer>();
        for (int i =s.length()-1; i>= 0;i=i-1){
            char ch = Character.toLowerCase(s.charAt(i));
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' ){
                System.out.println(i);
                intList.add(i);
            }
        }
        String result ="";
        int intStuff = 0;
        for (int i =0; i< s.length();i=i+1){
            char c = s.charAt(i);
            char ch = Character.toLowerCase(c);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' ){
                result= result + s.charAt(intList.get(intStuff));
                intStuff ++;
            }
            else{

                result =result + c;
            }  
        }
        return result;
    }
}