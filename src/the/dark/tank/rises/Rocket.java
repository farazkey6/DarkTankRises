
package the.dark.tank.rises;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;

public class Rocket extends ScreenObject implements Runnable{
    
    Tank.FACE face;
    int damage,
            team,
            velocity;
    AudioClip shot;
    
    public Rocket(int x, int y, int w, int h, int team, int damage, Tank.FACE face, ScreenManager sm) {

        super(x, y, w, h, sm);
        this.face = face;
        this.damage = damage;
        this.team = team;
        this.sm = sm;
        
        shot = sm.parent.getAudioClip ( sm.parent.getCodeBase(), "Rocket.wav");

        velocity = 10;

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, w, h);
    }
    
    @Override
    public void run() {
        
        shot.play();
        
        switch (face) {
            case UP:
                y -= sm.parent.GAME_SCALE;
                x += sm.parent.IMAGE_SIZE / 2;
                while (y - velocity > 0) {
                    y -= velocity;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
                break;

            case RIGHT:
                x += sm.parent.IMAGE_SIZE + sm.parent.GAME_SCALE;
                y += sm.parent.IMAGE_SIZE / 2;
                while (x + velocity < sm.parent.GAME_WIDTH) {
                    x += velocity;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
                break;

            case DOWN:
                y += sm.parent.IMAGE_SIZE + sm.parent.GAME_SCALE;
                x += sm.parent.IMAGE_SIZE / 2;
                while (y + velocity < sm.parent.GAME_HEIGHT) {
                    y += velocity;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
                break;

            case LEFT:
                x -= sm.parent.GAME_SCALE;
                y += sm.parent.IMAGE_SIZE / 2;
                while (x - velocity > 0) {
                    x -= velocity;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }
                }
                break;

        }
        sm.removeObject(this);
    }
}
