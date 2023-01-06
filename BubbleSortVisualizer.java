import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BubbleSortVisualizer extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;
  private static final int BAR_WIDTH = 10;
  private static final Color UNSORTED_COLOR = Color.BLACK;
  private static final Color SORTED_COLOR = Color.GREEN;
  private static final Color SWAP_COLOR = Color.BLUE;
  private static final int DELAY = 200; // ms

  private int[] array;
  private int i;
  private int j;
  private int temp;

  public BubbleSortVisualizer(int[] array) {
    this.array = array;
    JFrame frame = new JFrame("Bubble Sort Visualizer");
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
    for (int k = 0; k < array.length; k++) {
      int barHeight = (int) ((double) array[k] / maxValue * height);
      if (k == i || k == j) {
        g.setColor(SWAP_COLOR);
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
      for (j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          temp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = temp;
          repaint();
          Thread.sleep(DELAY);
        }
      }
    }
    i = 0;
    j = 0;
    repaint();
  }

  public static void main(String[] args) throws InterruptedException {
    int[] array = {6, 5, 3, 1, 8, 7, 2, 4};
    BubbleSortVisualizer visualizer = new BubbleSortVisualizer(array);
    visualizer.sort();
  }
}
