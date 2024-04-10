package SortingGUI;

public class BubbleSort {
    private static int compareIdx1 = -1; // Index of the first compared bar
    private static int compareIdx2 = -1; // Index of the second compared bar

    public static void sort(int[] arr) {
        SortingVisualizer.comparisons = 0;
        SortingVisualizer.accesses = 0;
        Thread sortingThread = new Thread(() -> {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        swap(arr, j, j + 1);        
                        SortingVisualizer.accesses += 2;
                        try {
                            Thread.sleep(100); // Adjust the delay as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        SortingVisualizer.setSwappedIndices(j, j + 1); // Set indices for comparison
                        SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
                    }
                    SortingVisualizer.comparisons++;
                    SortingVisualizer.accesses += 2;
                }
            }
            SortingVisualizer.updateCompAcc(SortingVisualizer.comparisons, SortingVisualizer.accesses);
            SortingVisualizer.clearSwappedIndices(); // Clear swapped indices after sorting is done
            SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
        });
        sortingThread.start();
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int getCompareIdx1() {
        return compareIdx1;
    }

    public static int getCompareIdx2() {
        return compareIdx2;
    }
}
