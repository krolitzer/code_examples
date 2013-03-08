/* Board.java */

package machine;

import player.*;

public class Board{

  private Chip[][] board = new Chip[8][8];  
  private Chip neighbor;
  private int whitePieces;
  private int blackPieces;
  private int connections = 0;

  /**
  * Board() constructs a 2D array of Chip objects
  * All corners are grey chips and the rest are empty.
  */
  public Board(){
    
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        board[i][j] = new Chip(i, j, Chip.EMPTY);
      }
    }

    board[0][0] = new Chip(0, 0, Chip.GREY);
    board[0][7] = new Chip(0, 7, Chip.GREY);
    board[7][0] = new Chip(7 ,0, Chip.GREY);
    board[7][7] = new Chip(7, 7, Chip.GREY);
    neighbor = board[0][0];//arbitrary to satisfy compiler.
  }
  
  /**
  * copyBoard() creates a duplicate Board object reflecting the current state of the game.
  * Useful to store the state of the Board before changes.
  * @return a duplicate Board object.
  **/
  public Board copyBoard(){
    Chip tempChip;
    Board newBoard = new Board();
      
    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){              
        tempChip = returnChip(i,j);
        newBoard.insertChip(i,j,tempChip);      
      }            
    }

    newBoard.whitePieces = whitePieces;
    newBoard.blackPieces = blackPieces;
    newBoard.connections = connections;
      
    return newBoard;         	  
  }

  /**
  * insertChip() puts a chip object in the desired location of the board.
  * @param x the x location
  * @param y the y location
  * @param chip a chip object.
  **/
  private void insertChip(int x, int y, Chip chip){
     board[x][y] = chip;  
  }

  /**
  * returnChip() returns a chip object from the desired location of the board.
  * @param x the x location
  * @param y the y location
  * @return a chip object.
  **/
  Chip returnChip(int x, int y){
    return board[x][y];
  }

 /**
  * chipColor() returns the color of the chip from the desired location.
  * @param x the x location
  * @param y the y location
  * @return int representing color.
  **/
  int chipColor(int x, int y){
    Chip chip = returnChip(x,y);
    int color =  chip.returnColor();
    return color;
  }
  
  /**
  * addChip() takes a move and a color and reflects that move on 'this' board, 
  * and also increments the piece count. STEP moves have their original 
  * locations removed.
  * @param m the desired move.
  * @param color the color of the team who made the move.
  **/
  public void addChip(Move m, int color){
    if(m.moveKind == m.ADD){
      board[m.x1][m.y1].color = color; 
      this.setPieces(color);     
    }
    else if(m.moveKind == m.STEP){
      removeChip(m.x2, m.y2);
      board[m.x1][m.y1].color = color; 
    }  
    else
      return;  
  }

  /**
  * removeChip() takes an x and y coord on 'this' board and sets the Chip's 
  * color at that location equal to EMPTY.
  * @param x the x coord.
  * @param y the y coord.
  **/
  void removeChip(int x, int y){ 
      board[x][y].color = Chip.EMPTY;
  }

  /** 
   * undoMove() sets the slot from move m to EMPTY and decreases the piece count 
   * for ADD moves. Reverts a STEP move.
   **/
  void undoMove(Move m, int color){
  	  if(m.moveKind == m.ADD){
  	  	  board[m.x1][m.y1] = new Chip(m.x1, m.y1, Chip.EMPTY);    	  
          //this.decPieces(color);  	 
  	  }
  	  else if(m.moveKind == m.STEP){
  	  	  board[m.x1][m.y1] = new Chip(m.x1, m.y1, Chip.EMPTY); 
  	  	  board[m.x2][m.y2] = new Chip(m.x2, m.y2, color);   
  	  }  
      else
        return;   
  }
  
  /**
   * setPieces() increments the count of white and black pieces every time addChip 
   * is called.
   * @param col the color of the team.
   **/
  void setPieces(int col){
    if(col == Chip.BLACK)
      ++blackPieces;
    if(col == Chip.WHITE)
      ++whitePieces; 
  }

  /**
   * decPieces() decrements the count of white or black pieces every time undoMove
   * is called.
   * @param col the color of the team.
   **/
  void decPieces(int col){
    if(col == Chip.BLACK)
      --blackPieces;
    if(col == Chip.WHITE)
      --whitePieces; 
  }

  /**
   * getPieces() returns the count of white or black pieces. This method is called
   * when we want to determine if we should make an ADD or STEP move.
   * @param col the color of the team.
   **/
  int getPieces(int col){
    if(col == Chip.BLACK)
      return blackPieces;
    else if(col == Chip.WHITE)
      return whitePieces;
    else
      return 0;
  }

  /**
   * getCurrChips() finds all of the current chips that have been
   * placed on the board by the given color, col. Once found, they are stored in
   * the 1D array currChips and then returned. Implemented so that we may make 
   * smart STEP moves by analyzing all legal STEP moves for all pieces in play.
   * @param col the color of the team.   
   **/
  Chip[] getCurrChips(int col){
    Chip[] currChips = new Chip[500];
    int counter = 0;

    for(int i = 0; i < 8; i++){
      for(int j = 0; j < 8; j++){
        if(board[i][j].returnColor() == col){
          currChips[counter] = board[i][j];
          ++counter;
        }
      }
    }

    return currChips;
  }

 /**
  * unflagAllChipsOfColor() is a helper method that changes the boolean, 'flag', of
  * all the chips of color col to false. Used in explore().
  * @param col the color of the chip.
  **/  
  private void unflagAllChipsOfColor(int col){
    for (int i=0; i<8; i++) {
      for (int j=0; j<8; j++) {
        if(board[i][j].returnColor() == col)
          board[i][j].unflag();
      }
    }
  }
  
  /**
   * hasNetwork() is a package protected method that takes a color and
   * returns true if that color has a network on the board, or false is not.
   * @param col is the color of the player we wish to consider
   * @return true to indicate a network was found, or false if not
   **/
  boolean hasNetwork(int col){
  
    boolean exp = false;

    if(col == Chip.WHITE){
      for (int i=1; i<7 && !exp; i++) {//Check the first goal on the left.
        if(board[0][i].returnColor() == col && !endGoalEmpty(col)){
          unflagAllChipsOfColor(col);
          exp = explore(col, board[0][i].getX(), board[0][i].getY(), 1, Direction.X);//Direction.x is to avoid direction bugz
        }
      }
    }

    if(col == Chip.BLACK){//Check the goal on the top.
      for (int i=1; i<7 && !exp; i++) {
        if(board[i][0].returnColor() == col && !endGoalEmpty(col)){       
          unflagAllChipsOfColor(col);
          exp = explore(col, board[i][0].getX(), board[i][0].getY(), 1, Direction.X);//Direction.x is to avoid direction bugz
        }
      }
    }

    return exp;
  }
  /**    
   * explore() is a private method that searches the board for potential moves to hop to.
   * @param color is the current chip color that might have a network.
   * @param c is the current chip I'm exploring from
   * @param length is the amount of hops taken.
   * @param direction is the direction I came from.
   * @return true to indicate a network was found, or false if not
   **/
  private boolean explore(int col, int x, int y, int len, Direction dir){

    board[x][y].flag();     

    Direction curr_dir = Direction.N; // N is arbitrary. There is not current direction yet.
      
    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
       
        // Current Point
        if( (i == 0) && (j==0)) 
          continue;

        //Running off board.
        if( ((x + i) < 0)  ||  ((y + j) < 0) || ((x + i) > 7) || ((y + j) > 7) )
          continue;

        //Eliminating Corners.
        if( (x + i == 0 && y + j == 0)  ||  (x + i == 7 && y + j == 0)   ||
            (x + i == 7 && y + j == 7)  ||  (x + i == 0 && y + j == 7) )
          continue;

        //Loop to explore board
        for(int k=1; k<8; k++){

            //Ran off the board
            if(((x + i*k) < 0)  ||  ((y + j*k) < 0) || ((x + i*k) > 7) || ((y + j*k) > 7))
              break;
          
            neighbor = board[x + i*k][y + j*k];
            curr_dir = Direction.getCurrDir(i, j);

             if(neighbor.returnColor() == Chip.EMPTY )
              continue;
            else
              break;

        }//end third for
        
        // wrong color
        if( neighbor.returnColor() != col )
          continue;

        // same direction
        if(curr_dir == dir)
          continue;

        // already visited
        if(board[neighbor.getX()][neighbor.getY()].isFlagged())
          continue;
        
        // If the neighbor is in the start goal.
        if( (col == Chip.WHITE && neighbor.getX() == 0) || 
            (col == Chip.BLACK && neighbor.getY() == 0))
          continue; 

        // neighbor is in the end-goal.
        if( (col == Chip.BLACK && neighbor.getY() == 7) || 
            (col == Chip.WHITE && neighbor.getX() == 7)){
            if (len >= 5) return true;
        
        }
        else{
            if(explore(col, neighbor.getX(), neighbor.getY(), len+1, curr_dir))
                return true;
        }
        }//end second for
      }//end first for
    board[x][y].unflag();//chip has no neighbors...how sad.
    return false;
  }

 /**
  * endGoalEmpty() is a package protected method that checks if the
  * 8th column (if white) or 8th row (if black) is empty.
  * @param col is the color of the team to consider.
  * @return true if empty false if not empty.
  **/
  boolean endGoalEmpty(int col){
    if (col == Chip.WHITE) {
      for (int i = 1; i<7; i++) {
        if(board[7][i].returnColor() == Chip.WHITE)
          return false;
      }
    }
    if (col == Chip.BLACK) {
      for (int i = 1; i<7; i++) {
        if(board[i][7].returnColor() == Chip.BLACK)
          return false;
      }
    }
    return true;
  }

 /**
  * startGoalEmpty() is a package protected method that checks if the 
  * 1st column (if white) or the 1st row (if black) is empty.
  * @param col is the color of the team to consider.
  * @return true if empty false if not empty.  
  **/  
  boolean startGoalEmpty(int col){
    if (col == Chip.WHITE) {
      for (int i = 1; i<7; i++) {
        if(board[0][i].returnColor() == Chip.WHITE)
          return false;
      }
    }
    if (col == Chip.BLACK) {
      for (int i = 1; i<7; i++) {
        if(board[i][0].returnColor() == Chip.BLACK)
          return false;
      }
    }
    return true;
  }

 /**
  * endGoalCount() counts how many chips of color col are in the
  * 8th column (if white) or 8th row (if black).
  * @param col is the color of the team to consider.
  * @return the number of chips. 
  **/
  int endGoalCount(int col){
    int count = 0;
    if (col == Chip.WHITE) {
      for (int i = 1; i<7; i++) {
        if(board[7][i].returnColor() == Chip.WHITE)
          count++;
      }
    }
    if (col == Chip.BLACK) {
      for (int i = 1; i<7; i++) {
        if(board[i][7].returnColor() == Chip.BLACK)
          count++;
      }
    }
    return count;
  }

 /**
  * startGoalCount() counts how many chips of color col are in the
  * 1st column (if white) or 1st row (if black).
  * @param col is the color of the team to consider.
  * @return the number of chips. 
  **/  
  int startGoalCount(int col){
    int count = 0;
    if (col == Chip.WHITE) {
      for (int i = 1; i<7; i++) {
        if(board[0][i].returnColor() == Chip.WHITE)
          count++;;
      }
    }
    if (col == Chip.BLACK) {
      for (int i = 1; i<7; i++) {
        if(board[i][0].returnColor() == Chip.BLACK)
          count++;
      }
    }
    return count;
  }

 /**
  * dumpBoard() prints out the current state of the board.
  *
  **/
  public void dumpBoard(){
    for( int j = 0; j < 8; j++){                                
      for(int i = 0; i < 8; i++){
        System.out.print("| " + board[i][j].toString() + " | ");
      }
      System.out.println();
    }                                                                     
  }

 /**
  * unTouchAllChipsOfColor() is a helper method that changes the boolean, 'touched', of
  * all chips of color col to false. Used in connectionExplore().
  * @param col the color of the chip to untouch.
  **/  
  private void unTouchAllChipsOfColor(int col){
    for (int i=0; i<8; i++) {
        for (int j=0; j<8; j++) {
          if(board[i][j].returnColor() == col)
            board[i][j].untouch();
        }
      }
  }

 /**
  * howManyConnections() counts how many unique connctions
  * exist between all the chips on the board. A unique connection is one chip that can
  * see another chip of the same color excluding those in the start goal. This method 
  * avoids counting the same connection twice, hence, counts unique conncections.
  * @param col the color of chip to consider.
  * @return the number of connections on the board.
  **/  
  int howManyConnections(int col){
    connections = 0;

    if(col == Chip.WHITE){
      unTouchAllChipsOfColor(col);
      for (int i=0; i<8; i++) {//Check the first goal on the left.
        for(int j=1; j<7; j++){
        if(board[i][j].returnColor() == col){
          connectionExplore(col, board[i][j].getX(), board[i][j].getY());
          }
        }
      }
    }
    if(col == Chip.BLACK){
      unTouchAllChipsOfColor(col);
      for (int j=0; j<8; j++) {//Check the goal on the top.
        for(int i=1; i<7; i++){
        if(board[i][j].returnColor() == col){
          connectionExplore(col, board[i][j].getX(), board[i][j].getY());
          }
        }
      }
    }
    return connections;
  }
    

  private void connectionExplore(int col, int x, int y){
    board[x][y].touch();
      
    for (int i=-1; i<=1; i++) {
      for (int j=-1; j<=1; j++) {
       
        // Current Point
        if( (i == 0) && (j==0))
          continue;

        //Running off board.
        if( ((x + i) < 0) || ((y + j) < 0) || ((x + i) > 7) || ((y + j) > 7) )
          continue;

        //Eliminating Corners.
        if( (x + i == 0 && y + j == 0) || (x + i == 7 && y + j == 0) ||
            (x + i == 7 && y + j == 7) || (x + i == 0 && y + j == 7) )
          continue;

        //Loop to explore board
        for(int k=1; k<8; k++){

          //Ran off the board
          if(((x + i*k) < 0) || ((y + j*k) < 0) || ((x + i*k) > 7) || ((y + j*k) > 7))
            break;
        
          neighbor = board[x + i*k][y + j*k];
          

           if(neighbor.returnColor() == Chip.EMPTY )
            continue;
          else
            break;
          }//end third for
          
        //wrong color
        if( neighbor.returnColor() != col )
            continue;

         //already visited
        if(board[neighbor.getX()][neighbor.getY()].isTouched())
          continue;

          // Neighbor is in the start goal
        if( (col == Chip.WHITE && neighbor.getX() == 0 ) || //NOTE: I don't consider a neighbor as a
            (col == Chip.BLACK && neighbor.getY() == 0)) //NOTE: I'm assuming black start_goal is top row
            continue;
          
          // Ignoring end goal neighbors from end goal chips
        if ((col == Chip.WHITE && x == 7 && neighbor.getX() == 7 ) || 
            (col == Chip.BLACK && y == 7 && neighbor.getY() == 7)) 
          continue;
        
        else
          connections++;
      
      }//end second for
    }//end first for  
  }

 /**
  * getSurroundingEmpties() is a package protected method that counts
  * the amount of empty cells (Chip with Empty color) surrounding a particular chip.
  * @param x is the x coordinate to look from.
  * @param y is the y coordinate to look from.
  * @return the number of empty cells around the chip.
  **/
  int getSurroundingEmpties(int x, int y){
    int e = 0;
    for(int i = -1; i <= 1; i++){
      for(int j = -1; j <= 1; j++){
        
        if( (i == 0) && (j==0))
          continue;

          //Running off board.
          if( ((x + i) < 0) || ((y + j) < 0) || ((x + i) > 7) || ((y + j) > 7) )
            continue;

          //Eliminating Corners.
          if( (x + i == 0 && y + j == 0) || (x + i == 7 && y + j == 0) ||
              (x + i == 7 && y + j == 7) || (x + i == 0 && y + j == 7) )
            continue;

          // Count empties
          if(board[i+x][j+y].returnColor() == Chip.EMPTY)
            e++;
      }
    }
    return e;
  }
}
