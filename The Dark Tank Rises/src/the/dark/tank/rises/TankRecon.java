package the.dark.tank.rises;

import java.awt.Image;

public class TankRecon extends TankBot implements Runnable {

    private char maze = 'R';

    public TankRecon(int x, int y, int w, int h, int team, int health, ScreenManager sm) {

        super(x, y, w, h, team, health, sm);

        img = images();

        velocity = 10;

        face = FACE.DOWN;
        
        range = 20;
    }

    @Override
    public void run() {

        while (isAlive) {

            do {
                pathAlgorithm();
            } while (!canMove(face));

            step();

            marking();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public final Image[] images() {

        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i] = sm.parent.getImage(sm.parent.getCodeBase(), "WhiteTank" + i + ".png");
        }

        return temp;
    }

    @Override
    public void pathAlgorithm() {

        if (maze == 'R') {
            rightMaze();
        } else {
            leftMaze();
        }
    }

    public void rightMaze() {

        if (canMove(rotateRight())) {
            face = rotateRight();
        } else if (!canMove(face)) {
            face = rotateLeft();
        }
    }

    public void leftMaze() {

        if (canMove(rotateLeft())) {
            face = rotateLeft();
        } else if (!canMove(face)) {
            face = rotateRight();
        }
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

                if (sm.position[ i][ j] != team && sm.position[ i][ j] != 0) { //if view enemy

//                    System.out.println("I can see!");

                    for (int k = 0; k < sm.ol.size(); k++) { // then MARK HIM!

                        ScreenObject so = sm.ol.get(k);

                        if (so instanceof Tank) {

                            Tank t = (Tank) so;

                            if (t.team != team) {
                                for (int p = t.x / sm.parent.GAME_SCALE; p < (t.x + t.w) / sm.parent.GAME_SCALE; p++) {
                                    for (int q = t.y / sm.parent.GAME_SCALE; q < (t.y + t.h) / sm.parent.GAME_SCALE; q++) {
                                        if (p == i && q == j) {
                                            if (t.canView(team)) {

                                                t.mark = true;
                                            }
                                        }
                                    }
                                }
                            }


                            if (t.team != team) {
                                if (face == FACE.UP && y > t.y
                                        || face == FACE.DOWN && y < t.y
                                        || face == FACE.LEFT && x > t.x
                                        || face == FACE.RIGHT && x < t.x) {
                                    runAway();
                                }
                            }

                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void runAway() {

        face = bargard();

        switch (maze) {

            case 'R':
                maze = 'L';
                break;
            case 'L':
                maze = 'R';
                break;
        }
    }
}