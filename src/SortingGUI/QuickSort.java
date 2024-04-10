package SortingGUI;

public class QuickSort {
    private static int compareIdx1 = -1; // Index of the first compared bar
    private static int compareIdx2 = -1; // Index of the second compared bar

    public static void sort(int[] arr) {
        SortingVisualizer.comparisons = 0;
        SortingVisualizer.accesses = 0;
        Thread sortingThread = new Thread(() -> {
            quickSort(arr, 0, arr.length - 1);
            SortingVisualizer.updateCompAcc(SortingVisualizer.comparisons, SortingVisualizer.accesses);
            SortingVisualizer.clearSwappedIndices();
            SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
        });
        sortingThread.start();
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        SortingVisualizer.accesses++;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);      
                SortingVisualizer.accesses++;
                try {
                    Thread.sleep(70); // Adjust the delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SortingVisualizer.setSwappedIndices(i, j); // Highlight the elements being compared
                SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
            }
            SortingVisualizer.comparisons++;
            SortingVisualizer.accesses++;
        }
        swap(arr, i + 1, high);       
        try {
            Thread.sleep(100); // Adjust the delay as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SortingVisualizer.setSwappedIndices(i + 1, high); // Highlight the pivot element
        SortingVisualizer.displayPanel.repaint(); // Update the display after each comparison
        return i + 1;
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
