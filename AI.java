/* AI.java */

package machine;

import player.*;

/**
 *	AI is a public class that contains methods that will perform 
 *  game-tree search and implement the mini-max algorithm to search for all
 *  possible moves and evaluate them. The search method will then return the 
 *  highest rated Move.
 */

public class AI{

   /**
	* smartMove() considers all possible legal moves and scores them. Once all
	* possibleMoves have been exhausted, it returns the highest scored move.
	* @param bd is the Board we want to look at.
	* @param myColor is the color of the player calling this method.
	* @param oppColor is the opponent's color.
	* @param searchDepth is the amount of "levels" we wish to look.
	* @return the Move with the highest score.
	**/
	public static Move smartMove(Board bd, int myColor, int oppColor, int searchDepth){
	    int maxScore = -1000;
	    int currScore;
	    Move maxMove = null;
	    Move currMove = null;
	    MoveIterator it = new MoveIterator(bd, myColor);

	    while((currMove = it.getNext()) != null){
	      currScore = scoreMove(bd, currMove, myColor, oppColor, 1, searchDepth, maxScore);
	      if (currScore >= maxScore){
	        maxScore = currScore;
	        maxMove = currMove;
	      }
    	}
    	
    	bd.addChip(maxMove, myColor);     
    	return maxMove;
  	}

   /**
	* scoreMove() determines what action to take when considering a possibleMove.
	* If we have already reached our max depth, we will begin to evaluate, 
	* otherwise we will continue to recurse using tryAll.
	* @param bd is the Board we wish to look at.
	* @param m is the Move we wish to score.
	* @param myColor is the color of the player calling this method.
	* @param oppColor is the opponent's color.
	* @param currDepth is the current search depth that the method was called on.
	* @param maxDepth is the maximum search depth we will search.
	* @param cutoff is the pruning value to stop at.
	* @return the int value associated with the score of the Move.
	**/
	private static int scoreMove(Board bd, Move m, int myColor, int oppColor, int currDepth, int maxDepth, int cutoff){
		int retVal;

	    if (currDepth >= maxDepth) {
	      	if(currDepth % 2 == 0){ //this means we are at a depth where we must eval for an opponent Move
	      		retVal = evaluate(bd, m, oppColor);   // eval is higher if pos is good for this color
	      		bd.undoMove(m, oppColor);
	      	}
	      	else{
	      		retVal = evaluate(bd, m, myColor);
	      		bd.undoMove(m, myColor);
	      	}
	      	
	      	return retVal;
	    }

	    if (bd.hasNetwork(myColor)  &&  !bd.hasNetwork(oppColor)) {
	      bd.undoMove(m, myColor);
	      return 1000;
	    }

	    retVal = -tryAll(bd, m, myColor, oppColor, currDepth, maxDepth, cutoff);
	    bd.undoMove(m, myColor);
	    return retVal;
  	}

   /**
    * tryAll() is somewhat of a wrapper-method that is used to continually recurse
    * until we have reache our maxDepth. It acts much like smartMove in the way it
    * loops through all possibleMoves.
    * @param bd is the Board we wish to look at.
	* @param m is the Move we wish to score.
	* @param myColor is the color of the player calling this method.
	* @param oppColor is the opponent's color.
	* @param currDepth is the current search depth that the method was called on.
	* @param maxDepth is the maximum search depth we will search.
	* @param cutoff is the pruning value to stop at.
	* @return the int value associated with the score of the Move.
    **/
	private static int tryAll(Board bd, Move m, int myColor, int oppColor, int currDepth, int maxDepth, int cutoff){
	    int currVal, maxVal = -1000;
	    MoveIterator it2 = new MoveIterator(bd, oppColor);
	    Move currMove;

	    while((currMove = it2.getNext()) != null) {
	      currVal = scoreMove(bd, currMove, myColor, oppColor, currDepth+1, maxDepth, maxVal);
	      if (currVal > maxVal) {
			maxVal = currVal;
			if (maxVal >= -cutoff)
		  		break;
	      	}
	    }
	    return maxVal;
  	}

