
package the.dark.tank.rises;

import java.util.ArrayList;

public class Team {
    
    private int team;
                
    
    int lifes;
    
    private ScreenManager sm;
    
    private ArrayList<Tank> targets; 
    
    public Team(int team, ScreenManager sm) {
        
        this.team = team;
        this.sm = sm;
        
        targets = new ArrayList<>();
    }
    
    public void createPlayer(int x, int y, int w, int h, int health, int range) {
        
        TankPlayer player = new TankPlayer(x, y, w, h, team, health, range, sm);
        
        sm.addObject(player);
    }
    
    public void createDemolition(int x, int y, int w, int h, int health, int range) {
        
        TankDemolition demolition = new TankDemolition(x, y, w, h, team, health, range, sm);
        
        sm.addObject(demolition);
        
        Thread t = new Thread(demolition);
        t.start();
    }
    
    public void createHunter(int x, int y, int w, int h, int health, int range) {
        
        TankHunter hunter = new TankHunter(x, y, w, h, team, health, range, targets, sm);
        
        sm.addObject(hunter);
        
        Thread t = new Thread(hunter);
        t.start();
    }
    
    public void createRecon(int x, int y, int w, int h, int health, int range) {
        
        TankRecon recon = new TankRecon(x, y, w, h, team, health, range, targets, sm);
        
        sm.addObject(recon);
        
        Thread t = new Thread(recon);
        t.start();
    }
    
    public void createKamikaze(int x, int y, int w, int h, int health, int range) {
        
        TankKamikaze kamikaze = new TankKamikaze(x, y, w, h, team, health, range, sm);
        
        sm.addObject(kamikaze);
        
        Thread t = new Thread(kamikaze);
        t.start();
    }
}
