package src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Board extends JFrame  {
    private JButton Button;
    private JTextField Text;
    private JPanel Board;
    private JLabel label;
    public void mouseClicked(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        System.out.println(x+","+y);//these co-ords are relative to the component
    }
    public Board(){

        label.setPreferredSize(new Dimension(255,255));
        this.setTitle("Графы");
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("C:\\Users\\papla\\Downloads\\1631741917132_61068c72905ad2000f514d27.jpg"));
        } catch (Exception e){
            System.out.println(":-(");
        }
        Image dimg = img.getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_FAST);
        label.setIcon(new ImageIcon(dimg));
        this.setContentPane(Board);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,900);
        this.pack();
        this.setVisible(true);
        Button.addActionListener(e -> {
            Text.setText("45645");
        });

    }

}
