import java.awt.*;
/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class Triangle
{
    private int[] xpoints = new int[3];
    private int[] ypoints = new int[3];
    private Color c;
    Polygon t;

    public Triangle(Point p1, Point p2, Point p3, Color c){
        xpoints[0] = p1.x;
        xpoints[1] = p2.x;
        xpoints[2] = p3.x;
        ypoints[0] = p1.y;
        ypoints[1] = p2.y;
        ypoints[2] = p3.y;
        this.c = c;
        makePoly(xpoints, ypoints);
    }
    public void makePoly(int[] xpoints, int[] ypoints){
        t = new Polygon(xpoints, ypoints, 3);
    }
    public void reposition(Point p1, Point p2, Point p3){
        xpoints[0] = p1.x;
        xpoints[1] = p2.x;
        xpoints[2] = p3.x;
        ypoints[0] = p1.y;
        ypoints[1] = p2.y;
        ypoints[2] = p3.y;
        makePoly(xpoints, ypoints);
    }

    public void paint(Graphics g){
        g.setColor(c);
        g.drawPolygon(t);
        g.fillPolygon(t);
    }
}