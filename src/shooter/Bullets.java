package shooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Bullets class
 * @author Chi Un (Jeffrey) Wong
 * @version September 20, 2012
 */
public class Bullets {

    int x; 
    int y;
    int width;
    int height;
    Color colour;
    int velocity;
    Rectangle r;

    public Bullets(int x, int y, int width, int height, Color colour) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colour = colour;
        velocity = 10;
        r = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        g.fillRect(x, y, width, height);
    }

    public void move() {
        y -= velocity;
        r.setLocation(x, y);
    }
}
