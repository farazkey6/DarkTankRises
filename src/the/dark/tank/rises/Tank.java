package the.dark.tank.rises;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Tank extends ScreenObject {

    int health,
            life = 3;

    enum FACE {

        UP, RIGHT, DOWN, LEFT
    };
    boolean mark = false;
    private Image markImg;
    Image[] img;
    int velocity,
            range,
            team,
            damage;
    FACE face;
    boolean isAlive = true;

    public Tank(int x, int y, int w, int h, int team, int health, int range, ScreenManager sm) {

        super(x, y, w, h, sm);

        this.health = health;
        this.team = team;
        this.range = range;

        img = new Image[4];

        markImg = sm.parent.getImage(sm.parent.getCodeBase(), "mark.png");
    }

    public void health(int damage) {

        health -= damage;

        if (health <= 0) {

            isAlive = false;
        }
    }

    public boolean canMove(FACE f) {

        for (int i = 0; i < sm.parent.IMAGE_SIZE / sm.parent.GAME_SCALE; i++) {

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
        Bullet bullet = new Bullet(x, y, 5, 5, team, damage, face, sm);
        sm.addObject(bullet);
        Thread t = new Thread(bullet);
        t.start();
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

        if (canView(sm.parent.PLAYER_TEAM)) {

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

    public FACE bargard() {

        switch (face) {

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

    public void respawn(Tank tank) {

        sm.removeObject(tank);

        boolean rs = false;

        if (--life >= 0) {

            Team team1 = new Team(1, sm),
                    team2 = new Team(2, sm);

            switch (team) {
                case 1:
                    y = sm.parent.GAME_HEIGHT - sm.parent.IMAGE_SIZE - sm.parent.GAME_SCALE;
                    do {

                        if (sm.position[(sm.parent.GAME_SCALE + sm.parent.IMAGE_SIZE) / sm.parent.GAME_SCALE][y / sm.parent.GAME_SCALE] == 0
                                && !rs) {

                            x = sm.parent.GAME_SCALE;


                            if (tank instanceof TankDemolition) {
                                team1.createDemolition(x, y, w, h, 150, range);

                                rs = true;
                            }
                            
                            if (tank instanceof TankHunter) {
                                team1.createHunter(x, y, w, h, 80, range);

                                rs = true;
                            }
                            if (tank instanceof TankKamikaze) {
                                team1.createKamikaze(x, y, w, h, 40, range);

                                rs = true;
                            }
                            if (tank instanceof TankRecon) {
                                team1.createRecon(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankPlayer) {
                                team1.createPlayer(x, y, w, h, 100, range);

                                rs = true;
                            }
                        }

                        if (sm.position[(sm.parent.GAME_WIDTH - sm.parent.GAME_SCALE - sm.parent.IMAGE_SIZE) / sm.parent.GAME_SCALE][y / sm.parent.GAME_SCALE] == 0
                                && !rs) {

                            x = sm.parent.GAME_WIDTH - sm.parent.GAME_SCALE - sm.parent.IMAGE_SIZE;

                            if (tank instanceof TankDemolition) {
                                team1.createDemolition(x, y, w, h, 150, range);

                                rs = true;
                            }
                            
                            if (tank instanceof TankHunter) {
                                team1.createHunter(x, y, w, h, 80, range);

                                rs = true;
                            }
                            if (tank instanceof TankKamikaze) {
                                team1.createKamikaze(x, y, w, h, 40, range);

                                rs = true;
                            }
                            if (tank instanceof TankRecon) {
                                team1.createRecon(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankPlayer) {
                                team1.createPlayer(x, y, w, h, 100, range);

                                rs = true;
                            }

                        }

                    } while (!rs);
                case 2:
                    y = sm.parent.GAME_SCALE;
                    do {

                        if (sm.position[(sm.parent.GAME_SCALE + sm.parent.IMAGE_SIZE) / sm.parent.GAME_SCALE][y / sm.parent.GAME_SCALE] == 0
                                && !rs) {

                            x = sm.parent.GAME_SCALE;

                            if (tank instanceof TankDemolition) {
                                team2.createDemolition(x, y, w, h, 100, range);

                                rs = true;
                            }
                            
                            if (tank instanceof TankHunter) {
                                team2.createHunter(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankKamikaze) {
                                team2.createKamikaze(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankRecon) {
                                team2.createRecon(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankPlayer) {
                                team2.createPlayer(x, y, w, h, 100, range);

                                rs = true;
                            }

                        }

                        if (sm.position[(sm.parent.GAME_WIDTH - sm.parent.GAME_SCALE - sm.parent.IMAGE_SIZE) / sm.parent.GAME_SCALE][y / sm.parent.GAME_SCALE] == 0
                                && !rs) {

                            x = sm.parent.GAME_WIDTH - sm.parent.GAME_SCALE - sm.parent.IMAGE_SIZE;
                            
                            if (tank instanceof TankDemolition) {
                                team2.createDemolition(x, y, w, h, 100, range);

                                rs = true;
                            }
                            
                            if (tank instanceof TankHunter) {
                                team2.createHunter(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankKamikaze) {
                                team2.createKamikaze(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankRecon) {
                                team2.createRecon(x, y, w, h, 100, range);

                                rs = true;
                            }
                            if (tank instanceof TankPlayer) {
                                team2.createPlayer(x, y, w, h, 100, range);

                                rs = true;
                            }

                        }

                    } while (!rs);
            }
        }
    }

    public abstract Image[] images();
}