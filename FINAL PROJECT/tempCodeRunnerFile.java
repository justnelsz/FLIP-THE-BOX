private void setupGameBoard() {
        // Create the card buttons and add them to the panel
        buttons = new JButton[20];
        for (int i = 0; i < 20; i++) {
            buttons[i] = createCardButton();
            String imagePath = foodImages.get(i);
            buttons[i].putClientProperty("imagePath", imagePath);
            cardPanel.add(buttons[i]);
        }
    
        // Set up the statsPanel for MOVES and MISSED
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.X_AXIS)); // Arrange labels horizontally
        movesLabel = new JLabel("MOVES: 0");
        missedLabel = new JLabel("MISSED: 0");
    
        statsPanel.add(movesLabel);
        statsPanel.add(Box.createHorizontalStrut(50)); // Add space between labels
        statsPanel.add(missedLabel);
    
        // Add components to the frame: timer bar, stats panel, and card panel
        frame.setLayout(new BorderLayout()); // Set the layout for the frame
        frame.add(timerBar, BorderLayout.NORTH); // Place the timer bar at the top
        frame.add(statsPanel, BorderLayout.CENTER); // Place the stats panel under the timer bar
        frame.add(cardPanel, BorderLayout.SOUTH); // Add the card panel at the bottom
    }