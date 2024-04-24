class Solution {
    public String reverseWords(String s) {
        String answer = "";
        boolean end = true;
        boolean first= true;
        for (int i =s.length()-1; i>=0;i=i-1){
            if (s.charAt(i) !=' '){
                if (end == true){
                    end =false;
                }
                append = append + s.charAt(i);
            }
            else{
                if (end == false){
                    if (first!= true){
                        answer=answer+" ";
                    }
                    else{
                        first = false;
                    }
                    answer=answer+ append;
                    end = true;
                    append = "";
                }
            }
        }
        if (append != ""){
            if (first!= true){
                answer=answer+" ";
            }
            answer=answer+append;
        }
        
        return answer;
    }
}