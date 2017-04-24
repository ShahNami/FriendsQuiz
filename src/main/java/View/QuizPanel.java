/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.CustomBorder;
import Model.Difficulty;
import Model.JMultilineLabel;
import Model.Mode;
import Model.MyConstants;
import Model.Question;
import Model.StyledButtonUI;
import Model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

/**
 *
 * @author namimac
 */

public class QuizPanel extends JPanel {
    
     //private JPanel contentPane; //declare variable
    private JPanel innerContentPane;
    private MainFrame _frame;
    private Mode _mode;
    private List<Question> _questions; 
    private int _questionNr;
    private int _correct;
    private JLabel _answer;
    private User _user1;
    private User _user2;
    private JButton btnSubmit;
    private JButton btnSkip;
    private JButton btnCheat;
    
    public QuizPanel(MainFrame frame, Mode mode) throws SQLException //create constructor
    {
        _questionNr = 0;
        _questions = new ArrayList<>();
        _frame = frame;
        _mode = mode;
        _answer = new JLabel();
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
        btnSubmit = new JButton("Submit");
        btnSubmit.setFont(new Font("Impact", Font.PLAIN, 15));
        btnSubmit.setBackground(new Color(0x0abfee));
        btnSubmit.setForeground(Color.white);
        // customize the button with your own look
        btnSubmit.setUI(new StyledButtonUI());
        
        btnSubmit.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                getQuestion(false);
            }
        });
        
        btnCheat = new JButton("Cheat");
        btnCheat.setFont(new Font("Impact", Font.PLAIN, 15));
        btnCheat.setBackground(new Color(0xfac64c));
        btnCheat.setForeground(Color.white);
        // customize the button with your own look
        btnCheat.setUI(new StyledButtonUI());
        
        btnCheat.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {                        
                if(mode == Mode.PLAYER){
                    JOptionPane.showMessageDialog(_frame, "You can't cheat while playing against someone else!", "Nope!", 3);                
                } else {
                    showAnswer();
                }
            }
        });
        
        btnSkip = new JButton("Skip");
        btnSkip.setFont(new Font("Impact", Font.PLAIN, 15));
        btnSkip.setBackground(new Color(0xe43f45));
        btnSkip.setForeground(Color.white);
        // customize the button with your own look
        btnSkip.setUI(new StyledButtonUI());
        
        btnSkip.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                getQuestion(true);
            }				
        });
        
        buttonsPane.add(btnSubmit);
        buttonsPane.add(btnCheat);
        buttonsPane.add(btnSkip);
        try {
            Class.forName(MyConstants.JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement preparedStatement;
        conn = DriverManager.getConnection(MyConstants.DB_URL, MyConstants.USER, MyConstants.PASS);
        stmt = conn.createStatement();
                              
        try {
            preparedStatement = conn.prepareStatement("SELECT question, answer, option1, option2, option3 FROM questions");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                List<String> options = new ArrayList<>();
                options.add(option1);
                options.add(option2);
                options.add(option3);
                Question q = new Question(question, answer, options);
                _questions.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuizPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        innerContentPane.setBackground(Color.white);
        buttonsPane.setBackground(Color.white);
        setBackground(Color.white);
        add(innerContentPane, BorderLayout.NORTH);
        add(buttonsPane, BorderLayout.SOUTH);
        add(new JLabel(imageIcon), BorderLayout.CENTER);
        _frame.revalidate();
        _frame.repaint();
    }
    
    public void showAnswer(){
        innerContentPane.remove(_answer);
        _answer = new JLabel("The answer is: " + _questions.get(_questionNr-1).getAnswer());
        innerContentPane.add(_answer, "grow, wrap");
        _frame.revalidate();
        _frame.repaint();
    }
    
    public void getQuestion(boolean skipped){
        if(_questionNr > 0 && !skipped){
            if(_mode != Mode.PRACTISE){
                if(_questionNr % 2 == 0){
                    //Player 1
                    _user1.setCurrentScore(_user1.getCurrentScore()+_correct);
                } else {
                    //Player 2 || Computer
                    _user2.setCurrentScore(_user2.getCurrentScore()+_correct);
                }
            } else {
                _user1.setCurrentScore(_user1.getCurrentScore()+_correct);
            }
        }
        _correct = 0;
        if(_questionNr == _questions.size()){
            if(_mode != Mode.PRACTISE){
                JOptionPane.showMessageDialog(_frame, "That was it! Thank you for playing!\n"+_user1.getName()+" has a score of: "+_user1.getCurrentScore()+"\n"+_user2.getName()+" has a score of: "+_user2.getCurrentScore(), "Congratulations!", 3);                
                saveUserScore(_user1);
                if(_mode != Mode.COMPUTER){
                    saveUserScore(_user2);
                }
                _user2.setCurrentScore(0);
            } else {
                JOptionPane.showMessageDialog(_frame, "That was it! Thank you for playing!\nYour score is: "+_user1.getCurrentScore()+"", "Congratulations!", 3);                
            }
            
            _user1.setCurrentScore(0);
            QuizHomePanel quizHomePanel = new QuizHomePanel(_frame, _user1);
            _frame.setContentPane(quizHomePanel);
            _frame.revalidate();
            _frame.repaint();
        } else {
            //Check Answer
            JMultilineLabel lblQuestion;
            lblQuestion = new JMultilineLabel(_questions.get(_questionNr).getQuestion());
            lblQuestion.setFont(new Font("Impact", Font.ITALIC, 14));
            lblQuestion.setSize(new Dimension(MyConstants.WIDTH, 20));

            JRadioButton option1 = new JRadioButton(_questions.get(_questionNr).getAnswer());
            option1.setMnemonic(KeyEvent.VK_1);
            option1.setActionCommand("answer");

            JRadioButton option2 = new JRadioButton(_questions.get(_questionNr).getOptions().get(0));
            option2.setMnemonic(KeyEvent.VK_2);

            JRadioButton option3 = new JRadioButton(_questions.get(_questionNr).getOptions().get(1));
            option3.setMnemonic(KeyEvent.VK_3);

            JRadioButton option4 = new JRadioButton(_questions.get(_questionNr).getOptions().get(2));
            option4.setMnemonic(KeyEvent.VK_4);

            //Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(option1);
            group.add(option2);
            group.add(option3);
            group.add(option4);

            List<JRadioButton> shuffleList = new ArrayList<>();
            shuffleList.add(option1);
            shuffleList.add(option2);
            shuffleList.add(option3);
            shuffleList.add(option4);
            long seed = System.nanoTime();
            Collections.shuffle(shuffleList, new Random(seed));

            innerContentPane.removeAll();
            innerContentPane.add(lblQuestion, "grow, wrap");
            innerContentPane.add(shuffleList.get(0), "grow, wrap");
            innerContentPane.add(shuffleList.get(1), "grow, wrap");
            innerContentPane.add(shuffleList.get(2), "grow, wrap");
            innerContentPane.add(shuffleList.get(3), "grow, wrap");

            if(_mode != Mode.PRACTISE){
                if(_questionNr % 2 == 0){
                    //Player 1
                    _answer = new JLabel("Turn: "+ _user2.getName());
                } else {
                    //Player 2 || Computer
                    _answer = new JLabel("Turn: "+ _user1.getName());
                }
                innerContentPane.add(_answer, "grow, wrap");
            }
            _frame.revalidate();
            _frame.repaint();
            
            option1.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    _correct = 1;
                }
            });
            option2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    _correct = 0;
                }
            });
            option3.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    _correct = 0;
                }
            });
            option4.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    _correct = 0;
                }
            });

            if(_mode == Mode.COMPUTER && _questionNr % 2 == 0){
                btnSubmit.setEnabled(false);
                btnCheat.setEnabled(false);
                btnSkip.setEnabled(false);
                int min = 1;
                int max = 4;
                switch(MyConstants.DIFFICULTY){
                    case EASY:
                        min = 1;
                        max = 4;
                        break;
                        
                    case MEDIUM:
                        min = 1;
                        max = 3;
                        break;
                        
                    case HARD:
                        min = 1;
                        max = 2;
                        break;
                        
                    default:
                        break;
                    
                }
                int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
                String computerOption = _questions.get(_questionNr).getAllOptions().get(randomNum-1);
                for(int i=0;i<shuffleList.size();i++){
                    if(shuffleList.get(i).getText().equalsIgnoreCase(computerOption)){
                        shuffleList.get(i).doClick();
                        //break;
                    }
                    shuffleList.get(i).setEnabled(false);
                }

                Timer timer = new Timer(MyConstants.COMPUTER_CHOICE_DELAY, new ActionListener(){
                  @Override
                  public void actionPerformed( ActionEvent e ){
                    btnSubmit.setEnabled(true);
                    btnSubmit.doClick();
                  }
                });
                timer.setRepeats(false);
                timer.start();
            } else if(_mode == Mode.COMPUTER && _questionNr % 2 != 0) {
                btnSubmit.setEnabled(true);
                btnCheat.setEnabled(true);
                btnSkip.setEnabled(true);
            }
            
            if(_questionNr != _questions.size()){
                _questionNr++;
            }
        }

    }
    
    public void setUser1(User user){
        this._user1 = user;
    }
    public void setUser2(User user){
        this._user2 = user;
    }
    
    public void saveUserScore(User user){
        try {
            Connection conn = null;
            Statement stmt = null;
            PreparedStatement preparedStatement;
            conn = DriverManager.getConnection(MyConstants.DB_URL, MyConstants.USER, MyConstants.PASS);
            stmt = conn.createStatement();
            if(user.getScore().get(0) < user.getCurrentScore()){
                preparedStatement = conn.prepareStatement( "UPDATE users SET score1 = ? WHERE name = ?");
                preparedStatement.setInt(1, user.getCurrentScore());
                preparedStatement.setString(2, user.getName());
                preparedStatement.executeUpdate();        
            } else if(user.getScore().get(1) < user.getCurrentScore()){
                preparedStatement = conn.prepareStatement( "UPDATE users SET score2 = ? WHERE name = ?");
                preparedStatement.setInt(1, user.getCurrentScore());
                preparedStatement.setString(2, user.getName());
                preparedStatement.executeUpdate();        
            } else if(user.getScore().get(2) < user.getCurrentScore()){
                preparedStatement = conn.prepareStatement( "UPDATE users SET score3 = ? WHERE name = ?");
                preparedStatement.setInt(1, user.getCurrentScore());
                preparedStatement.setString(2, user.getName());
                preparedStatement.executeUpdate();        
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(QuizPanel.class.getName()).log(Level.SEVERE, null, ex);                       
        }
    }
}
