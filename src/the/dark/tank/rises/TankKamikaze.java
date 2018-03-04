package the.dark.tank.rises;

import java.applet.AudioClip;
import java.awt.Image;

public class TankKamikaze extends TankBot implements Runnable {

    private AudioClip scream;
    
    private boolean spotted = false;
    private int targetX,
                targetY;
    
    private int memory = 0;
    private int[][] past;

    public TankKamikaze(int x, int y, int w, int h, int team, int health, int range, ScreenManager sm) {

        super(x, y, w, h, team, health, range, sm);

        velocity = 10;
        
        img = images();

        face = FACE.DOWN;
        
        scream = sm.parent.getAudioClip(sm.parent.getCodeBase(), "kamikaze.wav");
        
        way = ALGORITHM.xTargetting;
        
        past = new int[ 2 ][ 8 ]; 
    }

    @Override
    public void run() {

        while (isAlive) {
            
            pathTrack();
            if (stuck())
                way = ALGORITHM.xTargetting;
                        
            detonate();
            targetting();
            pathAlgorithm();
            
            if (canMove(face))
                step();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
            
        }
        
         respawn(this);
    }

    @Override
    public void pathAlgorithm() {
        
        if (x == targetX && y == targetY) {
            
            spotted = false;
            
            targetX = targetY = 0;
        }
        
        if (spotted) {
            
            if (way == ALGORITHM.rightMaze) {
            
                if (!canMove(FACE.RIGHT) || !canMove(FACE.LEFT)) {
                
                    if (x == targetX)
                        way = ALGORITHM.yTargetting;
                
                    else if (y == targetY)
                        way = ALGORITHM.xTargetting;
                }
            
                if (!canMove(FACE.UP) || !canMove(FACE.DOWN)) {
                
                    if (y == targetY)
                        way = ALGORITHM.xTargetting;
                
                    else if (x == targetX)
                        way = ALGORITHM.yTargetting;
                }
                
                rightMaze();
            }  
        
            else if (way == ALGORITHM.xTargetting)
                xTargetting();
        
            else if (way == ALGORITHM.yTargetting)
                yTargetting();
            
            }
        
        else {
            
            if (!canMove(face)) {
                
                do {
                    switch ((int) (Math.random() * 2)) {
                        
                        case 0:
                            face = rotateLeft();
                            break;
                            
                        case 1:
                            face = rotateRight();
                            break;
                    }
                } while (!canMove(face));
            }
        }
    }

    @Override
    public void runAway() {
    }

    @Override
    public final Image[] images() {

        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i] = sm.parent.getImage(sm.parent.getCodeBase(), "kamikaze" + i + ".png");
        }

        return temp;
    }
    
    public void xTargetting() {
        
        if (x == targetX)
            way = ALGORITHM.yTargetting;
        
        else if (x < targetX) {
                
            if (canMove(FACE.RIGHT))
                face = FACE.RIGHT;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
       
        else if (x > targetX) {
                
            if (canMove(FACE.LEFT))
                face = FACE.LEFT;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
    }
    
    public void yTargetting() {
        
        if (y == targetY)
            way = ALGORITHM.xTargetting;
            
        else if (y < targetY) {
                
            if (canMove(FACE.DOWN))
                face = FACE.DOWN;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
       
        else if (y > targetY) {
                
            if (canMove(FACE.UP))
                face = FACE.UP;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
    }
    
    public void targetting() {

        if (viewEnemy()) {
            
            spotted = true; // Traget Detected! :D

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

                                    for (int p = t.x / sm.parent.GAME_SCALE; p < (t.x + t.w) / sm.parent.GAME_SCALE; p++) {
                                        for (int q = t.y / sm.parent.GAME_SCALE; q < (t.x + t.w) / sm.parent.GAME_SCALE; q++) {
                                            if (p == i && q == j) {
                                                
                                                targetX = t.x;
                                                targetY = t.y;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
    
    private void detonate() { // bopok! :D
        
        int scale = sm.parent.GAME_SCALE,
            xo = (x + w / 2) / scale, //central X
            yo = (y + h / 2) / scale, //central Y
            nx = (x + w) / scale + 1, // next X
            px = x / scale - 1, // pervoius X
            ny = (y + h) / scale + 1, //next Y
            py = y / scale - 1; // pervious Y
            
        
        if (ny < sm.parent.GAME_HEIGHT / scale) {
            
            if (sm.position[ xo ][ ny ] != 0 &&
                sm.position[ xo ][ ny ] != team) {
                
                isAlive = false;
            }
        }
        
        if (py > 0) {
            
            if (sm.position[ xo ][ py ] != 0 &&
                sm.position[ xo ][ py ] != team) {
                
                isAlive = false;
            }
        }
        
        if (nx < sm.parent.GAME_WIDTH / scale) {
            
            if (sm.position[ nx ][ yo ] != 0 &&
                sm.position[ nx ][ yo ] != team) {
                
                isAlive = false;
            }
        }
        
        if (px > 0) {
            
            if (sm.position[ px ][ yo ] != 0 &&
                sm.position[ px ][ yo ] != team) {
                
                isAlive = false;
            }
        }
    }
}
