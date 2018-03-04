package the.dark.tank.rises;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static the.dark.tank.rises.Tank.FACE.UP;

public class TankPlayer extends Tank {

    boolean[] keys;
    
    public TankPlayer(int x, int y, int w, int h, int team, int health, int range,ScreenManager sm) {

        super(x, y, w, h, team, health, range,sm);

        img = images();
        
        velocity = 10;
        
        damage = 20;
        
        face = FACE.DOWN;
        
        keys = new boolean[ 5 ];

        sm.parent.addKeyListener(
                new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {


                    if (e.getKeyCode() == KeyEvent.VK_UP)
                        keys[ 0] = true;
                      
                    if (e.getKeyCode() == KeyEvent.VK_LEFT)
                        keys[ 1] = true;

                    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                        keys[ 2] = true;
                    
                    if (e.getKeyCode() == KeyEvent.VK_DOWN)
                        keys[ 3] = true;

                    if (e.getKeyCode() == KeyEvent.VK_SPACE)
                        keys[ 4] = true;
                
                if (keys[ 0 ])
                    if (canMove(face))
                        step();
                if (keys[ 1 ])
                    face = rotateLeft();
                
                if (keys[ 2 ])
                    face = rotateRight();
                
                if (keys[ 3 ])
                    backward();
                if (keys [ 4 ])
                    fire();
            }

            @Override
            public void keyReleased(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        keys[ 0] = false;
                        break;

                    case KeyEvent.VK_LEFT:
                        keys[ 1] = false;
                        break;

                    case KeyEvent.VK_RIGHT:
                        keys[ 2] = false;
                        break;

                    case KeyEvent.VK_DOWN:
                        keys[ 3] = false;
                        break;

                    case KeyEvent.VK_SPACE:
                        keys[ 4 ] = false;
                        break;
                }
            }
        });
    }

    @Override
    public final Image[] images() {

        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i] = sm.parent.getImage(sm.parent.getCodeBase(), "playerTank" + i + ".png");
        }

        return temp;
    }
    
    private void backward() {
        
        if (canMove(bargard())) {
            
            switch (bargard()) {
                
                case UP:
                    y -= velocity;
                    break;
                    
                case DOWN:
                    y += velocity;
                    break;
                    
                case LEFT:
                    x -= velocity;
                    break;
                    
                case RIGHT:
                    x += velocity;
                    break;
            }
        }
    }
}
