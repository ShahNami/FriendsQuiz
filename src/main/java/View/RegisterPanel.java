/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CustomBorder;
import Model.MyConstants;
import Model.StyledButtonUI; 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import layout.SpringUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author namimac
 */
public class RegisterPanel extends JPanel {
    //private JPanel contentPane; //declare variable
    private JPanel innerContentPane;
    private JTextField txtUser = null;
    private JPasswordField txtPass = null;
    private JPasswordField txtCPass = null;
    private MainFrame _frame;
    
    public RegisterPanel(MainFrame frame) //create constructor
    { 
        JLabel lblUser;
        JLabel lblPass;
        JLabel lblCPass;
        JButton btnSignup;
        JButton btnBack;
        _frame = frame;
           
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
        //setContentPane();
        
        JPanel buttonsPane;
        buttonsPane = new JPanel();
        //set contentPane layout is null
        setLayout(new BorderLayout());
        
        //Create and populate the panel.
        innerContentPane.setLayout(new MigLayout());
        btnSignup = new JButton("Sign Up");
        btnSignup.setFont(new Font("Impact", Font.PLAIN, 15));
        btnSignup.setBackground(new Color(0xfac64c));
        btnSignup.setForeground(Color.white);
        // customize the button with your own look
        btnSignup.setUI(new StyledButtonUI());
        
        btnSignup.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(txtPass.getPassword().length > 0 && Arrays.equals(txtPass.getPassword(), txtCPass.getPassword())){
                    if(!txtUser.getText().equalsIgnoreCase("")){
                        try {
                            Class.forName(MyConstants.JDBC_DRIVER);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Connection conn = null;
                        Statement stmt = null;
                        PreparedStatement preparedStatement;
                        try {
                            MessageDigest messageDigest;
                            try {
                                conn = DriverManager.getConnection(MyConstants.DB_URL, MyConstants.USER, MyConstants.PASS);
                                stmt = conn.createStatement();
                                messageDigest = MessageDigest.getInstance("SHA-256");
                                messageDigest.update(String.valueOf(txtPass.getPassword()).getBytes());
                                String encryptedString = new String(messageDigest.digest());
                                preparedStatement = conn.prepareStatement("INSERT INTO  users values (default, ?, ?, ?, ?, ?, ?)");
                                preparedStatement.setString(1, txtUser.getText());
                                preparedStatement.setString(2, encryptedString);
                                preparedStatement.setInt(3, 0);
                                preparedStatement.setInt(4, 0);
                                preparedStatement.setInt(5, 0);
                                preparedStatement.setInt(6, 0);
                                preparedStatement.executeUpdate();
                                LoginPanel loginPanel = new LoginPanel(_frame, null);
                                _frame.setContentPane(loginPanel);
                                _frame.revalidate();
                                _frame.repaint();
                            } catch (SQLIntegrityConstraintViolationException ex){
                                JOptionPane.showMessageDialog(_frame, "An entry with this username already exists.", "Woops!", 2);
                            } catch (SQLException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        buttonsPane.add(btnSignup);
        
        lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Impact", Font.PLAIN, 15));
        txtUser = new JTextField();
        txtUser.setFont(new Font("Impact", Font.PLAIN, 15));
        txtUser.setForeground(Color.LIGHT_GRAY);
        txtUser.setBorder(new CustomBorder(0, Color.WHITE));
        txtUser.setPreferredSize(new Dimension((MyConstants.WIDTH+MyConstants.WIDTH/6),40));
        
        lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Impact", Font.PLAIN, 15));
        txtPass = new JPasswordField();
        txtPass.setForeground(Color.LIGHT_GRAY);
        txtPass.setBorder(new CustomBorder(0, Color.white));
        txtPass.setPreferredSize(new Dimension((MyConstants.WIDTH+MyConstants.WIDTH/6),40));
        
        lblCPass = new JLabel("Password");
        lblCPass.setFont(new Font("Impact", Font.PLAIN, 15));
        txtCPass = new JPasswordField();
        txtCPass.setForeground(Color.LIGHT_GRAY);
        txtCPass.setBorder(new CustomBorder(0, Color.white));
        txtCPass.setPreferredSize(new Dimension((MyConstants.WIDTH+MyConstants.WIDTH/6),40));
        
        innerContentPane.add(lblUser);
        innerContentPane.add(txtUser, "grow, wrap");
        innerContentPane.add(lblPass);
        innerContentPane.add(txtPass, "grow, wrap");
        innerContentPane.add(lblCPass);
        innerContentPane.add(txtCPass, "grow, wrap");
        
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
