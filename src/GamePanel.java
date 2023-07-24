import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    public void draw(Graphics g) {
        if(running) {
            //Add a grid
            for (int i = 0; i < Screen_Height / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, Screen_Height);
                g.drawLine(0, i * UNIT_SIZE, Screen_Width, i * UNIT_SIZE);
            }
            //Draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Draw head and body of the snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());//align message center of screen
            g.drawString("Score: "+applesEaten,(Screen_Width - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
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
    //grabbing the apple
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //check if head collide with body
        for(int i = bodyParts; i > 0 ;i--) {
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //check if head touches left border
        if(x[0]<0){
            running = false;
        }
        //check if head touches right border
        if(x[0] > Screen_Width) {
            running = false;
        }
        //check if head touches top border
        if(y[0]< 0){
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > Screen_Height){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());//align message center of screen
        g.drawString("Score: "+applesEaten,(Screen_Width - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());//align message center of screen
        g.drawString("Game Over",(Screen_Width - metrics2.stringWidth("Game Over"))/2, Screen_Height/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Move the snake
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
            repaint();
        }
    //Inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        //move controller
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                if(direction != 'R'){
                    direction = 'L';
                }
                break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
               }
               break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                }
                break;
            }
        }
    }
}
