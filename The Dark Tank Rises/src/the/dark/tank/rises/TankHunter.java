
package the.dark.tank.rises;

import java.awt.Image;

public class TankHunter extends TankBot implements Runnable {
    
    private int targetX = sm.parent.recon1.x,
                targetY = sm.parent.recon1.x,
                alaki = 1;
    
    private boolean[][] wayChecker;
    
    static int a = 1000000;
    
    public TankHunter(int x, int y, int w, int h, int team, int health, ScreenManager sm) {
        
        super(x, y, w, h, team, health, sm);
        
        img = images();
        
        velocity = 10;
        
        face = FACE.DOWN;
        
        wayChecker = new boolean[ sm.parent.GAME_WIDTH ][ sm.parent.GAME_HEIGHT ];
    }

    @Override
    public final Image[] images() {
        
        Image[] temp = new Image[ img.length ];
        
        for (int i = 0; i < img.length; i++) {
            
            temp[ i ] = sm.parent.getImage(sm.parent.getCodeBase(), "WhiteTank" + i + ".png");
        }
        
        return temp;
    }

    @Override
    public void run() {
        
        while(isAlive) {
            
            pathAlgorithm();
//            BestWay(31, 30, 0);
//            
//            for (int i = 0; i < 80; i++)
//                for (int j = 0; j < 60; j++)
//                    wayChecker[ i ][ j ] = false;
//            
//            System.out.println(a);
//            a = 1000000;
            
            try {
                Thread.sleep(85);
            } catch (InterruptedException ex) {}
        }
    }

    @Override
    public void pathAlgorithm() {
        
        if (alaki == 1) {
            
            if (targetX != x) {
                
                if (x < targetX) 
                    right();
                else
                    left();
            }
            
            else {
                
                targetX = sm.parent.recon1.x;
                targetY = sm.parent.recon1.y;
                
                alaki = 0;
            }
        }
        
        else if (alaki == 0) {
            
            if (targetY != y) {

                if (y < targetY)
                    down();
                else
                    up();
            }
            
            else {
                
                targetX = sm.parent.recon1.x;
                targetY = sm.parent.recon1.y;
                
                alaki = 1;
            }
        }
    }
    
    public void BestWay(int i, int j, int n) {
                
        wayChecker[ i ][ j ] = true;
        
        if (i == (x / sm.parent.GAME_SCALE) && j == (y / sm.parent.GAME_SCALE)) {
            
            if (n < a)
                a = n;
        }
            
        if (!wayChecker[ i ][ j - 1 ] &&
            j - 1 >= 0 &&
            sm.background.bg[ i ][ j - 1 ] == 0)
            BestWay(i, j - 1, n + 1 );
        
        if (!wayChecker[ i ][ j + 1 ] &&
            j + 1 < sm.parent.GAME_HEIGHT &&
            sm.background.bg[ i ][ j + 1 ] == 0)
            BestWay(i, j + 1, n + 1 );
        
        if (!wayChecker[ i - 1 ][ j ] &&
            i - 1 >= 0 &&
            sm.background.bg[ i - 1 ][ j ] == 0)
            BestWay(i - 1, j, n + 1 );
        
        if (!wayChecker[ i + 1 ][ j ] &&
            i + 1 < sm.parent.GAME_WIDTH &&
            sm.background.bg[ i + 1 ][ j ] == 0)
            BestWay(i + 1, j, n + 1 );
    }
    
    @Override
    public void runAway() {
        
        
    }
}