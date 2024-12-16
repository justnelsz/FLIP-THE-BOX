import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Category implements ActionListener{
    Category(){
        JFrame frame = new JFrame("FLIP THE BOX");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("Icon.png"); 
        frame.setIconImage(frameIcon.getImage());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
            Toolkit.getDefaultToolkit().getScreenSize().height);

        ImageIcon backgroundIcon = new ImageIcon("category.png");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);

        backgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0,0, Toolkit.getDefaultToolkit().getScreenSize().width, 
            Toolkit.getDefaultToolkit().getScreenSize().height);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        JButton backButton = new JButton("BACK");
        backButton.setBounds(1250, 30, 80, 60);
        backButton.setFont(new Font("Baskerville", Font.BOLD, 20));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.ORANGE));
        backButton.addActionListener(this);
        layeredPane.add(backButton, JLayeredPane.PALETTE_LAYER);
    
        JButton foods = new JButton("FOODS");
        foods.setBounds(150, 610, 150, 80);
        foods.setFont(new Font("Baskerville", Font.BOLD, 30));
        foods.setBackground(Color.BLACK);
        foods.setForeground(Color.WHITE);
        foods.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE));
        foods.addActionListener(this);
        layeredPane.add(foods, JLayeredPane.PALETTE_LAYER);

        JButton cars = new JButton("CARS");
        cars.setBounds(600, 610, 150, 80);
        cars.setFont(new Font("Baskerville", Font.BOLD, 30));
        cars.setBackground(Color.BLACK);
        cars.setForeground(Color.WHITE);
        cars.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE));
        cars.addActionListener(this);
        layeredPane.add(cars, JLayeredPane.PALETTE_LAYER);

        JButton shoes = new JButton("SHOES");
        shoes.setBounds(1060, 610, 150, 80);
        shoes.setFont(new Font("Baskerville", Font.BOLD, 30));
        shoes.setBackground(Color.BLACK);
        shoes.setForeground(Color.WHITE);
        shoes.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE));
        shoes.addActionListener(this);
        layeredPane.add(shoes, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane);
        frame.setVisible(true);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("FOODS")) {
            new FoodGame();  // Start the FoodGame when the "FOODS" button is clicked
        } else if (e.getActionCommand().equals("CARS")) {
            new CarGame();   // Start the CarGame when the "CARS" button is clicked
        } else if (e.getActionCommand().equals("SHOES")) {
            new ShoeGame();  // Start the ShoeGame when the "SHOES" button is clicked
        } else if (e.getActionCommand().equals("BACK")) {
            new Homepage();  // Navigate back to Homepage when the "BACK" button is clicked
        }
    }

    public static void main(String[] args){
        new Category();
    }
}
