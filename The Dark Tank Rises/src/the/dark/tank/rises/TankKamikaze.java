/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.dark.tank.rises;

import java.awt.Image;

/**
 *
 * @author FarazoO
 */
public class TankKamikaze extends TankBot {

    private boolean spotted;
    private int tempx,tempy;

    public TankKamikaze(int x, int y, int w, int h, int team, int health, ScreenManager sm) {

        super(x, y, w, h, team, health, sm);

    }

    @Override
    public Image[] images() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pathAlgorithm() {

        if (spotted) {
            for (int i = 0; i < sm.ol.size(); i++) {
                if (sm.ol.get(i) instanceof Tank) {
                    Tank tank = (Tank) sm.ol.get(i);
                    
                    if ( tank.team != 1){
                        
                        tempx = tank.x;
                        tempy = tank.y;
                        
                        while ((x < tempx) && canMove(FACE.RIGHT)){
                            right();
                        }
                        
                    }
                }
            }
        }

    }

    @Override
    public void runAway() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
