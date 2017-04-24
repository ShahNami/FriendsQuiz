/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Mode;
import Model.MyConstants;
import Model.StyledButtonUI;
import Model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author namimac
 */
public class QuizHomePanel extends JPanel {
    private JPanel innerContentPane;
    private MainFrame _frame;
    private User _user;
    private User _user2;
    
    public QuizHomePanel(MainFrame frame, User user) //create constructor
    {
        JButton btnPlayer;
        JButton btnComputer;
        JButton btnYourself;
        _frame = frame;
        _user = user;
        innerContentPane = new JPanel();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/friendsbgsmall.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Image dimg = img.getScaledInstance(300, 62, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        //setContentPane();
        setLayout(new BorderLayout());
        
        //Create and populate the panel.
        innerContentPane.setLayout(new BoxLayout(innerContentPane, BoxLayout.Y_AXIS));
        btnPlayer = new JButton("Player");
        btnPlayer.setFont(new Font("Impact", Font.PLAIN, 25));
        //btnPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnPlayer.setBackground(new Color(0xf2c244));
        btnPlayer.setForeground(Color.white);
        // customize the button with your own look
        btnPlayer.setUI(new StyledButtonUI(new Dimension(MyConstants.WIDTH/2, MyConstants.WIDTH/2/3)));
        
        btnPlayer.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                //Login with second User
                LoginPanel loginPanel = new LoginPanel(_frame, QuizHomePanel.this);
                _frame.setContentPane(loginPanel);
                _frame.revalidate();
                _frame.repaint();
            }				
        });
        
        btnComputer = new JButton("Computer");
        btnComputer.setFont(new Font("Impact", Font.PLAIN, 25));
        //btnComputer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComputer.setBackground(new Color(0x44a6fd));
        btnComputer.setForeground(Color.white);
        // customize the button with your own look
        btnComputer.setUI(new StyledButtonUI(new Dimension(MyConstants.WIDTH/2, MyConstants.WIDTH/2/3)));
        
        btnComputer.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                _user2 = new User("Computer", "Computer");
                QuizPanel quizPanel;
                try {
                    quizPanel = new QuizPanel(_frame, Mode.COMPUTER);
                    quizPanel.setUser1(_user);
                    quizPanel.setUser2(_user2);
                    quizPanel.getQuestion(false);
                    _frame.setContentPane(quizPanel);
                    _frame.revalidate();
                    _frame.repaint();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }				
        });
        
        btnYourself = new JButton("Prepare");
        btnYourself.setFont(new Font("Impact", Font.PLAIN, 25));
        //btnYourself.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnYourself.setBackground(new Color(0xe3513a));
        btnYourself.setForeground(Color.white);
        // customize the button with your own look
        btnYourself.setUI(new StyledButtonUI(new Dimension(MyConstants.WIDTH/2, MyConstants.WIDTH/2/3)));
        btnYourself.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                QuizPanel quizPanel;
                try {
                    quizPanel = new QuizPanel(_frame, Mode.PRACTISE);
                    quizPanel.setUser1(_user);
                    quizPanel.getQuestion(false);
                    _frame.setContentPane(quizPanel);
                    _frame.revalidate();
                    _frame.repaint();
                } catch (SQLException ex) {
                    Logger.getLogger(QuizHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }				
        });
        
        //Try to make these boxes same size
        
        innerContentPane.add(Box.createRigidArea(new Dimension((MyConstants.WIDTH - (MyConstants.WIDTH/2))/2 , MyConstants.WIDTH/4)));
        innerContentPane.add(btnComputer);
        innerContentPane.add(Box.createRigidArea(new Dimension((MyConstants.WIDTH - (MyConstants.WIDTH/2))/2, 30)));
        innerContentPane.add(btnPlayer);
        innerContentPane.add(Box.createRigidArea(new Dimension((MyConstants.WIDTH - (MyConstants.WIDTH/2))/2, 30)));
        innerContentPane.add(btnYourself);
        
        innerContentPane.setBackground(Color.white);
        setBackground(Color.white);
        add(innerContentPane, BorderLayout.CENTER);
        add(new JLabel(imageIcon), BorderLayout.NORTH);
        _frame.revalidate();
        _frame.repaint();
    }
    
    public void setUser1(User user){
        _user = user;
    }
    
    public void setUser2(User user){
        _user2 = user;
    }
    
    public User getUser1(){
        return _user;
    }
    
    public User getUser2(){
        return _user2;
    }
}
