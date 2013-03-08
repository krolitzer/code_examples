import java.awt.*;

/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class CheckBoard extends PositionalGraphic {
    private Rect[] tile = new Rect[64];
    private Color c1, c2;

    public CheckBoard(int width, int height){
        super(0, 0, width, height);

        int x, y;   // Top-left corner of square
        Color tempColor;
        int count = 0;
        for (int row = 0;  row < 8;  row++ ) {
            for (int col = 0;  col < 8;  col++) {
                x = col * width/8;
                y = row * height/8;
                if ( (row % 2) == (col % 2) )
                    tempColor = Color.red;
                else
                    tempColor = Color.black;
                tile[count] = new Rect(x, y, width/8 , height/8, tempColor);
                count++;
            }

        } // end for row
    }
    public void paint(Graphics g) {
        for (int i=0; i<64; i++) {
            tile[i].paint(g);
        }

    }  // end paint()
}