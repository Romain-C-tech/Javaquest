package fr.javaquest;

import fr.javaquest.entity.Player;
import fr.javaquest.inputs.KeyboardInputs;
import fr.javaquest.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Player player;

    public GamePanel(){
        player = new Player(200,200);

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this, player));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Rectangle hitbox = player.getHitbox();
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        g.fillRect(0, 210, 400,400);

    }
}
