import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

class ShoeGame implements ActionListener {
    private JFrame frame;
    private JPanel cardPanel, statsPanel;
    private ArrayList<String> shoeImages;
    private JButton[] buttons;
    private String firstCard, secondCard;
    private JButton firstButton, secondButton;
    private int pairsFound = 0;
    private int moves = 0, missed = 0, score;

    private JLabel movesLabel, missedLabel, scoreLabel;
    private JProgressBar timerBar; // Timer bar
    private Timer timer;
    private int timeRemaining = 200; // Total time in "progress units"

    ShoeGame() {
        frame = new JFrame("FLIP THE BOX");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("Icon.png");
        frame.setIconImage(frameIcon.getImage());

        cardPanel = new JPanel(new GridLayout(5, 4, 10, 10)); // GridLayout for cards
        loadImages();
        setupTimerBar(); // Add timer bar at the top
        setupGameBoard();

        frame.setVisible(true);
        startTimer(); // Start the timer
    }

    // Load images into the array
    private void loadImages() {
        shoeImages = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String imagePath = "shoeimages/s" + i + ".png";
            shoeImages.add(imagePath);
            shoeImages.add(imagePath);
        }
        Collections.shuffle(shoeImages);
    }

    private void setupTimerBar() {
        timerBar = new JProgressBar(0, 120); 
        timerBar.setValue(timeRemaining);
        timerBar.setForeground(Color.GREEN);
        timerBar.setBackground(Color.LIGHT_GRAY);
        timerBar.setStringPainted(false); // Hide text on the bar
    }

    private void setupGameBoard() {
        buttons = new JButton[20];
        for (int i = 0; i < 20; i++) {
            buttons[i] = createCardButton();
            String imagePath = shoeImages.get(i);
            buttons[i].putClientProperty("imagePath", imagePath);
            cardPanel.add(buttons[i]);
        }

        statsPanel = new JPanel();
        movesLabel = new JLabel("MOVES: 0");
        missedLabel = new JLabel("MISSED: 0");
        scoreLabel = new JLabel("SCORE: 0");

        statsPanel.add(movesLabel);
        statsPanel.add(new JLabel("    "));
        statsPanel.add(missedLabel);
        statsPanel.add(new JLabel("    "));
        statsPanel.add(scoreLabel);

        frame.setLayout(new BorderLayout()); 
        frame.add(timerBar, BorderLayout.NORTH); 
        frame.add(statsPanel, BorderLayout.CENTER); 
        frame.add(cardPanel, BorderLayout.SOUTH); 
    }

    private JButton createCardButton() {
        JButton button = new JButton();
        button.setBackground(Color.RED);
        button.setPreferredSize(new Dimension(120, 120));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        button.addActionListener(this);
        return button;
    }

    private void startTimer() {
        timeRemaining = 40;
        timerBar.setMaximum(40);
        timerBar.setValue(timeRemaining);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeRemaining -= 1;
                timerBar.setValue(timeRemaining);

                // Change bar color as time decreases
                if (timeRemaining <= 30) {
                    timerBar.setForeground(Color.ORANGE);
                }
                if (timeRemaining <= 10) {
                    timerBar.setForeground(Color.RED);
                }

                if (timeRemaining <= 0) {
                    timer.cancel();
                    gameOver();
                }
            }
        },0, 900); // Update every 100 milliseconds
    }

    public void saveScore(String username, String password, int score) {
        try (BufferedReader reader = new BufferedReader(new FileReader("useraccounts.txt"))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            boolean updated = false;
    
            // Read existing lines and update the score for the matching username and password
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    fileContent.append(username).append(", ").append(password).append(", ").append(score).append("\n");
                    updated = true; // Mark that we've updated the score for this account
                } else {
                    fileContent.append(line).append("\n");
                }
            }
    
            if (!updated) {
                fileContent.append(username).append(", ").append(password).append(", ").append(score).append("\n");
            }
    
            try (FileWriter writer = new FileWriter("useraccounts.txt")) {
                writer.write(fileContent.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int loadScore(String username, String password) {
        int savedScore = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("useraccounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    savedScore = Integer.parseInt(parts[2]); // Extract the score for the matching account
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedScore;
    }
    

    private void gameOver() {
        JOptionPane.showMessageDialog(frame, "Time's Up! Game Over.\nYour Score is: " + score);
        frame.dispose();
        new Homepage();
    }

    @Override
public void actionPerformed(ActionEvent e) {
    JButton clickedButton = (JButton) e.getSource();

    if (clickedButton.getIcon() != null) return; // Ignore already flipped cards

    String imagePath = (String) clickedButton.getClientProperty("imagePath");

    if (firstCard == null) {
        firstCard = imagePath;
        firstButton = clickedButton;
        showImage(clickedButton, firstCard);
    } else if (secondCard == null && clickedButton != firstButton) {
        secondCard = imagePath;
        secondButton = clickedButton;
        showImage(clickedButton, secondCard);

        Timer matchCheckTimer = new Timer();
        matchCheckTimer.schedule(new TimerTask() {
            public void run() {
                if (firstCard.equals(secondCard)) {
                    firstButton.removeActionListener(ShoeGame.this);
                    secondButton.removeActionListener(ShoeGame.this);
                    score += 10; // points
                    SwingUtilities.invokeLater(() -> scoreLabel.setText("SCORE: " + score));

                    pairsFound++;
                    if (pairsFound == 10) {
                        timer.cancel();
                        JOptionPane.showMessageDialog(frame, "Congratulations! You Won!\nYour Score is: " + score);
                        frame.dispose();
                        new Homepage();
                    }
                } else {
                    hideImage(firstButton);
                    hideImage(secondButton);
                    missed++;
                    // Update MISSED label in the UI
                    SwingUtilities.invokeLater(() -> missedLabel.setText("MISSED: " + missed));
                }
                resetTurn();
            }
        }, 1000); // Delay before checking match

        moves++;
        // Update MOVES label in the UI
        SwingUtilities.invokeLater(() -> movesLabel.setText("MOVES: " + moves));
    }
}


    private void resetTurn() {
        firstCard = null;
        secondCard = null;
        firstButton = null;
        secondButton = null;
    }

    private void showImage(JButton button, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
    }

    private void hideImage(JButton button) {
        button.setIcon(null);
        button.setBackground(Color.RED);
    }
    public static void main(String[] args) {
        new ShoeGame(); 
    }
}
