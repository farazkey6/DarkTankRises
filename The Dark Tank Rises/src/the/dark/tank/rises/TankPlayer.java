package the.dark.tank.rises;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static the.dark.tank.rises.Tank.FACE.UP;

public class TankPlayer extends Tank {

    private boolean forward,
            backward,
            turnleft,
            turnright;

    public TankPlayer(int x, int y, int w, int h, int team, int health, ScreenManager sm) {

        super(x, y, w, h, team, health, sm);

        img = images();

        velocity = 10;

        face = FACE.DOWN;
        
        range = 10;

        sm.parent.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        
                        switch (ke.getKeyCode()) {

                            case KeyEvent.VK_UP:
                                forward = true;
                                break;

                            case KeyEvent.VK_DOWN:
                                backward = true;
                                break;

                            case KeyEvent.VK_LEFT:
                                turnleft = true;
                                break;

                            case KeyEvent.VK_RIGHT:
                                turnright = true;
                                break;
                        }
                        
                        if(forward && canMove(face))
                            step();
                        
                        if(backward)
                            backStep();
                        
                        if(turnleft)
                            face = rotateLeft();
                        
                        if(turnright)
                            face = rotateRight();
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                        
                        switch (ke.getKeyCode()) {

                            case KeyEvent.VK_UP:
                                forward = false;
                                break;

                            case KeyEvent.VK_DOWN:
                                backward = false;
                                break;

                            case KeyEvent.VK_LEFT:
                                turnleft = false;
                                break;

                            case KeyEvent.VK_RIGHT:
                                turnright = false;
                                break;
                        }
                    }
                });
    }

    @Override
    public final Image[] images() {

        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i] = sm.parent.getImage(sm.parent.getCodeBase(), "WhiteTank" + i + ".png");
        }

        return temp;
    }

    public FACE uTurn(){
        face = rotateLeft();
        face = rotateLeft();
        return face;
    }
    
    public FACE rotateRight() {

        switch (face) {

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

        switch (face) {

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
}
