package edu.gwu.seas.ai.team6.game.board;

import edu.gwu.seas.ai.team6.game.board.interfaces.Board;
import edu.gwu.seas.ai.team6.game.board.interfaces.Piece;

abstract class AbstractBoard implements Board {
    Piece[][] board;
    Piece.PieceType ourPieceType;
    Piece.PieceType opponentsPieceType;
    Piece lastPiece;

    AbstractBoard(int n, Piece.PieceType ourPieceType) {
        this.board = new Piece[n][n];
        this.ourPieceType = ourPieceType;
        this.opponentsPieceType = ourPieceType == Piece.PieceType.O ? Piece.PieceType.X : Piece.PieceType.O;
    }
}
