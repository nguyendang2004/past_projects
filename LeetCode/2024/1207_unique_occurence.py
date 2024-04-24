class Solution {
    public boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer,Integer> occur = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++){
            if (occur.containsKey(arr[i])){
                occur.put(arr[i], occur.get(arr[i])+1);
            }
            else{
                occur.put(arr[i],1);
            }
        }
        HashSet <Integer> count = new  HashSet <Integer>();
        for (Integer item: occur.keySet()){
            boolean h =count.add(occur.get(item));
            if (h ==false){
                return false;
            }
        }
        return true;
    }
}