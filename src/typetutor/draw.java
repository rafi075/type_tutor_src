/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typetutor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author USER
 */
public class draw extends JPanel implements ActionListener
{

    Timer t = new Timer(100, this);
    Random r = new Random();
    Random r1=new Random();
    boolean snd=true;
    int wallHeight = 200;
    int wallWidth = 250;
    int pillarUpHeight = 235;
    int grassx,grassy;

    int planeHeight = 101;
    BufferedImage wall1Img, wall2Img,grass1,grass2,grass3;
    String wordList = "TypeAndFire Antelope Beaver Elephant Tiger Lion Deer Crane Duck Fox Peacock Cat Dog Lizard Badger Squirrel Pigeon Hawk Dove Baboon Bangladesh India Nepal Bhutan Myanmar Goat Cow Sheep Racoon Crocodile Nilgai Otter China Malaysia Thailand Monkey Panda Heron Rhinoceros Ox Mule Donkey Horse Vulture";
    String[] words;
    int indexOfArray = 0;
    int underlineIndex = 0;
    boolean typeComplete = false, firing=false;
    boolean gameoverbool = false;
    int scoreCount = 0;
    User temp;
    

    int planex, planey, pillarUpX, pillarUpY, valx, valy;
    JLabel plane, gateLabel, score, letter, wall1, wall2;

    JButton buttonPause1, buttonReset1, buttonStart1;
    BufferedImage aero, pillarUp, pillarDown, gameover,flash;

