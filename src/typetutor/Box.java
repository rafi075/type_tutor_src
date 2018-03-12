/*
 * box game done with button+score
 */
package typetutor;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javafx.application.ConditionalFeature;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author AUNY
 */
public class Box extends JPanel implements ActionListener, MouseListener
{

    //variable declaration
    Timer tbox = new Timer(600, this);
    int x = 300, y = 120;
    
    Random r= new  Random();
    
    boolean sound_on=true;
    int lastPosition = 420;//eta update hobe every time type korte na parle r new game dile/reset dile 420 hobe
    String errorString1, errorString2, errorString3, errorString4, errorString5;
    String wordList = "Duck Fox Peacock Cat Dog Lion Deer Crane Dove Baboon Bangladesh India Nepal Bhutan Myanmar Goat Cow Sheep Racoon Crocodile Nilgai Otter China Malaysia Thailand Monkey Panda Heron Rhinoceros Lizard Badger Squirrel Pigeon Hawk Antelope Beaver Elephant Tiger Ox Mule Donkey Horse Vulture";
    String[] words;
    int indexOfArray = 0;
    int underlineIndex = 0;
    boolean gameOver = false, gamePaused = false, gameRunning = false;
    int boxScore = 0, errorPenalty = 0, correct = 0;
    User temp;
    int temp1=0;

    //component variable declaration
    JLabel textInFallingBox, score, textBellow;
    JButton buttonPause, buttonReset, buttonStart;
    BufferedImage back;

