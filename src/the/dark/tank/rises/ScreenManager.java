
package the.dark.tank.rises;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class ScreenManager {
    
    final int TEAMS = 2;
    
    TheDarkTankRises parent;
    
    BackGround background;
    
    ArrayList<ScreenObject> ol;
    
    int[][] position;
    
    boolean[][][] teamView;
    
    public ScreenManager(TheDarkTankRises parent) {
        
        this.parent = parent;
        
        background = new BackGround(parent);
        
        ol = new ArrayList<>();
        
        position = new int[ parent.GAME_WIDTH / parent.GAME_SCALE ][ parent.GAME_HEIGHT / parent.GAME_SCALE ];
        teamView = new boolean[ TEAMS + 1 ][ parent.GAME_WIDTH / parent.GAME_SCALE ][ parent.GAME_HEIGHT / parent.GAME_SCALE ];
    }
    
    public void addObject(ScreenObject so) {
        
        ol.add(so);
    }
    
    public void removeObject(ScreenObject so) {
        
        ol.remove(so);
    }
    
    public void updatePosition() {
        
        initPosition();
        
        for (int i = 0; i < ol.size(); i++) {
            
            ScreenObject so = ol.get(i);
            
            if (so instanceof Tank) {
                
                Tank t = (Tank) so;
                
                for (int k = t.x / parent.GAME_SCALE; k < (t.x + t.w) / parent.GAME_SCALE; k++)
                    for (int j = t.y / parent.GAME_SCALE; j < (t.y + t.h) / parent.GAME_SCALE; j++)
                        position[ k ][ j ] = t.team;
            }
        }
    }
    
    public void bulletPhysics() {
       
        for (int i = 0; i < ol.size(); i++) {
            
            ScreenObject so = ol.get(i);
            
            if (so instanceof Bullet) {
                
                Bullet b = (Bullet) so;
                
                if (background.bg[ b.x / parent.GAME_SCALE ][ b.y / parent.GAME_SCALE ] == 1) {
                    
                    background.bg[ b.x / parent.GAME_SCALE ][ b.y / parent.GAME_SCALE ] = 0;
                    ol.remove(b);
                }
                
                else if (background.bg[ b.x / parent.GAME_SCALE ][ b.y / parent.GAME_SCALE ] == 4) {
                    
                     ol.remove(b);
                }
                    
            }
//            
            else if (so instanceof Rocket) {
                
                Rocket b = (Rocket) so;
                
                if (background.bg[ b.x / parent.GAME_SCALE ][ b.y / parent.GAME_SCALE ] == 1) {
                    
                    for (int j = -1; j < 2; j++) {
                        
                        for (int p = -1; p < 2; p++) {
                            
                            background.bg[ b.x / parent.GAME_SCALE + j ][ b.y / parent.GAME_SCALE + p ] = 0;
                        }
                    }
                    ol.remove(b);
                }   
            }
        }
    }
    
    public void conflict() {
        
        ArrayList<Bullet> bullets = new ArrayList<>();
        ArrayList<Rocket> rockets = new ArrayList<>();
        ArrayList<Tank> tanks = new ArrayList<>();
        
        for (int i = 0; i < ol.size(); i++) {
            
            ScreenObject so = ol.get(i);
            
            if (so instanceof Bullet) {
                bullets.add((Bullet) so);
            }
            
            if (so instanceof Rocket) {
                rockets.add((Rocket) so);
            }
            
            if (so instanceof Tank) {
                tanks.add((Tank) so);
            }
            
        }
        
        for (int j = 0; j < tanks.size(); j++) {
            
            Tank tank = tanks.get(j);
            
            for (int i = 0; i < bullets.size(); i++) {
                
                Bullet bullet = bullets.get(i);
                int blltx = bullet.x;
                int bllty = bullet.y;
                
                if (blltx >= tank.x && blltx <= tank.x + tank.w && bllty >= tank.y && bllty <= tank.y + tank.h) {
                    tank.health(bullet.damage);
                    removeObject(bullet);
                    
                    if (tank instanceof TankPlayer && tank.health <= 0) {
                        
                        tank.respawn((TankPlayer) tank);
                    }
                }
            }
            
            for (int k = 0; k < rockets.size(); k++) {
                
                Rocket rocket = rockets.get(k);
                int rocketX = 0, rocketY = 0;
                
                switch (rocket.face) {
                    
                    case UP:
                        rocketX = rocket.x + rocket.w / 2;
                        rocketY = rocket.y;
                        break;
                    
                    case DOWN:
                        rocketX = rocket.x + rocket.w / 2;
                        rocketY = rocket.y + rocket.h;
                        break;
                    
                    case RIGHT:
                        rocketX = rocket.x + rocket.w;
                        rocketY = rocket.y + rocket.h / 2;
                        break;
                    
                    case LEFT:
                        rocketX = rocket.x;
                        rocketY = rocket.y + rocket.h / 2;
                        break;
                    
                    default:
                        break;
                }
                
                if (rocketX >= tank.x && rocketX <= tank.x + tank.w && rocketY >= tank.y && rocketY <= tank.y + tank.h) {
                    tank.health(rocket.damage);
                    removeObject(rocket);
                }
            }
        }
    }

    
    public void draw(Graphics g) {
        
        conflict();
        bulletPhysics();
        
        updatePosition();
        view();
        
        Image offImg = parent.createImage(parent.GAME_WIDTH, parent.GAME_HEIGHT);
        Graphics offg = offImg.getGraphics();
        offg.setColor(Color.BLACK);
        offg.fillRect(0, 0, parent.GAME_WIDTH, parent.GAME_HEIGHT);
        background.draw(offg);
        
        for (int i = 0; i < ol.size(); i++) {
            
            ol.get(i).draw(offg);
        }
        
        g.drawImage(offImg, 0, 0, parent);
    }
    
    public void view() {
        
        initView();
        
        for (int i = 0; i < ol.size(); i++) {
            
            if (ol.get(i) instanceof Tank) {
                
                Tank t = (Tank) ol.get(i);
                t.view();
            }   
        }
    }
    
    public void initPosition() {
        
        for (int i = 0; i < position.length; i++) {
            
            for (int j = 0; j < position[ i ].length; j++) {
                
                position[ i ][ j ] = 0;
            }
        }
    }
    
    public void initView() {
        
        for (int i = 0; i < parent.GAME_WIDTH / parent.GAME_SCALE; i++) {
            
            for (int j = 0; j < parent.GAME_HEIGHT / parent.GAME_SCALE; j++) {
                
                for (int k = 0; k < teamView.length; k++) {
                    
                    teamView[ k ][ i ][ j ] = false;
                }
            }
        }
    }
}