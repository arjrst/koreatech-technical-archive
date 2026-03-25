import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardWidth = 600;
        int boardHeight = boardWidth; // Keeping board square-shaped
        int tileSize = 25;

        // Create the JFrame
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create the SnakeGame instance and add it to the frame
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight, tileSize);
        frame.add(snakeGame);

        // Set up the frame size and visibility
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);

        // Ensure SnakeGame panel gets focus for key inputs
        snakeGame.requestFocusInWindow();
    }
}


/*public class App {
    public static void main(String[] args) throws Exception {
        int boardWitdh=600;
        int boardHeight=boardWitdh;

        JFrame frame=new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWitdh,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame=new SnakeGame(boardWitdh, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}*/