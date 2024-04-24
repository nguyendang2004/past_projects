class Solution {
       public List<List<Integer>> findDifference(int[] nums1, int[] nums2){
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        ArrayList<Integer> diff1 = new ArrayList<>();
        ArrayList<Integer> diff2 = new ArrayList<>();


        for (int i : nums1) {
            set1.add(i);
        }

        for (int i : nums2) {
            set2.add(i);
            if (set1.add(i)) {
                diff2.add(i);
            }
        }

        for (int i : nums1) {
            if (set2.add(i)) {
                diff1.add(i);
            }
        }

        return List.of(diff1, diff2);
    }
}