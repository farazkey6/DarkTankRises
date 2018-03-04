package the.dark.tank.rises;

import java.awt.Image;

public class TankDemolition extends TankBot implements Runnable {

    private int memory = 0, tempX, tempY;
    private int[][] past;
    private boolean spotted = false;

    public TankDemolition(int x, int y, int w, int h, int team, int health, int range, ScreenManager sm) {

        super(x, y, w, h, team, health, range, sm);

        velocity = 10;

        range = 10;

        img = images();

        face = FACE.DOWN;

        past = new int[2][8];
        
        way = ALGORITHM.xTargetting;
        
        damage = 50;
    }

    @Override
    public void run() {
        
        while (isAlive) {

            pathAlgorithm();
            if (canMove(face)) {
                step();
            }
            try {
                Thread.sleep(185);
            } catch (InterruptedException ex) {
            }
        }
        
        respawn(this);
    }

    @Override
    public void pathAlgorithm() {

        if (viewEnemy()) {

            spotted = true;
            
            targetting();
        } 
        else {

            spotted = false;
        }
        
        if (spotted) {
            
            if (x == tempX || y == tempY) {

                if (x == tempX) {
                    if (y > tempY) {

                        face = FACE.UP;
                        fire();
                    } else {

                        face = FACE.DOWN;
                        fire();
                    }
                } else if (y == tempY) {

                    if (x > tempX) {

                        face = FACE.LEFT;
                        fire();
                    } else {

                        face = FACE.RIGHT;
                        fire();
                    }
                }
            } 
            
            else {
                
                if (x < tempX && canMove(FACE.RIGHT) ||
                    x > tempX && canMove(FACE.LEFT))
                    way = ALGORITHM.xTargetting;
                    
                else if (y < tempY && canMove(FACE.DOWN) ||
                    y > tempY && canMove(FACE.UP))
                    way = ALGORITHM.yTargetting;
                
                
                if (way == ALGORITHM.xTargetting)
                    xTargetting();
                
                else if (way == ALGORITHM.yTargetting)
                    yTargetting();
                
                else if (way == ALGORITHM.rightMaze)
                    rightMaze();
            }
            
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

    public void xTargetting() {
        
        if (x < tempX) {
                
            if (canMove(FACE.RIGHT))
                face = FACE.RIGHT;
            
            else {
                
                face = rotateLeft();
                
                way = ALGORITHM.rightMaze;
            }
        }
       
        else if (x > tempX) {
                
            if (canMove(FACE.LEFT))
                face = FACE.LEFT;
            
            else {
                
                face = rotateLeft();
                
                way = ALGORITHM.rightMaze;
            }
        }
    }
    
    public void yTargetting() {
        
        if (y == tempY)
            way = ALGORITHM.xTargetting;
            
        else if (y < tempY) {
                
            if (canMove(FACE.DOWN))
                face = FACE.DOWN;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
       
        else if (y > tempY) {
                
            if (canMove(FACE.UP))
                face = FACE.UP;
                
            else {
                        
                face = rotateLeft(); //Debug!
                way = ALGORITHM.rightMaze;
            }
        }
    }
    
    public void rightMaze() {

        if (canMove(rotateRight()))
            face = rotateRight();
        
        else if (!canMove(face))
            face = rotateLeft();
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
                                                
                                                tempX = t.x;
                                                tempY = t.y;
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
    
    @Override
    public void runAway() {
    }

    @Override
    public Image[] images() {
        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i] = sm.parent.getImage(sm.parent.getCodeBase(), "demoTank" + i + ".png");
        }

        return temp;
    }
    
    @Override
    public void fire() {
        Rocket rocket = new Rocket(x, y, 5, 5, team, damage, face, sm);
        sm.addObject(rocket);
        Thread t = new Thread(rocket);
        t.start();
    }
}
