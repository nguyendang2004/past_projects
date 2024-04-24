class Solution {
    public int equalPairs(int[][] grid) {
        // Get the size of the grid (assuming it's a square grid)
        int n = grid.length;
        
        // Initialize a variable to store the final result
        int ans = 0;
        
        // Create maps to store the count of each unique row and column
        Map<String, Integer> row = new HashMap<>();
        Map<String, Integer> col = new HashMap<>();

        // Count occurrences of each unique row
        for(int i=0; i<n; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j=0; j<n; j++) {
                sb.append(grid[i][j]);
                sb.append(":");
            }
            String str = sb.toString();
            row.put(str, row.getOrDefault(str, 0) + 1);
        }

        // Count occurrences of each unique column
        for(int i=0; i<n; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j=0; j<n; j++) {
                sb.append(grid[j][i]);
                sb.append(":");
            }
            String str = sb.toString();
            col.put(str, col.getOrDefault(str, 0) + 1);
        }
        
        // Count equal pairs by iterating through the keys of the 'row' map
        for(String s : row.keySet()) {
            // Check if the corresponding column key exists in the 'col' map
            if(col.containsKey(s)) {
                // Multiply the counts and add to the final result
                ans += row.get(s) * col.get(s);
            }
        }
        
        // Return the total count of equal pairs
        return ans;
    }
}