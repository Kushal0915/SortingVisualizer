package SortingGUI;

public class SelectionSort {
    private static int compareIdx1 = -1; // Index of the first compared bar
    private static int compareIdx2 = -1; // Index of the second compared bar

    public static void sort(int[] arr) {
        SortingVisualizer.comparisons = 0;
        SortingVisualizer.accesses = 0;
        Thread sortingThread = new Thread(() -> {
            for (int i = 0; i < arr.length - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }
                    SortingVisualizer.setSwappedIndices(i, j); // Set indices for comparison                               
                    try {
                        Thread.sleep(35); // Adjust the delay as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Update the display after each comparison
                    SortingVisualizer.comparisons++;
                    SortingVisualizer.accesses += 2;
                    SortingVisualizer.displayPanel.repaint();
                }
                swap(arr, i, minIndex);
                SortingVisualizer.accesses += 2;
            }         
            SortingVisualizer.updateCompAcc(SortingVisualizer.comparisons, SortingVisualizer.accesses);
            SortingVisualizer.clearSwappedIndices(); // Clear swapped indices after sorting is done
            SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
        });
        sortingThread.start();     
    }

    public static int getCompareIdx1() {
        return compareIdx1;
    }

    public static int getCompareIdx2() {
        return compareIdx2;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
