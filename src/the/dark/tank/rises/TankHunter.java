/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.dark.tank.rises;

import java.awt.Image;
import java.util.ArrayList;

public class TankHunter extends TankBot implements Runnable {
    
    private ArrayList<Tank> targets;
    
    private ArrayList<FACE> path;
    
    private boolean[][] wayChecker;
    private int[][] distance,
                    predI,
                    predJ;
    
    public TankHunter(int x, int y, int w, int h, int team, int health, int range, ArrayList<Tank> targets, ScreenManager sm) {

        super(x, y, w, h, team, health, range, sm);

        this.targets = targets;
        
        velocity = 10;

        img = images();

        face = Tank.FACE.DOWN;
        
        path = new ArrayList<>();
        
        wayChecker = new boolean[ sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ][ sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ];
        
        distance = new int[ sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ][ sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ];
        predI = new int[ sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ][ sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ];
        predJ = new int[ sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ][ sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ];
    }

    @Override
    public void run() {
        
        best(41, 50);
        
        respawn(this);
    }

    @Override
    public void pathAlgorithm() {
        
        if (path.isEmpty()) {
            
            
        }
        
        else {
            
            
        }
            
    }

    @Override
    public void runAway() {
        
    }
    
    public void best(int i, int j) {
        
        createSet();
        
        distance[x / sm.parent.GAME_SCALE ][ y / sm.parent.GAME_SCALE ] = 0;
        
        path.clear();
        
        int tx = 0,
            ty = 0;
        
        while (!isEmpty(wayChecker)) {
            
            System.out.println(predI[ tx ][ ty ] + " " + predJ[ tx ][ ty ]);
            
            int temp = Integer.MAX_VALUE;
            
            for (int p = 0; p < wayChecker.length; p++) {
                for (int q = 0; q < wayChecker[ i ].length; q++) {
                    
                    if (wayChecker[ p ][ q ]) {
                        
                        if (distance[ p ][ q ] < temp) {
                            
                            temp = distance[ p ][ q ];
                            
                            tx = p;
                            ty = q;
                        }
                    }
                }
            }
                                
            wayChecker[ tx ][ ty ] = false;
                        
            if (tx == i && ty == j)
                movementList(i, j);
            
            if (distance[ tx ][ ty ] == Integer.MAX_VALUE - 1)
                break;
                
            if (tx - 1 >= 0 &&
                sm.background.bg[ tx - 1 ][ ty ] == 0) {
                
                if (distance[ tx ][ ty ] + 1 < distance[ tx - 1 ][ ty ]) {
                    
                    distance[ tx - 1 ][ ty ] = distance[ tx ][ ty ] + 1;
                    
                    predI[ tx - 1 ][ ty ] = tx;
                    predJ[ tx - 1 ][ ty ] = ty;
                }
            }
            
            if (ty - 1 >= 0 &&
                sm.background.bg[ tx ][ ty - 1 ] == 0) {
                
                if (distance[ tx ][ ty ] + 1 < distance[ tx ][ ty - 1 ]) {
                    
                    distance[ tx ][ ty - 1 ] = distance[ tx ][ ty ] + 1;
                    
                    predI[ tx ][ ty - 1 ] = tx;
                    predJ[ tx ][ ty - 1 ] = ty;
                }
            }
            
            if (tx + w / sm.parent.GAME_SCALE + 1 < sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE &&
                sm.background.bg[ tx + w / sm.parent.GAME_SCALE + 1 ][ ty ] == 0) {
                
                if (distance[ tx ][ ty ] + 1 < distance[ tx + w / sm.parent.GAME_SCALE + 1 ][ ty ]) {
                    
                    distance[ tx + w / sm.parent.GAME_SCALE + 1 ][ ty ] = distance[ tx ][ ty ] + 1;
                    
                    predI[ tx + w / sm.parent.GAME_SCALE + 1 ][ ty ] = tx;
                    predJ[ tx + w / sm.parent.GAME_SCALE + 1 ][ ty ] = ty;
                }
            }
            
            if (ty + h / sm.parent.GAME_SCALE + 1 < sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE &&
                sm.background.bg[ tx ][ ty + h / sm.parent.GAME_SCALE + 1 ] == 0) {
                
                if (distance[ tx ][ ty ] + 1 < distance[ tx ][ ty + h / sm.parent.GAME_SCALE + 1 ]) {
                    
                    distance[ tx ][ ty + h / sm.parent.GAME_SCALE + 1 ] = distance[ tx ][ ty ] + 1;
                    
                    predI[ tx ][ ty + h / sm.parent.GAME_SCALE + 1 ] = tx;
                    predJ[ tx ][ ty + h / sm.parent.GAME_SCALE + 1 ] = ty;
                }
            }
        }
    }

    @Override
    public final Image[] images() {

        Image[] temp = new Image[img.length];

        for (int i = 0; i < img.length; i++) {

            temp[ i ] = sm.parent.getImage(sm.parent.getCodeBase(), "WhiteTank" + i + ".png");
        }

        return temp;
    }
    
    public void createSet() {
        
        for (int i = 0; i < wayChecker.length; i++) {
            for (int j = 0; j < wayChecker[ i ].length; j++) {
                if (sm.background.bg[ i ][ j ] == 0) {
                    
                    wayChecker[ i ][ j ] = true;
                    
                    distance[ i ][ j ] = Integer.MAX_VALUE - 1;
                }
            }
        }
    }
    
    public boolean isEmpty(boolean[][] list) {
        
        for (int i = 0; i < list.length; i++)
            for (int j = 0; j <  list[ i ].length; j++)
                if (wayChecker[ i ][ j ])
                    return false;
              
        return true;
    }
    
    private void movementList(int i, int j) {
        
        while (predI[ i ][ j ] != x / sm.parent.GAME_SCALE && predJ[ i ][ j ] != y / sm.parent.GAME_SCALE) {
            
            if (predI[ i ][ j ] < i)
                path.add(0, FACE.LEFT);
            else if (predI[ i ][ j ] > i)
                path.add(0, FACE.RIGHT);
            else if (predJ[ i ][ j ] < j)
                path.add(0, FACE.UP);
            else if (predJ[ i ][ j ] > j)
                path.add(0, FACE.DOWN);
                    
            int t = predI[ i ][ j ];
            j = predJ[ i ][ j ];
            i = t;
        }
    } 
}