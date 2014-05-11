package shooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Upgrades
 * @author Chi Un (Jeffrey) Wong
 * @version September 20, 2012
 */
public class Upgrades {

    Rectangle r;
    int x;
    int y;
    int width;
    int height;
    Color colour;
    /**
     * Upgrade constructor in rectangular shape
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width
     * @param height The height
     * @param col The colour
     */
    public Upgrades(int x, int y, int width, int height, Color colour) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.colour = colour;
        r = new Rectangle(x, y, width, height);
    }

    public void move() {
        y += 2;
        r.setLocation(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(colour);
        g.fillRect(x, y, width, height);
    }
}