	/**
	* evaluate() takes in a possible move to a board and returns an integer
	* based on how good the move is. A higher number corresponds to a better move
	* and vice versa.
	* @param bd the current state of the board
	* @param mv a possible move that will be scored
	* @param color the color of the team machinePlayer is on.
	* @return an integer between -1000 and 1000.
	*/
	public static int evaluate(Board bd, Move mv, int color){
		int score = 0;
		Board board = bd.copyBoard();		
		Move move = mv;	
		int pieces = bd.getPieces(color);
		Board oldBoard = bd;
		int enemyColor = getEnemyColor(color);
		int prevConnections = oldBoard.howManyConnections(color);
		int preMoveEnemyConnections = oldBoard.howManyConnections(enemyColor);

		board.addChip(mv,color);

		// If proposed move gives us a network...without giving the enemy a network.					
		if(board.hasNetwork(color) && !board.hasNetwork(enemyColor)){//and enemy does NOT have network.
			score = 1000;
			return score;
		}
		// So we don't crowd the goals.
		if(board.startGoalCount(color) > 2)
			score -= 550;
		
		// So we don't crowd the goals
		if(board.endGoalCount(color) > 2)
			score -= 300;

		// To spread out the chips a little more
		if(board.getSurroundingEmpties(mv.x1, mv.y1) > 0)
			score += board.getSurroundingEmpties(mv.x1, mv.y1) * 3;
		
		// If enemy has a network
		if(board.hasNetwork(enemyColor))
			score = -1000;
		

		// Take the center in the beginning.
		if (pieces <3){ 
			if(mv.x1 > 2 && mv.x1 < 5 && mv.y1 > 2 && mv.y1 < 5)
				score += 250;
		}

		//If we are into the game and still don't have a chip in the endgoal. 
		if(pieces >= 4 && oldBoard.endGoalEmpty(color))
		{				
			if(!board.endGoalEmpty(color) || !board.startGoalEmpty(color)) 
				score += 325;						
		}


		//Difference in our connections
		int diffConnections = board.howManyConnections(color) - prevConnections;
		if (diffConnections < 0)
			score -= diffConnections*115;
		if (diffConnections > 0)
			score += diffConnections*115; //creating more connections is good. 
		if (diffConnections == 0)
			score -= 50;
		
		// Difference in enemy connections. 
		int diffEnemyConnections = board.howManyConnections(enemyColor) - preMoveEnemyConnections;
		if (diffEnemyConnections < 0)
			score += diffEnemyConnections*75;
		if (diffEnemyConnections > 0 )
			score -= diffEnemyConnections*75;
		if (diffEnemyConnections == 0)
			score += 50;

		if(clusterRatio(oldBoard,color) >= 0.5 && mv.x1 < 4){
			score += 500;
		}

		if(clusterRatio(oldBoard,color) <= 0.5 && mv.x1 > 4){
			score += 100;
		}

		return score; 		
	}

	/**
	* getEnemyColor() is a helper method that takes in machinePlayer's
	* and returns the enemy's color.
	* @param myColor is machinePlayer's color
	* @return the int representing the enemyColor.
	*/ 
	private static int getEnemyColor(int myColor){
		int enemyColor; 

		if(myColor==Chip.BLACK)			
			enemyColor = Chip.WHITE;
		else
			enemyColor=Chip.BLACK;

		return  enemyColor;
	}

	/**
	* clusterRatio() is a method that determines the ratio of friendly chips on the right side
	* of the board and returns a float which evaluate uses to utilize more open board space.
	* @param bd the current board.
	* @param myColor is machinePlayer's color
	* @return the ratio of friendly chips on one side.
	*/
	private static float clusterRatio(Board bd, int myColor){
		int colorCounter = 0;
		int totalColor = 0;
		float ratio = 0;
		Chip temp;
		total: for(int i = 0; i < 8; i++)
		{
			
			for(int j = 0; j < 8; j++)
			{
				if(i == 0 && j == 0)
					continue total;
				if(i == 0 && j == 7)
					continue total;
				if(i == 7 && j == 0)
					continue total;
				if(i == 7 && j == 7)
					continue total;
					
				temp = bd.returnChip(i,j);
				if(temp.returnColor() == myColor)
					totalColor ++;	
			}
	
		
		}
		counter: for(int i = 4; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(i == 0 && j == 0)
					continue counter;
				if(i == 0 && j == 7)
					continue counter;
				if(i == 7 && j == 0)
					continue counter;
				if(i == 7 && j == 7)
					continue counter;
				temp = bd.returnChip(i,j);
				if(temp.returnColor() == myColor)
					colorCounter ++;	
			}
		}
	
		ratio = (float)colorCounter/(float)totalColor;
		return ratio;
	}		
}
