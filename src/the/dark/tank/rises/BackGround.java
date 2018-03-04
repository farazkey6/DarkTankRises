
package the.dark.tank.rises;

import java.awt.Graphics;
import java.awt.Image;

public class BackGround {
    
    int[][] bg;
    
    private TheDarkTankRises parent;
    
    private Image[] bgImg;
    
    public BackGround(TheDarkTankRises parent) {
        
        this.parent = parent;
        
        bg = new int[ parent.GAME_WIDTH / parent.GAME_SCALE ][ parent.GAME_HEIGHT / parent.GAME_SCALE ];
        
        initialize();
        
        bgImg = new Image[ 5 ];
        bgImg[ 0 ] = parent.getImage(parent.getCodeBase(), "blank.png");
        bgImg[ 1 ] = parent.getImage(parent.getCodeBase(), "brick.png");
        bgImg[ 2 ] = parent.getImage(parent.getCodeBase(), "jungle.jpg");
        bgImg[ 3 ] = parent.getImage(parent.getCodeBase(), "river.jpg");
        bgImg[ 4 ] = parent.getImage(parent.getCodeBase(), "iron.png");
        
        for(int i = 0; i <= 10; i++) {
            
            bg[ 5 ][ i ] = 1;
            bg[ 6 ][ i ] = 1;
            bg[ 7 ][ i ] = 1;
        }
        
        for(int i = 0; i < 30; i++)
            bg[ 75 ][ i ] = 1;
        
        for (int i = 8; i < 30; i++) {
            
            bg[ i ][ 10 ] = 1;
            bg[ 30 ][ i + 2 ] = 1;
        }
    }
    
    public final void initialize() {
        
        for (int i = 0; i < bg.length; i++)
            for (int j = 0; j < bg[ i ].length; j++)
                bg[ i ][ j ] = 0;
        
        for (int i = 0; i <bg.length; i++) {
            bg[ i ][ 0 ] = 4;
            bg[ i ][ bg[ 0 ].length - 1 ] = 4;
        }
        for (int i = 0; i <bg[ 0 ].length; i++) {
            bg[ 0 ][ i ] = 4;
            bg[ bg.length - 1 ][ i ]= 4;
        }
    }
    
    public void draw(Graphics g) {
        
        for (int i = 0; i < bg.length; i++) {
            
            for (int j = 0; j < bg[ i ].length; j++) {

                if (parent.sm.teamView[ 1 ][ i ][ j ])
                    g.drawImage(bgImg[ bg[ i ][ j ] ], i * parent.GAME_SCALE, j * parent.GAME_SCALE, parent);
            }
        }
    }
}