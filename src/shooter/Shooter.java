package shooter;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Space Shooter Game
 * @author Chi Un (Jeffrey) Wong
 * @version September 20, 2012
 *
 */
public class Shooter extends Applet implements Runnable, KeyListener {

    private ArrayList<Bullets> bullets;
    private ArrayList<Upgrades> upgrades;
    private ArrayList<Enemy> enemies;
    private Graphics graphics;
    private boolean isRunning = true;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private int fps = 50;
    private int bulletCount;
    private int upgradeCount;
    private int enemyCount;
    private int weaponDamage = 1;
    private int weaponUpgrade = 1;
    private int score = 0;
    private Image img;
    private Player p;


    @Override
    public void init() {
        setSize(300, 400);
        setBackground(Color.BLACK);
        addKeyListener(this);
        p = new Player(getWidth() / 2, getHeight() - 55, 30, 8, Color.RED);
        bullets = new ArrayList<Bullets>();
        upgrades = new ArrayList<Upgrades>();
        enemies = new ArrayList<Enemy>();
    }

    @Override
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (isRunning) {
            repaint();
            try {
                Thread.sleep(1000 / fps);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public void destroy() {
        isRunning = false;
    }

    @Override
    public void paint(Graphics g) {
        if (p.health > 0) {
            // Health bar, score, weapon
            g.setColor(Color.RED);
            g.fillRect(10, getHeight() - 20, p.health / 10, 12);
            g.setColor(Color.WHITE);
            g.drawString(("Health Bar"), 10, getHeight() - 25);
            g.drawString("" + p.health, 40, getHeight() - 10);
            g.drawString("Weapon Upgrade: " + weaponUpgrade, getWidth() - 130, getHeight() - 25);
            g.drawString("Score: " + score, getWidth() - 130, getHeight() - 10);
            g.drawLine(0, getHeight() - 40, getWidth(), getHeight() - 40);
            // All bullets in the ArrayList bullets are drawn
            for (Bullets b : bullets) {
                b.draw(g);
            }
            // All upgrades in the ArrayList upgrades are drawn
            for (Upgrades u : upgrades) {
                u.draw(g);
            }
            // All enemies in the ArrayList enemies are drawn
            for (Enemy e : enemies) {
                e.drawImage(g);
            }
            update();
        } else if (p.health <= 0) { // Game ends when the player's health is less than or equal to 0
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", getWidth() / 2, getHeight() / 2);
        }
        p.draw(g);

    }

    /*
     * Double buffer method
     */
    @Override
    public void update(Graphics g) {
        if (img == null) {
            img = createImage(this.getWidth(), this.getHeight());
            graphics = img.getGraphics();
        }
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics.setColor(getForeground());
        paint(graphics);
        g.drawImage(img, 0, 0, this);
    }

    // Smoother player movement
    public void update() {
        // Player moves up, down, left or right
        if (moveUp && p.y > 0) {
            p.moveUp();
        }
        if (moveDown && p.y + p.height < getHeight() - 42) {
            p.moveDown();
        }
        if (moveLeft && p.x > 0) {
            p.moveLeft();
        }
        if (moveRight && p.x + p.width < getWidth()) {
            p.moveRight();
        }
        
         // The chances of an enemy spawning is 10/500. Spawn one, and the chances
         // for another one to spawn increases. Enemies progressively respawn faster
         
        Random ran = new Random();
        int rand = ran.nextInt(500);
        int chance = 10;
        if (rand < chance) {
            enemyCount = 1;
            chance++;
        } else {
            enemyCount = 0;
        }
        // Create enemy with random health who moves at random speed
        if (enemyCount == 1) {
            Enemy e = null;
            Random r = new Random();
            int x = r.nextInt(getWidth());
            int randHealth = r.nextInt(500);
            int randVelocity = r.nextInt(5) + 1;
            e = new Enemy(x, 0, 32, 32, randHealth, randVelocity, "spaceMonster.png");
            enemies.add(e);
            repaint();
            enemyCount++;
        }
        // If the upgrade counter is 1, a random upgrade will be added 
        if (upgradeCount == 1) {
            Random r = new Random();
            Upgrades u = null;
            int randomUpgrade = r.nextInt(30);
            int x = r.nextInt(getWidth());
            if (randomUpgrade <= 10) { //restores health
                u = new Upgrades(x, 0, 3, 3, Color.RED);
            }
            if (randomUpgrade > 10 && randomUpgrade <= 20) { // Speed upgrade
                u = new Upgrades(x, 0, 3, 3, Color.GREEN);
            }
            if (randomUpgrade > 20 && randomUpgrade <= 30) { // Weapon upgrade
                u = new Upgrades(x, 0, 3, 3, Color.ORANGE);
            }
            upgrades.add(u);
            repaint();
            upgradeCount++;
        }
        // If the bullet counter is 1, a new bullet will be added
        if (bulletCount == 1) {
            Bullets b = null;
            if (weaponUpgrade == 1) { // Initial weapon
                b = new Bullets(p.x + 13, p.y - p.height + 2, 3, 3, Color.YELLOW);
                weaponDamage = 100;
            }
            if (weaponUpgrade == 2) { // First upgrade; big bullets, higher damage
                b = new Bullets(p.x + 13, p.y - p.height + 2, 6, 6, Color.YELLOW);
                weaponDamage = 200;
            }
            if (weaponUpgrade == 3) { // Second upgrade; shoots two bullets at a time
                Bullets c;
                b = new Bullets(p.x + 8, p.y - p.height + 2, 6, 6, Color.YELLOW);
                c = new Bullets(p.x + 18, p.y - p.height + 2, 6, 6, Color.YELLOW);
                bullets.add(c);
                weaponDamage = 200;
            }
            if (weaponUpgrade >= 4) { // Third upgrade; back to one bullet but more damage
                b = new Bullets(p.x + 13, p.y - p.height + 2, 8, 8, Color.YELLOW);
                weaponDamage = 500;
            }

            bullets.add(b);
            repaint();
            bulletCount++;
        }
        // Enemies move towards player ship. If they pass, then the interface line
        // is deleted. If an enemy hits the player ship, damage is dealt to the player
        // and the enemy is removed. If bullet hits enemy, damage is dealt to enemy
        // according to the number of upgrades.
        
        for (int j = 0; j < enemies.size(); j++) {
            enemies.get(j).move();
            if (enemies.get(j).y + enemies.get(j).height > getHeight() - 42) {
                enemies.remove(j);
                j--;
                continue;
            }
            if (enemies.get(j).r.intersects(p.r)) {
                enemies.remove(j);
                p.health -= weaponDamage;
                j--;
                continue;
            }
            for (int i = 0; i < bullets.size(); i++) {
                try {
                    if (enemies.get(j).r.intersects(bullets.get(i).r)) {
                        enemies.get(j).health -= 100;
                        bullets.remove(i);
                        if (enemies.get(j).health <= 0) {
                            enemies.remove(j);
                            score += 50;
                            continue;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        // Fire all created bullets. When enemy is hit or bullets are off the screen,
        // they are removed.
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            if (bullets.get(i).y <= 0) {
                bullets.remove(i);
                i--;
                continue;
            }
        }

         // Loop size of the list to see if player ship collides with an upgrade.
         // Player will receive the upgrade if collided, and the upgrade is removed 
       
        for (int i = 0; i < upgrades.size(); i++) {
            upgrades.get(i).move();
            if (upgrades.get(i).y >= getHeight()) {
                upgrades.remove(i);
                i--;
                continue;
            }
            if (upgrades.get(i).r.intersects(p.r)) {
                if (upgrades.get(i).colour == Color.GREEN) {
                    p.velocity++;
                    upgrades.remove(i);
                    i--;
                    continue;
                }
                if (upgrades.get(i).colour == Color.RED) {
                    if (p.health < 1500) {
                        p.health += 100;
                    }
                    upgrades.remove(i);
                    i--;
                    continue;
                }
                if (upgrades.get(i).colour == Color.ORANGE) {
                    weaponUpgrade++;
                    upgrades.remove(i);
                    i--;
                    continue;
                }
            }
        }
    }
    /**
     * Movement functionality for player ship. Random upgrade generated upon
     * shooting by player.
     */
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            moveUp = true;
        }
        if (key == KeyEvent.VK_S) {
            moveDown = true;
        }
        if (key == KeyEvent.VK_A) {
            moveLeft = true;
        }
        if (key == KeyEvent.VK_D) {
            moveRight = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            bulletCount = 1;
            Random r = new Random();
            int rand = r.nextInt(1000);
            if (rand == 3 || rand == 489 || rand == 968 || rand == 302) {
                upgradeCount = 1;
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            moveUp = true;
        }
        if (key == KeyEvent.VK_S) {
            moveDown = true;
        }
        if (key == KeyEvent.VK_A) {
            moveLeft = true;
        }
        if (key == KeyEvent.VK_D) {
            moveRight = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            bulletCount = 1;
            Random r = new Random();
            int rand = r.nextInt(1000);
            if (rand == 3 || rand == 489 || rand == 968 || rand == 302) {
                upgradeCount = 1;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            moveUp = false;
        }
        if (key == KeyEvent.VK_S) {
            moveDown = false;
        }
        if (key == KeyEvent.VK_A) {
            moveLeft = false;

        }
        if (key == KeyEvent.VK_D) {
            moveRight = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            bulletCount = 0;
        }
    }
}