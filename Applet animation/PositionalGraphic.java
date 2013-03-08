import java.awt.*;

/**
 * @author Doug Fitzpatrick, Chris Costello, Chris Giersch
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */

public class PositionalGraphic
{
    private Rectangle _boundingRect;
    private Color _c;

    public PositionalGraphic()
    {
        _boundingRect = new Rectangle(80, 80);
        _c = Color.black;

    }
    public PositionalGraphic(int width, int height)
    {
        _boundingRect = new Rectangle(width, height);
        _c = Color.black;
    }
    public PositionalGraphic(int x, int y, int width, int height)
    {
        _boundingRect = new Rectangle(x, y, width, height);
        _c = Color.black;
    }
    public PositionalGraphic(int x, int y, int width, int height, Color c)
    {
        _boundingRect = new Rectangle(x, y, width, height);
        _c = c;
    }

    public Color getCurrentColor()
    {
        return _c;
    }
    public void setColor(Color c)
    {
        _c = c;
    }

    public void setTopLeftX(int x)
    {
        _boundingRect.x = x;
    }
    public int getTopRightX()
    {
        return _boundingRect.x + _boundingRect.width;
    }
    public int getTopLeftX()
    {
        return _boundingRect.x;
    }
    public void setTopLeftY(int y)
    {
        _boundingRect.y = y;
    }
    public int getTopLeftY()
    {
        return _boundingRect.y;
    }
    public void setWidth(int width)
    {
        _boundingRect.width = width;
    }
    public int getWidth()
    {
        return _boundingRect.width;
    }
    public void setHeight(int height)
    {
        _boundingRect.height = height;
    }
    public int getHeight()
    {
        return _boundingRect.height;
    }
    public int getVerticalCenter()
    {
        return _boundingRect.y + _boundingRect.height/2;
    }
    public int getHorizontalCenter()
    {
        return _boundingRect.x + _boundingRect.width/2;
    }
    public Point getCenterPoint()
    {
        return new Point(_boundingRect.height/2, _boundingRect.width/2);
    }
    public Point getTopLeftCorner()
    {
        return new Point(_boundingRect.x, _boundingRect.y);
    }
    public Point getTopRightCorner()
    {
        return new Point((_boundingRect.x+_boundingRect.width), _boundingRect.y);
    }
    public  Point getLowerRightCorner()
    {
        return new Point((_boundingRect.x+_boundingRect.width), (_boundingRect.y+_boundingRect.height));
    }
    public Point getLowerLeftCorner()
    {
        return new Point(_boundingRect.x, (_boundingRect.y+_boundingRect.height));
    }
    public  Point getTopCenterPoint()
    {
        return new Point(_boundingRect.x+(_boundingRect.width/2), _boundingRect.y);
    }
    public  Point getBottomCenterPoint()
    {
        return new Point(_boundingRect.x+(_boundingRect.width/2), _boundingRect.y+_boundingRect.height);
    }
    public  Point getLeftCenterPoint()
    {
        return new Point(_boundingRect.x, _boundingRect.y+(_boundingRect.height/2));
    }
    public Point getRightCenterPoint()
    {
        return new Point(_boundingRect.x+_boundingRect.width, _boundingRect.y+(_boundingRect.height/2));
    }

    public Rectangle getBoundingRectangle()
    {
        return _boundingRect;
    }
    public Point getBowlCenter()
    {
        return new Point ((_boundingRect.x + _boundingRect.width/2), (_boundingRect.y+(2*_boundingRect.height/7)));
    }
    public Point getBaseCenter()
    {
        return new Point((_boundingRect.x+_boundingRect.width/2), (_boundingRect.y+_boundingRect.height - _boundingRect.height/11));
    }
    public Point getBaseLowerLeft()
    {
        return new Point(_boundingRect.x+_boundingRect.width/6, _boundingRect.y+_boundingRect.height);
    }
    public Point getBaseLowerRight()
    {
        return new Point((_boundingRect.x+_boundingRect.width - _boundingRect.width/6), (_boundingRect.y+ _boundingRect.height));
    }

    //The stem's height does not lign up the bowl's and base's center point
    //because it looks more natural slightly overlapping the the two.
    //That's why the numbers and proportions are ugly.
    public int getStemX()
    {
        int width = _boundingRect.width;
        return _boundingRect.x + width/2 - width/24;
    }
    public int getStemY()
    {
        int height = _boundingRect.height;
        return _boundingRect.y + height/7;
    }
    public int getStemWidth()
    {
        return _boundingRect.width/12;
    }
    public int getStemHeight()
    {
        return 13*_boundingRect.height/16;
    }
    public void setTopLeftCorner(int x, int y)
    {
        _boundingRect.x = x;
        _boundingRect.y = y;
    }
    public void setTopRightCorner(int x, int y)
    {
        _boundingRect.x = x - _boundingRect.width;
        _boundingRect.y = y;
    }
    public void setLowerRightCorner(int x, int y)
    {
        _boundingRect.x = x - _boundingRect.width;
        _boundingRect.y = y - _boundingRect.height;
    }
    public void setLowerLeftCorner(int x, int y)
    {
        _boundingRect.x = x;
        _boundingRect.y = y - _boundingRect.height;
    }
    //So you don't lose precicion, choose rectangles with widths and heights of even numbers.
    public void setTopCenterPoint(int x, int y)
    {
        _boundingRect.x = x - _boundingRect.width/2;
        _boundingRect.y = y;
    }
    public void setCenter(int x, int y){
        _boundingRect.x = x - _boundingRect.width/2;
        _boundingRect.y = y - _boundingRect.height/2;
    }
    public void setBottomCenterPoint(int x, int y)
    {
        _boundingRect.x = x - _boundingRect.width/2;
        _boundingRect.y = y - _boundingRect.y;
    }
    public void setLeftCenterPoint(int x, int y)
    {
        _boundingRect.x = x;
        _boundingRect.y = y - _boundingRect.height/2;
    }
    public void setRightCenterPoint(int x, int y)
    {
        _boundingRect.x = x - _boundingRect.width;
        _boundingRect.y = y - _boundingRect.y/2;
    }
    public void reposition(int x, int y, int width, int height){}
    public void paint(Graphics g) {}
}

