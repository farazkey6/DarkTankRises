
package the.dark.tank.rises;

import java.awt.Graphics;

public abstract class ScreenObject {
    
    int x,
        y,
        w,
        h;
    
    ScreenManager sm;
    
    public ScreenObject(int x, int y, int w, int h, ScreenManager sm) {
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sm = sm;
    }
    
    public abstract void draw(Graphics g);
}
