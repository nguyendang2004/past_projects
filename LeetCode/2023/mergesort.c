void merge(int* nums1, int nums1Size, int m, int* nums2, int nums2Size, int n){
    int index1 = m-1;
    int index2 = n-1;
    int final_index =m+n-1;

    while (index2>-1 && index1>-1){
        if (nums2[index2] < nums1[index1]){
            nums1[final_index] = nums1[index1];
            final_index--;
            index1--;
        }
        else if (nums2[index2] >= nums1[index1]){
            nums1[final_index] = nums2[index2];
            index2--;
            final_index--;
        }
    }

    if (index1==-1 && index2!=-1){
        for (int i = 0; i<= index2; i=i+1){
            nums1[i] = nums2[i];
        }
    }
}