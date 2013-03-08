import java.awt.*;
/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class Rect extends PositionalGraphic
{
    public Rect(int x, int y, int width, int height, Color c){
        super(x, y, width, height, c);
    }
    public void setCenter(int x, int y){
        super.setCenter(x, y);
    }
    public void reposition(int x, int y, int width, int height){
        super.setTopLeftCorner(x, y);
        setWidth(width);
        setHeight(height);
    }
    public void setTopLeftPoint(int x, int y){
        super.setTopLeftCorner(x, y);
    }
    public void paint(Graphics g){
        int x = getBoundingRectangle().x;
        int y = getBoundingRectangle().y;
        g.setColor(getCurrentColor());
        g.drawRect(x, y, getWidth(), getHeight());
        g.fillRect(x, y, getWidth(), getHeight());
    }
}