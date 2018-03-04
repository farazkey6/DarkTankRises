
package the.dark.tank.rises;

import java.awt.Image;
import java.util.ArrayList;

public class TankRecon extends TankBot implements Runnable {
    
    private int memory = 0;
    
    private int[][] past;
    
    private ArrayList<Tank> targets;
    
    public TankRecon(int x, int y, int w, int h, int team, int health, int range, ArrayList<Tank> targets,ScreenManager sm) {
        
        super(x, y, w, h, team, health, range,sm);
        
        this.targets = targets;
        
        img = images();
        
        velocity = 10;
        
        face = FACE.DOWN;
        
        way = ALGORITHM.rightMaze;
        
        past = new int[ 2 ][ 8 ];
    }

    @Override
    public void run() {
        
        while (isAlive) {
            
            pathTrack();
            if (stuck())
                way = ALGORITHM.straght;
            
            do {
                pathAlgorithm();      
            } while(!canMove(face));
            
            step();

            marking();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        }
        
        respawn(this);
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
    public void pathAlgorithm() {
        
        if (way == ALGORITHM.rightMaze)
            rightMaze();
        else if (way == ALGORITHM.leftMaze)
            leftMaze();
        else if (way == ALGORITHM.straght)
            straght();
    }

    public void rightMaze() {

        if (canMove(rotateRight()))
            face = rotateRight();
        
        else if (!canMove(face))
            face = rotateLeft();
    }
    
    public void leftMaze() {
        
        if (canMove(rotateLeft()))
            face = rotateLeft();
        
        else if (!canMove(face))
            face = rotateRight();
    }
    
    public void marking() {
    
        if (viewEnemy()) {
            
            
        }
    }
    
    public boolean viewEnemy() { // mark and detect enemy
        
        int minI = (x + w / 2) / sm.parent.GAME_SCALE - range / 2, // detect RANG
            minJ = (y + h / 2) / sm.parent.GAME_SCALE - range / 2,
            maxI = (x + w / 2) / sm.parent.GAME_SCALE + range / 2 + 1,
            maxJ = (y + h / 2) / sm.parent.GAME_SCALE + range / 2 + 1;
        
        int beginI = minI >= 0 ? minI : 0,
            endI = maxI < sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ? maxI : sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE,
            beginJ = minJ >= 0 ? minJ : 0,
            endJ = maxJ < sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ? maxJ : sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE;    
        
        for (int i = beginI; i < endI; i++) {
            
            for (int j = beginJ; j < endJ; j++) {
                
                if (sm.position[ i ][ j ] != team && sm.position[ i ][ j ] != 0) { //if view enemy
                    
                    for (int k = 0; k < sm.ol.size(); k++) { // then MARK HIM!
                        
                        ScreenObject so = sm.ol.get(k);
                        
                        if (so instanceof Tank) {
                            
                            Tank t = (Tank) so;
                            
                            if (t.team != team) {
                                
                                if (face == FACE.UP && y > t.y ||
                                    face == FACE.DOWN && y < t.y ||
                                    face == FACE.LEFT && x > t.x ||
                                    face == FACE.RIGHT && x <t.x)
                                    runAway();
                                
                                for (int p = t.x / sm.parent.GAME_SCALE; p < (t.x + t.w) / sm.parent.GAME_SCALE; p++)
                                    for (int q = t.y / sm.parent.GAME_SCALE; q < (t.y + t.h) / sm.parent.GAME_SCALE; q++)
                                        if (p == i && q == j) {
                                            
                                            t.mark = true;
                                            targets.add(t);
                                                
                                            return true;
                                        }
                                            
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private void pathTrack() {
        
         past[ 0 ][ memory ] = x / sm.parent.GAME_SCALE;
         past[ 1 ][ memory ] = y / sm.parent.GAME_SCALE;
         
         ++memory;
         memory %= 8;
    }
    
    private boolean stuck() {
        
        int temp = 0;
        
        for (int i = 0; i < 4; i++)
            if (past[ 0 ][ i ] == past[ 0 ][ i + 4 ] &&
                past[ 1 ][ i ] == past[ 1 ][ i + 4 ])
                ++temp;
        
        if (temp == 4)
            return true;
        else
            return false;
    }
    
    public void straght() {
        
        if (!canMove(face)) {
            
            face = rotateLeft();
            
            way = ALGORITHM.rightMaze;
        }
        
    }
    
    @Override
    public void runAway() {
        
            face = bargard();
        
            way = way == ALGORITHM.rightMaze ? ALGORITHM.leftMaze : ALGORITHM.rightMaze;
    }
}