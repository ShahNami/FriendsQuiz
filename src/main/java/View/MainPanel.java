/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.MyConstants;
import Model.StyledButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author namimac
 */
public class MainPanel extends JPanel {
    private MainFrame _frame;
    private JPanel innerContentPane;
    
    public MainPanel(MainFrame frame){
        JButton btnSignup;
        JButton btnLogin;
        innerContentPane = new JPanel();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        _frame = frame;
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
        setLayout(new BorderLayout());
        
        btnSignup = new JButton("Sign Up");
        btnSignup.setFont(new Font("Impact", Font.PLAIN, 15));
        btnSignup.setBackground(new Color(0xfac64c));
        btnSignup.setForeground(Color.white);
        btnSignup.setUI(new StyledButtonUI());
        
        btnSignup.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                RegisterPanel registerPanel = new RegisterPanel(_frame);
                _frame.setContentPane(registerPanel);
                _frame.revalidate();
                _frame.repaint();
            }				
        });

        innerContentPane.add(btnSignup);	
        
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Impact", Font.PLAIN, 15));
        btnLogin.setBackground(new Color(0x0abfee));
        btnLogin.setForeground(Color.white);
        btnLogin.setUI(new StyledButtonUI());
        
        btnLogin.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                LoginPanel loginPanel = new LoginPanel(_frame, null);
                _frame.setContentPane(loginPanel);
                _frame.revalidate();
                _frame.repaint();
            }				
        });
        innerContentPane.add(btnLogin);		
        innerContentPane.setBackground(Color.white);
        setBackground(Color.white);
        add(innerContentPane, BorderLayout.SOUTH);
        add(new JLabel(imageIcon), BorderLayout.CENTER);
        _frame.revalidate();
        _frame.repaint();
    }
}
