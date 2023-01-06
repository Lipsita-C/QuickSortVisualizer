import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelectionSortVisualizer extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final int BAR_WIDTH = 10;
  private static final Color UNSORTED_COLOR = Color.BLACK;
  private static final Color SORTED_COLOR = Color.GREEN;
  private static final Color MIN_COLOR = Color.RED;
  private static final Color SWAP_COLOR = Color.BLUE;
  private static final int DELAY = 200; // ms

  private int[] array;
  private int minIndex;
  private int i;
  private int j;
  private int temp;

  public SelectionSortVisualizer(int[] array) {
    this.array = array;
    JFrame frame = new JFrame("Selection Sort Visualizer");
    frame.setSize(WIDTH, HEIGHT);
    frame.setDefaultCloseOperation
    (JFrame.EXIT_ON_CLOSE);
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
    for (int k = 0; k < array.length; k++) {
      int barHeight = (int) ((double) array[k] / maxValue * height);
      if (k == i) {
        g.setColor(SWAP_COLOR);
      } else if (k == minIndex) {
        g.setColor(MIN_COLOR);
      } else if (k < i) {
        g.setColor(SORTED_COLOR);
      } else {
        g.setColor(UNSORTED_COLOR);
      }
      g.fillRect(x, height - barHeight, barWidth, barHeight);
      x += barWidth;
    }
  }

  public void sort() throws InterruptedException {
    int n = array.length;
    for (i = 0; i < n - 1; i++) {
      minIndex = i;
      for (j = i + 1; j < n; j++) {
        if (array[j] < array[minIndex]) {
          minIndex = j;
        }
      }
      if (minIndex != i) {
        temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
        repaint();
        Thread.sleep(DELAY);
      }
    }
    i = 0;
    j = 0;
    minIndex = 0;
    repaint();
  }

  public static void main(String[] args) throws InterruptedException {
    int[] array = {6, 5, 3, 1, 8, 7, 2, 4};
    SelectionSortVisualizer visualizer = new SelectionSortVisualizer(array);
    visualizer.sort();
  }
}

