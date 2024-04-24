class Solution {
    public int largestAltitude(int[] gain) {
        int max=0;
        int sum=0;
        for (int i =0;i< gain.length;i=i+1){
            sum =sum+ gain[i];
            max= Math.max(sum,max);
        }
        return max;
    }
}