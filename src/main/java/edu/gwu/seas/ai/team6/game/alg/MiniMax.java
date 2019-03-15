package edu.gwu.seas.ai.team6.game.alg;

import edu.gwu.seas.ai.team6.game.board.DefaultBoard;
import edu.gwu.seas.ai.team6.game.board.DefaultPiece;
import edu.gwu.seas.ai.team6.game.board.interfaces.Board;
import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

public class MiniMax implements Algorithm {
    private static double MaxDepth;

    /**
     *MiniMax cannot be instantiated.
     */
    private MiniMax(){}

    /**
     * Execute the algorithm
     * @param board     the board to play on
     * @param MaxDepth  the maximum depth
     */
    static void run(DefaultBoard board,Double MaxDepth){
        if(MaxDepth < 1){
            throw new IllegalArgumentException("MaxDepth must be greater than 0.");
        }

        MiniMax.MaxDepth = MaxDepth;
        miniMax(board.getTurn(),board,0);
    }

    /**
     * minimax algorithm
     * @param player the player that moves
     * @param board     the board to play on
     * @param currentDepth  the current depth
     * @return the score of board
     */
    private static double miniMax(Piece.PieceType player,DefaultBoard board,int currentDepth){
        if((currentDepth++ == MaxDepth) || board.isGameOver()){
            return heuristicScore(player, board);
        }
        if(board.getTurn()==player){
            return getMax(player,board,currentDepth);
        }else {
            return getMin(player,board,currentDepth);
        }
    }


    private static double getMax (Piece.PieceType player, DefaultBoard board, int currentDepth) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableCells()) {

            DefaultBoard modifiedBoard = board.copyBoard();
            modifiedBoard.moveAt(theMove, true);

            double score = miniMax(player, modifiedBoard, currentDepth);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.moveAt(indexOfBestMove,true);
        return bestScore;
    }

    private static double getMin (Piece.PieceType player, DefaultBoard board, int currentDepth) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableCells()) {

            DefaultBoard modifiedBoard = board.copyBoard();
            modifiedBoard.moveAt(theMove, false);

            double score = miniMax(player, modifiedBoard, currentDepth);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.moveAt(indexOfBestMove,false);
        return bestScore;
    }


    private static double CheckCol(int x,int y, DefaultBoard board) {
        int i,j;
        i=j=y;
        Piece[][] boardinfo = board.getBoard();
        while(boardinfo[x][--i].getType()==board.getLastPiece().getType()||boardinfo[x][--i].getType()==null);
        while(boardinfo[x][++j].getType()==board.getLastPiece().getType()||boardinfo[x][++j].getType()==null);
        if(j-i+1 < board.getGoal()){ return 0;}
        return 0;
    }

    private static double CheckRow(int x,int y, DefaultBoard board){
        int i,j;
        i=j=x;
        Piece[][] boardinfo = board.getBoard();
        while(boardinfo[--i][y].getType()==board.getLastPiece().getType()||boardinfo[--i][y].getType()==null);
        while(boardinfo[++j][y].getType()==board.getLastPiece().getType()||boardinfo[++j][y].getType()==null);
        if(j-i+1 < board.getGoal()){ return 0;}
        return 0;
    }

    private static double CheckDiagonalFromTopLeft(int x,int y, DefaultBoard board){
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        Piece[][] boardinfo = board.getBoard();
        while(boardinfo[--ix][++iy].getType()==board.getLastPiece().getType()||boardinfo[--ix][++iy].getType()==null);
        while(boardinfo[++jx][--jy].getType()==board.getLastPiece().getType()||boardinfo[++jx][--jy].getType()==null);
        return 0;
    }

    private static double CheckDiagonalFromTopRight(int x,int y, DefaultBoard board){
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        Piece[][] boardinfo = board.getBoard();
        while(boardinfo[--ix][--iy].getType()==board.getLastPiece().getType()||boardinfo[--ix][--iy].getType()==null);
        while(boardinfo[++jx][++jy].getType()==board.getLastPiece().getType()||boardinfo[++jx][++jy].getType()==null);
        return 0;
    }

    private static double heuristicScore(Piece.PieceType player, DefaultBoard board){
        if(player==null){
            throw new IllegalArgumentException("player must be X or O");
        }
        Piece current = board.getLastPiece();
        int x = current.getCoordinate().getX();
        int y = current.getCoordinate().getY();
        CheckCol(x,y,board);
        CheckRow(x,y,board);
        CheckDiagonalFromTopLeft(x,y,board);
        CheckDiagonalFromTopRight(x,y,board);
        return 0;

    }

}