    public draw()
    {
        this.words = wordList.split(" ");//array te neoa
        setLayout(null);
        setBackground(Color.cyan);
        setSize(1280, 700);
        planex = 100;
        planey = 250;
        pillarUpX = 1280;
        pillarUpY = 0;
        valx = 10;
        grassx=50;
        grassy=450;
       
        try
        {
            wall1Img = ImageIO.read(new File("wall1.jpg"));
            wall2Img = ImageIO.read(new File("wall2.jpg"));
            aero = ImageIO.read(new File("plane.png"));
            // pillarDown = ImageIO.read(new File("pillar down.png"));
            //  pillarUp = ImageIO.read(new File("pillar up.png"));
            gameover = ImageIO.read(new File("gameover.png"));
            flash = ImageIO.read(new File("flash.png"));
            grass1 = ImageIO.read(new File("building.png"));
            grass2 = ImageIO.read(new File("grass2.png"));
            grass3 = ImageIO.read(new File("grass3.png"));
        } catch (IOException ex)
        {

        }
        buttonPause1 = new JButton("Pause");
        buttonReset1 = new JButton("Reset");
        buttonStart1 = new JButton("Start");
        plane = new JLabel();
        letter = new JLabel("<html>" + words[indexOfArray] + "</html>");
        score = new JLabel("<html>" + "score " + scoreCount + "</html>");
        gateLabel = new JLabel("<html>" + words[indexOfArray] + "</html>");

        gateLabel.setFont(new java.awt.Font("Tahoma", 0, 20));
        letter.setFont(new java.awt.Font("Tahoma", 0, 20));
        score.setFont(new java.awt.Font("Tahoma", 0, 20));
        letter.setBounds(1100, 10, 400, 100);
        score.setBounds(10, 5, 100, 50);

        plane.setIcon(new javax.swing.ImageIcon("plane.png"));

        buttonReset1.setBounds(860, 600, 100, 40);
        buttonPause1.setBounds(1000, 600, 100, 40);
        buttonStart1.setBounds(1140, 600, 100, 40);

        buttonPause1.setFocusable(false);
        buttonReset1.setFocusable(false);
        buttonStart1.setFocusable(false);

        add(score);
        add(letter);
        add(gateLabel);
        add(buttonPause1);
        add(buttonReset1);
        add(buttonStart1);
        add(plane);
        //addKeyListener(this);
        //setFocusable(true);
        //setFocusTraversalKeysEnabled(false);

        buttonPause1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                //fallingBall.setText("1");
                t.stop();
            }
        });
        buttonStart1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                // textInFallingBox.setText("2");
                t.start();
            }
        });
        buttonReset1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                //textInFallingBox.setText("3");
                reset();
                
                

            }
        });

        //temp for mouse
        // addMouseListener( this);
        changeText();

    }

    public void keyEventHandle(KeyEvent e)
    {

        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP)
        {
            up();
        }
        if (code == KeyEvent.VK_DOWN)
        {
            down();
        }
        if (code == KeyEvent.VK_LEFT)
        {
            left();
        }
        if (code == KeyEvent.VK_RIGHT)
        {
            right();
        }
        

        if (planey >= pillarUpHeight && planey <= (pillarUpHeight + wallHeight - 100) && underlineIndex < words[indexOfArray].length() && e.getKeyChar() == words[indexOfArray].charAt(underlineIndex))
        {
            underlineIndex++;
            changeText();
            if(snd)
            {
            Sound.FIRE.play();
            }
        }
        
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.darkGray);

        g.drawImage(aero, planex, planey, null);

        g.fill3DRect(pillarUpX, pillarUpY, wallWidth, pillarUpHeight, true);
        g.fill3DRect(pillarUpX, pillarUpY + pillarUpHeight + wallHeight, wallWidth, 650 - (pillarUpHeight + wallHeight), true);

        if (typeComplete == false)
        {
            gateLabel.setVisible(true);
            g.drawImage(wall2Img, pillarUpX, pillarUpHeight, null);
            g.drawImage(wall2Img, pillarUpX + wallWidth - 20, pillarUpHeight, null);
        }
        else
        {
            gateLabel.setVisible(false);
        }
        if(firing==true)
        {
            g.drawImage(flash, planex+140, planey+30, null);
            g.drawImage(flash, planex+75, planey+65, null);
            g.drawLine(planex+145, planey+50, pillarUpX, planey+50);
            g.drawLine(planex+90, planey+80, pillarUpX, planey+80);
            firing=false;
        }

        gateLabel.setBounds(pillarUpX + 50, pillarUpHeight, wallWidth, wallHeight);

        if (gameoverbool == true)
        {
            g.drawImage(gameover, 450, 100, null);
        }
        g.drawImage(grass1, 0, 480, null);
        

    }

    public void actionPerformed(ActionEvent e)
    {
        score.setText("Score " + scoreCount);
        pillarUpX -= valx;

        collide();

        if (pillarUpX + wallWidth == 0)
        {
            typeComplete = false;
            pillarUpHeight = 100 + r.nextInt(250);
            pillarUpX = 1280;
           // indexOfArray++;
            indexOfArray= r1.nextInt(40);
            underlineIndex = 0;
            gateLabel.setText("<html>" + words[indexOfArray] + "</html>");
            letter.setText("<html>" + words[indexOfArray] + "</html>");

        }

        repaint();

    }

    public void up()
    {
        planey -= 5;
    }

    public void down()
    {
        planey += 5;
    }
    public void left()
    {
        planex-=5;
    }
    public void right()
    {
        planex+=5;
    }

    public void changeText()
    {
        firing=true;
        
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
            gateLabel.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "<u>" + words[indexOfArray].charAt(underlineIndex) + "</u>" + sub2 + "</html>");
            letter.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "<u>" + words[indexOfArray].charAt(underlineIndex) + "</u>" + sub2 + "</html>");

        }
        else
        {
            sub1 = words[indexOfArray].substring(0);
            gateLabel.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "</html>");
            letter.setText("<html>" + "<font color='red'>" + sub1 + "</font>" + "</html>");
            //oneWordComplete();
            typeComplete = true;
            if(snd)
            {
            Sound.Wall.play();
            }
            scoreCount += 5;
            score.setText("Score " + scoreCount);
        }

        repaint();

    }

    public void collide()
    {
        if (pillarUpX < planex + 160 && planex<=pillarUpX+wallWidth)
        {
            if (planey < pillarUpHeight || planey > (pillarUpHeight + wallHeight - 100))
            {
                if(snd)
                {
                Sound.Wall.play();
                }
                gameEnding();

            }
            if (typeComplete == false)
            {
                if(snd){
               Sound.Wall.play();
                }
                gameEnding();
            }
        }
    }

    public void gameEnding()
    {
        t.stop();
        gameoverbool = true;
        int i=temp.getDrawScore();
        if(scoreCount>i) temp.setDrawScore(scoreCount);
        System.out.println(temp.drawScore);
    }

    public void reset()
    {
        pillarUpHeight = 235;
        indexOfArray = 0;
        underlineIndex = 0;
        typeComplete = false;
        gameoverbool = false;
        scoreCount = 0;
        planex = 100;
        planey = 250;
        pillarUpX = 1280;
        pillarUpY = 0;
        valx = 10;
        changeText();
        repaint();
        t.stop();
        

    }

    public void getReference(User current)
    {
        temp=current;
        
    }

}
