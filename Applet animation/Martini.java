import java.awt.*;
/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class Martini extends PositionalGraphic{
    private Triangle bowl;
    private Triangle base;
    private PositionalGraphic stem, bg;

    public Martini(int x, int y, int width, int height, Color m, Color b) {
        super(x, y, width, height, m);
        bowl = new Triangle(super.getTopLeftCorner(), super.getTopRightCorner(), getBowlCenter(), m);
        stem = new Rect(getStemX(), getStemY(), getStemWidth(), getStemHeight(), m);
        base = new Triangle(getBaseCenter(), getBaseLowerLeft(), getBaseLowerRight(), m);
        bg = new Rect(x, y, getWidth(), getHeight(), b);
    }
    public Martini(int x, int y, int width, int height, Color m) {
        //a glass with no background color
        super(x, y, width, height, m);
        bowl = new Triangle(super.getTopLeftCorner(), super.getTopRightCorner(), getBowlCenter(), m);
        stem = new Rect(getStemX(), getStemY(), getStemWidth(), getStemHeight(), m);
        base = new Triangle(getBaseCenter(), getBaseLowerLeft(), getBaseLowerRight(), m);
    }
    public void setTopLeftPoint(int x, int y){
        super.setTopLeftCorner(x, y);
        reposition();
    }
    public void reposition(){
        int x = getBoundingRectangle().x;
        int y = getBoundingRectangle().y;
        bowl.reposition(super.getTopLeftCorner(), super.getTopRightCorner(), getBowlCenter());
        stem.reposition(getStemX(), getStemY(), getStemWidth(), getStemHeight());
        base.reposition(getBaseCenter(), getBaseLowerLeft(), getBaseLowerRight());
        bg.reposition(x, y, getWidth(), getHeight());
    }

    public Point getTopCenterPoint(){
        return new Point(super.getTopCenterPoint());
    }

    public void paint(Graphics g){
        if(bg != null)
            bg.paint(g);
        bowl.paint(g);
        stem.paint(g);
        base.paint(g);
    }
}