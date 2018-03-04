
package the.dark.tank.rises;

import java.awt.Graphics;
import java.awt.Image;

public class BackGround {
    
    int[][] bg;
    
    private ScreenManager sm;
    
    private Image[] bgImg;
    
    public BackGround(ScreenManager sm) {
        
        this.sm = sm;
        
        bg = new int[ sm.parent.GAME_WIDTH / sm.parent.GAME_SCALE ][ sm.parent.GAME_HEIGHT / sm.parent.GAME_SCALE ];
        
        bgImg = new Image[ 5 ];
        bgImg[ 0 ] = sm.parent.getImage(sm.parent.getCodeBase(), "blank.png");
        bgImg[ 1 ] = sm.parent.getImage(sm.parent.getCodeBase(), "brick.png");
        bgImg[ 2 ] = sm.parent.getImage(sm.parent.getCodeBase(), "jungle.jpg");
        bgImg[ 3 ] = sm.parent.getImage(sm.parent.getCodeBase(), "river.jpg");
        bgImg[ 4 ] = sm.parent.getImage(sm.parent.getCodeBase(), "iron.jpg");
        
        initialize();
        
        for(int i = 0; i <= 10; i++) {
            
            bg[ 5 ][ i ] = 1;
            bg[ 6 ][ i ] = 1;
            bg[ 7 ][ i ] = 1;
            
            
        }
        
        for(int i = 0; i < 30; i++)
            bg[ 75 ][ i ] = 1;
    }
    
    private void initialize() {
        
        for (int i = 0; i <bg.length; i++) {
            bg[ i ][ 0 ] = 1;
            bg[ i ][ bg[ 0 ].length - 1 ] = 1;
        }
        for (int i = 0; i <bg[ 0 ].length; i++) {
            bg[ 0 ][ i ] = 1;
            bg[ bg.length - 1 ][ i ]= 1;
        }
    }
    
    public void draw(Graphics g) {
        
        for (int i = 0; i < bg.length; i++) {
            
            for (int j = 0; j < bg[ i ].length; j++) {

                if (sm.teamView[ 1 ][ i ][ j ])
                    g.drawImage(bgImg[ bg[ i ][ j ] ], i * sm.parent.GAME_SCALE, j * sm.parent.GAME_SCALE, sm.parent);
            }
        }
    }
}