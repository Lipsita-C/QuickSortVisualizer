import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HeapSortVisualizer extends JFrame {

    private int[] array;
    private JButton startButton;
    private JButton pauseButton;
    private JPanel panel;
    private boolean isSorting;
    private boolean isPaused;
    private Thread sortingThread;
    
    

    public HeapSortVisualizer(int[] array) {
        this.array = array;
        this.isSorting = false;
        this.isPaused = false;

        setTitle("Heap Sort Visualizer");
        setSize(600, 400);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });
        add(startButton, "North");

        pauseButton = new JButton("Pause");
        pauseButton.setEnabled(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseSorting();
            }
        });
        add(pauseButton, "North");

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHeap(g, array, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(600, 300));
        add(panel);
    }

    public void startSorting() {
        if (!isSorting) {
            isSorting = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            sortingThread = new Thread(new SortingTask());
            sortingThread.start();
        }
    }

    public void pauseSorting() {
        if (isSorting && !isPaused) {
            isPaused = true;
            pauseButton.setText("Resume");
        } else if (isSorting && isPaused) {
            isPaused = false;
            pauseButton.setText("Pause");
            synchronized (sortingThread) {
                sortingThread.notify();
            }
        }
    }

    public void stopSorting() {
        if (isSorting) {
            isSorting = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
            pauseButton.setText("Pause");
        }
    }

    public void sort() {
        // Build the heap
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(array, array.length, i);
        }

        // Extract elements from the heap one by one
        for (int i = array.length - 1; i >= 0; i--) {
            // Move the current root (maximum value) to the end
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Call heapify on the reduced heap
            heapify(array, i, 0);
        }
    }

    // Heapify the heap
    public static void heapify(int[] array, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        // If the left child is larger than the root
        if (left < size && array[left] > array[largest]) {
            largest = left;
        }

        // If the right child is larger than the largest so far
        if (right < size && array[right] > array[largest]) {
            largest = right;
        }

        // If the largest is not the root
        if (largest != root) {
            int temp = array[root];
            array[root] = array[largest];
            array[largest] = temp;

            // Recursively heapify the affected sub-tree
            heapify(array, size, largest);
        }
    }

    public static void drawHeap(Graphics g, int[] array, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        int w = width / array.length;
        for (int i = 0; i < array.length; i++) {
            int h = array[i] * height / Arrays.stream(array).max().getAsInt();
            g.setColor(Color.BLACK);
            g.fillRect(i * w, height - h, w, h);
            g.setColor(Color.WHITE);
            g.drawRect(i * w, height - h, w, h);
        }
    }

    class SortingTask implements Runnable {
        @Override
        public void run() {
            sort();
            stopSorting();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int[] array = {3, 7, 1, 5, 2, 8, 9, 4, 6, 0};
                HeapSortVisualizer visualizer = new HeapSortVisualizer(array);
                visualizer.setVisible(true);
            }
        });
    }
}


