class Solution {
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> returnList = new ArrayList<Boolean>();
        if (candies.length == 0){
            return  returnList;
        }
        int largest =candies[0];
        for (int i =1; i< candies.length; i=i+1){
            if (largest < candies[i]){
                largest = candies[i];
            }
        }
        for (int i =0; i< candies.length; i=i+1){
            if ( candies[i] + extraCandies >= largest){
                returnList.add(true);
            }
            else{
                 returnList.add(false);
            }
        }
        return  returnList;
    }
}