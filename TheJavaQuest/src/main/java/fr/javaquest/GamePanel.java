package fr.javaquest;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private int xDelta = 200, yDelta = 200;
    private boolean onGround = true;

    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public void changeXDelta(int value){
        this.xDelta += value;
        repaint();
    }
    public void changeYDelta(int value){
        this.yDelta += value;
        repaint();
    }
    public void jump() {
        if (onGround) {
            onGround = false;

            Timer timer = new Timer(20, null);

            final double[] velocity = {-8};

            timer.addActionListener(e -> {

                yDelta += velocity[0];
                velocity[0] += 0.4;

                if (yDelta >= 200) {
                    yDelta = 200;
                    timer.stop();
                    onGround = true;
                }
                repaint();
            });

            timer.start();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(xDelta, yDelta, 10, 10);
    }

    public void setRectPosition (int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
        repaint();
    }
}
