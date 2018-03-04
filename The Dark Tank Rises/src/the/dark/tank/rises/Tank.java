package the.dark.tank.rises;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Tank extends ScreenObject {

    private int health,
            life = 3;

    enum FACE {

        UP, RIGHT, DOWN, LEFT
    };
    boolean mark = false;
    Image markImg;
    int velocity,
            range,
            team;
    FACE face;
    Image[] img;
    boolean isAlive = true;

    public Tank(int x, int y, int w, int h, int team, int health, ScreenManager sm) {

        super(x, y, w, h, sm);

        this.health = health;
        this.team = team;

        img = new Image[4];

        markImg = sm.parent.getImage(sm.parent.getCodeBase(), "mark.png");
    }

    public void health(int damage) {

        health -= damage;

        if (health <= 0) {
            life();
        }
    }

    public void life() {

        if (--life <= 0) {
            sm.removeObject(this);
        } else {
            life = 1;
        }
    }

    public boolean canMove(FACE f) {

        for (int i = 0; i < w / sm.parent.GAME_SCALE; i++) {

            switch (f) {

                case UP:
                    if (((sm.background.bg[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y - velocity) / sm.parent.GAME_SCALE] != 0)
                            && (sm.background.bg[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y - velocity) / sm.parent.GAME_SCALE] != 2))
                            || (y - velocity <= 0)
                            || (sm.position[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y - velocity) / sm.parent.GAME_SCALE] != 0)) {
                        return false;
                    }
                    break;

                case DOWN:
                    if (((sm.background.bg[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y + h + velocity) / sm.parent.GAME_SCALE - 1] != 0)
                            && (sm.background.bg[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y + h + velocity) / sm.parent.GAME_SCALE - 1] != 2))
                            || (y + h + velocity >= sm.parent.GAME_HEIGHT)
                            || (sm.position[ (x + i * velocity) / sm.parent.GAME_SCALE][ (y + h + velocity) / sm.parent.GAME_SCALE - 1] != 0)) {
                        return false;
                    }
                    break;

                case RIGHT:
                    if (((sm.background.bg[ (x + w + velocity) / sm.parent.GAME_SCALE - 1][ (y + i * velocity) / sm.parent.GAME_SCALE] != 0)
                            && (sm.background.bg[ (x + w + velocity) / sm.parent.GAME_SCALE - 1][ (y + i * velocity) / sm.parent.GAME_SCALE] != 2))
                            || (x + w + velocity >= sm.parent.GAME_WIDTH)
                            || (sm.position[ (x + w + velocity) / sm.parent.GAME_SCALE - 1][ (y + i * velocity) / sm.parent.GAME_SCALE] != 0)) {
                        return false;
                    }
                    break;

                case LEFT:
                    if (((sm.background.bg[ (x - velocity) / sm.parent.GAME_SCALE][ (y + i * velocity) / sm.parent.GAME_SCALE] != 0)
                            && (sm.background.bg[ (x - velocity) / sm.parent.GAME_SCALE][ (y + i * velocity) / sm.parent.GAME_SCALE] != 2))
                            || (x - velocity <= 0)
                            || (sm.position[ (x - velocity) / sm.parent.GAME_SCALE][ (y + i * velocity) / sm.parent.GAME_SCALE] != 0)) {
                        return false;
                    }
                    break;
            }
        }

        return true;
    }

    public void left() {

        if (face != FACE.LEFT) {
            face = FACE.LEFT;
        } else {
            x -= velocity;
        }
    }

    public void right() {

        if (face != FACE.RIGHT) {
            face = FACE.RIGHT;
        } else {
            x += velocity;
        }
    }

    public void up() {

        if (face != FACE.UP) {
            face = FACE.UP;
        } else {
            y -= velocity;
        }
    }

    public void down() {

        if (face != FACE.DOWN) {
            face = FACE.DOWN;
        } else {
            y += velocity;
        }
    }

    public void backStep() {

        switch (face) {

            case UP:
                if (canMove(FACE.DOWN)) {
                    y += velocity;
                }
                break;

            case DOWN:
                if (canMove(FACE.UP)) {
                    y -= velocity;
                }
                break;

            case RIGHT:
                if (canMove(FACE.LEFT)) {
                    x -= velocity;
                }
                break;

            case LEFT:
                if (canMove(FACE.RIGHT)) {
                    x += velocity;
                }
                break;
        }

    }

    public void step() {

        switch (face) {

            case UP:
                up();
                break;

            case DOWN:
                down();
                break;

            case RIGHT:
                right();
                break;

            case LEFT:
                left();
                break;
        }
    }

    public void fire() {
    }

    /*
     */
    public void view() {

        int minI = (x + w / 2) / sm.parent.GAME_SCALE - range / 2,
                minJ = (y + h / 2) / sm.parent.GAME_SCALE - range / 2,
                maxI = (x + w / 2) / sm.parent.GAME_SCALE + range / 2 + 1,
                maxJ = (y + h / 2) / sm.parent.GAME_SCALE + range / 2 + 1;

        int beginI = minI >= 0 ? minI : 0,
                endI = maxI < sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ? maxI : sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE,
                beginJ = minJ >= 0 ? minJ : 0,
                endJ = maxJ < sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ? maxJ : sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE;

        for (int i = beginI; i < endI; i++) {

            for (int j = beginJ; j < endJ; j++) {

                sm.teamView[ team][ i][ j] = true;
            }
        }
    }

    @Override
    public void draw(Graphics g) {

        if (canView(1)) {

            switch (face) {

                case UP:
                    g.drawImage(img[ 0], x, y, sm.parent);
                    break;

                case RIGHT:
                    g.drawImage(img[ 1], x, y, sm.parent);
                    break;

                case DOWN:
                    g.drawImage(img[ 2], x, y, sm.parent);
                    break;

                case LEFT:
                    g.drawImage(img[ 3], x, y, sm.parent);
                    break;
            }
        } else if (mark) {

            g.drawImage(markImg, x, y, sm.parent);
        }
    }

    public boolean canView(int team) {

        if (sm.teamView[ team][ (x + w / 2) / sm.parent.GAME_SCALE][ (y + h / 2) / sm.parent.GAME_SCALE]) {
            return true;
        }

        return false;
    }

    public void respawn() {
    }

    public abstract Image[] images();
}