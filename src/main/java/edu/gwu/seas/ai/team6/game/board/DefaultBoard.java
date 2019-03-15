package edu.gwu.seas.ai.team6.game.board;

import com.sun.org.apache.xpath.internal.operations.Bool;
import edu.gwu.seas.ai.team6.game.board.interfaces.Board;
import edu.gwu.seas.ai.team6.game.board.interfaces.Coordinate;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

import java.util.HashSet;

/**
 * Default implementation of {@link Board}
 *
 * @author qijiuzhi
 * @date 2019-03-04
 */
public class DefaultBoard extends AbstractBoard {

    public DefaultBoard(int n,int m, Piece.PieceType ourPieceType) {
        super(n,m, ourPieceType);
    }

    /**
     * @see Board#moveAt(int, int, boolean)
     */
    @Override
    public void moveAt(int x, int y, boolean isOurMove) {
        moveAt(new DefaultCoordinate(x, y), isOurMove);
    }

    /**
     * @see Board#moveAt(Coordinate, boolean)
     */
    @Override
    public void moveAt(Coordinate coordinate, boolean isOurMove) {
        if(gameOver){
            throw new IllegalArgumentException("Game Over");
        }
        Piece.PieceType type = isOurMove ? ourPieceType : opponentsPieceType;

        int x = coordinate.getX();
        int y = coordinate.getY();
        if(board[x][y] == null){
            Piece piece = new DefaultPiece(coordinate, type);
            this.board[x][y] = piece;
            lastPiece = piece;
        }else {
            throw new IllegalArgumentException("cell occupied");
        }

        moveCount++;
        availableCell.remove(x+y*width);

        //The game is a draw
        if(moveCount == width*width){
            winner = null;
            gameOver = true;
        }

        //Check for a winner.
        CheckCol(x,y);
        CheckRow(x,y);
        CheckDiagonalFromTopLeft(x,y);
        CheckDiagonalFromTopRight(x,y);

        playerTurn = isOurMove ? opponentsPieceType : ourPieceType;
    }

    /**
     * @see Board#moveAt(int, boolean)
     */
    @Override
    public void moveAt(int index, boolean isOurMove){
        moveAt(index%width, index/width, isOurMove);
    }

    /**
     * @see Board#getLastPiece()
     */
    @Override
    public Piece getLastPiece() {
        return lastPiece;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    public Piece.PieceType getTurn(){ return playerTurn;}

    public boolean isGameOver(){
        return gameOver;
    }

    public Piece.PieceType getWinner(){
        return winner;
    }

    public HashSet<Integer> getAvailableCells(){
        return availableCell;
    }

    public int getWidth(){ return width;}
    public int getGoal(){ return goal;}

    public Piece[][] getBoard(){ return board;}


    private void CheckCol(int x,int y) {
        int i,j;
        i=j=y;
        while(board[x][--i].getType()==lastPiece.getType());
        while(board[x][++j].getType()==lastPiece.getType());
        if (j-i+1 == goal) {
            winner = playerTurn;
            gameOver = true;
        }
    }

    private void CheckRow(int x,int y){
        int i,j;
        i=j=x;
        while(board[--i][y].getType()==lastPiece.getType());
        while(board[++j][y].getType()==lastPiece.getType());
        if (j-i+1 == goal) {
            winner = playerTurn;
            gameOver = true;
        }
    }

    private void CheckDiagonalFromTopLeft(int x,int y){
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        while(board[--ix][++iy].getType()==lastPiece.getType());
        while(board[++jx][--jy].getType()==lastPiece.getType());
        if (jx-ix+1 == goal) {
            winner = playerTurn;
            gameOver = true;
        }
    }

    private void CheckDiagonalFromTopRight(int x,int y){
        int ix,jx,iy,jy;
        ix=jx=x;
        iy=jy=y;
        while(board[--ix][--iy].getType()==lastPiece.getType());
        while(board[++jx][++jy].getType()==lastPiece.getType());
        if (jx-ix+1 == goal) {
            winner = playerTurn;
            gameOver = true;
        }
    }

    public DefaultBoard copyBoard(){
        DefaultBoard board = new DefaultBoard(width,goal, ourPieceType);

        for(int i=0;i<width;i++){
            board.board[i] = this.board[i].clone();
        }

        board.width = this.width;
        board.goal = this.goal;
        board.ourPieceType = this.ourPieceType;
        board.opponentsPieceType = this.opponentsPieceType;
        board.lastPiece = this.lastPiece;
        board.playerTurn = this.playerTurn;
        board.winner = this.winner;
        board.availableCell = new HashSet<>();
        board.availableCell.addAll(this.availableCell);
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;

        return board;
    }

}
