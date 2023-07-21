import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame() {
        //Instance for panel
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //set the frame in the middle of the screen
    }
}
