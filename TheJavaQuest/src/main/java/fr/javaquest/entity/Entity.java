package fr.javaquest.entity;

import java.awt.*;

public abstract class Entity {

    protected int hp;
    protected int movementSpeed;
    protected Rectangle hitbox;

    public Entity(int hp, int movementSpeed, int x, int y){
        this.hp = hp;
        this.movementSpeed = movementSpeed;
        this.hitbox = new Rectangle(x, y, 10, 10);
    }

    public int getHp(){
        return hp;
    }

    public int getMovementSpeed(){
        return movementSpeed;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public void takeDamage(int damage){
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            System.out.println("Player is dead");
//            fjalmehtmat_________________________________________________
        }
    }

    public abstract void move();
}
