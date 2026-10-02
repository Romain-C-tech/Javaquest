package inputs;

import fr.javaquest.GamePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    private boolean left, right;

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        movementTimer.start();

    }

    Timer movementTimer = new Timer(20, e -> {
        if (left) {
            gamePanel.changeXDelta(-5);
        }

        if (right) {
            gamePanel.changeXDelta(5);
        }
    });

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_Q -> left = true;
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_S -> System.out.println("S");
            case KeyEvent.VK_DOWN -> System.out.println("down arrow");
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> gamePanel.jump();
            case KeyEvent.VK_UP -> gamePanel.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_Q -> left = false;
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_RIGHT -> right = false;
        }
    }
}
