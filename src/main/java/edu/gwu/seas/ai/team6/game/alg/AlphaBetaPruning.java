package edu.gwu.seas.ai.team6.game.alg;

import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

public class AlphaBetaPruning {
    private static double MaxDepth;

    /**
     *MiniMax cannot be instantiated.
     */
    private AlphaBetaPruning(){}

    /**
     * Execute the algorithm
     * @param board     the board to play on
     * @param MaxDepth  the maximum depth
     */
    static void run(DefaultBoard board, int MaxDepth){
        if(MaxDepth < 1){
            throw new IllegalArgumentException("MaxDepth must be greater than 0.");
        }

        AlphaBetaPruning.MaxDepth = MaxDepth;
        alphaBeta(board.getTurn(),board,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,0);
        System.out.println("AI plays on (" +board.getLastPiece().getCoordinate().getX()+","+board.getLastPiece().getCoordinate().getY()+")");
    }

    /**
     * minimax algorithm
     * @param player the player that moves
     * @param board     the board to play on
     * @param currentDepth  the current depth
     * @return the score of board
     */
    private static double alphaBeta(Piece.PieceType player, DefaultBoard board, double alpha, double beta, int currentDepth){
        if((currentDepth++ == MaxDepth) || board.isGameOver()){
            return heuristicScore(player, board,currentDepth);
            //return heuristicFun2(player,board);
        }
        if(board.getTurn()==player){
            return getMax(player,board,alpha,beta,currentDepth);
        }else {
            return getMin(player,board,alpha,beta,currentDepth);
        }
    }


    private static double getMax (Piece.PieceType player, DefaultBoard board,double alpha, double beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableCells()) {

            DefaultBoard modifiedBoard = board.copyBoard();
            modifiedBoard.moveAt(theMove, true);

            double score = alphaBeta(player, modifiedBoard,alpha,beta,currentDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = theMove;
            }
            if (alpha >= beta) {
                break;
            }
        }

