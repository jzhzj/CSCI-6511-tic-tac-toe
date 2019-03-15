package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Board;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

import java.util.HashSet;

abstract class AbstractBoard implements Board {
    Piece[][] board;
    int width;//n
    int goal;//m
    Piece.PieceType ourPieceType;
    Piece.PieceType opponentsPieceType;
    Piece lastPiece;
    Piece.PieceType playerTurn;
    Piece.PieceType winner;
    HashSet<Integer> availableCell;

    int moveCount;
    Boolean gameOver;

    AbstractBoard(int n,int m, Piece.PieceType ourPieceType) {
        this.width = n;
        this.goal = m;
//        this.board = new Piece[width][width];
        this.ourPieceType = ourPieceType;
        this.opponentsPieceType = ourPieceType == Piece.PieceType.O ? Piece.PieceType.X : Piece.PieceType.O;
//        this.playerTurn = ourPieceType;
//        this.gameOver = false;
//        this.winner = null;
//        this.availableCell = new HashSet<>();
        reset();
    }

    /**
     * reset the board
     */
    private void reset(){
        this.board = new Piece[width][width];
        this.playerTurn = ourPieceType;
        this.gameOver = false;
        this.moveCount = 0;
        this.winner = null;
        this.availableCell = new HashSet<>();
        this.availableCell.clear();
        for (int i = 0; i < width*width; i++) {
            this.availableCell.add(i);
        }
    }
}
