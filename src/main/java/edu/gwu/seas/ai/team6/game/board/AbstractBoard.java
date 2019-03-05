package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Board;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

abstract class AbstractBoard implements Board {
    Piece[][] board;
    AbstractPiece.PieceType ourPieceType;
    AbstractPiece.PieceType opponentsPieceType;
    Piece lastPiece;

    AbstractBoard(int n, AbstractPiece.PieceType ourPieceType) {
        this.board = new Piece[n][n];
        this.ourPieceType = ourPieceType;
        this.opponentsPieceType = ourPieceType == AbstractPiece.PieceType.O ? AbstractPiece.PieceType.X : AbstractPiece.PieceType.O;
    }
}
