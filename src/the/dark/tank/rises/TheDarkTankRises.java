
package the.dark.tank.rises;

import java.awt.Graphics;
import javax.swing.JApplet;

public class TheDarkTankRises extends JApplet {

    final int GAME_WIDTH = 800,
              GAME_HEIGHT = 600,
              GAME_SCALE = 10,
              IMAGE_SIZE = 30,
              PLAYER_TEAM = 1;
    
    ScreenManager sm;
    
    Levels level;
    
    Refresher refresh;
    
    @Override
    public void init() {
        
        setSize(GAME_WIDTH, GAME_HEIGHT);
        
        setFocusable(true);
        requestFocus();
        
        sm = new ScreenManager(this);
        
        level = new Levels(sm);
        level.initialize(level.no);
        
        refresh = new Refresher(this);
        refresh.start();
    }
    
    @Override
    public void paint(Graphics g) {
        
        sm.draw(g);
    }
}
