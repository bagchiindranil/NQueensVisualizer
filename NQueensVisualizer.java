import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

// Class to represent the chessboard
class Board {
    private int[][] board;
    private int size;

    public Board(int size) {
        this.size = size;
        board = new int[size][size];
    }

    public void placeQueen(int row, int col) {
        board[row][col] = 1;
    }

    public void removeQueen(int row, int col) {
        board[row][col] = 0;
    }

    public boolean isSafe(int row, int col) {
        // Check this row on left side
        for (int i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        // Check upper diagonal on left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // Check lower diagonal on left side
        for (int i = row, j = col; j >= 0 && i < size; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public int[][] getBoard() {
        return board;
    }
}

// Class to implement the N-Queens solver using backtracking
class Solver {
    private Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean solve(int col) {
        if (col >= board.getSize()) {
            return true;
        }
        for (int i = 0; i < board.getSize(); i++) {
            if (board.isSafe(i, col)) {
                board.placeQueen(i, col);
                if (solve(col + 1)) {
                    return true;
                }
                board.removeQueen(i, col); // BACKTRACK
            }
        }
        return false;
    }

    public void displaySolution() {
        for (int[] row : board.getBoard()) {
            for (int value : row) {
                System.out.print(value == 1 ? "Q " : ". ");
            }
            System.out.println();
        }
    }
}

// Class to visualize the N-Queens solution using a GUI
public class NQueensVisualizer extends JPanel {
    private Board board;

    public NQueensVisualizer(Board board) {
        this.board = board;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = board.getSize();
        int cellSize = getWidth() / size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Draw chessboard cells
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

                // Draw the queens
                if (board.getBoard()[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(j * cellSize + 10, i * cellSize + 10, cellSize - 20, cellSize - 20);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Take input for board size from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the board (N): ");
        int n = scanner.nextInt();
        scanner.close();

        Board board = new Board(n);
        Solver solver = new Solver(board);

        if (solver.solve(0)) {
            solver.displaySolution();
        } else {
            System.out.println("No solution found.");
        }

        JFrame frame = new JFrame("N-Queens Visualizer");
        NQueensVisualizer visualizer = new NQueensVisualizer(board);
        frame.add(visualizer);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}