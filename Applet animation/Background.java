import java.awt.*;
/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class Background extends Rect{
    private Color c;
    public int x, y, width, height;

    public Background(int x, int y, int width, int height, Color c){
        super(x, y, width, height, c);
    }
    public void paint(Graphics g){
        super.paint(g);
    }
    public void setTopLeftPoint(int x, int y){
        super.setTopLeftPoint(x, y);
    }
    public void reposition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setCenter(int x, int y){
        super.setCenter(x, y);
    }
}