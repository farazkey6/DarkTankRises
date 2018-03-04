
package the.dark.tank.rises;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class ScreenManager {
    
    TheDarkTankRises parent;
    
    BackGround background;
    
    ArrayList<ScreenObject> ol;
    
    int[][] position;
    
    boolean[][][] teamView;
    
    public ScreenManager(TheDarkTankRises parent) {
        
        this.parent = parent;
        
        background = new BackGround(this);
        
        ol = new ArrayList<>();
        
        position = new int[ parent.GAME_WIDTH / parent.GAME_SCALE ][ parent.GAME_HEIGHT / parent.GAME_SCALE ];
        teamView = new boolean[ 3 ][ parent.GAME_WIDTH / parent.GAME_SCALE ][ parent.GAME_HEIGHT / parent.GAME_SCALE ];
    }
    
    public void addObject(ScreenObject so) {
        
        ol.add(so);
    }
    
    public void removeObject(ScreenObject so) {
        
        ol.remove(so);
    }
    
    public void updatePosition() {
        
        for (int i = 0; i < position.length; i++) {
            
            for (int j = 0; j < position[ i ].length; j++) {
                
                position[ i ][ j ] = 0;
            }
        }
        
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
    
    public void conflict() {
        
        
    }
    
    public void draw(Graphics g) {
        
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
        
        for (int i = 0; i < parent.GAME_WIDTH / parent.GAME_SCALE; i++) {
            
            for (int j = 0; j < parent.GAME_HEIGHT / parent.GAME_SCALE; j++) {
                
                for (int k = 0; k < teamView.length; k++) {
                    
                    teamView[ k ][ i ][ j ] = false;
                }
            }
        }
        
        for (int i = 0; i < ol.size(); i++) {
            
            if (ol.get(i) instanceof Tank) {
                
                Tank t = (Tank) ol.get(i);
                t.view();
            }   
        }
    }
}