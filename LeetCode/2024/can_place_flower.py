class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        
        boolean pass = true;
        int h =0;
        List<Integer> numbList = new ArrayList<Integer>();
        for (int i =0; i < flowerbed.length; i=i+1){
            if (flowerbed[i]==0){
                h=h+1;
            }else {
                numbList.add(h);
                h=0;
            }
        }
        for (int i =0; i < numbList.size(); i=i+1){
            System.out.println(n);
            if (numbList.get(i)>1){
                n =n - numbList.get(i)/2;
                if (numbList.get(i)%2==0){
                    n=n+1;
                }
                else {
                    n=n-0;
                }
            }
        }  
        return n <= 0;
    }
}