package edu.gwu.seas.ai.team6.game.board.interfaces;

import edu.gwu.seas.ai.team6.game.board.AbstractPiece;

public interface Piece {
    /**
     * get the coordinate of the piece
     *
     * @return the coordinate of the piece
     */
    Coordinate getCoordinate();

    /**
     * get the type of the piece
     *
     * @return the type of the piece
     */
    AbstractPiece.PieceType getType();
}
