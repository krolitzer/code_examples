//shuttle3d.cxx
//Written by Christopher Costello 4/22/12
//The purpose of this program is to draw the wire frame of
//a space shuttle and rotate the shuttle based on user input.
#include <cassert>
#include <fstream>
#include <graphics.h>
#include <iostream>
#include <cstdlib>
#include <cmath>
using namespace std;

//Constants
const int S = 500;
const size_t M = 522;
const size_t N = 891;
const double WMAX = 14000.0;
// Constants used in the transformation matrices:
const double ANGLE = M_PI/100;
const double SIN = sin(ANGLE);
const double COS = cos(ANGLE);
const double ZOOM = 1.1;
// The transformation matrices:
const double ZOOMIN[3][3]=           {{ZOOM, 0, 0}, {0, ZOOM, 0}, {0, 0, ZOOM}};
const double ZOOMOUT[3][3]=    {{1/ZOOM, 0, 0}, {0, 1/ZOOM, 0}, {0, 0, 1/ZOOM}};
const double CLOCKWISE[3][3]=        {{COS, -SIN, 0}, {SIN, COS, 0}, {0, 0, 1}};
const double COUNTERCLOCKWISE[3][3]= {{COS, SIN, 0}, {-SIN, COS, 0}, {0, 0, 1}};
const double DOWN[3][3]=             {{1, 0, 0}, {0, COS, -SIN}, {0, SIN, COS}};
const double UP[3][3]=               {{1, 0, 0}, {0, COS, SIN}, {0, -SIN, COS}};
const double RIGHT[3][3]=            {{COS, 0, SIN}, {0, 1, 0}, {-SIN, 0, COS}};
const double LEFT[3][3]=             {{COS, 0, -SIN}, {0, 1, 0}, {SIN, 0, COS}};

//Prototypes

//pixel converts a point wx from a range of wmin to wmax to
//pixel coordinates with a range from pmin to pmax.
int pixel(double wx, double wmin, double wmax, int pmin, int pmax);

//store_verticies reads all the necessary data from the shuttle.dat file.
//data reads the coordinates for the verticies, vertex takes in the lineto
//movements and color sets the line color and the moveto movements. 
void store_vertices(double data[M][3], unsigned int vertex[N], int color[N]);

//draw_ship uses the data from shuttle.dat and uses it to draw the wire frame
//in a graphics window. Data, vertex, and color are information for the ship.
//max and min are the maximum and minimum values in the array and are
//used in the pixel function to scale the ship to size.
void draw_ship(double data[M][3], unsigned int vertex[N], int color[N],
	       double max, double min );

//find_max finds the maximum and minimum values in the array which are
//used in the pixel function.
void find_max(double data[M][3], double& max, double& min);

//this function provides a transformation matrix based on which key is hit.
void interact(double data[M][3]);

//transform takes in the info for the ship coordinates and multiplies
//it by a matrix that adjusts the coordinates to simulate the movement.
//data is the ship coordinates and move is the matrix taken from interact.
void transform(double data[M][3], const double move[3][3]);


int main()

{
    
    double data[M][3];//The array for the verticies
    unsigned int vertex[N];//provides info for lineto
    int color[N];//provides info for moveto and color
    double min, max;//the minimum and maximum values to scale the ship in pixel
    
    store_vertices(data, vertex, color);
    find_max(data, max, min);
    draw_ship(data, vertex, color, max, min);

    return EXIT_SUCCESS;
}
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
void store_vertices(double data[M][3], unsigned int vertex[N], int color[N])
{

    char in_file[40] = "shuttle.dat";//the file with the information
    unsigned int i, j, value1, value2;//i and j are used in the for loop.
    //value1 and value2 are used to check if the constants are correct
    ifstream ins;//reads and stores data

    ins.open(in_file);

    if (ins.fail())
    {
	cerr << "***ERROR: Can't open " << in_file << ". Sorry :( " <<endl;
	exit(1);
    }

    ins >> value1;
    assert(value1 == M);
    
    for(i=0; i<M; i++)
    {
	for(j=0; j<3; j++)
	{
	    ins >> data[i][j]; 
	}
    }
    
    ins >> value2;
    assert(value2 == N);

    for(i=0; i<N; i++)
    {
	ins >> vertex[i];
	ins >> color[i];
    }
    ins.close();
}
//------------------------------------------------------------------------------

//------------------------------------------------------------------------------
int pixel(double wx, double wmin, double wmax,int pmin, int pmax)
{
    /*ratio is variable that take world coordinates
      and creates a value proportional in */
    double ratio;
    double result;
    ratio = (pmax - pmin)/(wmax - wmin);
    result = (wx - wmin)* ratio+pmin;
    return int(result);//the int gets rid of the warning. 
}
//------------------------------------------------------------------------------	    

//------------------------------------------------------------------------------	    
void draw_ship(double data[M][3], unsigned int vertex[N], int color[N],
	       double max, double min)
{
    unsigned int i;//i is used in the for loop
    int value, row;//value is set equal to color[i] to check for moveto
    //and  row is set equal to vertex[i] and it goes to a particular
    //row of data in shuttle.dat 
    

    initwindow(S, S, "shuttle.cxx", 0, 0, true);
    while(true)
    {
	clearviewport();
	
	moveto(pixel(data[vertex[0]][0], -WMAX, WMAX, 0, S),
	  pixel(data[vertex[0]][1], -WMAX, WMAX, S, 0));

	for(i=1; i<N; i++)
	{
	    value = color[i];
	    row = vertex[i];

	    if(value == 0)
		moveto(pixel(data[row-1][0], -WMAX, WMAX, 0, S),
		       pixel(data[row-1][1], -WMAX, WMAX, S, 0));
	
	    else
	    {
		setcolor(color[i]);
		lineto(pixel(data[row-1][0], -WMAX, WMAX, 0, S),
		       pixel(data[row-1][1], -WMAX, WMAX, S, 0));
	    }
	}
	
	swapbuffers();
	interact(data);
	delay(30);
    }

}
//------------------------------------------------------------------------------	    

//------------------------------------------------------------------------------	    
void find_max(double data[M][3], double& max, double& min)
{
    unsigned int i, j; //Used in for loop
    max = data[0][0];
    min = data[0][0];

    for(i=0; i<M; i++)
    {
	for(j=0; j<2; j++)
	{
	    if(data[i][j] > max)
		max = data[i][j];
	}
    }
    for(i=0; i<M; i++)
    {
	for(j=0; j<2; j++)
	{
	    if(data[i][j] < min)
		min = data[i][j];
	}
    }
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void interact(double data[M][3])
//Obtained from the class website.
{
    while (!kbhit( ))
    {
	delay(10);
    }
    switch (getch( ))
    {
    case '/':       transform(data, CLOCKWISE);        break;
    case '\\':      transform(data, COUNTERCLOCKWISE); break;
    case KEY_UP:    transform(data, UP);               break;
    case KEY_DOWN:  transform(data, DOWN);             break;
    case KEY_LEFT:  transform(data, LEFT);             break;
    case KEY_RIGHT: transform(data, RIGHT);            break;
    case '+':       transform(data, ZOOMIN);           break;
    case '-':       transform(data, ZOOMOUT);          break;
    }
}
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
void transform(double data[M][3], const double move[3][3])
{
    size_t i, j;//Used in for loop.

    for(i=0; i<M; i++)
    {
	for(j=0; j<3; j++)
	{
	    data[i][j] =
	    data[i][0] * move[0][j]+
	    data[i][1] * move[1][j]+
	    data[i][2] * move[2][j];
	}
    }
}
