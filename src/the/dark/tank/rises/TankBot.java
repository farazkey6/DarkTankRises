
package the.dark.tank.rises;

public abstract class TankBot extends Tank {
    
    enum ALGORITHM {rightMaze, leftMaze, xTargetting, yTargetting, straght}
    ALGORITHM way;
    
    public TankBot(int x, int y, int w, int h, int team, int health, int range,ScreenManager sm) {
        
        super(x, y, w, h, team, health, range, sm);
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
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public abstract void pathAlgorithm();
    //public abstract void elimineted();
    public abstract void runAway();
    
}