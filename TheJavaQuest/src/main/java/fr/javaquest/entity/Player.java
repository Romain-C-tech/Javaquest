package fr.javaquest.entity;

import javax.swing.*;

public class Player extends Entity{
    private boolean onGround = true;

    public Player(int x, int y){
        super(100, 4, x, y);
    }

    public void moveLeft(){
        hitbox.x -= movementSpeed;
    }
    public void moveRight(){
        hitbox.x += movementSpeed;
    }
    public void jump(){
        if (onGround) {
            onGround = false;

            Timer timer = new Timer(20, null);

            final double[] velocity = {-8};

            timer.addActionListener(e -> {

                hitbox.y += velocity[0];
                velocity[0] += 0.4;

                if (hitbox.y >= 200) {
                    hitbox.y = 200;
                    timer.stop();
                    onGround = true;
                }
            });

            timer.start();
        }
    }
    @Override
    public void move() {

    }
}
