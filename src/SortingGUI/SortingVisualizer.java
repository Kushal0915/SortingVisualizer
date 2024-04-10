package SortingGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class SortingVisualizer {

    private JFrame frame;
    static JPanel displayPanel;
    private JPanel controlPanel;
    private JTextField sizeField;
    private JButton resizeButton;
    private JButton sortButton;
    private JComboBox<String> algorithmComboBox;
    private static JLabel comparisonLabel;
    private static JLabel accessLabel;
    private JTextField runtimeComplexityField;
    private int[] array;

    private String[] runtimeComplexities = {"Selection: O(n^2)", "Insertion: O(n^2)", "Bubble: O(n^2)", "Merge: O(nlog(n))", "Quick: O(nlog(n))"};

    static int comparisons;
    static int accesses;
    private static ArrayList<Integer> swappedIndices = new ArrayList<>();

    public SortingVisualizer() {
        frame = new JFrame("Sorting Algorithm Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 600);
        frame.getContentPane().setBackground(new Color(48, 48, 48));

        displayPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBars(g);
            }
        };
        displayPanel.setBackground(Color.BLACK);
        frame.add(new JScrollPane(displayPanel), BorderLayout.CENTER);

        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frame.add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setBackground(new Color(48, 48, 48));

        sizeField = new JTextField("50", 5);
        controlPanel.add(sizeField);

        resizeButton = new JButton("Resize Array");
        resizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resizeArray();
            }
        });
        controlPanel.add(resizeButton);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortArray();
            }
        });
        controlPanel.add(sortButton);

        algorithmComboBox = new JComboBox<>(new String[]{"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"});
        algorithmComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateComplexityLabel();
            }
        });
        controlPanel.add(algorithmComboBox);

        comparisonLabel = new JLabel("Comparisons: 0");
        comparisonLabel.setForeground(Color.WHITE);
        controlPanel.add(comparisonLabel);

        accessLabel = new JLabel("Array Accesses: 0");
        accessLabel.setForeground(Color.WHITE);
        controlPanel.add(accessLabel);

        runtimeComplexityField = new JTextField(runtimeComplexities[0], 20);
        runtimeComplexityField.setEditable(false);
        runtimeComplexityField.setBackground(new Color(48, 48, 48));
        runtimeComplexityField.setForeground(Color.WHITE);
        controlPanel.add(runtimeComplexityField);

        array = generateRandomArray(50);

        frame.setVisible(true);
    }

    private int[] generateRandomArray(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i * 2);
        }
        Collections.shuffle(list);
        return list.stream().mapToInt(i -> i).toArray();
    }

    private void drawBars(Graphics g) {
        int numBars = array.length;
        int panelWidth = displayPanel.getWidth();
        int barGap = 2;
        int totalBarWidth = panelWidth - (numBars + 1) * barGap;
        int barWidth = totalBarWidth / numBars;
        int remainder = totalBarWidth % numBars;
        int panelHeight = displayPanel.getHeight();
        for (int i = 0; i < numBars; i++) {
            int barHeight = (int) Math.round((double) array[i] / 100 * panelHeight * 0.9);
            barHeight = Math.max(barHeight, 1);
            if (swappedIndices.contains(i)) {
                g.setColor(i % 2 == 0 ? Color.RED : Color.YELLOW);
            } else {
                g.setColor(Color.CYAN);
            }
            int x = i * (barWidth + barGap) + barGap + (i < remainder ? i : remainder);
            g.fillRect(x, panelHeight - barHeight, barWidth + (i < remainder ? 1 : 0), barHeight);
        }
    }

    private void resizeArray() {
        try {
            int newSize = Integer.parseInt(sizeField.getText());
            if (newSize < 10 || newSize > 50) {
                JOptionPane.showMessageDialog(frame, "Array size must be between 10 and 50.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                array = generateRandomArray(newSize);
                displayPanel.repaint();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid array size.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sortArray() {
        switch (algorithmComboBox.getSelectedIndex()) {
            case 0:
                SelectionSort.sort(array);             
                break;
            case 1:
                InsertionSort.sort(array);
                break;
            case 2:
                BubbleSort.sort(array);
                break;
            case 3:
                MergeSort.sort(array);
                break;
            case 4:
                QuickSort.sort(array);
                break;
        }
        // Update labels after sorting
        swappedIndices.clear(); // Clear previous swapped indices
    }
    
    public static void updateCompAcc(int compare, int access) {
        comparisonLabel.setText("Comparisons: " + compare);
        accessLabel.setText("Array Accesses: " + access);
    }

    public static void setSwappedIndices(int idx1, int idx2) {
        swappedIndices.clear();
        swappedIndices.add(idx1);
        swappedIndices.add(idx2);
    }

    public static void clearSwappedIndices() {
        swappedIndices.clear();
    }

    private void updateComplexityLabel() {
        runtimeComplexityField.setText(runtimeComplexities[algorithmComboBox.getSelectedIndex()]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortingVisualizer::new);
    }
}
