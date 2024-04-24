class Solution {
    public int maxArea(int[] height) {
        int max = 0;
        int current =0;
        int left=0;
        int right = height.length -1;
        while (left< right){
            current = (right-left) * Math.min(height[right], height[left]);
            max = Math.max(current, max);
            if (height[left]> height[right]){
                right--;
            }
            else{
                left++;
            }
        }
        return max;
    }
}

