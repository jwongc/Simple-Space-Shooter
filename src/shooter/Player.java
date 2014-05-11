package shooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * Player class
 * @author Chi Un (Jeffrey) Wong
 * @version September 20, 2012
 */
public class Player {
    int velocity;
    int x;
    int y;
    int width, height;
    int health = 1000;
    Color colour;
    Rectangle r;
    Image img;
    /**
     * Player constructer in rectangular shape
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width
     * @param height The height
     * @param colour The colour 
     */
    public Player(int x, int y, int width, int height, Color colour) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colour = colour;
        health = 1000;
        velocity = 3;
        r = new Rectangle(x, y, width, height);
    }
    /**
     * Constructor for displaying an image for player
     * @param x the x coord
     * @param y the y coord
     * @param width the width
     * @param height the height
     * @param s the location of the image
     */
    public Player(int x, int y, int width, int height, String s) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        colour = Color.RED;
        health = 1000;
        velocity = 3;
        r = new Rectangle(x, y, width, height);
        img = Toolkit.getDefaultToolkit().getImage(s);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(colour);
        g2.fillRect(x, y, width, height);
    }
    public void drawImage(Graphics g) {
        g.drawImage(img, x, y, null);
    }
    public void setImage(String s) {
        img = Toolkit.getDefaultToolkit().getImage(s);
    }

    public void moveLeft() {
        x -= velocity;
        r.setLocation(x,y);
    }

    public void moveRight() {
        x += velocity;
        r.setLocation(x,y);
    }

    public void moveUp() {
        y -= velocity;
        r.setLocation(x, y);
    }

    public void moveDown() {
        y += velocity;
        r.setLocation(x, y);
    }

  
}
