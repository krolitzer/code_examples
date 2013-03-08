import javax.swing.*;
import java.awt.*;

/**
 * @author Chris Costello
 *
 * On my honor, as a University of Colorado at Boulder student,
 * I have neither given nor received unauthorized assistance on this work.
 */
public class Animate extends JApplet implements Runnable// if want a thread
{
    private Martini chip[] = new Martini[24];
    Thread      myThread;       // will hold thread object
    Image       imageBuffer;   // temporary buffer
    Graphics    bufferG;        // graphic area in temp buffer
    int         imageWidth;     // width from HTML
    int         imageHeight;   // height from HTML
    private CheckBoard b;



    public void init()
    {
        imageWidth = this.getSize().width;      // get width
        imageHeight = this.getSize().height;    // get height

        imageBuffer = createImage(imageWidth, imageHeight);
        bufferG = imageBuffer.getGraphics();
        b = new CheckBoard(imageWidth, imageHeight);
        int widthOverEight = imageWidth/8;
        int heightOverEight = imageHeight/8;
        chip[0] = new Martini(widthOverEight, 0, widthOverEight,  heightOverEight, Color.green);
        chip[1] = new Martini(3*widthOverEight, 0, widthOverEight,  heightOverEight, Color.green);
        chip[2] = new Martini(5*widthOverEight, 0, widthOverEight,  heightOverEight, Color.green);
        chip[3] = new Martini(7*widthOverEight, 0, widthOverEight,  heightOverEight, Color.green);
        chip[4] = new Martini(0, heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[5] = new Martini(2*widthOverEight, heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[6] = new Martini(4*widthOverEight, heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[7] = new Martini(6*widthOverEight, heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[8] = new Martini(widthOverEight, 2*heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[9] = new Martini(3*widthOverEight, 2*heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[10] = new Martini(5*widthOverEight, 2*heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[11] = new Martini(7*widthOverEight, 2*heightOverEight, widthOverEight,  heightOverEight, Color.green);
        chip[12] = new Martini(0*widthOverEight, 5*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[13] = new Martini(2*widthOverEight, 5*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[14] = new Martini(4*widthOverEight, 5*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[15] = new Martini(6*widthOverEight, 5*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[16] = new Martini(1*widthOverEight, 6*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[17] = new Martini(3*widthOverEight, 6*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[18] = new Martini(5*widthOverEight, 6*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[19] = new Martini(7*widthOverEight, 6*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[20] = new Martini(0*widthOverEight, 7*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[21] = new Martini(2*widthOverEight, 7*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[22] = new Martini(4*widthOverEight, 7*heightOverEight, widthOverEight, heightOverEight, Color.blue);
        chip[23] = new Martini(6*widthOverEight, 7*heightOverEight, widthOverEight, heightOverEight, Color.blue);



    }
    // Create and start thread

    public void start()
    {
        if (myThread == null)
            {
                myThread = new Thread(this);    // create thread
                myThread.start();               // start it
            }
    }


    // Override update() so it doesn't clear the screen (required for pre-swing)

    public void update(Graphics g)
    {
        paint(g);
    }


    // Take image buffer and show on screen

    public void paint (Graphics g)
    {
        g.drawImage(imageBuffer, 0, 0, null);
    }


    // Here's the work for the Applet

    public void run()
    {

        while(true)
            {
                bufferG.clearRect(0, 0, imageWidth, imageHeight);
                b.paint(bufferG);
                for (int i=0; i<24; i++) {
                    chip[i].paint(bufferG);
                    pause(650);
                    repaint();
                }
                pause(1000);
            }
    }


    // Create image and initialize image buffer


    void pause(int milliseconds)
    {
        try
            {
                Thread.sleep(milliseconds);
            }
        catch (InterruptedException e) {}
    }


    public synchronized void stop()
    {
        myThread = null;        // set to null so start recreates
        notifyAll();
    }


    /* Here's the pre-Swing version

       public void stop()
       {
       if (myThread != null)
       {
       myThread.stop(); // deprecated in pre 1.2
       myThread = null; // set to null so start recreates
       }
       }
    */


    public void destroy()
    {
        bufferG.dispose();      // destroy temp Graphics object
    }

}