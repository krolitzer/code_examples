import java.awt.*;
/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class MartiniCluster extends PositionalGraphic{

    private Martini m[] = new Martini[4];
    Color deepPink = new Color (238, 18, 137);
    Color seaGreen = new Color(84, 255, 159);
    Color darkOrchid = new Color(153, 50, 204);

    public MartiniCluster(int x, int y, int width, int height){
        super(x, y, width, height);
        x -= getHorizontalCenter();
        m[0] = new Martini(x, y                 , getWidth(), super.getHeight()/4, Color.green, Color.blue);
        m[1] = new Martini(x, y+getHeight()/4   , getWidth(), super.getHeight()/4, Color.orange, deepPink);
        m[2] = new Martini(x, y+2*getHeight()/4, getWidth(), super.getHeight()/4, darkOrchid, seaGreen);
        m[3] = new Martini(x, y+3*getHeight()/4, getWidth(), super.getHeight()/4, Color.cyan, Color.magenta);
    }
    public void setCenter(int x, int y)
    {
        super.setCenter(x, y);
        setPositions();
    }
    public void setTopCenterPoint(int x, int y){
        super.setTopCenterPoint(x, y);
        setPositions();
    }
    public void setPositions(){
        int x = getBoundingRectangle().x;
        int y = getBoundingRectangle().y*2;
        m[0].setTopLeftPoint(x, y);
        m[1].setTopLeftPoint(x , y+getHeight()/4);
        m[2].setTopLeftPoint(x, y+2*getHeight()/4);
        m[3].setTopLeftPoint(x, y+3*getHeight()/4);
    }
    public void paint(Graphics g){
        for (int i=0; i<4; i++) {
            m[i].paint(g);
        }

    }
}