package the.dark.tank.rises;

import java.applet.AudioClip;

public class Levels {

    Team team1,
            team2;
    AudioClip mission;
    static int no = 1;
    ScreenManager sm;

    public Levels(ScreenManager sm) {

        this.sm = sm;

        team1 = new Team(1, sm);
        team2 = new Team(2, sm);

    }

    public void square(int x, int y, int kind) {

        for (int j = -1; j < 2; j++) {

            for (int p = -1; p < 2; p++) {

                sm.background.bg[ x + j][ y + p] = kind;
            }
        }
    }

    public void initialize(int no) {

        for (int i = 0; i < sm.ol.size(); i++) {

            if (sm.ol.get(i) instanceof Tank) {

                Tank t = (Tank) sm.ol.get(i);

                t.isAlive = false;
            }

            sm.removeObject(sm.ol.get(i));
        }

        sm.initPosition();
        sm.initView();

        sm.background.initialize();

        switch (no) {

            case 0:
                menu();
                break;

            case 1:
                level1();
                break;

            case 2:
                level2();
                break;
        }
    }

    private void menu() {

        mission = sm.parent.getAudioClip(sm.parent.getCodeBase(), "main menu.mid");
        mission.loop();
    }

    private void level1() {

        mission = sm.parent.getAudioClip(sm.parent.getCodeBase(), "mission1.mid");
        mission.loop();
        
//        team1.createKamikaze(500, 500, 30, 30, 100, 6);
//        //team1.createHunter(310, 300, 30, 30, 100, 6);
//
//        team2.createKamikaze(200, 250, 30, 30, 100, 10);
//        team1.createHunter(760 ,10, 30, 30, 100, 20);
//        team1.createPlayer(350, 160, 30, 30, 100, 100);
//        team2.createRecon(10, 50, 30, 30, 100, 20);
//        team2.createDemolition(10, 10, 30, 30, 100, 20);
        
        team1.createRecon(10, 560, 30, 30, 80, 25);
//        team1.createPlayer(200, 560, 30, 30, 100, 15);
        team1.createPlayer(400, 560, 30, 30, 100, 15);
        team1.createKamikaze(760, 560, 30, 30, 40, 10);
        team1.createDemolition(460, 560, 30, 30, 150, 20);
        
        team2.createRecon(760, 10, 30, 30, 80, 25);
        team2.createKamikaze(10, 10, 30, 30, 40, 10);
        team2.createDemolition(300, 10, 30, 30, 150, 20);
        
        for (int i = 7; i < sm.parent.GAME_WIDTH/sm.parent.GAME_SCALE - 7; i++)
        square(i, 7, 1);
        
        for (int i = 20; i < sm.parent.GAME_HEIGHT/sm.parent.GAME_SCALE - 20; i++)
        square(40, i, 1);
        
        for (int i = 10; i < sm.parent.GAME_HEIGHT/sm.parent.GAME_SCALE - 40; i++)
        square(20, i, 1);
        
        for (int i = 10; i < sm.parent.GAME_HEIGHT/sm.parent.GAME_SCALE - 40; i++)
        square(60, i, 1);
        
        for (int i = 40; i < sm.parent.GAME_HEIGHT/sm.parent.GAME_SCALE - 10; i++)
        square(30, i, 1);
        
        for (int i = 40; i < sm.parent.GAME_HEIGHT/sm.parent.GAME_SCALE - 10; i++)
        square(50, i, 1);
        
        for (int i = 7; i < sm.parent.GAME_WIDTH/sm.parent.GAME_SCALE - 7; i++)
        square(i, 52, 1);
        
        for (int i = 13; i < 15; i++)
        square(38, i, 4);
        
        for (int i = 13; i < 15; i++)
        square(42, i, 4);
        
        for (int i = 13; i < 15; i++)
        square(40, i, 3);
        
        for (int i = 45; i < 47; i++)
        square(38, i, 4);
        
        for (int i = 45; i < 47; i++)
        square(42, i, 4);
        
        for (int i = 45; i < 47; i++)
        square(40, i, 3);
    }

    private void level2() {

        mission = sm.parent.getAudioClip(sm.parent.getCodeBase(), "mission2.mid");

        mission.loop();
        
        team1.createPlayer(250, 560, 30, 30, 100, 15);
        team1.createRecon(10, 560, 30, 30, 80, 25);
        team1.createRecon(560, 560, 30, 30, 80, 25);
        
        team2.createKamikaze(10, 10, 30, 30, 40, 10);
        team2.createDemolition(20, 10, 30, 30, 150, 20);
        team2.createKamikaze(200, 10, 30, 30, 40, 10);
        team2.createDemolition(300, 10, 30, 30, 150, 20);
        
        for (int i = 5; i < 15; i++)
        square(i, 5, 1);
        
        for (int i = 20; i < 30; i++)
        square(i, 5, 1);
        
        for (int i = 35; i < 45; i++)
        square(i, 5, 1);
        
        for (int i = 50; i < 60; i++)
        square(i, 5, 1);
        
        for (int i = 55; i < 65; i++)
        square(i, 5, 1);
        
        for (int i = 70; i < 78; i++)
        square(i, 5, 1);
        
        for (int i = 5; i < 15; i++)
        square(i, 25, 1);
        
        for (int i = 20; i < 30; i++)
        square(i, 25, 1);
        
        for (int i = 35; i < 45; i++)
        square(i, 25, 1);
        
        for (int i = 50; i < 60; i++)
        square(i, 25, 1);
        
        for (int i = 55; i < 65; i++)
        square(i, 25, 1);
        
        for (int i = 70; i < 78; i++)
        square(i, 25, 1);
        
        for (int i = 5; i < 15; i++)
        square(i, 45, 1);
        
        for (int i = 20; i < 30; i++)
        square(i, 45, 1);
        
        for (int i = 35; i < 45; i++)
        square(i, 45, 1);
        
        for (int i = 50; i < 60; i++)
        square(i, 45, 1);
        
        for (int i = 55; i < 65; i++)
        square(i, 45, 1);
        
        for (int i = 70; i < 78; i++)
        square(i, 45, 1);
        
        for (int i = 2; i < 77; i++)
        square(i, 30, 3);

    }

    public void mission() {

        if (team1.lifes < 0) {
            System.out.println("Avarin Team 2!");

            nextLevel();
        } else if (team2.lifes < 0) {
            System.out.println("Avarin Team 1!");

            nextLevel();
        }
    }

    private void nextLevel() {

        initialize(++no);
    }
}
