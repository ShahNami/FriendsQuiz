/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import Model.MyConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author namimac
 */
public class MainFrame extends JFrame {
    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException // main method
    {
        Class.forName(MyConstants.JDBC_DRIVER);
                    
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() //define run method
            {
                try  //try block
                {
                    System.setProperty("apple.laf.useScreenMenuBar", "true");
                    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    mainFrame.setSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
                    mainFrame.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setResizable(false);
                    mainFrame.pack();     
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) //catch block
                {
                    //
		}
            }
	});
    }
    
    
    /**
     * Create the frame.
     */
    public MainFrame() //create constructor
    {	
        setTitle("Friends Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel mainPanel = new MainPanel(this);
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }
}
