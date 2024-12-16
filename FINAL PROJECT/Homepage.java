import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Homepage implements ActionListener{
    private JLabel scoreLabel;

    Homepage(){
        JFrame frame = new JFrame("FLIP THE BOX");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon frameIcon = new ImageIcon("Icon.png"); 
        frame.setIconImage(frameIcon.getImage());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, 
            Toolkit.getDefaultToolkit().getScreenSize().height);

        ImageIcon backgroundIcon = new ImageIcon("bg.png");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width,
            Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);

        backgroundIcon = new ImageIcon(backgroundImage);
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0,0, Toolkit.getDefaultToolkit().getScreenSize().width, 
            Toolkit.getDefaultToolkit().getScreenSize().height);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        JButton play = new JButton("PLAY");
        play.setBounds(550, 300, 250, 100);
        play.setFont(new Font("Baskerville", Font.BOLD, 30));
        play.setBackground(Color.BLACK);
        play.setForeground(Color.WHITE);
        play.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE));
        play.addActionListener(this);
        layeredPane.add(play, JLayeredPane.PALETTE_LAYER);

        JButton exit = new JButton("EXIT");
        exit.setBounds(550, 500, 250, 100);
        exit.setFont(new Font("Baskerville", Font.BOLD, 30));
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.WHITE);
        exit.setBorder(BorderFactory.createMatteBorder(4,4,4,4,Color.ORANGE));
        exit.addActionListener(this);
        layeredPane.add(exit, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane); 
        frame.setVisible(true);
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getActionCommand().equals("PLAY")){
            new Category();
       } else {
            System.exit(0);
       }
    }
    public static void main(String[] args){
        new Homepage();
    } //maghimo lang guro kog file oy para scores kakapoy hahaha
}