    public Box()
    {
        this.words = wordList.split(" ");//array te neoa

        setLayout(null); //eta dile component er size bole deoa lagbe
        setBackground(Color.LIGHT_GRAY);//change for color
        textInFallingBox = new JLabel(words[indexOfArray]);
        textInFallingBox.setFont(new java.awt.Font("Tahoma", 0, 16));
        buttonPause = new JButton("Pause");
        buttonReset = new JButton("Reset");
        buttonStart = new JButton("Start");
        score = new JLabel("<html>" + "Click START and start typing before Word Falls" + "</html>");
        textBellow = new JLabel(words[indexOfArray]);
        textBellow.setFont(new java.awt.Font("Tahoma", 0, 16));
        
        try{
             back = ImageIO.read(new File("Box back.jpg"));
            
        }
        catch(IOException e)
        {
            
        }

        score.setBounds(860, 200, 420, 300);
        score.setFont(new java.awt.Font("Tahoma", 0, 20));
        textBellow.setBounds(300, 500, 250, 100);
        buttonReset.setBounds(1140, 600, 100, 40);
        buttonPause.setBounds(1000, 600, 100, 40);
        buttonStart.setBounds(860, 600, 100, 40);
        buttonPause.setFocusable(false);
        buttonReset.setFocusable(false);
        buttonStart.setFocusable(false);

        add(score);
        add(textInFallingBox);
        add(textBellow);
        add(buttonPause);
        add(buttonReset);
        add(buttonStart);

        //drawRecs();
        //event listeners
        buttonPause.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                if (gameOver == false && gameRunning == true)
                {
                    tbox.stop();
                    gamePaused = true;
                    gameRunning = false;
                    score.setText("Game paused, click Start to resume");
                }
            }
        });
        buttonStart.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {

                if (gameRunning == false)
                {
                    gameRunning = true;
                    if (gamePaused == false)
                    {
                        gameStart();
                    }

                    else
                    {
                        tbox.start();
                        gamePaused = false;
                    }
                    score.setText("Started");
                }
            }
        });
        buttonReset.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                resetState();
                score.setText("Reseted, click Start to start");
            }
        });

        //temp for mouse
        addMouseListener(this);

        //tbox.start();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(back, 0, 0, null);
        textInFallingBox.setBounds(310, y, 100, 30);
        g.setFont(new java.awt.Font("Tahoma", 0, 16));

        g.setColor(Color.DARK_GRAY);
        g.fill3DRect(250, 420, 300, 20, true);//lower rectangle
        g.fill3DRect(250, 270, 20, 150, true);//left
        g.fill3DRect(530, 270, 20, 150, true);//right

        g.setColor(Color.ORANGE);
        g.fill3DRect(275, y, 250, 30, true);//moving rectangle

        drawRecInBox(g);
    }

    public void actionPerformed(ActionEvent e)
    {
        y += 30;
        repaint();
        decreasingLastPosition();

    }

    //own methods
    public void eventhandling(KeyEvent evt)
    {

        if (gameOver == false && gamePaused == false && gameRunning==true && underlineIndex < words[indexOfArray].length() && evt.getKeyChar() == words[indexOfArray].charAt(underlineIndex))
        {
            underlineIndex++;
            changeText();
            correct++;
        }
        else
        {
            if(sound_on)
            {
                Sound.ERROR.play();
            }
            errorPenalty++;
        }

    }
    
    public void getReferenceBox(User current)
    {
        temp=current;
        
    }

    public void oneWordComplete()//type kore complete korle eita evoke hobe r normally hobe na type na korle
    {
        indexOfArray= r.nextInt(40);
        System.out.println(indexOfArray);
        if (indexOfArray == words.length)
        {
            gameComplete();
        }
        else
        {
            initiateBoxFall();
        }
    }

    public void gameComplete()//typing kore onewordcomplete or initiateboxfall e eita call hoi game complete kina dekhar jonno
    {
        gameOver = true;
        if(sound_on==true)
        {
        Sound.CLAP.play();
        }

        System.out.println("over by typing");
        tbox.stop();
        gameRunning = false;
        updateScore();
    }
    
    public void updateScore()
    {
        boxScore = (correct * 5) - (errorPenalty * 2);
        if(boxScore<0) boxScore=0;
        score.setText("your Score: " + boxScore);
        int i=temp.getUserboxScore();
        if(boxScore>i) temp.setUserboxScore(boxScore);
        
    }

    public void gameStart()
    {
        changeText();
        tbox.start();
        gameRunning = true;
        gamePaused = false;
        gameOver = false;
    }

    public void changeText()
    {
        String sub1;

        String sub2 = null;

        if (underlineIndex <= words[indexOfArray].length() - 1)
        {

            if (underlineIndex == words[indexOfArray].length() - 1)
            {
                sub2 = "";

            }
            else if (underlineIndex < words[indexOfArray].length() - 1)
            {
                sub2 = words[indexOfArray].substring(underlineIndex + 1);

            }
            sub1 = words[indexOfArray].substring(0, underlineIndex);//index lentgh+1 hole ektu vejal korbe
            textInFallingBox.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "<u>" + words[indexOfArray].charAt(underlineIndex) + "</u>" + sub2 + "</html>");
            textBellow.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "<u>" + words[indexOfArray].charAt(underlineIndex) + "</u>" + sub2 + "</html>");

        }
        else
        {
            sub1 = words[indexOfArray].substring(0);
            textBellow.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "</html>");
            textInFallingBox.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "</html>");
            oneWordComplete();
        }
        repaint();

    }

    public void resetState()//course er updtae string part ta
    {
        lastPosition = 420;
        underlineIndex = 0;
        y = 120;
        indexOfArray = 0;
        changeText();
        errorString1 = null;
        errorString2 = null;
        errorString3 = null;
        errorString4 = null;
        errorString5 = null;
        gameOver = false;

        correct = 0;
        boxScore = 0;
        errorPenalty = 0;
        tbox.stop();
        gameRunning = false;
        gamePaused = false;

    }

    public void initiateBoxFall()//ekhane string er word pick kore dite hobe
    {
        y = 120;
        underlineIndex = 0;
        if (indexOfArray == words.length)
        {
            gameComplete();
        }
        else
        {
            changeText();
        }

    }

    public void decreasingLastPosition()//ekhane errorstring gula update hobe
    {                                    //game ending mechanism ekhono baki
        
        if (y == lastPosition)
        {
            if(sound_on)
            {
                Sound.THEEND.play();
            }
            lastPosition -= 30;
            temp1=indexOfArray;
            indexOfArray= r.nextInt(40);;
            initiateBoxFall();
            

        }
        if (lastPosition == 390)
        {
            errorString1 = words[temp1];
            
        }
        if (lastPosition == 360)
        {
            errorString2 = words[temp1];
            
        }
        if (lastPosition == 330)
        {
            errorString3 = words[temp1];
            
        }
        if (lastPosition == 300)
        {
            errorString4 = words[temp1];
            
        }
        if (lastPosition == 270)//type na kore complete hoye gese
        {
            
            errorString5 = words[temp1];
            System.out.println("game over by failure");
            gameOver = true;
            gameRunning = false;
            tbox.stop();
            
            updateScore();
        }
    }

    public void drawRecInBox(Graphics g)//box gula dekhabe kina ta errorString gula diye controlled
    {
        if (errorString1 != null)
        {
            g.setColor(Color.RED);//color change korte hobe
            g.fill3DRect(275, 390, 250, 30, true);
            g.setColor(Color.WHITE);
            g.drawString(errorString1, 300, 410);
        }
        if (errorString2 != null)
        {
            g.setColor(Color.BLUE);
            g.fill3DRect(275, 360, 250, 30, true);
            g.setColor(Color.WHITE);
            g.drawString(errorString2, 300, 380);
        }
        if (errorString3 != null)
        {
            g.setColor(Color.GREEN);
            g.fill3DRect(275, 330, 250, 30, true);
            g.setColor(Color.WHITE);
            g.drawString(errorString3, 300, 350);
        }
        if (errorString4 != null)
        {
            g.setColor(Color.ORANGE);
            g.fill3DRect(275, 300, 250, 30, true);
            g.setColor(Color.WHITE);
            g.drawString(errorString4, 300, 320);
        }
        if (errorString5 != null)
        {
            g.setColor(Color.PINK);
            g.fill3DRect(275, 270, 250, 30, true);
            g.setColor(Color.WHITE);
            g.drawString(errorString5, 300, 290);
        }
    }

    /* public void drawRecs()
     {
     rec1=new JLabel("fgf");
     rec2=new JLabel();
     rec3=new JLabel();
     rec4=new JLabel();
     rec5=new JLabel();
        
     rec1.setBounds(275, 390, 250, 30);//bottom 1
     rec2.setBounds(275, 360, 250, 30);
     rec3.setBounds(275, 330, 250, 30);
     rec4.setBounds(275, 300, 250, 30);
     rec5.setBounds(275, 270, 250, 30);
        
     rec1.setBackground(Color.YELLOW);
     rec2.setBackground(Color.YELLOW);
     rec3.setBackground(Color.YELLOW);
     rec4.setBackground(Color.YELLOW);
     rec5.setBackground(Color.YELLOW);
        
     rec1.setOpaque(true);
     rec2.setOpaque(true);
     rec3.setOpaque(true);
     rec4.setOpaque(true);
     rec5.setOpaque(true);
        
     add(rec1);
     add(rec2);
     add(rec3);
     add(rec4);
     add(rec5);
        
     }*/
    @Override
    public void mouseClicked(MouseEvent e)
    {
        System.out.println(e.getPoint());//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
