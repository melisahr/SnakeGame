import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int Screen_Width = 600;
    static final int Screen_Height = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (Screen_Width*Screen_Height)/UNIT_SIZE;
    static final int DELAY=75;

    final int x[] = new int[GAME_UNITS];
    final int y[]= new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        //Instance for random
        random = new Random();
        this.setPreferredSize(new Dimension(Screen_Width,Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    //Methods
    public void startGame(){
        newApple(); //call the newApple method
        running = true;
        timer = new Timer(DELAY,this); //create timer instance
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //Add a graph
        for  (int i=0; i < Screen_Height/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,Screen_Height);
            g.drawLine(0,i*UNIT_SIZE,Screen_Width,i*UNIT_SIZE);
        }
        //Draw the apple
        g.setColor(Color.red);
        g.fillOval(appleX,appleY,UNIT_SIZE, UNIT_SIZE);
    }
    public void newApple(){
        appleX = random.nextInt((Screen_Width/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((Screen_Height/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        //iterate all the body parts of the snake
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];//shift the coordinates by one
            y[i]= y[i-1];
        }
        //direction of the snake
        switch (direction){
            case 'U':
                y[0] = y[0]-UNIT_SIZE;//go to the next position
            break;
            case 'D':
                y[0] = y[0]+UNIT_SIZE;//go down
            break;
            case 'L':
                x[0] = x[0]-UNIT_SIZE;//go left
            break;
            case 'R':
                x[0] = x[0]+UNIT_SIZE;//go right
            break;
        }
    }
    public void checkApple(){

    }
    public void checkCollisions(){

    }
    public void gameOver(Graphics g){

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    //Inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
