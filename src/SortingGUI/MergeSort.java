package SortingGUI;

public class MergeSort {
    private static int compareIdx1 = -1; // Index of the first compared bar
    private static int compareIdx2 = -1; // Index of the second compared bar

    public static void sort(int[] arr) {
        SortingVisualizer.comparisons = 0;
        SortingVisualizer.accesses = 0;
        Thread sortingThread = new Thread(() -> {
            mergeSort(arr, 0, arr.length - 1);
            SortingVisualizer.updateCompAcc(SortingVisualizer.comparisons, SortingVisualizer.accesses);
        });
        sortingThread.start();
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
            SortingVisualizer.accesses++;
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
            SortingVisualizer.accesses++;
        }

        int i = 0;
        int j = 0;
        int k = left;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            SortingVisualizer.accesses++; // Increment accesses
            SortingVisualizer.comparisons++; // Increment comparisons
            try {
                Thread.sleep(100); // Adjust the delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SortingVisualizer.setSwappedIndices(k, k); // Highlight the element being compared
            SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            SortingVisualizer.accesses++;
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            SortingVisualizer.accesses++;
            j++;
            k++;
        }

        SortingVisualizer.clearSwappedIndices(); // Clear highlighted elements after merging
        SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
    }

    public static int getCompareIdx1() {
        return compareIdx1;
    }

    public static int getCompareIdx2() {
        return compareIdx2;
    }
}