        if(indexOfBestMove != -1){
            board.moveAt(indexOfBestMove,true);
        }
        return alpha;
    }

    private static double getMin (Piece.PieceType player, DefaultBoard board,double alpha,double beta, int currentDepth) {
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableCells()) {

            DefaultBoard modifiedBoard = board.copyBoard();
            modifiedBoard.moveAt(theMove, false);

            double score = alphaBeta(player, modifiedBoard,alpha,beta, currentDepth);

            if (score < beta) {
                beta = score;
                indexOfBestMove = theMove;
            }
            if (alpha >= beta) {
                break;
            }
        }
        if(indexOfBestMove != -1){
            board.moveAt(indexOfBestMove,false);
        }
        return beta;
    }


    private static double CheckCol(int x,int y, DefaultBoard board) {
        if(board.isGameOver()) {
            if (board.getWinner() == null) {
                return 0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        int i,j;
        i=j=y;
        Piece[][] boardinfo = board.getBoard();
        while(i>-1&&boardinfo[x][i].getType()==board.getLastPiece().getType()){i--;}
        while(j<board.getWidth()&&boardinfo[x][j].getType()==board.getLastPiece().getType()){j++;}
        int continuous = j-i-1;
        while(i>-1&&boardinfo[x][i].getType()==Piece.PieceType.Blank){i--;}
        while(j<board.getWidth()&&boardinfo[x][j].getType()==Piece.PieceType.Blank){j++;}
        int window = j-i-1;
        if(window<board.getGoal()){
            return 0;
        }else if(window==board.getGoal()){
            return Math.pow(4,2*continuous-1);  //return 4^(2*k-1)
        }else{
            return Math.pow(4,2*continuous);
        }
    }

    private static double CheckRow(int x,int y, DefaultBoard board){
        if(board.isGameOver()) {
            if (board.getWinner() == null) {
                return 0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        int i,j;
        i=j=x;
        Piece[][] boardinfo = board.getBoard();
        while(i>-1&&boardinfo[i][y].getType()==board.getLastPiece().getType()){i--;}
        while(j<board.getWidth()&&boardinfo[j][y].getType()==board.getLastPiece().getType()){j++;}
        int continuous = j-i-1;
        while(i>-1&&boardinfo[i][y].getType()==Piece.PieceType.Blank){i--;}
        while(j<board.getWidth()&&boardinfo[j][y].getType()==Piece.PieceType.Blank){j++;}
        int window = j-i-1;
        if(window<board.getGoal()){
            return 0;
        }else if(window==board.getGoal()){
            return Math.pow(4,2*continuous-1);  //return 4^(2*k-1)
        }else{
            return Math.pow(4,2*continuous);
        }
    }

    private static double CheckDiagonalFromTopLeft(int x,int y, DefaultBoard board){
        if(board.isGameOver()) {
            if (board.getWinner() == null) {
                return 0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        Piece[][] boardinfo = board.getBoard();
        while(ix>-1&&iy<board.getWidth()&&boardinfo[ix][iy].getType()==board.getLastPiece().getType()){ix--;iy++;}
        while(jx<board.getWidth()&&jy>-1&&boardinfo[jx][jy].getType()==board.getLastPiece().getType()){jx++;jy--;}
        int continuous = jx-ix-1;
        while(ix>-1&&iy<board.getWidth()&&boardinfo[ix][iy].getType()==Piece.PieceType.Blank){ix--;iy++;}
        while(jx<board.getWidth()&&jy>-1&&boardinfo[jx][jy].getType()==Piece.PieceType.Blank){jx++;jy--;}
        int window = jx-ix-1;
        if(window<board.getGoal()){
            return 0;
        }else if(window==board.getGoal()){
            return Math.pow(4,2*continuous-1);  //return 4^(2*k-1)
        }else{
            return Math.pow(4,2*continuous);
        }
    }

    private static double CheckDiagonalFromTopRight(int x,int y, DefaultBoard board){
        if(board.isGameOver()) {
            if (board.getWinner() == null) {
                return 0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        Piece[][] boardinfo = board.getBoard();
        while(ix>-1&&iy>-1&&boardinfo[ix][iy].getType()==board.getLastPiece().getType()){ix--;iy--;}
        while(jx<board.getWidth()&&jy<board.getWidth()&&boardinfo[jx][jy].getType()==board.getLastPiece().getType()){jx++;jy++;}
        int continuous = jx-ix-1;
        while(ix>-1&&iy>-1&&boardinfo[ix][iy].getType()==Piece.PieceType.Blank){ix--;iy--;}
        while(jx<board.getWidth()&&jy<board.getWidth()&&boardinfo[jx][jy].getType()==Piece.PieceType.Blank){jx++;jy++;}
        int window = jx-ix-1;
        if(window<board.getGoal()){
            return 0;
        }else if(window==board.getGoal()){
            return Math.pow(4,2*continuous-1);  //return 4^(2*k-1)
        }else{
            return Math.pow(4,2*continuous);
        }
    }

    private static double heuristicScore(Piece.PieceType player, DefaultBoard board,int currentDepth){
        if(player == Piece.PieceType.Blank){
            throw new IllegalArgumentException("player must be X or O");
        }
        Piece current = board.getLastPiece();
        int x = current.getCoordinate().getX();
        int y = current.getCoordinate().getY();
        double score = CheckCol(x,y,board)+CheckRow(x,y,board)+CheckDiagonalFromTopLeft(x,y,board)+CheckDiagonalFromTopRight(x,y,board);
        if(current.getType()==board.getOurtype()){
            return score - currentDepth;
        }else{return currentDepth - score;}
    }

    private static double heuristicFun2(Piece.PieceType player, DefaultBoard board){
        if (player ==  Piece.PieceType.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Piece.PieceType opponent = (player == Piece.PieceType.X) ? Piece.PieceType.O : Piece.PieceType.X;
        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }
}
