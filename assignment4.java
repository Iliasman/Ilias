import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int SNAKE_SPEED = 10;
    private static final int SNAKE_BLOCK = 10;
    private static final int DIS_WIDTH = 600;
    private static final int DIS_HEIGHT = 400;
    private int x1, y1;
    private int foodx, foody;
    private ArrayList<ArrayList<Integer>> snakeList;
    private int score;
    private boolean gameOver;
    private Timer timer;
    private int snakeSpeed;

    public SnakeGame() {
        this.setPreferredSize(new Dimension(DIS_WIDTH, DIS_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                for (int i = 0; i < 4; i++) {
                    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                        if (i == 0 && X_CHANGE[0] != 0 && Y_CHANGE[0] != 0 || i == 1 && X_CHANGE[1] != 0 && Y_CHANGE[1] != 0 || i == 2 && X_CHANGE[2] != 0 && Y_CHANGE[2] != 0 || i == 3 && X_CHANGE[3] != 0 && Y_CHANGE[3] != 0) {
                            X_CHANGE[0] = 0;
                            Y_CHANGE[0] = 0;
                            X_CHANGE[1] = 0;
                            Y_CHANGE[1] = 0;
                            X_CHANGE[2] = 0;
                            Y_CHANGE[2] = 0;
                            X_CHANGE[3] = 0;
                            Y_CHANGE[3] = 0;
                            X_CHANGE[i] = X_CHANGE[i];
                            Y_CHANGE[i] = Y_CHANGE[i];
                        }
                    }
                }
            }
        });
        startGame();
    }

    private void startGame() {
        x1 = DIS_WIDTH / 2;
        y1 = DIS_HEIGHT / 2;
        foodx = getRandomFoodX();
        foody = getRandomFoodY();
        snakeList = new ArrayList<>();
        snakeList.add(new ArrayList<>(Arrays.asList(x1, y1)));
        score = 0;
        gameOver = false;
        snakeSpeed = SNAKE_SPEED;
        timer = new Timer(snakeSpeed, this);
        timer.start();
    }

    private int getRandomFoodX() {
        return (int) (Math.random() * (DIS_WIDTH / SNAKE_BLOCK)) * SNAKE_BLOCK;
    }

    private int getRandomFoodY() {
        return (int) (Math.random() * (DIS_HEIGHT / SNAKE_BLOCK)) * SNAKE_BLOCK;
    }

    private void draw(Graphics g) {
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 150, 250);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 150, 300);
            return;
        }

        g.setColor(Color.WHITE);
        g.fillRect(foodx, foody, SNAKE_BLOCK, SNA
                // Draw Snake
        for (ArrayList<Integer> snakeBlock : snakeList) {
            g.fillRect(snakeBlock.get(0), snakeBlock.get(1), SNAKE_BLOCK, SNAKE_BLOCK);
        }

        // Draw Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 20, 30);
    }

    private void move() {
        for (int i = snakeList.size() - 1; i > 0; i--) {
            snakeList.set(i, new ArrayList<>(snakeList.get(i - 1)));
        }

        snakeList.get(0).set(0, snakeList.get(0).get(0) + X_CHANGE[0]);
        snakeList.get(0).set(1, snakeList.get(0).get(1) + Y_CHANGE[0]);
    }

    private void checkCollision() {
        // Check if head collides with body
        for (int i = 1; i < snakeList.size(); i++) {
            if (snakeList.get(0).get(0).equals(snakeList.get(i).get(0)) && snakeList.get(0).get(1).equals(snakeList.get(i).get(1))) {
                gameOver = true;
                break;
            }
        }

        // Check if head touches border
        if (snakeList.get(0).get(0) < 0 || snakeList.get(0).get(0) >= DIS_WIDTH || snakeList.get(0).get(1) < 0 || snakeList.get(0).get(1) >= DIS_HEIGHT) {
            gameOver = true;
        }

        // Check if head touches food
        if (snakeList.get(0).get(0).equals(foodx) && snakeList.get(0).get(1).equals(foody)) {
            // Increase score
            score++;
            // Generate new food
            foodx = getRandomFoodX();
            foody = getRandomFoodY();
            // Add new block to the snake
            snakeList.add(new ArrayList<>(snakeList.get(snakeList.size() - 1)));
            // Increase speed
            if (snakeSpeed > 20) {
                snakeSpeed -= 5;
                timer.setDelay(snakeSpeed);
            }
        }
    }

    private static final int[] X_CHANGE = {0, SNAKE_BLOCK, -SNAKE_BLOCK, 0};
    private static final int[] Y_CHANGE = {SNAKE_BLOCK, 0, 0, -SNAKE_BLOCK};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame snakeGame = new SnakeGame();
        frame.add(snakeGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
