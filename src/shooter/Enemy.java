package shooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * Enemy Class
 * @author Chi Un (Jeffrey) Wong
 * @version September 20, 2012
 */
public class Enemy {

    int x;
    int y;
    int height;
    int width;
    int velocity;
    int health;
    Image img;
    Color colour;
    Rectangle r;
    
    /**
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width
     * @param height The height
     * @param health The total health
     * @param vel The velocity
     * @param s Image location
     */
    public Enemy(int x, int y, int width, int height, int health, int vel, String s) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.velocity = vel;
        img = Toolkit.getDefaultToolkit().getImage(s);
        this.health = health;
        colour = null;
        r = new Rectangle(x,y,height,width);
    }
    /**
     * Draws the enemy as an image.
     * @param s
     */
    public void setImage(String s) {
        img = Toolkit.getDefaultToolkit().getImage(s);
    }
    /**
     * Moves the enemy downwards, as well as the rectangle for collision
     */
    public void move() {
        y += velocity;
        r.setLocation(x,y); //moves the rectangle - for testing collision
    }
    public void draw(Graphics g) {
        g.setColor(colour);
        g.fillRect(x, y, width, height);
    }
    public void drawImage(Graphics g) {
        g.drawImage(img, x, y, null);
    }
}
