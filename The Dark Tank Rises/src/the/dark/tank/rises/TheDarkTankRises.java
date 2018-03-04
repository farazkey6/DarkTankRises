
package the.dark.tank.rises;

import java.awt.Graphics;
import javax.swing.JApplet;
import the.dark.tank.rises.Tank.FACE;

public class TheDarkTankRises extends JApplet {

    final int GAME_WIDTH = 800,
              GAME_HEIGHT = 600,
              GAME_SCALE = 10,
              IMAGE_SIZE = 30;
    
    
    ScreenManager sm;
    
    TankRecon recon;
    TankRecon recon1;
    TankRecon recon3;
    
    TankHunter hunter;
    
    TankPlayer playerTank;
    
    Refresher refresh;
    
    @Override
    public void init() {
        
        setSize(GAME_WIDTH, GAME_HEIGHT);
        
        setFocusable(true);
        requestFocus();
        
        sm = new ScreenManager(this);
        
        refresh = new Refresher(this);
        refresh.start();
        
        recon1 = new TankRecon(760, 10, 30, 30, 1, 100,sm);
        recon3 = new TankRecon(10, 10, 30, 30, 1, 100, sm);
        recon = new TankRecon(700, 560, 30, 30, 2, 100, sm);
        
        playerTank = new TankPlayer(100, 100, 30, 30, 1, 100, sm);
        
        hunter = new TankHunter(300, 300, 30, 30, 2, 100, sm);
        
        recon.face = FACE.RIGHT;
        
        sm.addObject(recon);
        sm.addObject(recon1);
        sm.addObject(recon3);
        sm.addObject(playerTank);
//        sm.addObject(hunter);
        
        Thread t = new Thread(recon);
        t.start();
        t = new Thread(recon1);
        t.start();
        t = new Thread(recon3);
        t.start();
//        t = new Thread(hunter);
//        t.start();
    }
    
    @Override
    public void paint(Graphics g) {
        
        sm.draw(g);
    }
}
