/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CustomBorder;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author namimac
 */
public class LoginPanel extends JPanel {
    //private JPanel contentPane; //declare variable
    private JPanel innerContentPane;
    private JTextField txtUser = null;
    private JPasswordField txtPass = null;
    private MainFrame _frame;
    
    public LoginPanel(MainFrame frame, QuizHomePanel redirect) //create constructor
    {
        JLabel lblUser;
        JLabel lblPass;
        JButton btnLogin;
        JButton btnBack;
        _frame = frame;
        
        //contentPane = new JPanel();
        innerContentPane = new JPanel();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/friendsbg.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Image dimg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        
        JPanel buttonsPane;
        buttonsPane = new JPanel();
        //set contentPane layout is null
        setLayout(new BorderLayout());
        
        //Create and populate the panel.
        innerContentPane.setLayout(new MigLayout());
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Impact", Font.PLAIN, 15));
        btnLogin.setBackground(new Color(0x0abfee));
        btnLogin.setForeground(Color.white);
        // customize the button with your own look
        btnLogin.setUI(new StyledButtonUI());
        
        btnLogin.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(txtPass.getPassword().length > 0){
                    if(!txtUser.getText().equalsIgnoreCase("")){
                        try {
                            Class.forName(MyConstants.JDBC_DRIVER);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Connection conn = null;
                        Statement stmt = null;
                        PreparedStatement preparedStatement;
                        MessageDigest messageDigest;
                        try {
                            conn = DriverManager.getConnection(MyConstants.DB_URL, MyConstants.USER, MyConstants.PASS);
                            stmt = conn.createStatement();
                            messageDigest = MessageDigest.getInstance("SHA-256");
                            messageDigest.update(String.valueOf(txtPass.getPassword()).getBytes());
                            String encryptedString = new String(messageDigest.digest());
                            
                            preparedStatement = conn.prepareStatement("SELECT name, password, score1, score2, score3, rank FROM users WHERE name = ? AND password = ?");
                            preparedStatement.setString(1, txtUser.getText());
                            preparedStatement.setString(2, encryptedString);
                            
                            ResultSet rs = preparedStatement.executeQuery();
                            while (rs.next()) {
                                String dbName = rs.getString("name");
                                String dbPass = rs.getString("password");
                                int dbRank = rs.getInt("rank");
                                int dbScore1 = rs.getInt("score1");
                                int dbScore2 = rs.getInt("score2");
                                int dbScore3 = rs.getInt("score3");
                                List<Integer> scores = new ArrayList<>();
                                scores.add(dbScore1);
                                scores.add(dbScore2);
                                scores.add(dbScore3);
                                User user = new User(dbName, dbPass);
                                user.setRank(dbRank);
                                user.setScore(scores);
                                user.setCurrentScore(0);
                                //Verified - Start Quiz Screen
                                
                                if(redirect != null){                
                                    QuizPanel quizPanel;
                                    try {
                                        quizPanel = new QuizPanel(_frame, Mode.PLAYER);
                                        quizPanel.setUser1(redirect.getUser1());
                                        quizPanel.setUser2(user);
                                        quizPanel.getQuestion(false);
                                        _frame.setContentPane(quizPanel);
                                        _frame.revalidate();
                                        _frame.repaint();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(QuizHomePanel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    QuizHomePanel quizHomePanel = new QuizHomePanel(_frame, user);
                                    _frame.setContentPane(quizHomePanel);
                                    _frame.revalidate();
                                    _frame.repaint();
                                }
                            }

                        } catch (SQLException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }				
        });
        
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Impact", Font.PLAIN, 15));
        btnBack.setBackground(new Color(0xe43f45));
        btnBack.setForeground(Color.white);
        // customize the button with your own look
        btnBack.setUI(new StyledButtonUI());
        
        btnBack.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                MainPanel mainPanel = new MainPanel(_frame);
                _frame.setContentPane(mainPanel);
                _frame.revalidate();
                _frame.repaint();
            }				
        });
        
        buttonsPane.add(btnBack);
        buttonsPane.add(btnLogin);
        
        
        lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Impact", Font.PLAIN, 15));
        txtUser = new JTextField(MyConstants.TEST_USER);
        txtUser.setFont(new Font("Impact", Font.PLAIN, 15));
        txtUser.setForeground(Color.LIGHT_GRAY);
        txtUser.setBorder(new CustomBorder(0, Color.WHITE));
        txtUser.setPreferredSize(new Dimension((MyConstants.WIDTH+MyConstants.WIDTH/6),40));
        
        lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Impact", Font.PLAIN, 15));
        txtPass = new JPasswordField(MyConstants.TEST_USER);
        txtPass.setForeground(Color.LIGHT_GRAY);
        txtPass.setBorder(new CustomBorder(0, Color.white));
        txtPass.setPreferredSize(new Dimension((MyConstants.WIDTH+MyConstants.WIDTH/6),40));
        
        innerContentPane.add(lblUser);
        innerContentPane.add(txtUser, "grow, wrap");
        innerContentPane.add(lblPass);
        innerContentPane.add(txtPass, "grow, wrap");
        //innerContentPane.add(new JLabel(mediumImage), "grow, wrap");
        
        innerContentPane.setBackground(Color.white);
        buttonsPane.setBackground(Color.white);
        setBackground(Color.white);
        add(innerContentPane, BorderLayout.NORTH);
        add(buttonsPane, BorderLayout.SOUTH);
        add(new JLabel(imageIcon), BorderLayout.CENTER);
        _frame.revalidate();
        _frame.repaint();
        txtUser.grabFocus();
    }
}
