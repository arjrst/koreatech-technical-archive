import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private Snake snake;
    private Food food;
    private Timer gameLoop;
    private int boardWidth, boardHeight, tileSize;
    private int velocityX, velocityY;
    private boolean gameOver;

    public SnakeGame(int boardWidth, int boardHeight, int tileSize) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        snake = new Snake(5, 5);
        food = new Food();

        velocityX = 0;
        velocityY = 0;
        gameOver = false;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        //draw food
        g.setColor(Color.red);
        Tile foodTile = food.getTile();
        g.fill3DRect(foodTile.x * tileSize, foodTile.y * tileSize, tileSize, tileSize, true);

        //draw snake
        g.setColor(Color.green);
        g.fill3DRect(snake.getHead().x * tileSize, snake.getHead().y * tileSize, tileSize, tileSize, true);
        for (Tile bodyPart : snake.getBody()) {
            g.fill3DRect(bodyPart.x * tileSize, bodyPart.y * tileSize, tileSize, tileSize, true);
        }

        //draw score or game over message
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.white);
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over!", boardWidth / 2 - 60, boardHeight / 2 - 10);
            g.drawString("Score: " + snake.getBody().size(), boardWidth / 2 - 50, boardHeight / 2 + 20);
        } else {
            g.drawString("Score: " + snake.getBody().size(), 10, 20);
        }
    }

    private void checkCollision() {
        //check collision with body
        for (Tile bodyPart : snake.getBody()) {
            if (snake.getHead().isEqual(bodyPart)) {
                gameOver = true;
                return;
            }
        }

        //check collision with walls
        if (snake.getHead().x < 0 || snake.getHead().y < 0 ||
            snake.getHead().x >= boardWidth / tileSize || snake.getHead().y >= boardHeight / tileSize) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            if (snake.getHead().isEqual(food.getTile())) {
                snake.grow();
                food.placeFood();
            }
            snake.move(velocityX, velocityY);
            checkCollision();
        } else {
            gameLoop.stop();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if (velocityY != 1) {
                    velocityX = 0;
                    velocityY = -1;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (velocityY != -1) {
                    velocityX = 0;
                    velocityY = 1;
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (velocityX != 1) {
                    velocityX = -1;
                    velocityY = 0;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (velocityX != -1) {
                    velocityX = 1;
                    velocityY = 0;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private class Tile {
        int x, y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isEqual(Tile other) {
            return this.x == other.x && this.y == other.y;
        }
    }

    private class Snake {
        private Tile head;
        private ArrayList<Tile> body;

        public Snake(int startX, int startY) {
            head = new Tile(startX, startY);
            body = new ArrayList<>();
        }

        public Tile getHead() {
            return head;
        }

        public ArrayList<Tile> getBody() {
            return body;
        }

        public void move(int velocityX, int velocityY) {
            if (!body.isEmpty()) {
                for (int i = body.size() - 1; i > 0; i--) {
                    body.get(i).x = body.get(i - 1).x;
                    body.get(i).y = body.get(i - 1).y;
                }
                body.get(0).x = head.x;
                body.get(0).y = head.y;
            }
            head.x += velocityX;
            head.y += velocityY;
        }

        public void grow() {
            body.add(new Tile(head.x, head.y));
        }
    }

    private class Food {
        private Tile tile;
        private Random random;

        public Food() {
            random = new Random();
            placeFood();
        }

        public Tile getTile() {
            return tile;
        }

        public void placeFood() {
            int x = random.nextInt(boardWidth / tileSize);
            int y = random.nextInt(boardHeight / tileSize);
            tile = new Tile(x, y);
        }
    }
}
