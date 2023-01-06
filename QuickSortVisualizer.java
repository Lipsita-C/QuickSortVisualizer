
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

    

public class QuickSortVisualizer extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final int BAR_WIDTH = 2;
  private static final Color UNSORTED_COLOR = Color.BLACK;
  private static final Color SORTED_COLOR = Color.GREEN;
  private static final Color PIVOT_COLOR = Color.RED;
  private static final Color SWAP_COLOR = Color.BLUE;
  private static final int DELAY = 10; // ms

  private int[] array;
  private int pivotIndex;
  private int leftIndex;
  private int rightIndex;
  private int leftSwapIndex;
  private int rightSwapIndex;
  private boolean pivotSelected;
  private boolean swapping;

  public QuickSortVisualizer(int[] array) {
    this.array = array;
    JFrame frame = new JFrame("Quick Sort Visualizer");
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(this);
    frame.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    int barWidth = BAR_WIDTH;
    int maxValue = Arrays.stream(array).max().getAsInt();
    int x = width / 2 - (array.length * barWidth) / 2;
    int pivotY = 0;
    int leftY = 0;
    int rightY = 0;
    int leftSwapY = 0;
    int rightSwapY = 0;
    for (int i = 0; i < array.length; i++) {
      int barHeight = (int) ((double) array[i] / maxValue * height);
      if (i == pivotIndex) {
        g.setColor(PIVOT_COLOR);
        pivotY = height - barHeight;
      } else if (i == leftIndex) {
        g.setColor(SWAP_COLOR);
        leftY = height - barHeight;
      } else if (i == rightIndex) {
        g.setColor(SWAP_COLOR);
        rightY = height - barHeight;
      } else if (i == leftSwapIndex) {
        g.setColor(SWAP_COLOR);
        leftSwapY = height - barHeight;
      } else if (i == rightSwapIndex) {
        g.setColor(SWAP_COLOR);
        rightSwapY = height - barHeight;
      } else {
        g.setColor(UNSORTED_COLOR);
      }
      g.fillRect(x, height - barHeight, barWidth, barHeight);
      x += barWidth;
    }
    if (pivotSelected) {
        g.setColor(PIVOT_COLOR);
        g.fillRect(width / 2 - barWidth / 2, pivotY, barWidth, height - pivotY);
      }
      if (swapping) {
        g.setColor(SWAP_COLOR);
        g.fillRect(width / 2 - barWidth / 2, leftSwapY, barWidth, height - leftSwapY);
        g.fillRect(width / 2 - barWidth / 2, rightSwapY, barWidth, height - rightSwapY);
      }
    }
  
    public void sort() throws InterruptedException {
      quickSort(0, array.length - 1);
    }
  
    private void quickSort(int left, int right) throws InterruptedException {
      if (left < right) {
        pivotIndex = right;
        pivotSelected = true;
        repaint();
        Thread.sleep(DELAY);
        pivotSelected = false;
        int pivot = array[pivotIndex];
        int partitionIndex = partition(left, right, pivot);
        quickSort(left, partitionIndex - 1);
        quickSort(partitionIndex + 1, right);
      }
    }
  
    private int partition(int left, int right, int pivot) throws InterruptedException {
      leftIndex = left;
      rightIndex = right;
      while (left <= right) {
        while (array[left] < pivot) {
          left++;
        }
        while (array[right] > pivot) {
          right--;
        }
        if (left <= right) {
          leftSwapIndex = left;
          rightSwapIndex = right;
          swapping = true;
          repaint();
          Thread.sleep(DELAY);
          int temp = array[left];
          array[left] = array[right];
          array[right] = temp;
          swapping = false;
          left++;
          right--;
        }
      }
      return left;
    }
  
    public static void main(String[] args) throws InterruptedException {
      int[] array = {6, 5, 3, 1, 8, 7, 2, 4};
      QuickSortVisualizer visualizer = new QuickSortVisualizer(array);
      visualizer.sort();
      visualizer.repaint();
    }
  }
  


    

