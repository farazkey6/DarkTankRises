
package the.dark.tank.rises;

import static the.dark.tank.rises.Tank.FACE.UP;

public abstract class TankBot extends Tank {
    
    public TankBot(int x, int y, int w, int h, int team, int health, ScreenManager sm) {
        
        super(x, y, w, h, team, health, sm);
    }
    
    public FACE rotateRight() {
        
        switch(face) {
            
            case UP:
                return FACE.RIGHT;
                
            case RIGHT:
                return FACE.DOWN;
                
            case DOWN:
                return FACE.LEFT;
                
            case LEFT:
                return FACE.UP;
                
            default:
                return null;
        }
    }
    
    public FACE rotateLeft() {
        
        switch(face) {
            
            case UP:
                return FACE.LEFT;
                
            case RIGHT:
                return FACE.UP;
                
            case DOWN:
                return FACE.RIGHT;
                
            case LEFT:
                return FACE.DOWN;
                
            default:
                return null;
        }
    }
    
    public FACE bargard() {
        
        switch(face) {
            
            case UP:
                return FACE.DOWN;
                
            case RIGHT:
                return FACE.LEFT;
                
            case DOWN:
                return FACE.UP;
                
            case LEFT:
                return FACE.RIGHT;
                
            default:
                return null;
        }
    }
    
    public abstract void pathAlgorithm();
    //public abstract void elimineted();
    public abstract void runAway();
    
}